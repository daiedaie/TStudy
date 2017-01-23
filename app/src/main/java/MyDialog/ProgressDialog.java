package MyDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.lijie.itstudy.R;

/**
 * Created by 礼节 on 2016/10/29.
 */
public class ProgressDialog extends AlertDialog {
    public ProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
    }
}
