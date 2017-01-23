package Adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lijie.itstudy.R;

import java.util.List;
import Entity.TCourse;
import Utils.Util;

/**
 * Created by 礼节 on 2016/10/15.
 */
public class CourseAdapter extends BaseAdapter {
    private Context context;
    private List<TCourse> list;
    public CourseAdapter(Context context, List<TCourse> list){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView img;
        TextView tv_content,tv_time,tv_people;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.course_item,null);
        }
        img= (ImageView) convertView.findViewById(R.id.courseitem_img);
        tv_people= (TextView) convertView.findViewById(R.id.courseitem_people);
        tv_time= (TextView) convertView.findViewById(R.id.courseitem_time);
        tv_content= (TextView) convertView.findViewById(R.id.courseitem_content);

        img.setBackground(new BitmapDrawable(Util.setbitmap(list.get(position).getCourseImg())));
        tv_people.setText(list.get(position).getCoursePeople()+"人在学");
        tv_time.setText("更新时间："+list.get(position).getCourseTime());
        tv_content.setText(list.get(position).getCourseName());

        return convertView;
    }
}
