package com.lijie.itstudy;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.LeftSubjectAdapter;
import Adapter.RightSubjectAdapter;
import Entity.TClass;
import Entity.TSubject;
import MyDialog.ProgressDialog;
import Utils.Util;

/**
 * Created by 礼节 on 2016/10/14.
 */
public class SubjectFragment extends Fragment {
    private View view;
    private List<TClass> list;
    private ListView left_listView;
    private LeftSubjectAdapter adapter;
    private Context context;
    private ListView right_listview;
    private List<TSubject> list1;
    private String json="";
    private LoadSubAsyncTask asyncTask;
    private ProgressBar bar;
    private TextView textView;

    public SubjectFragment(Context context){
        this.context=context;
    }

    public SubjectFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.subject_fragment, null);
        init();

        left_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter = new LeftSubjectAdapter(context, list, position);
                left_listView.setAdapter(adapter);

                list1=jsonToSubject(list.get(position).getClassId());

                RightSubjectAdapter adapter2 = new RightSubjectAdapter(context, list1);
                right_listview.setAdapter(adapter2);
                right_listview.setDividerHeight(0);
            }
        });

        right_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context,CourseActivity.class);
                intent.putExtra("subjectId",list1.get(position).getSubjectId());
                intent.putExtra("subjectName",list1.get(position).getSubjectName());
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

        return view;
    }

    private void init(){
        left_listView= (ListView) view.findViewById(R.id.list_left);
        right_listview= (ListView) view.findViewById(R.id.list_right);
        bar= (ProgressBar) view.findViewById(R.id.subbar_load);
        textView= (TextView) view.findViewById(R.id.subtext_load);
        bar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        asyncTask=new LoadSubAsyncTask();
        asyncTask.execute();
    }


    private List<TClass> jsonToClass(){
        List<TClass> tClasslist=new ArrayList<TClass>();
        TClass tClass=null;
        Thread d=new Thread(new Runnable() {
            @Override
            public void run() {
                json=Util.HttpString(Util.IP_CONFIG+"selClass");
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
                for(int i=0;i<array.length();i++){
                    tClass=new TClass();
                    JSONObject object=array.getJSONObject(i);
                    tClass.setClassId(object.getInt("classId"));
                    tClass.setClassName(object.getString("className"));
                    tClasslist.add(tClass);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tClasslist;
    }

    private List<TSubject> jsonToSubject(final int id){
        List<TSubject> tSubjectslist=new ArrayList<TSubject>();
        TSubject tSubject=null;
        Thread d=new Thread(new Runnable() {
            @Override
            public void run() {
                json=Util.HttpString(Util.IP_CONFIG+"selSubjectByClassId&id="+id);
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
                for(int i=0;i<array.length();i++){
                    tSubject=new TSubject();
                    JSONObject object=array.getJSONObject(i);
                    tSubject.setClassId(object.getInt("classId"));
                    tSubject.setSubjectId(object.getInt("subjectId"));
                    tSubject.setSubjectName(object.getString("subjectName"));
                    tSubjectslist.add(tSubject);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tSubjectslist;
    }

    class LoadSubAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            //加载类别
            list=  jsonToClass();
            //加载科目
            list1=jsonToSubject(list.get(0).getClassId());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter=new LeftSubjectAdapter(context,list,0);
            left_listView.setAdapter(adapter);
            RightSubjectAdapter adapter2=new RightSubjectAdapter(context,list1);
            right_listview.setAdapter(adapter2);
            right_listview.setDividerHeight(0);
            bar.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!asyncTask.isCancelled()){
            asyncTask.cancel(true);
        }
    }
}
