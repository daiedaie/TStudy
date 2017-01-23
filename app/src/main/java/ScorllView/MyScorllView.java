package ScorllView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by 礼节 on 2016/10/10.
 */
public class MyScorllView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;

    //回调接口
    public interface ScrollViewListener{
        void OnMyScrollChanged(MyScorllView scrollView, int x, int y,
                             int oldx, int oldy
        );
    }

    public MyScorllView(Context context) {
        super(context);
    }

    public MyScorllView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScorllView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //set方法
    public void setScrollViewListener(MyScorllView.ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    //滑动变化持续调用
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(scrollViewListener!=null){
            scrollViewListener.OnMyScrollChanged(this,l,t,oldl,oldt);
        }
    }
}
