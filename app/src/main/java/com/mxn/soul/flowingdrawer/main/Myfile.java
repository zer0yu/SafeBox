package com.mxn.soul.flowingdrawer.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.mxn.soul.flowingdrawer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2016/5/22.
 */
public class Myfile extends Activity implements View.OnClickListener {
    private TextView tv_content;
    private File file;
    private static String database_name = loginActivity.userNameValue;
    private static File selectedfie,copy_file;
    public static File getSelectedfie() {
        return selectedfie;
    }
    public static void setSelectedfie(File selectedfie) {
        Myfile.selectedfie = selectedfie;
    }
    private ListView lv_mnue;
    private List<Integer> keynum;
    private Integer n;
    private List list;
    private List<String> filename,filepath;
    private List<String> path;
    private DatabaseHelper databasehelper;
    private SQLiteDatabase database;
    private static String table_name2 = "file_list";
    private String SQL2 = "CREATE TABLE IF NOT EXISTS "+table_name2+"(ID INTEGER,File_name VARCHAR,"
            + "File_path VARCHAR,n INTEGER);";
    private Button btn_creat;
    private ArrayAdapter<String> adapter;
    private static final int REQUEST_CHOOSER = 1234;
    private String file_path="";
    private String file_name="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfile);
        btn_creat = (Button) findViewById(R.id.btn_creat);
        lv_mnue = (ListView) findViewById(R.id.lv_phonefile);
        filename = new ArrayList<String>();
        filepath = new ArrayList<String>();
        keynum = new ArrayList<Integer>();
        list = new ArrayList();

        databasehelper = new DatabaseHelper(this, database_name, null, 1);

        btn_creat.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent getContentIntent = FileUtils.createGetContentIntent();
                Intent intent = Intent.createChooser(getContentIntent, "Select a file");
                startActivityForResult(intent, REQUEST_CHOOSER);
            }
        });
        lv_mnue.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                readSqlite();
                file_name =filename.get(position);
                file_path =filepath.get(position);
                file_path= Environment.getExternalStorageDirectory()+"/encryptfile/"+loginActivity.userNameValue+"/"+file_name;
                final File file1=new File (file_path);
                n=5;
                /*if (selectedfie.isDirectory()) {
                    initdata(path.get(position));
                } else {*/
                new AlertDialog.Builder(Myfile.this)
                        //.setIcon(getResources().getDrawable(R.drawable.ic_launcher))
                        .setMessage("是否解密这个文件")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //设置对文件的读写操作权限
                                /*selectedfie.setExecutable(true, false);
                                selectedfie.setReadable(true, false);
                                selectedfie.setWritable(true, false);*/
                                //Intent intent = new Intent(Myfile.this, Makechoice.class);
                                //startActivity(intent);
                                makechoice1 s2 = new makechoice1();
                                File discry1 = s2.discry(file1);
                                Toast.makeText(Myfile.this, "文件解密完成", Toast.LENGTH_SHORT).show();
                                chooseFile(discry1);
                            }
                        })

                        .setNegativeButton("否", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        return;
                                    }
                                }

                        ).show();
            }

        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHOOSER:
                if (resultCode == RESULT_OK) {

                    final Uri uri = data.getData();

                    // Get the File path from the Uri
                    String path = FileUtils.getPath(this, uri);

                    // Alternatively, use FileUtils.getFile(Context, Uri)
                    if (path != null && FileUtils.isLocal(path)) {
                        initdata(path);
                        selectedfie = new File(path);
                        makechoice1 s1 =new makechoice1();
                        s1.encry(selectedfie);
                        setSqlite(selectedfie);
                        Toast.makeText(Myfile.this, "文件加密完成", Toast.LENGTH_SHORT).show();

                        // file = new File(path);
                    }
                }
                break;
        }


    }
protected void readSqlite () {
    database = databasehelper.getReadableDatabase();
    database.execSQL(SQL2);
    Cursor cursor1 = database.query(table_name2, new String[]{"File_name", "File_path"}, null, null, null, null, null);
    if (cursor1.moveToFirst()) {
        int count = cursor1.getCount();
        for (int i = 0; i < count; i++) {
            filename.add(cursor1.getString(0));
            filepath.add(cursor1.getString(1));
            keynum.add((int) cursor1.getShort(2));
            cursor1.moveToNext();
        }
        cursor1.close();
    } else {
        Toast.makeText(this, table_name2 + "不存在" + database_name, Toast.LENGTH_SHORT).show();
    }
}


        protected void setSqlite (File mfile){
            File selectedfile=mfile;
            database = databasehelper.getWritableDatabase();
            //database = databasehelper.getWritableDatabase();
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
            file.put("ID",count);
            file.put("File_name", selectedfile.getName());
            file.put("File_path",selectedfile.getName());
            file.put("n", 5);
            database.insert(table_name2, null, file);
            //Toast.makeText(Makechoice.this, "文件保存完成", Toast.LENGTH_SHORT).show();
                    /*else {
						Toast.makeText(Makechoice.this, "请输入网盘信息", Toast.LENGTH_SHORT).show();
					}*/
            //super.onPostExecute(result);
        }

    public  void firstinitdata (String Path)
    {
        File file=new File(Path);
        filename=new ArrayList<String>();
        filename.add(file.getName());
        //adapter =(ArrayAdapter) getListAdapter();
        adapter= new ArrayAdapter<String>(Myfile.this,android.R.layout.simple_list_item_1,filename);
        lv_mnue.setAdapter(adapter);
        //adapter.notifyDataSetChanged();


    }
    private void initdata (String Path){
        File file=new File(Path);
        //File[] files=file.listFiles();
        filename=new ArrayList<String>();
        //path=new ArrayList<String>();
        //lastPath=file.getParent();
        //tv_content.setText(file.getAbsolutePath());

        filename.add(file.getName());
        adapter.notifyDataSetChanged();

    }






    private boolean checkEndsWithInStringArray(String checkItsEnd,
                                               String[] fileEndings){
        for(String aEnd : fileEndings){
            if(checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }
    private void chooseFile ( File currentPath)
    {
        if(currentPath!=null&&currentPath.isFile())
        {
            String fileName = currentPath.toString();
            Intent intent;
            if(checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingImage))){
                intent = openfile.getImageFileIntent(currentPath);
                startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingWebText))){
                intent = openfile.getHtmlFileIntent(currentPath);
                startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingPackage))){
                intent = openfile.getApkFileIntent(currentPath);
                startActivity(intent);

            }else if(checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingAudio))){
                intent = openfile.getAudioFileIntent(currentPath);
                startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingVideo))){
                intent = openfile.getVideoFileIntent(currentPath);
                startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingText))){
                intent = openfile.getTextFileIntent(currentPath);
                startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingPdf))){
                intent = openfile.getPdfFileIntent(currentPath);
                startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingWord))){
                intent = openfile.getWordFileIntent(currentPath);
                startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingExcel))){
                intent = openfile.getExcelFileIntent(currentPath);
                startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingPPT))){
                intent = openfile.getPPTFileIntent(currentPath);
                startActivity(intent);
            }else
            {
                Toast.makeText(Myfile.this, "无法打开，请安装相应的软件！", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Toast.makeText(Myfile.this, "对不起，这不是文件！", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onClick(View v) {

    }
}




