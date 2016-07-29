package com.mxn.soul.flowingdrawer.SQLcloud;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by sunnys on 2015/11/10.
 */
public class Client {
    public static String Postmethod(String url,String json){
        DefaultHttpClient defaultHttpClient;
        HttpPost post;
        HttpResponse response;
        String str_response ="";//返回的内容
        try {
            defaultHttpClient = new DefaultHttpClient();
            post = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(json,"UTF-8");
            stringEntity.setContentType("application/json;charset=gbk");
            post.setEntity(stringEntity);
            post.setHeader("User-Agent", "Mozilla/31.0 (compatible; MSIE 10.0; Windows NT; DigExt)");
            response = defaultHttpClient.execute(post);
            if(response.getStatusLine().getStatusCode() != 200){
                throw new RuntimeException("error code :"+response.getStatusLine().getStatusCode());
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = bufferedReader.readLine();
            while(line != null){
                str_response += line;
                line = bufferedReader.readLine();
            }
            defaultHttpClient.getConnectionManager().shutdown();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            return null;
        }
        return str_response;
    }
}
