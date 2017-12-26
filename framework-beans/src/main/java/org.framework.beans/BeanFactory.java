package org.framework.beans;

import org.framework.beans.test.ScanUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

    //存放单例容器
    public static Map<String,Object> singleton=new HashMap<>();
    //存放类的描述信息
    private static Map<String,Definition> prototype=new HashMap<>();

    /**
     * 对外提供的构造方法
     * @param packagePath
     * @throws BeanException
     */
    public BeanFactory(String packagePath) {
        init(packagePath);
    }

    public void init(String packagePath){
        System.out.println("获得的路径："+packagePath);
        try {
            analyseClass(packagePath);
            initSingleton();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BeanFactory(){

    }

    /**
     * 解析所有的类信息，构建描述定义信息
     * @param packagePath
     * @throws ClassNotFoundException
     */
    private static void analyseClass(String packagePath) throws ClassNotFoundException{
        //扫描指定包下面的所有Class文件获得完整类名
        List<String> list= ScanUtil.scan(packagePath);
        //保存所有的类描述信息
        setDefinition(list);
    }

    /**
     * 关闭容器
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void close() throws IllegalAccessException,InvocationTargetException{
        destroy();
        singleton.clear();
        prototype.clear();
    }

    /**
     * 销毁之前执行的一些操作
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void destroy() throws IllegalAccessException,InvocationTargetException{
        for(String s : prototype.keySet()){
            Method method=prototype.get(s).getDestroyMethod();
            if(method!=null){
                Object obj=getBean(s);
                method.invoke(obj);
            }
        }
    }

    /**
     * 保存类的描述定义信息
     * @param list
     * @throws ClassNotFoundException
     */
    private static void setDefinition(List<String> list) throws ClassNotFoundException{
        String componentName=null;
        String scope=null;
        for(String s : list){
            Class<?> clazz=Class.forName(s);
            if(clazz.isAnnotationPresent(Component.class)){
                    Definition definition=new Definition();
                    componentName=clazz.getAnnotation(Component.class).value();
                    if(componentName!=null){
                        definition.setComponent(componentName);
                    }else{
                        definition.setComponent(toLowerCaseFirstOne(clazz.getSimpleName()));
                    }

                if(clazz.isAnnotationPresent(Scope.class)){
                    scope=clazz.getAnnotation(Scope.class).value();
                    if(scope!=null){
                        definition.setScope(scope);
                    }else{
                        definition.setScope("singleton");
                    }
                }
                if(clazz.isAnnotationPresent(Inject.class)){
                    definition.setInjectName(clazz.getAnnotation(Inject.class).name());
                }
                ergodicMethod(clazz,definition);
                definition.setClazz(clazz);
                prototype.put(componentName,definition);
            }

        }
    }

    /**
     * 遍历所有方法找到初始化方法和销毁方法
     * @param clazz
     * @param definition
     */
    private static void ergodicMethod(Class<?> clazz,Definition definition){
        Method[] methods=clazz.getDeclaredMethods();
        for(Method method : methods){
            if(method.isAnnotationPresent(PostConstruct.class)){
                definition.setInitMethod(method);
            }
            if(method.isAnnotationPresent(PreDestroy.class)){
                definition.setDestroyMethod(method);
            }
        }
    }

    /**
     * 初始化单例容器
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void initSingleton()
            throws IllegalAccessException,InstantiationException,InvocationTargetException,IntrospectionException{
        for(String s : prototype.keySet()){
            Definition definition=prototype.get(s);
            Object obj=definition.getClazz().newInstance();
            if(definition.getScope().equals("singleton")){
                singleton.put(s,obj);
            }
            if(definition.getInitMethod()!=null){
                definition.getInitMethod().invoke(obj);
            }

        }

    }

    /**
     * 获取对象（需要自己强转类型）
     * @param componentName
     * @return
     */
    public Object getBean(String componentName){
        return getContainerBean(componentName);
    }

    /**
     * 获取对象（不需要强转类型）
     * @param componentName
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getBean(String componentName,Class<?> clazz){
        return (T)getContainerBean(componentName);
    }

    /**
     * 从容器中取出相对应的对象
     * @param componentName
     * @return
     */
    private Object getContainerBean(String componentName){
        String scope=prototype.get(componentName).getScope();
        Object obj=null;
        try {
            if(scope.equals("singleton")){
                obj=singleton.get(componentName);
            }else{
                obj=prototype.get(componentName).getClazz().newInstance();
                Method method=prototype.get(componentName).getInitMethod();
                if(method!=null){
                    method.invoke(obj);
                }
            }
            createBean(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return obj;
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

    /**
     * 构建bean对象准备注入
     * @param instance
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IntrospectionException
     */
    private void createBean(Object instance)
            throws InstantiationException,IllegalAccessException,InvocationTargetException,IntrospectionException{
        InjectExecutor.executionInject(instance,this);
    }

    public static Map<String, Object> getSingleton() {
        return singleton;
    }

    public static void setSingleton(Map<String, Object> singleton) {
        BeanFactory.singleton = singleton;
    }
}
