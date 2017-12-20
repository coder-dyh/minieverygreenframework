package org.framework.view.impl;

import org.framework.view.ViewResult;

import java.io.IOException;

public class RedirectView extends ViewResult{

    private String redirectPath;

    public RedirectView(String redirectPath){
        this.redirectPath=redirectPath;
    }
    public RedirectView(){

    }

    @Override
    public void dealViewResult() {
        try {
            super.getResponse().sendRedirect(redirectPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
