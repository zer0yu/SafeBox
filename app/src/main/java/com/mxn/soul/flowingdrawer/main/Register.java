package com.mxn.soul.flowingdrawer.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer.R;
import com.mxn.soul.flowingdrawer.SQLcloud.Sqltools_cloud;
import com.mxn.soul.flowingdrawer.data.UserMessage;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private EditText et_username;
    private EditText et_password;
    private Button btn_confirm;
    private ProgressBar pb_register;
    public static String username;
    public static String password;
    private Sqltools_cloud sqltools_cloud;//对晕数据库操作的工具类
    private HandlerThread thread;
    private Handler handler;
    private UIhandler uIhandler;
    private Runnable initcloudsql = new Runnable() {
        @Override
        public void run() {
            sqltools_cloud = new Sqltools_cloud();
            UserMessage user = new UserMessage(username,password);
            sqltools_cloud.insert(user);
            Intent intent = new Intent(Register.this,loginActivity.class);
            startActivity(intent);
            finish();
                /*if(!sqltools_cloud.Basic_Sql(Const.selectusername,list_name,true).next()){

                    sqltools_cloud.Basic_Sql(Const.insertusername,list_name,false);
                    sqltools_cloud.Basic_Sql(Const.insertuserpass,list_pass,false);
                    Intent intent = new Intent(Register.this,loginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Register.this, "用户名已存在，请重新输入", Toast.LENGTH_SHORT).show();
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("success",0);
                    msg.setData(bundle);
                    uIhandler.sendMessage(msg);
                }
                sqltools_cloud.clear();*/

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        thread = new HandlerThread("register");
        thread.start();
        handler = new Handler(thread.getLooper());
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        pb_register = (ProgressBar) findViewById(R.id.pb_register);
        btn_confirm.setOnClickListener(this);
        uIhandler = new UIhandler();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                username = et_username.getText().toString().trim();
                password = et_password.getText().toString().trim();
                if(username.equals("") || username.equals(null)){
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                }else {
                    btn_confirm.setVisibility(View.INVISIBLE);
                    pb_register.setVisibility(View.VISIBLE);
                    handler.post(initcloudsql);
                }
                break;

        }
    }
    class UIhandler extends Handler {
        public UIhandler(){

        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int success = bundle.getInt("success");
            if(success == 0){
                btn_confirm.setVisibility(View.VISIBLE);
                pb_register.setVisibility(View.INVISIBLE);
            }
        }
    }

}
