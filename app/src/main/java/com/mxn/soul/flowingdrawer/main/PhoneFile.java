package com.mxn.soul.flowingdrawer.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mxn.soul.flowingdrawer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhoneFile extends Activity implements android.view.View.OnClickListener{
	private File file;
	private String lastPath="/sdcard/";
	private ListView lv_mnue;
	private TextView tv_content;
	private List<String> name;
	private List<String> path;
	private Button btn_creat,btn_return,btn_dis;
	private ArrayAdapter<String> adapter;
	private String dictoryname=null;
	private static File selectedfie,copy_file;
	private static byte[] copy_content;
	public static File getSelectedfie() {
		return selectedfie;
	}
	private static final int REQUEST_CHOOSER = 1234;
	public static void setSelectedfie(File selectedfie) {
		PhoneFile.selectedfie = selectedfie;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phonefile);
		setTitle("手机文件目录");
		iniview();
		initdata(lastPath);
		/*lv_mnue.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				//selectedfie=new File(path.get(position));
				new AlertDialog.Builder(PhoneFile.this)
				.setTitle("文件夹选项")
				.setItems(new String[]{"复制","剪切","删除"}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							try {
								FileInputStream is=new FileInputStream(selectedfie);
								copy_content=new byte[is.available()];
								is.close();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case 1:

							break;
						case 2:
							selectedfie.delete();
							initdata(selectedfie.getParent());
							break;
						default:
							break;
						}
					}
				})
				.show();
				return false;
			}
		});*/
		lv_mnue.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				selectedfie = new File(path.get(position));
				if (selectedfie.isDirectory()) {
					initdata(path.get(position));
				} else {
					new AlertDialog.Builder(PhoneFile.this)
							//.setIcon(getResources().getDrawable(R.drawable.ic_launcher))
							.setMessage("是否选择这个文件")
							.setPositiveButton("是", new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									//设置对文件的读写操作权限
									selectedfie.setExecutable(true, false);
									selectedfie.setReadable(true, false);
									selectedfie.setWritable(true, false);
									Intent intent = new Intent(PhoneFile.this, Makechoice.class);
									startActivity(intent);
								}
							})
							.setNegativeButton("否", new OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									return;
								}
							}).show();
				}
			}

		});



	}

	private void iniview(){
		lv_mnue=(ListView) findViewById(R.id.lv_phonefile);
		tv_content=(TextView) findViewById(R.id.tv_content);
		btn_creat=(Button) findViewById(R.id.btn_creat);
		btn_return=(Button) findViewById(R.id.btn_return);
		btn_dis =(Button) findViewById(R.id.btn_dis);
		btn_creat.setOnClickListener(this);
		btn_return.setOnClickListener(this);
		btn_dis.setOnClickListener(this);
	}
	private void initdata (String rootPath){
		file=new File(rootPath);
		File[] files=file.listFiles();
		selectedfie=file;
		name=new ArrayList<String>();
		path=new ArrayList<String>();
		lastPath=file.getParent();
		tv_content.setText(file.getAbsolutePath());
		if(files!=null){
			for (File currentfile : files) {
				name.add(currentfile.getName());
				path.add(currentfile.getAbsolutePath());
			}
		}
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,name);
		lv_mnue.setAdapter(adapter);
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_creat:
			//Intent getContentIntent = FileUtils.createGetContentIntent();
			//Intent intent = Intent.createChooser(getContentIntent, "Select a file");
			//startActivityForResult(intent, REQUEST_CHOOSER);
			final EditText et_input=new EditText(PhoneFile.this);
			new AlertDialog.Builder(PhoneFile.this, AlertDialog.THEME_HOLO_DARK)
			.setTitle("输入文件夹名称")//.setIcon(getResources()
					//.getDrawable(R.drawable.image10))
			.setView(et_input)//et_input
			.setNegativeButton("取消", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			})
			.setPositiveButton("确定", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dictoryname=et_input.getText().toString().trim();
					File file1=new File(selectedfie,dictoryname);
					file1.mkdir();
					/*fileStorage fs = new fileStorage();
					File tar = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+".加密文件存储");
					if (!tar.exists()) tar.mkdir();
					fs.CopySdcardFile(file.getPath(), tar.getPath() + "/" + et_input.toString());
					String newpath =tar.getAbsolutePath() + "/" + et_input.toString();*/
					initdata(file1.getAbsolutePath());
					//selectedfie.delete();
				}
			}).show();
			break;
			case R.id.btn_dis:
				Intent i=new Intent(PhoneFile.this,Discrypt.class);
				startActivity(i);
				break;
		case R.id.btn_return:
			if(selectedfie.getAbsolutePath().equals("/")){
				Intent intent1=new Intent(PhoneFile.this,MainActivity.class);
				intent1.putExtra("Username", loginActivity.userNameValue);
				startActivity(intent1);
			}else{
				initdata(selectedfie.getParentFile().getAbsolutePath());
			}
			break;
		default:
			break;
		}
	}
	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_CHOOSER:
				if (resultCode == RESULT_OK) {

					final Uri uri = data.getData();

					// Get the File path from the Uri
					String path = FileUtils.getPath(this, uri);

					// Alternatively, use FileUtils.getFile(Context, Uri)
					if (path != null && FileUtils.isLocal(path)) {

						file = new File(path);
						/*System.out.println("test_file_path:"+path);
						fileStorage fs = new fileStorage();
						File tar = new File("/storage/emulated/0/.加密文件存储/");
						if (!tar.exists()) tar.mkdir();
						fs.CopySdcardFile(file.getPath(), tar.getPath() + "/" + file.getName());

						int a = fs.CopySdcardFile(file.getPath(), tar.getPath() + "/" + file.getName());
						if (a == 0) System.out.println("test_success");
						else System.out.println("test_fail");
						file.delete();*//*


					}
				}
				break;
		}

	}*/





}
