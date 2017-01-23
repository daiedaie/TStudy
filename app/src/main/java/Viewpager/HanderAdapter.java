package Viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 礼节 on 2016/10/8.
 */
public class HanderAdapter extends PagerAdapter {
    private List<ImageView> list;
    private Context context;
    public HanderAdapter(List<ImageView> list,Context context){
        this.list=list;
        this.context=context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position%list.size()));//防止角标越界
        return list.get(position%list.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view== (View) object;
    }
}
