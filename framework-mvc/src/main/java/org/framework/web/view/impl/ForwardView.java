package org.framework.web.view.impl;

import org.framework.web.view.ViewResult;

import javax.servlet.ServletException;
import java.io.IOException;

public class ForwardView extends ViewResult{

    private String forwardPath;

    public ForwardView(String forwardPath){
        this.forwardPath=forwardPath;
    }
    public ForwardView(){

    }

    @Override
    public void dealViewResult(){
        try {
            super.getRequest().getRequestDispatcher(forwardPath)
                    .forward(super.getRequest(),super.getResponse());
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
