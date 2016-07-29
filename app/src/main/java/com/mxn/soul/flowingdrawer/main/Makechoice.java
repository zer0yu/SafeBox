package com.mxn.soul.flowingdrawer.main;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer.DES_TooLs.Encrypt;
import com.mxn.soul.flowingdrawer.File_TooLs.File_operate;
import com.mxn.soul.flowingdrawer.R;
import com.mxn.soul.flowingdrawer.SQLcloud.Sqltools_key;
import com.mxn.soul.flowingdrawer.SQLite.DBOpenHandler;
import com.mxn.soul.flowingdrawer.SQLite.SQLiteDAOImpl;
import com.mxn.soul.flowingdrawer.data.MainApplication;
import com.mxn.soul.flowingdrawer.data.SecretKey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import Decoder.BASE64Encoder;

public class Makechoice extends Activity implements OnClickListener {
	private static File selectedfile=PhoneFile.getSelectedfie();
	private Spinner sp_encryptchoice;
	private CheckedTextView ctv_deletefile;
	private Button btn_upload;
	private ArrayAdapter<String> adapter;
	private String content=null;
	private String key[]=null;
	private String key_temp=null;
	private DatabaseHelper databasehelper;
	private SQLiteDatabase database;
	private Sqltools_key sqltools_key;
	private SQLiteDAOImpl sqlite;
	private Handler handler;
	//数据库名称
	private static String database_name=loginActivity.userNameValue;//调用自己的用户名
	private static String table_name2="file_list";
	private String SQL2="CREATE TABLE IF NOT EXISTS "+table_name2+"(ID INTEGER,File_name VARCHAR,"
			+ "File_path VARCHAR,n INTEGER);";
	//手机密钥路径
	public static String Key_Phonepath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Key/"+loginActivity.userNameValue +"/"+selectedfile.getName()+"/";
	//手机加密文件路径
	private String File_encryptpath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/EncyptFile/"+loginActivity.userNameValue +"/"+selectedfile.getName()+"/";
	private String key_name="-s";
	//加密文件用到的工具类
	private Encrypt enc1;//,enc2;
	//要上传的文件
	private File ecFile;
	private File key_file;
	//建立encoder对象	
	private BASE64Encoder encoder;
	/*
	* @param context
	*/
	private Runnable initcloudsql = new Runnable() {
		@Override
		public void run() {
			sqltools_key = new Sqltools_key();
			SecretKey user = new SecretKey(loginActivity.userNameValue,selectedfile.getName(),key[0],key[1],key[2],key[3],key[4]);
			sqltools_key.insert(user);

			sqlite = new SQLiteDAOImpl(MainApplication.getContext());
			SecretKey locate = new SecretKey(loginActivity.userNameValue,selectedfile.getName(),key[5],key[6],key[7],key[8],key[9]);
			sqlite.save(locate);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.makechoice);
		super.onCreate(savedInstanceState);
		setTitle("上传设置");
		initview();
		initdata();
	}
	private void initdata() {
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,0 );
		sp_encryptchoice.setAdapter(adapter);
		ctv_deletefile.setChecked(false);
		ctv_deletefile.setOnClickListener(this);
		btn_upload.setOnClickListener(this);
	}
	private void initview() {
		//从PhoneFile里面获取选择的文件
		selectedfile=PhoneFile.getSelectedfie();
		sp_encryptchoice=(Spinner) findViewById(R.id.sp_encryptchoice);
		ctv_deletefile=(CheckedTextView) findViewById(R.id.ctv_deletefile);
		btn_upload=(Button) findViewById(R.id.btn_upload);
		databasehelper=new DatabaseHelper(Makechoice.this, database_name, null, 1);
		database=databasehelper.getWritableDatabase();
		encoder=new BASE64Encoder();
	}
	public void Create() {
		DBOpenHandler dbHandler = new DBOpenHandler(MainApplication.getContext(), "dbtest.db", null, 1);// 创建数据库文件
		dbHandler.getWritableDatabase();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ctv_deletefile:
			if(ctv_deletefile.isChecked()){
				ctv_deletefile.setChecked(false);
			}else{
				ctv_deletefile.setChecked(true);
			}
			break;
		case R.id.btn_upload:
			/**
			 * 接下来应该是判断该帐号拥有的网盘数量能否满足选取的加密方式；
			 * 然后就是把文件加密；
			 * 上传；
			 * */
			new AsyncTask<Void, String, Boolean>() {

				@Override
				protected Boolean doInBackground(Void... params) {
					try {
						File keyfile=new File(Key_Phonepath);
						if(!keyfile.exists()){
							keyfile.mkdirs();
						}
						//读取选取的文件内容
						FileInputStream is=new FileInputStream(selectedfile);
						byte[] bytefile=new byte[is.available()];
						is.read(bytefile);
						is.close();
						content=encoder.encode(bytefile);
						File file=new File(File_encryptpath);
						if(!file.exists()){
							file.mkdirs();
						}
						//存放加密内容的文件
						ecFile=new File(file, "ec_file.mac");
						FileOutputStream os=new FileOutputStream(ecFile);
						os.write(bytefile, 0, bytefile.length);
						os.flush();
						os.close();
						//加密接口
						//根据加密等级的不同选取不同的加密方案

						enc1=new Encrypt(5,content, ecFile.getAbsolutePath());
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
						for(int i=1;i<11;i++) {
							key_temp = Key_Phonepath + key_name + i + ".txt";
							key_file = new File(key_temp);
								key[i] = File_operate.txt2String(key_file);
							if (i == 10) {
								publishProgress("密钥拆分完成");
							}
						}

					handler.post(initcloudsql);


					return true;
				}

				@Override
				protected void onPostExecute(Boolean result) {
					if(result){
						//判断是否需要删除原文件
						if(ctv_deletefile.isChecked()){
							selectedfile.delete();
						}
						database.execSQL(SQL2);
						Cursor cursor1=database.query(table_name2, null, null, null, null, null, null);
						int count;
						database.delete(table_name2, "File_name=?", new String[]{selectedfile.getName()});
						if(cursor1.moveToFirst()){
							count=cursor1.getCount();
						}else {
							count=0;
						}
						ContentValues file=new ContentValues();
						file.put("ID", count);
						file.put("File_name", selectedfile.getName());
						file.put("File_path",selectedfile.getPath());
						//file.put("File_path",File_Drivepath+selectedfile.getName());
						file.put("n", 5);
						database.insert(table_name2, null, file);
						Toast.makeText(Makechoice.this, "文件保存完成", Toast.LENGTH_SHORT).show();
					}/*else {
						Toast.makeText(Makechoice.this, "请输入网盘信息", Toast.LENGTH_SHORT).show();
					}*/
					super.onPostExecute(result);
				}

				@Override
				protected void onProgressUpdate(String... values) {
					Toast.makeText(Makechoice.this, values[0], Toast.LENGTH_SHORT).show();
					super.onProgressUpdate(values);
				}

			}.execute();
			//Intent intent=new Intent(Makechoice.this, PhoneFile.class);
			//startActivity(intent);
			break;
			default:
				break;
		}
	}
}
