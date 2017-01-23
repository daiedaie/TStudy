package com.lijie.itstudy;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 礼节 on 2016/10/14.
 */
public class MeFragment extends Fragment {
    private View view;
    private ListView listView;
    private Context context;
    private ImageView img_me;
    private TextView tv_name,tv_math,tv_time,tv_study;
    private TextView tv1,tv2,tv3;
    private Button btn;
    public MeFragment(Context context){
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.me_fragment, null);
        init();

        return view;
    }

    private void init(){
        listView= (ListView) view.findViewById(R.id.listview1);
        List<String> list=new ArrayList<String>();
        list.add("心愿墙");
        list.add("修改密码");
        list.add("切换账号");
        ArrayAdapter adapter=new ArrayAdapter(context,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        img_me= (ImageView) view.findViewById(R.id.me_icon);
        tv_name= (TextView) view.findViewById(R.id.me_name);
        tv_math= (TextView) view.findViewById(R.id.me_math);
        tv_time= (TextView) view.findViewById(R.id.me_time);
        tv_study= (TextView) view.findViewById(R.id.me_study);
        tv1= (TextView) view.findViewById(R.id.tv_me1);
        tv2= (TextView) view.findViewById(R.id.tv_me2);
        tv3= (TextView) view.findViewById(R.id.tv_me3);
        btn= (Button) view.findViewById(R.id.me_button);

        tv_name.setVisibility(View.GONE);
        tv_math.setVisibility(View.GONE);
        tv_time.setVisibility(View.GONE);
        tv_study.setVisibility(View.GONE);
        tv1.setVisibility(View.GONE);
        tv2.setVisibility(View.GONE);
        tv3.setVisibility(View.GONE);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,LoginActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }
}
