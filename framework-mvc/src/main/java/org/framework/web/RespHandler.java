package org.framework.web;

import org.framework.web.view.ViewResult;
import org.framework.web.view.impl.DefaultView;

/**
 * Create by coder_dyh on 2017/12/27
 * 视图结果处理器
 */
public class RespHandler {

    /**
     * 处理视图结果
     * @param viewResult
     */
    public static void executeViewResult(Object viewResult){
        if(viewResult!=null){
            ViewResult result=(viewResult instanceof ViewResult) ? (ViewResult) viewResult
                    : new DefaultView(viewResult);
            result.dealViewResult();
        }
    }
}
