package com.lijie.itstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.GridViewAdapter;
import Utils.Util;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_name,et_pwd;
    private Button btn;
    private GridView view1;
    private List<Integer> list;
    private boolean[] bools;
    private GridViewAdapter adapter;
    private String json="";
    private int img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.settransparentView(this);
        setContentView(R.layout.activity_register);
        init();

        view1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bools = new boolean[8];
                for(int i=0;i<bools.length;i++){
                    bools[i]=false;
                }

                bools[position]=true;
                adapter=new GridViewAdapter(RegisterActivity.this,list,bools);
                view1.setAdapter(adapter);
            }
        });
    }

    private void init(){
        view1= (GridView) findViewById(R.id.gridView_reg);
        et_name= (EditText) findViewById(R.id.accountEt);
        et_pwd= (EditText) findViewById(R.id.pwdEt);
        btn= (Button) findViewById(R.id.subBtn);
        list=new ArrayList<Integer>();
        list.add(R.drawable.h1);
        list.add(R.drawable.h2);
        list.add(R.drawable.h3);
        list.add(R.drawable.h4);
        list.add(R.drawable.h55);
        list.add(R.drawable.h6);
        list.add(R.drawable.h7);
        list.add(R.drawable.h8);
        bools=new boolean[8];
        for(int i=0;i<bools.length;i++){
            if(i==0){
                bools[i]=true;
            }else {
                bools[i] = false;
            }
        }

        adapter=new GridViewAdapter(this,list,bools);
        view1.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<bools.length;i++){
                    if(bools[i]) {
                        img=list.get(i);
                    }
                }
                final String name=et_name.getText().toString().trim();
                final String pwd=et_pwd.getText().toString().trim();
                if(!"".equals(name) && !"".equals(pwd)){
                    Thread d=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            json= Util.sendPost("http://192.168.56.1:8080/Tstudy/CourseServlet?action=register","{\"name\":\""+name+"\",\"pwd\":\""+pwd+"\",\"img\":"+img+"}");
                        }
                    });
                    d.start();
                    try {
                        d.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject Object=new JSONObject(json);
                        if(Object.getInt("code")==1){
                            Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        }else{
                            Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
