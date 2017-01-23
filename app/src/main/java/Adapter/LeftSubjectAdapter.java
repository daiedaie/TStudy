package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lijie.itstudy.R;

import java.util.List;

import Entity.TClass;

/**
 * Created by 礼节 on 2016/10/15.
 */
public class LeftSubjectAdapter extends BaseAdapter {
    private Context context;
    private List<TClass> list;
    private int i;//要改变背景的item（用户选中）
    public LeftSubjectAdapter(Context context, List<TClass> list, int i){
        this.context=context;
        this.list=list;
        this.i=i;
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
        TextView tvB,tvContent;
        RelativeLayout layout;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.left_item,null);
        }
        tvB= (TextView) convertView.findViewById(R.id.left_t);
        tvContent= (TextView) convertView.findViewById(R.id.left_content);
        layout= (RelativeLayout) convertView.findViewById(R.id.left_ralative);
        if(i==position){
            tvB.setBackgroundColor(Color.rgb(40,194,119));
            layout.setBackgroundColor(Color.rgb(230,230,250));
        }else{
            tvB.setBackgroundColor(Color.rgb(207,207,207));
            layout.setBackgroundColor(Color.rgb(207, 207, 207));
        }
        tvContent.setText(list.get(position).getClassName());

        return convertView;
    }
}
