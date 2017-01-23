package MediaPlayer;

/**
 * Created by 礼节 on 2016/10/20.
 */
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.lijie.itstudy.R;
import com.lijie.itstudy.StudyActivity;

public class Player implements OnBufferingUpdateListener,
        OnCompletionListener, MediaPlayer.OnPreparedListener,
        SurfaceHolder.Callback {
    public MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    private SeekBar skbProgress;
    private Timer mTimer=new Timer();
    private ProgressBar bar;
    private SurfaceView surfaceView;
    private MyHandler handler;
    private long lastUpdateTime;
    public static int size=0;
    public Player(SurfaceView surfaceView,SeekBar skbProgress,ProgressBar bar)
    {
        handler=new MyHandler();
        this.bar=bar;
        this.surfaceView=surfaceView;
        this.skbProgress=skbProgress;
        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    /*******************************************************
     * 通过定时器和Handler来更新进度条
     ******************************************************/
    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if(mediaPlayer==null)
                return;
            if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) {
                handleProgress.sendEmptyMessage(0);
            }
        }
    };

    Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {

            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();

            if (duration > 0) {
                long pos = skbProgress.getMax() * position / duration;
                skbProgress.setProgress((int) pos);
            }
        };
    };
    //*****************************************************
    public void playInPause(){
        mediaPlayer.start();
    }


    public void playUrl(String videoUrl)
    {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(videoUrl);
            mediaPlayer.prepareAsync();//prepare之后自动播放

            //mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void pause()
    {
        mediaPlayer.pause();
    }

    public void stop()
    {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
        } catch (Exception e) {
            Log.e("mediaPlayer", "error", e);
        }
        Log.e("mediaPlayer", "surface created");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        skbProgress.setSecondaryProgress(0);
        skbProgress.setEnabled(false);
        skbProgress.setProgress(0);
        StudyActivity.progress1 = 0;
        StudyActivity.isPlay = false;
        StudyActivity.i = 0;
        StudyActivity.img_play.setImageResource(R.drawable.pause);
        StudyActivity.title_cha.setVisibility(View.VISIBLE);
        StudyActivity.title_cha.setText("HTML5 Web开发进入新时代教程");
        surfaceView.getBackground().setAlpha(255);
    }


    @Override
    /**
     * 通过onPrepared播放
     */
    public void onPrepared(MediaPlayer mp) {
        if(mp != null)  {
            mp.start(); // 播放视频
            StudyActivity.isPlay=true;
            bar.setVisibility(View.GONE);
            skbProgress.setEnabled(true);
            System.out.println("开始播放");
        }
    }


    @Override
    public void onCompletion(MediaPlayer arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
        skbProgress.setSecondaryProgress(bufferingProgress);
        int currentProgress=skbProgress.getMax()*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
        Log.e(currentProgress + "% play", bufferingProgress + "% buffer");

        mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                       if(size==0) {
                           lastUpdateTime=System.currentTimeMillis();
                           System.out.println("在这里！");
                           size++;
                           return;
                       }else {
                           long now = System.currentTimeMillis();
                           if (now - lastUpdateTime > 30 * 1000) {
                               System.out.println("播放完毕");
                               skbProgress.setSecondaryProgress(0);
                               skbProgress.setEnabled(false);
                               skbProgress.setProgress(0);
                               StudyActivity.progress1 = 0;
                               StudyActivity.isPlay = false;
                               StudyActivity.i = 0;
                               StudyActivity.img_play.setImageResource(R.drawable.pause);
                               StudyActivity.title_cha.setVisibility(View.VISIBLE);
                               StudyActivity.title_cha.setText(StudyActivity.name);
                               surfaceView.getBackground().setAlpha(255);
                               size=0;
                           }
                       }
            }
        });
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            size=0;
        }
    }

    public Handler getHandler(){
        return handler;
    }
}
