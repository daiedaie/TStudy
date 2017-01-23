package Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entity.TCourse;

/**
 * Created by 礼节 on 2016/10/8.
 */
public class Util {
    //当前要加载的课程信息
    public static TCourse course;
    //记录指示器的位置，确保切换View数据同步
    public static int y=0;//ScrollView下滑的高度
    //网络请求地址
    public static String IP_CONFIG="http://119.29.248.212:8080/Tstudy/CourseServlet?action=";
    public static String Ip="http://119.29.248.212:8080/Tstudy";
    //设置透明沉浸栏
    public static void settransparentView(Activity activity){
        if(Build.VERSION.SDK_INT >=  4.4) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //sdk21以上
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    //为GridView加载数据项(数组转为Map对象)
    public static  List<Map<String, Object>> getData(int[] icon,String[] to){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img",icon[i]);
            map.put("text",to[i]);
            list.add(map);
        }

        return  list;
    }

    //修改RadioButton选中样式
    public static void editTopDrawable(Context context,RadioButton b2,int d){
        Drawable img_top;
        Resources res=context.getResources();
        img_top=res.getDrawable(d);
        img_top.setBounds(0,0,50,50);
        b2.setCompoundDrawables(null, img_top, null, null);
        b2.setTextColor(Color.rgb(40, 194, 119));
    }

    //恢复RadioButton默认样式
    public static void defaltTopDrawable(Context context,RadioButton b2,int d){
        Drawable img_top;
        Resources res=context.getResources();
        img_top=res.getDrawable(d);
        img_top.setBounds(0,0,50,50);
        b2.setCompoundDrawables(null, img_top, null, null);
        b2.setTextColor(Color.rgb(0, 0, 0));
    }

    //图片地址转bitmap
    private static Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static Bitmap setbitmap(final String str){
        final Bitmap[] bitmap = {null};
        Thread d=new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap[0] = getBitmap(Ip + str);
            }
        });
        d.start();
        try {
            d.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap[0];
    }


    public static String HttpString(String urlstr){
        String line;
        String str=null;
        StringBuffer buffer=new StringBuffer();
        try {
            URL url=new URL(urlstr);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            if((line=reader.readLine())!=null){
                buffer.append(line);
            }
            str=buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type","Content-Type: application/json");//非常关键 不可缺少
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
