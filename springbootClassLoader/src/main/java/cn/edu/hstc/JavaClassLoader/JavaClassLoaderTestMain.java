package cn.edu.hstc.JavaClassLoader;

public class JavaClassLoaderTestMain {
    public static void main(String[] args) {
        new Thread(new MsgHandle()).start();
    }
}
