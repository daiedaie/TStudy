package ViewIndictor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.lijie.itstudy.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.CommentAdapter;
import Entity.CommentInView;

/**
 * Created by 礼节 on 2016/10/16.
 */
public class CommitFragment extends Fragment {
    private View view;
    private Context context;
    private ListView listView;
    private List<CommentInView> list;
    private CommentAdapter adapter;
    private ImageView img_save;
    private EditText et_context;
    public CommitFragment(Context context){
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.indrcator_commit, null);
        init();
        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setView(et_context,0,10,0,10);
                builder.setTitle("提示");
                builder.setMessage("你想对这个课程说什么？");
                builder.show();
            }
        });

        return view;
    }

    private void init(){
        listView= (ListView) view.findViewById(R.id.listview_commit);
        img_save= (ImageView) view.findViewById(R.id.save_commit);
        list=new ArrayList<CommentInView>();
        et_context=new EditText(context);

        adapter=new CommentAdapter(context,list);
        listView.setAdapter(adapter);
    }
}
