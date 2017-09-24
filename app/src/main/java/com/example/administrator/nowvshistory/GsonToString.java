package com.example.administrator.nowvshistory;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/2/25 0025.
 */
public class GsonToString {

    public static List<Response.Result> getEvent(String str){
        Response response = new Gson().fromJson(str, Response.class);
        if(response.getError_code() != 0){
            return null;
        }
        List<Response.Result> resultList = response.getResult();
        return resultList;
    }

    public static List<EventMessage.Result> getInformation(String str){
        EventMessage message = new Gson().fromJson(str, EventMessage.class);
        if(message.getError_code() != 0){
            return null;
        }
        List<EventMessage.Result> resultList = message.getResult();
        return resultList;
    }

}
