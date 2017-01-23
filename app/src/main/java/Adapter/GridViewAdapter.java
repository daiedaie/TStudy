package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lijie.itstudy.R;

import java.util.List;

/**
 * Created by 礼节 on 2016/11/3.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Integer> list;
    private boolean[] bools;
    public GridViewAdapter(Context context,List<Integer> list,boolean[] bools){
        this.bools=bools;
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view;
        convertView= LayoutInflater.from(context).inflate(R.layout.head_gridview,null);
        view= (ImageView) convertView.findViewById(R.id.view);
        view.setBackgroundResource(list.get(position));
        if(bools[position]){
            view.getBackground().setAlpha(102);
        }else{
            view.getBackground().setAlpha(255);
        }

        return convertView;
    }
}
