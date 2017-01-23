package com.lijie.itstudy;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import MediaPlayer.Player;
import MyDialog.ProgressDialog;
import Utils.Util;
import ViewIndictor.ChapterFragment;
import ViewIndictor.CommitFragment;
import ViewIndictor.ProfileFragment;
import ViewIndictor.ViewpagerIndicator;

public class StudyActivity extends FragmentActivity {
    private ViewPager mViewpager;
    private ViewpagerIndicator mIndicator;
    private List<Fragment> mContents=new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;

    public static ImageView img_play;
    public static TextView title_cha;
    public static int progress1;
    public static boolean isPlay;
    public static int i=0;
    private TextView tv1,tv2,tv3;
    private SeekBar skbProgress;
    private SurfaceView surfaceView;
    private Player player;
    private String url="";
    private ProgressBar probar;
    private Bitmap map;
    private VideoHandler handler;
    private Handler handler2;
    public static String name;
    private ProgressDialog dialog;
    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
        progress1 = 0;
        isPlay = false;
        i = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.settransparentView(this);
        setContentView(R.layout.activity_study);

        initViews();
        initDatas();
        mViewpager.setAdapter(mAdapter);
        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //偏移量
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tv1.setTextColor(Color.rgb(40, 194, 119));
                    tv2.setTextColor(Color.rgb(128, 128, 128));
                    tv3.setTextColor(Color.rgb(128, 128, 128));
                } else if (position == 1) {
                    tv1.setTextColor(Color.rgb(128, 128, 128));
                    tv2.setTextColor(Color.rgb(40, 194, 119));
                    tv3.setTextColor(Color.rgb(128, 128, 128));
                } else {
                    tv1.setTextColor(Color.rgb(128, 128, 128));
                    tv2.setTextColor(Color.rgb(128, 128, 128));
                    tv3.setTextColor(Color.rgb(40, 194, 119));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initDatas() {
        handler=new VideoHandler();
        mContents.add(new ProfileFragment());
        mContents.add(new ChapterFragment(this,handler));
        mContents.add(new CommitFragment(this));

        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };
    }

    private void initViews() {
        isPlay=false;
        tv1= (TextView) findViewById(R.id.tv1);
        tv2= (TextView) findViewById(R.id.tv2);
        tv3= (TextView) findViewById(R.id.tv3);
        mViewpager= (ViewPager) findViewById(R.id.study_viewpager);
        mIndicator= (ViewpagerIndicator) findViewById(R.id.indicator);
        img_play= (ImageView) findViewById(R.id.playurl);
        skbProgress= (SeekBar) findViewById(R.id.skbProgress);
        surfaceView= (SurfaceView) findViewById(R.id.surfaceView1);
        probar= (ProgressBar) findViewById(R.id.progress);
        title_cha= (TextView) findViewById(R.id.title_cha);
        skbProgress.setEnabled(false);
        probar.setVisibility(View.GONE);
        //设置不播放时得背景
        surfaceView.getBackground().setAlpha(255);

        //设置课程数据
        title_cha.setText("" + Util.course.getCourseName());

        map=Util.setbitmap(Util.course.getCourseImg());
        Drawable drawable =new BitmapDrawable(map);
        surfaceView.setBackground(drawable);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewpager.setCurrentItem(0);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewpager.setCurrentItem(1);
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewpager.setCurrentItem(2);
            }
        });
        player=new Player(surfaceView,skbProgress,probar);
        handler2=player.getHandler();

        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0){
                    Toast.makeText(StudyActivity.this, "i=0", Toast.LENGTH_SHORT).show();
                    title_cha.setVisibility(View.GONE);
                    probar.setVisibility(View.VISIBLE);
                    surfaceView.getBackground().setAlpha(0);
                    skbProgress.setEnabled(true);
                    i++;
                }
                if (isPlay) {
                    if(player.mediaPlayer.isPlaying()){
                        img_play.setImageResource(R.drawable.pause);
                        Toast.makeText(StudyActivity.this, "暂停", Toast.LENGTH_SHORT).show();
                        player.pause();
                        isPlay = false;
                    }
                } else if (!isPlay) {
                    img_play.setImageResource(R.drawable.play);
                    if (progress1 > 0) {
                        Toast.makeText(StudyActivity.this, "从暂停状态播放", Toast.LENGTH_SHORT).show();
                        player.playInPause();
                        isPlay = true;
                    } else if (progress1 == 0) {
                        Toast.makeText(StudyActivity.this, "从头播放", Toast.LENGTH_SHORT).show();
                        player.playUrl(Util.Ip + url);
                        handler2.sendEmptyMessage(1);
                        isPlay = true;
                    }
                }
            }
        });
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            progress1 = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress1);
        }
    }

    class VideoHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            url=b.getString("url");
            name=b.getString("name");

            Toast.makeText(StudyActivity.this,url,Toast.LENGTH_SHORT).show();
            title_cha.setText(name);
            img_play.setVisibility(View.VISIBLE);
            skbProgress.setVisibility(View.VISIBLE);

            title_cha.setVisibility(View.GONE);
            probar.setVisibility(View.VISIBLE);
            surfaceView.getBackground().setAlpha(0);
            skbProgress.setEnabled(true);
            img_play.setImageResource(R.drawable.play);
            i++;
            player.playUrl(Util.Ip + url);
            handler2.sendEmptyMessage(1);
            isPlay=true;
        }
    }
}
