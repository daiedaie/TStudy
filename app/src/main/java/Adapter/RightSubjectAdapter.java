package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lijie.itstudy.R;

import java.util.List;

import Entity.TSubject;

/**
 * Created by 礼节 on 2016/10/15.
 */
public class RightSubjectAdapter extends BaseAdapter {
    private Context context;
    private List<TSubject> list;
    public RightSubjectAdapter(Context context, List<TSubject> list){
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
        TextView tv;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.right_item,null);
        }
        tv= (TextView) convertView.findViewById(R.id.tvh);
        tv.setText(list.get(position).getSubjectName());

        return convertView;
    }
}
