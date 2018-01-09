package cn.edu.hstc.JavaClassLoader;

public class MsgHandle implements Runnable {
    public void run() {
        while(true){
            BaseManager manager = ManagerFactory.getManager(ManagerFactory.CLASS_NAME);
            manager.logic();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
