package ViewIndictor;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lijie.itstudy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.ChapterAdapter;
import Entity.TItem;
import Utils.Util;

/**
 * Created by 礼节 on 2016/10/16.
 */
public class ChapterFragment extends Fragment {
    private View view;
    private ListView listView;
    private List<TItem> list;
    private Context context;
    private String json="";
    private Handler handler;
    private int item;//listview位置
    public ChapterFragment(Context context,Handler handler){
        this.context=context;
        this.handler=handler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.indicator_chapter, null);
        init();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("视频".equals(list.get(position).getChapterType())){
                    Message msg=new Message();
                    Bundle b=new Bundle();
                    b.putString("url", list.get(position).getChapterUrl());
                    b.putString("name",list.get(position).getChapterName());
                    msg.setData(b);
                    handler.sendMessage(msg);
                    item=position-3;
                    ChapterAdapter adapter=new ChapterAdapter(context,list,position);
                    listView.setAdapter(adapter);
                    listView.setSelection(item);
                }
            }
        });

        return view;
    }

    private void init() {
        Thread d=new Thread(new Runnable() {
            @Override
            public void run() {
                json=Util.HttpString(Util.IP_CONFIG+"selItemByCourseId&id="+ Util.course.getCourseId());
            }
        });
        d.start();
        try {
            d.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            list=new ArrayList<TItem>();
            TItem item=null;
            JSONArray array=new JSONArray(json);
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                item=new TItem();
                item.setCourseId(object.getInt("courseId"));
                item.setChapterId(object.getInt("chapterId"));
                item.setChapterName(object.getString("chapterName"));
                item.setChapterType(object.getString("chapterType"));
                item.setChapterUrl(object.getString("chapterUrl"));
                list.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView= (ListView) view.findViewById(R.id.chapter_listview);
        ChapterAdapter adapter=new ChapterAdapter(context,list,-1);
        listView.setAdapter(adapter);
    }
}
