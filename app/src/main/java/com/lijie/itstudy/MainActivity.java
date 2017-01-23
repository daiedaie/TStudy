package com.lijie.itstudy;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import Utils.Util;

public class MainActivity extends AppCompatActivity{
    private RadioGroup group;
    private RadioButton b1,b2,b3;
    private RelativeLayout layout;
    private int flag1,flag2,flag3;
    private FragmentManager manager;
    private FragmentTransaction ft;
    MainFragment mainFragment;
    SubjectFragment coursFragment;
    MeFragment meFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.settransparentView(this);//设置沉浸栏
        setContentView(R.layout.activity_main);

        group= (RadioGroup) findViewById(R.id.radiogroup);
        layout= (RelativeLayout) findViewById(R.id.layout);
        b1= (RadioButton) findViewById(R.id.b1);
        b2= (RadioButton) findViewById(R.id.b2);
        b3= (RadioButton) findViewById(R.id.b4);
        flag1=b1.getId();
        flag2=b2.getId();
        flag3=b3.getId();
        mainFragment=new MainFragment(this);
        coursFragment=new SubjectFragment(this);
        meFragment=new MeFragment(this);
        manager=getFragmentManager();
        ft=manager.beginTransaction();
        ft.replace(layout.getId(),mainFragment);
        ft.commit();
        Util.editTopDrawable(MainActivity.this,b1,R.drawable.main2);
        Util.defaltTopDrawable(MainActivity.this, b2, R.drawable.allsubject);
        Util.defaltTopDrawable(MainActivity.this, b3, R.drawable.me);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ft = manager.beginTransaction();
                //标准转场动画
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                if (checkedId == flag1) {
                    Util.editTopDrawable(MainActivity.this,b1,R.drawable.main2);
                    Util.defaltTopDrawable(MainActivity.this, b2, R.drawable.allsubject);
                    Util.defaltTopDrawable(MainActivity.this,b3,R.drawable.me);
                    ft.replace(layout.getId(), mainFragment);
                    ft.commit();
                } else if (checkedId == flag2) {
                    Util.defaltTopDrawable(MainActivity.this,b1,R.drawable.main);
                    Util.editTopDrawable(MainActivity.this, b2, R.drawable.allsubject2);
                    Util.defaltTopDrawable(MainActivity.this, b3, R.drawable.me);
                    ft.replace(layout.getId(), coursFragment);
                    ft.commit();
                }else if (checkedId == flag3) {
                    Util.defaltTopDrawable(MainActivity.this,b1,R.drawable.main);
                    Util.defaltTopDrawable(MainActivity.this, b2, R.drawable.allsubject);
                    Util.editTopDrawable(MainActivity.this, b3, R.drawable.me2);
                    ft.replace(layout.getId(), meFragment);
                    ft.commit();
                }
            }
        });
    }
}
