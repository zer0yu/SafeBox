package com.mxn.soul.flowingdrawer.main;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer.DES_TooLs.discrypt;
import com.mxn.soul.flowingdrawer.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Decoder.BASE64Encoder;

public class Discrypt extends Activity implements OnClickListener {
	private static File selectedfile=PhoneFile.getSelectedfie();
	private ArrayAdapter<String> adapter;
	private String lastPath="/sdcard/EncyptFile/"+loginActivity.userNameValue +"/";
	private ListView lv_skydrive;
	private static File selectedfie,copy_file;
	public static File getSelectedfie() {
		return selectedfie;
	}
	public static void setSelectedfie(File selectedfie) {
		Discrypt.selectedfie = selectedfie;
	}
	private DatabaseHelper databasehelper;
	private SQLiteDatabase database;
	private Button btn_return;
	private List<String> filename,drivefilepath;
	private List<Integer> keynum;
	private List<String> name;
	private List<String> path;
	private File file;
	private String tablename1="file_list";
	private String databasename=loginActivity.userNameValue;//文件名
	private  String file_name="";
	//手机文件路径
	private String file_path="";
	//手机密钥 路径
	private String Key_Phonepath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Key/"+loginActivity.userNameValue+"/"+selectedfile.getName()+"/";
	//记录密钥被分的份数
	private Integer n;
	private discrypt dis;
	//密文内容
	private String filecontent;
	//密钥内容
	private String[] keycontent=new String[5];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle("文件目录");
		setContentView(R.layout.discrypt);
		super.onCreate(savedInstanceState);
		initview();
		//initdata1(lastPath);
		initdata(lastPath);
	}
	//private void initdata1 (String rootPath){

	/*private void initdata1(String rootPath) {
		file = new File(rootPath);
		File[] files = file.listFiles();
		selectedfie = file;
		name = new ArrayList<String>();
		path = new ArrayList<String>();
		lastPath = file.getParent();
		if (files != null) {
			for (File currentfile : files) {
				name.add(currentfile.getName());
				path.add(currentfile.getAbsolutePath());
			}
		}
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
		lv_skydrive.setAdapter(adapter);
	}*/
	private void initdata(String rootPath){
		/*String SQL1="CREATE TABLE IF NOT EXISTS "+tablename1+"(ID INTEGER,File_name VARCHAR,"
				+ "File_path VARCHAR,n INTEGER);";
		database.execSQL(SQL1);
		Cursor cursor1=database.query(tablename1, new String[]{"File_name","File_path","n"}, null, null, null, null, null);
		if(cursor1.moveToFirst()) {
			int count = cursor1.getCount();
			for (int i = 0; i < count; i++) {
				filename.add(cursor1.getString(0));
				drivefilepath.add(cursor1.getString(1));
				keynum.add((int) cursor1.getShort(2));
				cursor1.moveToNext();
			}
			cursor1.close();
		}
		else {
			Toast.makeText(this, tablename1 + " don't exist " + databasename, Toast.LENGTH_SHORT).show();
		}*/
		file = new File(rootPath);
		File[] files = file.listFiles();
		selectedfie = file;
		name = new ArrayList<String>();
		path = new ArrayList<String>();
		lastPath = file.getParent();
		if (files != null) {
			for (File currentfile : files) {
				name.add(currentfile.getName());
				path.add(currentfile.getAbsolutePath());
			}
		}
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
		lv_skydrive.setAdapter(adapter);
		//adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,filename);
		//lv_skydrive.setAdapter(adapter);
		//lv_skydrive.setAdapter(adapter);
		lv_skydrive.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Toast.makeText(Discrypt.this, "Start Discryption", Toast.LENGTH_SHORT).show();
				file_path=path.get(position);
				file_name=name.get(position);
				n=5;
				file_path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/EncyptFile/"+loginActivity.userNameValue +"/"+selectedfile.getName()+"/";
				File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/EncyptFile/"+loginActivity.userNameValue +"/");
				if(!file.exists()){
					file.mkdirs();
				}
				new AsyncTask<Void, String, Boolean>() {
					@Override
					protected Boolean doInBackground(Void... params) {
						//根据选择的加密方式不同，对文件进行不同的操作
						//tools.download_file(file_path, file_drivepath, oauth_token, oauth_token_secret);
						publishProgress(file_name);
						File ec_file2=new File(file_path+"/"+"ec_file.mac");
						filecontent=readfile(ec_file2);
						Random ran2 = new Random();
						int[] key2 = new int[5];
						int k = 0;
						int m = 0;
						for (int i = 0; i < 5; i++) {
							k = (ran2.nextInt(10) + 1);
							for (m = 0; m < i; m++) {
								if (k == key2[m]) {
									i--;
									break;
								}
							}
							if (m == i) {
								key2[i] = k;
							}
						}
						for (int i = 0; i < 5; i++) {
							//tools.download_file(Key_Phonepath+"-s"+key2[i]+".txt", Key_Drivepath+name+"/"+"-s"+key2[i]+".txt",oauth_token	, oauth_token_secret);
							publishProgress("key" + i);
							File keyfile = new File(Key_Phonepath + "-s" + key2[i] + ".txt");
							keycontent[i] = readkey(keyfile);
							if (keycontent[i] == null || keycontent[i] == "") {
								i--;
							}
							keyfile.delete();
						}
						dis = new discrypt(filecontent, 5, file_path, "-s" + key2[0], "-s" + key2[1], "-s" + key2[2], "-s" + key2[3], "-s" + key2[4], keycontent[0], keycontent[1], keycontent[2], keycontent[3], keycontent[4]);
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
						Toast.makeText(Discrypt.this, "Transform Suceed", Toast.LENGTH_SHORT).show();
						super.onPostExecute(result);
					}

					//下载完成之后进行的操作
					@Override
					protected void onProgressUpdate(String... values) {
						String downname = values[0];
						Toast.makeText(Discrypt.this, downname + "Discription finished", Toast.LENGTH_SHORT).show();
						super.onProgressUpdate(values);
					}
				}.execute();

			}
		});
	}
	private void initview() {
		filename=new ArrayList<String>();
		drivefilepath=new ArrayList<String>();
		keynum=new ArrayList<Integer>();
		btn_return=(Button) findViewById(R.id.btn_return);
		lv_skydrive=(ListView) findViewById(R.id.lv_skydrive);
		databasehelper=new DatabaseHelper(this, databasename, null, 1);
		database=databasehelper.getReadableDatabase();
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filename);
		btn_return.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_return:
			Intent intent=new Intent(Discrypt.this, MainActivity.class);
			intent.putExtra("Username", loginActivity.userNameValue);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	//读取密钥文件内容的方法
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
