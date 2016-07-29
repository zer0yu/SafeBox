package com.mxn.soul.flowingdrawer.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer.R;
import com.mxn.soul.flowingdrawer.SQLcloud.Sqltools_cloud;
import com.squareup.picasso.Picasso;

/**
 * Created by yc on 2016/1/9.
 */

public class loginActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private Button btn_login, btnQuit, register;
    public static String userNameValue;
    public static String passwordValue;
    public static String password;
    private Handler handler;
    private UIhandler uIhandler;
    private EditText userName, passWord;
    private CheckBox rem_pw, auto_login;
    private Sqltools_cloud sqltools_cloud;
    private HandlerThread thread;
    private ImageView imageview;


    public Runnable cloudsql = new Runnable() {
        @Override
        public void run() {
            sqltools_cloud = new Sqltools_cloud();
            password = sqltools_cloud.GetPass(userNameValue);
                    if ( passwordValue.equals(password)) {
                        Toast.makeText(loginActivity.this, "登录中...", Toast.LENGTH_SHORT).show();
                        //登录成功和记住密码框为选中状态才保存用户信息
                        if (rem_pw.isChecked()) {
                            //记住用户名、密码
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("USER_NAME", userNameValue);
                            editor.putString("PASSWORD", passwordValue);
                            editor.commit();
                        }
                        //跳转界面
                        Intent intent = new Intent(loginActivity.this,MainActivity.class);
                        loginActivity.this.startActivity(intent);
                    } else
                        Toast.makeText(loginActivity.this, "用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();
    }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();

        //获得实例对象
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);
        rem_pw = (CheckBox) findViewById(R.id.checkBox);
        auto_login = (CheckBox) findViewById(R.id.checkBox2);
        btn_login = (Button) findViewById(R.id.buttonTos);
        btnQuit = (Button) findViewById(R.id.button);
        register = (Button) findViewById(R.id.button2);
        imageview = (ImageView) findViewById(R.id.imageView);
        Picasso.with(getApplicationContext())
                .load(R.drawable.a1)
                .transform(new CircleTransformation())
                .into(imageview);


        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, Register.class);
                loginActivity.this.startActivity(intent);
                finish();
            }
        });
        //判断记住密码多选框的状态
        if (sp.getBoolean("ISCHECK", false)) {
            //设置默认是记录密码状态
            rem_pw.setChecked(true);
            userName.setText(sp.getString("USER_NAME", ""));
            passWord.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if (sp.getBoolean("AUTO_ISCHECK", false)) {
                //设置默认是自动登录状态
                auto_login.setChecked(true);
                //跳转界面
                Intent intent1 = new Intent(loginActivity.this, MainActivity.class);
                loginActivity.this.startActivity(intent1);

            }
        }

        // 登录监听事件
        btn_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.buttonTos:
                        userNameValue = userName.getText().toString();
                        passwordValue = passWord.getText().toString();
                        if(userNameValue.equals("") || userNameValue.equals(null)){
                            Toast.makeText(loginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                        }else {
                            handler.post(cloudsql);
                        }
                        break;

                }
            }
        });

        //监听记住密码多选框按钮事件
        rem_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rem_pw.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                } else {

                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        //监听自动登录多选框事件
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (auto_login.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });


        btnQuit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void init() {
        thread = new HandlerThread("register");
        thread.start();
        handler = new Handler(thread.getLooper());
        uIhandler = new UIhandler();
    }

    class UIhandler extends Handler {
        public UIhandler() {

        }
    }
}













