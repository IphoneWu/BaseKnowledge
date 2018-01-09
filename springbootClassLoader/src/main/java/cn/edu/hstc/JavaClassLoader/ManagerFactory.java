package cn.edu.hstc.JavaClassLoader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ManagerFactory {
    //记录热加载类的加载信息
    private static final Map<String, LogicInfo> loadTimeMap = new HashMap<String, LogicInfo>();
    //要加载类的classpath
    public static final String CLASS_PATH = "E:/workspace/BaseKnowledge/springbootClassLoader/target/classes/";
    //要加载类的全名称（包名+类名）
    public static final String CLASS_NAME = "cn.edu.hstc.JavaClassLoader.BaseManagerImpl";

    public static BaseManager getManager(String className) {
        File loadFile = new File(CLASS_PATH + className.replaceAll("\\.", "/") + ".class");
        long lastModified = loadFile.lastModified();
        //loadTimeMap 不包含className 为key 的loadInfo信息，证明这个类没有被加载,需要加载
        if (loadTimeMap.get(className) == null) {
            load(className, lastModified);
        } else if (loadTimeMap.get(className).getLoadTime() != lastModified) {//加载类的时间戳变了，也需要重新加载
            load(className, lastModified);
        }
        return loadTimeMap.get(className).getBaseManager();
    }

    private static void load(String className, long lastModified) {
        MyClassLoader myClassLoader = new MyClassLoader(CLASS_PATH);
        Class<?> loadClass = null;
        try {
            loadClass = myClassLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        BaseManager manager = newInstance(loadClass);
        LogicInfo logicInfo = new LogicInfo(myClassLoader, lastModified);
        logicInfo.setBaseManager(manager);
        loadTimeMap.put(className, logicInfo);
    }

    //以反射的形式创建baseManager 的子类
    private static BaseManager newInstance(Class<?> loadClass) {
        try {
            return (BaseManager) loadClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
