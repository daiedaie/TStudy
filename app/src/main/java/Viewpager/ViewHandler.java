package Viewpager;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

/**
 * Created by 礼节 on 2016/10/8.
 */
public class ViewHandler extends Handler {
    private ViewPager viewPager;
    public ViewHandler(ViewPager viewPager){
        this.viewPager=viewPager;
    }
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case 1:
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                sendEmptyMessageDelayed(1,3000);
                break;
            default:
                break;
        }
    }
}
