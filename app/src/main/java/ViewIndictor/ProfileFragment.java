package ViewIndictor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lijie.itstudy.R;

import Utils.Util;

/**
 * Created by 礼节 on 2016/10/16.
 */
public class ProfileFragment extends Fragment {
    private RatingBar bar;
    private TextView tv_resever;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.indicator_profile, null);

        bar= (RatingBar) view.findViewById(R.id.ratingBar);
        tv_resever= (TextView) view.findViewById(R.id.reserve);
        bar.setRating((float) Util.course.getCourseMath());
        tv_resever.setText(Util.course.getCourseReserve());
        bar.setEnabled(false);

        return view;
    }
}
