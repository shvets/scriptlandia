public class TestVolatile3 implements Runnable {  
    private boolean stopRequested = false;  
    private int i = 0;  
  
    private final Object lock1 = new Object();  
    private final Object lock2 = new Object();  
  
    public void run() {  
        while(!stopRequested) {  
            synchronized(lock1) {}  
            i++;  
        }  
    }  
  
    public void stop() {  
        stopRequested = true;  
        synchronized(lock2) {}  
    }  
  
    public static void main(String[] args) throws InterruptedException {  
        TestVolatile3 tv = new TestVolatile3();  
        new Thread(tv, "Neverending").start();  
        Thread.sleep(1000);  
        tv.stop();  
    }  
}  