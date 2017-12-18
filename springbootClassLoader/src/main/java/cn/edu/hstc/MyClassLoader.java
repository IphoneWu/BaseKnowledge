package cn.edu.hstc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 自定义java 类加载器
 */
public class MyClassLoader extends ClassLoader {
    private String classPath;//要加载类的路径
    public MyClassLoader(String classPath) {
        super(ClassLoader.getSystemClassLoader());
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return super.defineClass(name,data,0,data.length);
    }

    /**
     * 加载class文件中的内容
     * @param name
     * @return
     */
    private byte[] loadClassData(String name) {
        try {
            name = name.replace(".","/");
            FileInputStream fileInputStream = new FileInputStream(new File(classPath+name+".class"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int b = 0;
            if ((b = fileInputStream.read())!=-1){
                byteArrayOutputStream.write(b);
            }
            fileInputStream.close();
            return byteArrayOutputStream.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

}
