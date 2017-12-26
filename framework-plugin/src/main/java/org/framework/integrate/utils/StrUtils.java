package org.framework.integrate.utils;

/**
 * Create by coder_dyh on 2017/12/26
 */
public class StrUtils {

    public static String replace(String str){
        return str.replace("/",".");
    }

    public static void main(String[] args) {
        System.out.println(replace("org/framework/web"));;
    }
}
