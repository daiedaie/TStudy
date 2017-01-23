package com.lijie.itstudy;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Entity.TCourse;
import ScorllView.MyScorllView;
import Utils.Util;
import Viewpager.HanderAdapter;
import Viewpager.ViewHandler;
import Viewpager.ZoomOutPageTransformer;

/**
 * Created by 礼节 on 2016/10/14.
 */
public class MainFragment extends Fragment implements MyScorllView.ScrollViewListener{
    private ViewPager viewpager;
    private HanderAdapter adapter;
    private List<ImageView> list;
    private ViewHandler handler;
    private ImageView point1,point2,point3,point4;
    private List<ImageView> pointList;
    private MyScorllView scrollView;
    private LinearLayout topLayout;
    private int imageHeight;//顶部图片高度
    private int imageHeight2;
    private GridView gridView;
    private String json;
    private int c=0;
    //GridView数据
    private int[] icon={R.drawable.cjia,R.drawable.ct,R.drawable.java,
            R.drawable.android,R.drawable.php,R.drawable.javascript,
            R.drawable.h5,R.drawable.sql};
    private String[] to={"C++","C语言","JAVA","Android","PHP","JavaScript","H5","Sql Server"};
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> data_list;
    private Context context;
    private View view;//MainFragment视图
    private ImageView imageview1;
    private float x1,x2,y1,y2;
    private TextView sousuo;
    private EditText et;
    private List<TCourse> coursesnew;
    private List<TCourse> coursesl;
    private List<TCourse> courselist;
    private int subjectId;
    private ImageView new_img1,new_img2,new_img3,new_img4,ma_img1,ma_img2,ma_img3,ma_img4,ma_img5,ma_img6,ma_img7,ma_img8;
    private TextView new_text1,new_text2,new_text3,new_text4,ma_text1,ma_text2,ma_text3,ma_text4,ma_text5,ma_text6,ma_text7,ma_text8;
    private ProgressBar load_bar;
    private TextView load_text;
    private LoadAsyncTask asyncTask;

