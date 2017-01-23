package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lijie.itstudy.R;

import java.util.List;

import Entity.TItem;

/**
 * Created by 礼节 on 2016/10/16.
 */
public class ChapterAdapter extends BaseAdapter {
    private Context context;
    private List<TItem> list;
    private int position1;
    private int lasteposition1=-1;
    public ChapterAdapter(Context context,List<TItem> list,int position1){
        this.context=context;
        this.list=list;
        this.position1=position1;
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
        TextView tv=null;
        if ("章节".equals(list.get(position).getChapterType())) {
            convertView = LayoutInflater.from(context).inflate(R.layout.parent_item, null);
            tv = (TextView) convertView.findViewById(R.id.parent_tv);
        } else {
            lasteposition1=position1;
            convertView = LayoutInflater.from(context).inflate(R.layout.son_item, null);
            LinearLayout layout= (LinearLayout) convertView.findViewById(R.id.layout2);
            tv = (TextView) convertView.findViewById(R.id.son_tv);
            if(position1==position) {
                tv.setTextColor(Color.rgb(40, 194, 119));
                layout.setBackgroundColor(Color.rgb(173,216,230));
            }
        }

        tv.setText(list.get(position).getChapterName());


        return convertView;
    }

}
