package org.framework.dispatcher;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * 类型处理器
 */
public class TypeExecutor {

    private static Iterator<TypeConvert> it;
    private static List<TypeConvert> list=new ArrayList<>();

    /*static {
        it= ServiceLoader.load(TypeConvert.class).iterator();
        System.out.println(it.hasNext());
    }*/

    static {
        list.add(new SimpleTypeConvert());
        list.add(new BeanTypeConvert());
        list.add(new ServletAPIConvert());
        it=list.iterator();
    }


    public Object execute(Parameter parameter) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Object object=null;
        if(it.hasNext()){
            object=it.next().convert(parameter,this);
        }
        return object;
    }

    public static void main(String[] args) {
        new TypeExecutor();
    }

}