    public MainFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.main_fragment, null);
        initView();

        point1.setBackgroundResource(R.drawable.xc);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //滑动完成
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < pointList.size(); i++) {
                    pointList.get(i).setBackgroundResource(R.drawable.ws);
                    if (position % pointList.size() == i) {
                        pointList.get(i).setBackgroundResource(R.drawable.xc);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //当鼠标落下停止切换，松开鼠标继续切换
        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    handler.removeMessages(1);
                    x1 = event.getX();
                    y1 = event.getY();
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    x2 = event.getX();
                    y2 = event.getY();
                    if(y1 - y2 > 10) {
                        //向上滑
                    } else if(y2 - y1 > 10) {
                        //向下滑
                    } else if(x1 - x2 > 10) {//左滑
                        viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                    } else if( x2 - x1 > 10){//右滑
                        viewpager.setCurrentItem(viewpager.getCurrentItem() - 1);
                    }
                    handler.sendEmptyMessageDelayed(1, 3000);
                }
                return false;
            }
        });
        return view;
    }

    private void initView(){
        et= (EditText) view.findViewById(R.id.et_sousuo);
        sousuo= (TextView) view.findViewById(R.id.sousuo);
        new_img1= (ImageView) view.findViewById(R.id.new1_image);
        new_img2= (ImageView) view.findViewById(R.id.new2_image);
        new_img3= (ImageView) view.findViewById(R.id.new3_image);
        new_img4= (ImageView) view.findViewById(R.id.new4_image);
        new_text1= (TextView) view.findViewById(R.id.new1_text);
        new_text2= (TextView) view.findViewById(R.id.new2_text);
        new_text3= (TextView) view.findViewById(R.id.new3_text);
        new_text4= (TextView) view.findViewById(R.id.new4_text);
        ma_img1= (ImageView) view.findViewById(R.id.ma_img1);
        ma_img2= (ImageView) view.findViewById(R.id.ma_img2);
        ma_img3= (ImageView) view.findViewById(R.id.ma_img3);
        ma_img4= (ImageView) view.findViewById(R.id.ma_img4);
        ma_img5= (ImageView) view.findViewById(R.id.ma_img5);
        ma_img6= (ImageView) view.findViewById(R.id.ma_img6);
        ma_img7= (ImageView) view.findViewById(R.id.ma_img7);
        ma_img8= (ImageView) view.findViewById(R.id.ma_img8);
        ma_text1= (TextView) view.findViewById(R.id.ma_text1);
        ma_text2= (TextView) view.findViewById(R.id.ma_text2);
        ma_text3= (TextView) view.findViewById(R.id.ma_text3);
        ma_text4= (TextView) view.findViewById(R.id.ma_text4);
        ma_text5= (TextView) view.findViewById(R.id.ma_text5);
        ma_text6= (TextView) view.findViewById(R.id.ma_text6);
        ma_text7= (TextView) view.findViewById(R.id.ma_text7);
        ma_text8= (TextView) view.findViewById(R.id.ma_text8);
        load_bar= (ProgressBar) view.findViewById(R.id.progress_load);
        load_text= (TextView) view.findViewById(R.id.load);


        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et.getText().toString().trim().equals("")){
                    String str=et.getText().toString().trim();
                    Intent intent=new Intent(context,CourseActivity.class);
                    intent.putExtra("subjectId",0);
                    intent.putExtra("action",str);
                    startActivity(intent);
                    ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            }
        });

        new_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.course=coursesnew.get(0);
                Intent intent=new Intent(context,StudyActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        new_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.course=coursesnew.get(1);
                Intent intent=new Intent(context,StudyActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        new_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.course=coursesnew.get(2);
                Intent intent=new Intent(context,StudyActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        new_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.course=coursesnew.get(3);
                Intent intent=new Intent(context,StudyActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        ma_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.course=coursesl.get(0);
                Intent intent=new Intent(context,StudyActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        ma_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.course = coursesl.get(1);
                Intent intent = new Intent(context, StudyActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        ma_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.course=coursesl.get(2);
                Intent intent=new Intent(context,StudyActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        ma_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.course=coursesl.get(3);
                Intent intent=new Intent(context,StudyActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        ma_img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.course=coursesl.get(4);
                Intent intent=new Intent(context,StudyActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        ma_img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.course=coursesl.get(5);
                Intent intent=new Intent(context,StudyActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        ma_img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.course=coursesl.get(6);
                Intent intent=new Intent(context,StudyActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        ma_img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.course=coursesl.get(7);
                Intent intent=new Intent(context,StudyActivity.class);
                startActivity(intent);
                ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        viewpager= (ViewPager) view.findViewById(R.id.viewpager);
        scrollView= (MyScorllView) view.findViewById(R.id.scrollView);
        topLayout= (LinearLayout) view.findViewById(R.id.toplayout);
        gridView= (GridView) view.findViewById(R.id.gridView);
        point1= (ImageView) view.findViewById(R.id.point1);
        point2= (ImageView) view.findViewById(R.id.point2);
        point3= (ImageView) view.findViewById(R.id.point3);
        point4= (ImageView) view.findViewById(R.id.point4);
        pointList=new ArrayList<ImageView>();
        coursesnew=new ArrayList<TCourse>();
        courselist=new ArrayList<TCourse>();
        simpleAdapter=new SimpleAdapter(context, Util.getData(icon, to),R.layout.maingridview_item,new String[]{"img","text"},new int[]{R.id.img,R.id.text});
        gridView.setAdapter(simpleAdapter);
        pointList.add(point1);
        pointList.add(point2);
        pointList.add(point3);
        pointList.add(point4);
        list=new ArrayList<ImageView>();
        imageview1=new ImageView(context);
        imageview1.setBackgroundResource(R.drawable.a);
        list.add(imageview1);
        ImageView imageView2=new ImageView(context);
        imageView2.setBackgroundResource(R.drawable.b);
        list.add(imageView2);
        ImageView imageView3=new ImageView(context);
        imageView3.setBackgroundResource(R.drawable.c);
        list.add(imageView3);
        ImageView imageView4=new ImageView(context);
        imageView4.setBackgroundResource(R.drawable.d);
        list.add(imageView4);
        adapter=new HanderAdapter(list,context);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(10000);


        load_bar.setVisibility(View.VISIBLE);
        load_text.setVisibility(View.VISIBLE);
        asyncTask=new LoadAsyncTask();
        asyncTask.execute();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        saveSubjectIdName("c++");
                        Intent intent=new Intent(context,CourseActivity.class);
                        intent.putExtra("subjectName","c++");
                        intent.putExtra("subjectId",subjectId);
                        startActivity(intent);
                        ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        break;
                    case 1:
                        saveSubjectIdName("c");
                        Intent intent2=new Intent(context,CourseActivity.class);
                        intent2.putExtra("subjectName","c++");
                        intent2.putExtra("subjectId",subjectId);
                        startActivity(intent2);
                        ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        break;
                    case 2:
                        saveSubjectIdName("java");
                        Intent intent3=new Intent(context,CourseActivity.class);
                        intent3.putExtra("subjectName","java");
                        intent3.putExtra("subjectId",subjectId);
                        startActivity(intent3);
                        ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        break;
                    case 3:
                        saveSubjectIdName("android");
                        Intent intent4=new Intent(context,CourseActivity.class);
                        intent4.putExtra("subjectName","android");
                        intent4.putExtra("subjectId",subjectId);
                        startActivity(intent4);
                        ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        break;
                    case 4:
                        saveSubjectIdName("php");
                        Intent intent5=new Intent(context,CourseActivity.class);
                        intent5.putExtra("subjectName","php");
                        intent5.putExtra("subjectId",subjectId);
                        startActivity(intent5);
                        ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        break;
                    case 5:
                        saveSubjectIdName("javascript");
                        Intent intent6=new Intent(context,CourseActivity.class);
                        intent6.putExtra("subjectName","javascript");
                        intent6.putExtra("subjectId",subjectId);
                        startActivity(intent6);
                        ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        break;
                    case 6:
                        saveSubjectIdName("Html5/CSS3");
                        Intent intent7=new Intent(context,CourseActivity.class);
                        intent7.putExtra("subjectName","Html5/CSS3");
                        intent7.putExtra("subjectId",subjectId);
                        startActivity(intent7);
                        ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        break;
                    case 7:
                        saveSubjectIdName("SQLServer");
                        Intent intent8=new Intent(context,CourseActivity.class);
                        intent8.putExtra("subjectName","SQLServer");
                        intent8.putExtra("subjectId",subjectId);
                        startActivity(intent8);
                        ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        break;
                }
            }
        });

        viewpager.setPageTransformer(true, new ZoomOutPageTransformer());//设置切换动画
        scrollView.setVerticalScrollBarEnabled(false);//隐藏纵向下拉条

        //获取ImageView的高度
        ViewTreeObserver vto = list.get(1).getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageHeight = list.get(1).getHeight();
                imageHeight2= list.get(1).getHeight();
                scrollView.setScrollViewListener(MainFragment.this);
            }
        });


        handler=new ViewHandler(viewpager);
    }


    //防止界面跳转时handler还在发消息从而内存泄漏
    @Override
    public void onStop() {
        super.onStop();
        handler.removeMessages(1);
        if(!asyncTask.isCancelled()){
            asyncTask.cancel(true);
        }
    }

    //重新开始发消息
    @Override
    public void onResume() {
        super.onResume();

        //界面切换动态维护顶部状态栏背景
        if ( Util.y< imageHeight2/2 || Util.y<=0) {
            topLayout.setBackgroundColor(Color.argb((int) 0, 40, 194, 119));//第一位代表透明度，其他三位代表RGB颜色
        } else if (Util.y >= imageHeight2/2 && Util.y < imageHeight2) {
            float scale = (float) Util.y / imageHeight2;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            topLayout.setBackgroundColor(Color.argb((int) alpha, 40, 194, 119));
        } else {
            topLayout.setBackgroundColor(Color.argb((int) 255,40, 194, 119));
        }
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    @Override
    public void OnMyScrollChanged(MyScorllView scrollView, int x, int y, int oldx, int oldy) {
        Util.y=y;
        if ( y< imageHeight/2 || y<=0) {
            topLayout.setBackgroundColor(Color.argb((int) 0, 40, 194, 119));//第一位代表透明度，其他三位代表RGB颜色
        } else if (y >= imageHeight/2 && y < imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            topLayout.setBackgroundColor(Color.argb((int) alpha, 40, 194, 119));
        } else {
            topLayout.setBackgroundColor(Color.argb((int) 255,40, 194, 119));
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

    private void saveSubjectIdName(final String name){
        Thread d=new Thread(new Runnable() {
            @Override
            public void run() {
                json=Util.HttpString(Util.IP_CONFIG+"selSubjectIdByName&name="+name);
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
            subjectId=object.getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //页面加载异步
    class LoadAsyncTask extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            coursesnew=jsonToCourse("selCourseByTime");
            coursesl=jsonToCourse("selCourseByNewId");

            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            new_img1.setBackground(new BitmapDrawable(Util.setbitmap(coursesnew.get(0).getCourseImg())));
            new_img2.setBackground(new BitmapDrawable(Util.setbitmap(coursesnew.get(1).getCourseImg())));
            new_img3.setBackground(new BitmapDrawable(Util.setbitmap(coursesnew.get(2).getCourseImg())));
            new_img4.setBackground(new BitmapDrawable(Util.setbitmap(coursesnew.get(3).getCourseImg())));
            new_text1.setText(coursesnew.get(0).getCourseName());
            new_text2.setText(coursesnew.get(1).getCourseName());
            new_text3.setText(coursesnew.get(2).getCourseName());
            new_text4.setText(coursesnew.get(3).getCourseName());

            ma_img1.setBackground(new BitmapDrawable(Util.setbitmap(coursesl.get(0).getCourseImg())));
            ma_img2.setBackground(new BitmapDrawable(Util.setbitmap(coursesl.get(1).getCourseImg())));
            ma_img3.setBackground(new BitmapDrawable(Util.setbitmap(coursesl.get(2).getCourseImg())));
            ma_img4.setBackground(new BitmapDrawable(Util.setbitmap(coursesl.get(3).getCourseImg())));
            ma_img5.setBackground(new BitmapDrawable(Util.setbitmap(coursesl.get(4).getCourseImg())));
            ma_img6.setBackground(new BitmapDrawable(Util.setbitmap(coursesl.get(5).getCourseImg())));
            ma_img7.setBackground(new BitmapDrawable(Util.setbitmap(coursesl.get(6).getCourseImg())));
            ma_img8.setBackground(new BitmapDrawable(Util.setbitmap(coursesl.get(7).getCourseImg())));
            ma_text1.setText(coursesl.get(0).getCourseName());
            ma_text2.setText(coursesl.get(1).getCourseName());
            ma_text3.setText(coursesl.get(2).getCourseName());
            ma_text4.setText(coursesl.get(3).getCourseName());
            ma_text5.setText(coursesl.get(4).getCourseName());
            ma_text6.setText(coursesl.get(5).getCourseName());
            ma_text7.setText(coursesl.get(6).getCourseName());
            ma_text8.setText(coursesl.get(7).getCourseName());

            load_text.setVisibility(View.GONE);
            load_bar.setVisibility(View.GONE);
        }

    }
}
