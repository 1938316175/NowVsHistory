package com.example.administrator.nowvshistory;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7 0007.
 */
public class InformationActivity extends AppCompatActivity{

    private TextView tx_title;
    private TextView tx_content;
    private ImageView image;
    private String TAG = "InformationActivity";
    private List<String> list_data = new ArrayList<String>();
    private List<EventMessage.Result> list = new ArrayList<EventMessage.Result>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informationactivity_layout);

        //初始化控件
        initView();
        //加载内容
        String event_title = getIntent().getStringExtra("event_title");
        String event_pic = getIntent().getStringExtra("event_id");
        String event_info = getIntent().getStringExtra("event_info");

        try{
            HttpHelper.loadImage("http://"+event_pic,
                    new HttpCallBackListener() {
                        @Override
                        public void onFinish(Object response) {
                            image.setImageBitmap((Bitmap) response);
                            Log.e("wangbin","图片加载");
                        }
                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "图片出错");
                        }
                    });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initView() {
        tx_title = (TextView)findViewById(R.id.tx_title);
        tx_content = (TextView)findViewById(R.id.tx_content);
        image = (ImageView)findViewById(R.id.image);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 3){
                list_data.clear();
                //获取返回的事件信息集合
                list = GsonToString.getInformation(msg.obj.toString());
                if(list != null){
                    for(EventMessage.Result result : list){
                        tx_title.setText(result.getTitle());
                        tx_content.setText(result.getContent());
                        image.setImageBitmap((Bitmap)msg.obj);

                    }
                }else {
                    Toast.makeText(InformationActivity.this,"错误的请求参数",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
