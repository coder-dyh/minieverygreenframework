package org.framework.web.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 此类多余，无法兼容macOS和Windows，改用ScanUtil
 */
public class Scan {

    //获得当前项目的绝对路径
    private static String path=Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //用于存放扫描好的class文件
    private static List<String> list=new ArrayList<>();

    /**
     * 扫描当前项目下的所有文件和文件夹，并找到所有的类的Class子节码文件
     *
     */
    public static List<String> scan(){

        readFile(path);
        return list;
    }

    private static void readFile(String path){
        try {
            path= URLDecoder.decode(path,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File file=new File(path);
        File[] files=file.listFiles();
        if(files!=null){
            for(File f:files){
                if(f.isFile()){
                    if(f.getName().endsWith(".class")){
                        list.add(analyzeFile(f.getAbsolutePath()));
                    }
                }else{
                    readFile(f.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 解析文件，获取对应的class文件
     * @param fileName
     * @return
     */
    private static String analyzeFile(String fileName){
        String projectPath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try {
            projectPath=URLDecoder.decode(projectPath,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fileName.substring(projectPath.length())
                .replace(".class","")
                .replace("/",".");
    }


    public static void main(String[] args) {
        scan();
        System.out.println(path);
        System.out.println(list.size());
        for(String s:list){
            System.out.println(s);
        }
    }

}

