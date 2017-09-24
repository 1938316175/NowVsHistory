package com.example.administrator.nowvshistory;

/**
 * Created by Administrator on 2017/2/25 0025.
 */
public interface  HttpCallBackListener {
    void onFinish(Object response);

    void onError(Exception e);
}
