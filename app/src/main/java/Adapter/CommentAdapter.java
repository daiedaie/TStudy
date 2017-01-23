package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.lijie.itstudy.R;

import java.util.List;

import Entity.CommentInView;

/**
 * Created by 礼节 on 2016/10/28.
 */
public class CommentAdapter extends BaseAdapter {
    private List<CommentInView> list;
    private Context context;
    public CommentAdapter(Context context,List<CommentInView> list){
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
        TextView tv_name=null,tv_time=null,tv_context=null,tv_com=null;
        ImageView img_head=null;
//        父评论ID等于0就是没有子留言
        if(list.get(position).getIsParent()==1){
            convertView= LayoutInflater.from(context).inflate(R.layout.pra_commit_item,null);
            tv_name= (TextView) convertView.findViewById(R.id.pra_commit_name);
            tv_time= (TextView) convertView.findViewById(R.id.pra_commit_time);
            tv_context= (TextView) convertView.findViewById(R.id.pra_commit_context);
            tv_com= (TextView) convertView.findViewById(R.id.pra_commit_com);
            img_head= (ImageView) convertView.findViewById(R.id.pra_commit_head);
        }else{
            convertView=LayoutInflater.from(context).inflate(R.layout.son_commit_item,null);
            tv_name= (TextView) convertView.findViewById(R.id.son_commit_name);
            tv_time= (TextView) convertView.findViewById(R.id.son_commit_time);
            tv_context= (TextView) convertView.findViewById(R.id.son_commit_context);
            tv_com= (TextView) convertView.findViewById(R.id.son_commit_com);
            img_head= (ImageView) convertView.findViewById(R.id.son_commit_head);
        }
        tv_name.setText(list.get(position).getUserName());
        tv_time.setText(list.get(position).getCommentTime());
        tv_context.setText(list.get(position).getCommentText());
        img_head.setImageBitmap(list.get(position).getUserImg());

        return convertView;
    }
}
