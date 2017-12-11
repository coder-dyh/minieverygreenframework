package org.framework.beans;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

    //存放单例的容器
    private static Map<String,Object> singleton=new HashMap<>();

    //存放原型的容器
    private static Map<String,Object> protoType=new HashMap<>();

    //受容器管理的类的别名和类的Class信息
    private static Map<String,Class<?>> componentNameClass=new HashMap<>();

    //受容器管理的类的类别名
    private static String componentName;

    public BeanFactory(String packagePath) throws BeanException{
        init(packagePath);
    }

    /**
     * 初始化对象，将指定的对象初始化好放入容器中保存
     */
    private static void init(String packagePath) throws BeanException{

        //扫描获取所有的Class文件的完整类名
        List<String> list= Scan.scan(packagePath);
        try {
            for(String s:list){

                Class<?> clazz=Class.forName(s);
                if(clazz.isAnnotationPresent(Component.class)){
                    if(clazz.getAnnotation(Component.class).value()!=null) {
                        //获得容器管理的类别名
                        componentName = clazz.getAnnotation(Component.class).value();
                    }else{
                        componentName = toLowerCaseFirstOne(clazz.getSimpleName());
                    }

                    if(clazz.isAnnotationPresent(Scope.class) || clazz.getAnnotation(Scope.class).value().equals("singleton")){
                        componentNameClass.put(componentName,clazz);
                        singleton.put(componentName,clazz.newInstance());
                    }else {
                        componentNameClass.put(componentName,clazz);
                        protoType.put(componentName,clazz.newInstance());
                    }
                }
            }

            traverseComponent();

        } catch (Exception e) {
            throw new BeanException(e);
        }
    }


    /**
     * 遍历所有受容器管理的类（traverse遍历）
     * @throws IntrospectionException
     * @throws IllegalAccessException
     */
    private static void traverseComponent() throws IntrospectionException,InstantiationException,IllegalAccessException,InvocationTargetException {

        PropertyInject propertyInject=new PropertyInject();
        SetMethodProperty setMethodProperty=new SetMethodProperty();

        //循环取出每一个受容器管理的类
        for (String key : componentNameClass.keySet()) {
            Class<?> clazz=componentNameClass.get(key);
            InjectExecutor.executionInject(key,clazz);

        }
    }

    /**
     * 获取对应的对象的实例,需要强转
     * @param name
     * @return
     */
    public static Object getBean(String name){
        Object bean = singleton.get(name);
        if(bean != null){
            return bean;
        }else{
            return protoType.get(name);
        }
    }

    /**
     * 获取对应的对象的实例,不需要强转
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static  <T> T getBean(String name,Class<?> clazz){
        Object bean = singleton.get(name);
        if(bean != null){
            return (T)bean;
        }else{
            return (T)protoType.get(name);
        }
    }

    public static Map<String, Class<?>> getComponentNameClass() {
        return componentNameClass;
    }

    /**
     * 将字符串首字母转小写
     * @param s
     * @return
     */
    private static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

}
