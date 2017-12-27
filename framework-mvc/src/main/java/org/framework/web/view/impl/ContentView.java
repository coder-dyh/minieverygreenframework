package org.framework.web.view.impl;

import com.google.gson.Gson;
import org.framework.web.view.ViewResult;

import java.io.IOException;

public class ContentView extends ViewResult{

    private String msg="响应成功！";

    public ContentView(){

    }
    public ContentView(String msg){
        this.msg=msg;
    }

    public ContentView(Object obj){
        this.msg=new Gson().toJson(obj);
    }

    @Override
    public void dealViewResult() {
        super.getResponse().setContentType("application/json;charset=utf-8");
        try {
            super.getResponse().getWriter().println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toJson(Object obj){
        msg=new Gson().toJson(obj);
    }
    public void toJsonAndResp(Object obj){
        dealViewResult();
    }
}
