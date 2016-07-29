package com.mxn.soul.flowingdrawer.main;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;

import com.mxn.soul.flowingdrawer.DES_TooLs.Encrypt;
import com.mxn.soul.flowingdrawer.DES_TooLs.discrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import Decoder.BASE64Encoder;


public class makechoice1 {
    private File selectedfile;
    private List<String> filename,drivefilepath;
    private List<Integer> keynum;
    //private Spinner sp_encryptchoice;
    private CheckedTextView ctv_deletefile;
    private Button btn_upload;
    private ArrayAdapter<String> adapter;
    private String content = null;
    private DatabaseHelper databasehelper;
    private SQLiteDatabase database;
    //数据库名称
    private static String database_name = loginActivity.userNameValue;//调用自己的用户名
    private static String table_name2 = "file_list";
    private String SQL2 = "CREATE TABLE IF NOT EXISTS " + table_name2 + "(ID INTEGER,File_name VARCHAR,"
            + "File_path VARCHAR);";
    //手机密钥路径
    public static String Key_Phonepath;
    //手机加密文件路径
    private String File_encryptpath;
    private String key_name = "-s";
    //加密文件用到的工具类
    private Encrypt enc1;//,enc2;
    //要上传的文件
    private File ecFile;
    private File key_file;
    private Integer n;
    private discrypt dis;
    //密文内容
    private String filecontent;
    //密钥内容
    private String[] keycontent=new String[5];
    //建立encoder对象
    private BASE64Encoder encoder;
    private  String file_name="";
    //手机文件路径
    private String file_path="";

