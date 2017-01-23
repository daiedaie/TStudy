package ViewIndictor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by 礼节 on 2016/10/16.
 */
public class ViewpagerIndicator extends LinearLayout {
    private Paint mPaint;
    private Path mPath;
    private int mTriangleWidth;
    private int mTriangleHeight;
    private static final float RADIO_WIDTH=1/6F;
    private int mInitTranslatitonX;
    private int mTranslationX;


    public ViewpagerIndicator(Context context) {
        this(context, null);
    }

    public ViewpagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        //初始化画笔
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#EBEBEB"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTriangleWidth= (int) (w/3*RADIO_WIDTH);
        mInitTranslatitonX=w/3/2-mTriangleWidth/2;
        initTrangle();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslatitonX + mTranslationX, getHeight() + 2);
        canvas.drawPath(mPath, mPaint);

        canvas.restore();

        super.dispatchDraw(canvas);
    }

    //初始化三角形
    private void initTrangle() {
        mTriangleHeight=mTriangleWidth/2;

        mPath=new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    //指示器跟随手指进行滚动
    public void scroll(int position, float positionOffset) {
        int tabWidth=getWidth()/3;
        mTranslationX= (int) (tabWidth*(positionOffset+position));
        invalidate();
    }
}
