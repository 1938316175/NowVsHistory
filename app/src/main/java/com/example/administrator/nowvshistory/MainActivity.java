package com.example.administrator.nowvshistory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static int GET_TEXT = 1;
    private static String TAG = "NowVsHistory";
    private Button bt_send;
    private TextView tv_text;
    private ListView listView;
    private List<String> list_data = new ArrayList<String>();
    private List<Response.Result> list = new ArrayList<Response.Result>();
    private ArrayAdapter<String> adapter;
    private String month, day;
    private EditText ed_month, ed_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_send = (Button)findViewById(R.id.bt_send);
        bt_send.setOnClickListener(listener);
//        tv_text = (TextView)findViewById(R.id.tv_text);
        listView = (ListView)findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list_data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("wangbin",list.size()+"");
                if(list != null) {
                    Toast.makeText(MainActivity.this, list.get(position).getDes(),Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(MainActivity.this, InformationActivity.class);
//                    intent.putExtra("event_id",list.get(position).get_id());
//                    startActivity(intent);
                }
            }
        });

        ed_day = (EditText)findViewById(R.id.ed_day);
        ed_month = (EditText)findViewById(R.id.ed_month);

        ed_day.setText("1");
        ed_month.setText("1");

        try{
            HttpHelper.sendHttpRequest("http://api.juheapi.com/japi/toh?key=0cf65231bc24fcd3c780edc64c68d48b&v=1.0&month=" + 1 + "&day=" + 1,
                    new HttpCallBackListener() {
                @Override
                public void onFinish(Object response) {
                    Message message = new Message();
                    message.what = GET_TEXT;
                    message.obj = response;
                    handler.sendMessage(message);
                }
                @Override
                public void onError(Exception e) {
                    Log.e(TAG, "请求数据出错");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                list_data.clear();
                //获取返回的事件信息集合
                list = GsonToString.getEvent(msg.obj.toString());
                if(list != null){
                    for(Response.Result result : list){
                        list_data.add(result.getTitle());
                    }
                    //获取各个事件的标题
                    adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,list_data);
                    listView.setAdapter(adapter);
                    //tv_text.setText(msg.obj.toString());
                }else {
                    Toast.makeText(MainActivity.this,"错误的请求参数，请检查输入日期是否出错",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            month = ed_month.getText().toString();
            day = ed_day.getText().toString();
                try{
                    HttpHelper.sendHttpRequest("http://api.juheapi.com/japi/toh?key=0cf65231bc24fcd3c780edc64c68d48b&v=1.0&month=" + month + "&day=" + day,
                            new HttpCallBackListener() {
                        @Override
                        public void onFinish(Object response) {
                            Message message = new Message();
                            message.what = GET_TEXT;
                            message.obj = response;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "请求数据出错");
                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"日期输入有误",Toast.LENGTH_SHORT).show();
                }
        }
    };


}