    protected File encry (File file) {
        selectedfile = file;
        Key_Phonepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Key/" + loginActivity.userNameValue + file.getName() + "/";
        File_encryptpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/encryptfile/" + loginActivity.userNameValue + file.getName() + "/";

        new AsyncTask<Void, String, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    File keyfile = new File(Key_Phonepath);
                    if (!keyfile.exists()) {
                        keyfile.mkdirs();
                    }
                    //读取选取的文件内容
                    FileInputStream is = new FileInputStream(selectedfile);
                    byte[] bytefile = new byte[is.available()];
                    is.read(bytefile);
                    is.close();
                    encoder = new BASE64Encoder();
                    content = encoder.encode(bytefile);
                    File file = new File(File_encryptpath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    //存放加密内容的文件
                    ecFile = new File(file, "ec_file.mac");
                    FileOutputStream os = new FileOutputStream(ecFile);
                    os.write(bytefile, 0, bytefile.length);
                    os.flush();
                    os.close();
                    //加密接口
                    //根据加密等级的不同选取不同的加密方案

                    enc1 = new com.mxn.soul.flowingdrawer.DES_TooLs.Encrypt(5, content, ecFile.getAbsolutePath());
                    enc1.file_encrypt();

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                    //得到所选文件的内容，接下来，就是根据选择的操作对文件进行不同的处理
                    //上传密钥文件
                    for (int i = 1; i < 11; i++) {
                        //tools.upload(url,Key_Phonepath+key_name+i+".txt",true, Key_Drivepath+key_name+i+".txt", oauth_token, oauth_token_secret);
                        key_file = new File(Key_Phonepath + key_name + i + ".txt");
                        //File tar = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+".加密文件存储");
                        //if (!tar.exists()) tar.mkdir();
                        //key_file.delete();
                        if (i == 10) {
                            publishProgress("密钥拆分完成");
                        }
                    }

                    return true;
                }

               /*@Override
                protected void onPostExecute (Boolean result){
                    databasehelper=new DatabaseHelper(makechoice1.this, database_name, null, 1);
                    database=databasehelper.getWritableDatabase();
                    database=databasehelper.getWritableDatabase();
                        database.execSQL(SQL2);
                        Cursor cursor1 = database.query(table_name2, null, null, null, null, null, null);
                        int count;
                        database.delete(table_name2, "File_name=?", new String[]{selectedfile.getName()});
                        if (cursor1.moveToFirst()) {
                            count = cursor1.getCount();
                        } else {
                            count = 0;
                        }
                        ContentValues file = new ContentValues();
                        file.put("ID", count);
                        file.put("File_name", selectedfile.getName());
                        file.put("File_path", selectedfile.getAbsolutePath());
                        //file.put("File_path",File_Drivepath+selectedfile.getName());
                        file.put("n", 5);
                        database.insert(table_name2, null, file);
                        //Toast.makeText(Makechoice.this, "文件保存完成", Toast.LENGTH_SHORT).show();
                    /*else {
						Toast.makeText(Makechoice.this, "请输入网盘信息", Toast.LENGTH_SHORT).show();
					}*/
                    //super.onPostExecute(result);
                //}

                @Override
                protected void onProgressUpdate (String...values){
                    //Toast.makeText(, values[0], Toast.LENGTH_SHORT).show();
                    super.onProgressUpdate(values);
                }

            }.execute();
            //Intent intent=new Intent(Makechoice.this, PhoneFile.class);
            //startActivity(intent);
        return ecFile;
        };
    protected File discry (File file){

        file_name=file.getName();
        file_path= Environment.getExternalStorageDirectory()+"/encryptfile/"+loginActivity.userNameValue+"/"+file_name;
        new AsyncTask<Void,String, Boolean>(){

            @Override
            protected Boolean doInBackground(Void... params) {
                //根据选择的加密方式不同，对文件进行不同的操作
                //tools.download_file(file_path, file_drivepath, oauth_token, oauth_token_secret);
                publishProgress(file_name);
                File ec_file2=new File(file_path);
                filecontent=readfile(ec_file2);
                Random ran2=new Random();
                int []key2=new int[5];
                int k=0;
                int m=0;
                for(int i=0;i<5;i++){
                    k=(ran2.nextInt(10)+1);
                    for(m=0;m<i;m++){
                        if(k==key2[m]){
                            i--;
                            break;
                        }
                    }
                    if(m==i){
                        key2[i]=k;
                    }
                }
                for(int i=0;i<5;i++){
                    //tools.download_file(Key_Phonepath+"-s"+key2[i]+".txt", Key_Drivepath+name+"/"+"-s"+key2[i]+".txt",oauth_token	, oauth_token_secret);
                    publishProgress("key"+i);
                    File keyfile=new File(Key_Phonepath+"-s"+key2[i]+".txt");
                    keycontent[i]=readkey(keyfile);
                    if(keycontent[i]==null||keycontent[i]==""){
                        i--;
                    }
                    keyfile.delete();
                }
                dis=new discrypt(filecontent, 5, file_path, "-s"+key2[0], "-s"+key2[1], "-s"+key2[2], "-s"+key2[3], "-s"+key2[4], keycontent[0], keycontent[1], keycontent[2], keycontent[3], keycontent[4]);
                dis.Discrypt();
                //tools.download_file(file_path, file_drivepath, oauth_token, oauth_token_secret);
                publishProgress(file_name);
                return true;
            }
            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(Boolean result) {
                //Toast.makeText(Discrypt.this, "传输成功", Toast.LENGTH_SHORT).show();
                super.onPostExecute(result);
            }
            //下载完成之后进行的操作
            @Override
            protected void onProgressUpdate(String... values) {
                String downname=values[0];
                //Toast.makeText(Discrypt.this, downname + "下载完成", Toast.LENGTH_SHORT).show();
                super.onProgressUpdate(values);
            }
        }.execute();
        return dis.Discrypt();
    }
    private String readkey(File keyfile) {
        String key_content="";
        try {
            FileReader reader=new FileReader(keyfile);
            BufferedReader br=new BufferedReader(reader);
            String line="";
            while((line=br.readLine()) != null){
                key_content+=line;
            }
            br.close();
            return key_content;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    //读取文件内容的方法
    private String readfile(File file){
        String file_content;
        byte[] file_byte;
        try {
            FileInputStream is=new FileInputStream(file);
            file_byte=new byte[is.available()];
            is.read(file_byte, 0, file_byte.length);
            BASE64Encoder encoder=new BASE64Encoder();
            file_content=encoder.encode(file_byte);
            is.close();
            return file_content;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }






}


