package com.lijie.itstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Utils.Util;

public class LoginActivity extends AppCompatActivity {
    private TextView tv_reg;
    private EditText et_name;
    private EditText et_pwd;
    private Button btn;
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.settransparentView(this);
        setContentView(R.layout.activity_login);

        btn= (Button) findViewById(R.id.subBtn2);
        et_name= (EditText) findViewById(R.id.et_name);
        et_pwd= (EditText) findViewById(R.id.et_pwd);
        tv_reg= (TextView) findViewById(R.id.tv_reg);
        tv_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_name.getText().toString().trim().equals("") && !et_pwd.getText().toString().trim().equals("")){
                    Thread d=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            json= Util.sendPost("http://192.168.56.1:8080/Tstudy/CourseServlet?action=register","{\"name\":\""+et_name.getText().toString().trim()+"\",\"pwd\":\""+et_pwd.getText().toString().trim()+"\"}");
                        }
                    });
                    d.start();
                    try {
                        d.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject object=new JSONObject(json);
                        if(object.getInt("code")==1){

                        }else{
                            Toast.makeText(LoginActivity.this,"登录失败，网络错误！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
