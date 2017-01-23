package com.lijie.itstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.CourseAdapter;
import Entity.TCourse;
import MyDialog.ProgressDialog;
import Utils.Util;

public class CourseActivity extends AppCompatActivity {
    private ListView listView;
    private List<TCourse> list;
    private int subjectId;
    private String subjectName="";
    private ProgressDialog dialog;
    private String json="";
    private ImageView img_back;
    private TextView tvcourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.settransparentView(this);
        setContentView(R.layout.activity_course);
        init();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Util.course=list.get(position-1);
                Intent intent=new Intent(CourseActivity.this,StudyActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
    }

    private void init(){
        listView= (ListView) findViewById(R.id.courselistview);
        tvcourse= (TextView) findViewById(R.id.course_text);
        img_back= (ImageView) findViewById(R.id.img_backmain);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CourseActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
        View view= View.inflate(this,R.layout.courselistview_header,null);
        TextView tv= (TextView) view.findViewById(R.id.handerText);
        Intent intent=getIntent();
        subjectId=intent.getIntExtra("subjectId", 0);
        subjectName=intent.getStringExtra("subjectName");
        if(subjectId!=0){
            list=jsonToCourse(subjectId);
            tv.setText("共" + list.size() + "门相关课程");
            tvcourse.setText(subjectName);
            listView.addHeaderView(view);
            for(int i=0;i<list.size();i++){
                System.out.println(list.get(i).toString());
            }
            CourseAdapter adapter=new CourseAdapter(CourseActivity.this,list);
            listView.setAdapter(adapter);
        }else{
            String str=intent.getStringExtra("action");
            list=jsonToCourse("selCourseByStr&Str="+str);
            tv.setText("共" + list.size() + "门相关课程");
            tvcourse.setText(str);
            listView.addHeaderView(view);
            for(int i=0;i<list.size();i++){
                System.out.println(list.get(i).toString());
            }
            CourseAdapter adapter=new CourseAdapter(CourseActivity.this,list);
            listView.setAdapter(adapter);
        }

    }

    private List<TCourse> jsonToCourse(final String action){
        List<TCourse> tCourselist=new ArrayList<TCourse>();
        TCourse tCourse=null;

        Thread d=new Thread(new Runnable() {
            @Override
            public void run() {
                json=Util.HttpString(Util.IP_CONFIG+action);
            }
        });
        d.start();
        try {
            d.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if(json.length()>2){
                JSONArray array=new JSONArray(json);
                for(int j=0;j<array.length();j++){
                    tCourse=new TCourse();
                    JSONObject object=array.getJSONObject(j);
                    tCourse.setSubjectId(object.getInt("subjectId"));
                    tCourse.setCourseId(object.getInt("courseId"));
                    tCourse.setCourseImg(object.getString("courseImg"));
                    tCourse.setCourseMath(object.getDouble("courseMath"));
                    tCourse.setCourseName(object.getString("courseName"));
                    tCourse.setCoursePeople(object.getInt("coursePeople"));
                    tCourse.setCourseReserve(object.getString("courseReserve"));
                    tCourse.setCourseTime(object.getString("courseTime"));
                    tCourselist.add(tCourse);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tCourselist;
    }


    private List<TCourse> jsonToCourse(final int subjectId){
        List<TCourse> tCourselist=new ArrayList<TCourse>();
        TCourse tCourse=null;

        Thread d=new Thread(new Runnable() {
            @Override
            public void run() {
                json=Util.HttpString(Util.IP_CONFIG+"selCourseBySubjectId&id="+subjectId);
            }
        });
        d.start();
        try {
            d.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if(json.length()>2){
                JSONArray array=new JSONArray(json);
                for(int j=0;j<array.length();j++){
                    tCourse=new TCourse();
                    JSONObject object=array.getJSONObject(j);
                    tCourse.setSubjectId(object.getInt("subjectId"));
                    tCourse.setCourseId(object.getInt("courseId"));
                    tCourse.setCourseImg(object.getString("courseImg"));
                    tCourse.setCourseMath(object.getDouble("courseMath"));
                    tCourse.setCourseName(object.getString("courseName"));
                    tCourse.setCoursePeople(object.getInt("coursePeople"));
                    tCourse.setCourseReserve(object.getString("courseReserve"));
                    tCourse.setCourseTime(object.getString("courseTime"));
                    tCourselist.add(tCourse);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tCourselist;
    }
}
