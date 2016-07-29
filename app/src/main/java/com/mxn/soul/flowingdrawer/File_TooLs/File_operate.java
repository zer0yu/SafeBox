package com.mxn.soul.flowingdrawer.File_TooLs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * 密钥文件内容读取
 * 文件名读取并剔除扩展名
 * Created by ZEROYU on 5/24/2016.
 */
public class File_operate {
    public static String getFileName(String fName){//fname是文件路径,调用此函数即可获取不带扩展名的文件名
        File tempFile =new File( fName.trim());
        String fileName = tempFile.getName();
        String file = getFileNameNoEx(fileName);
        return file;
    }

    public static String getFileNameNoEx(String filename) {//去除扩展名
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static String txt2String(File file){
        String result = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result = s;
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
