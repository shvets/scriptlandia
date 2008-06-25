public class TestVolatile1 implements Runnable {  
//    private volatile boolean stopRequested = false;
    private boolean stopRequested = false; // without "volatile" program is hanging
  
    public void run() {  
        while(!stopRequested) {  
            // do something here...  
        }  
    }  
  
    public void stop() {  
        stopRequested = true;  
    }  
  
    public static void main(String[] args) throws InterruptedException {  
        TestVolatile1 tv = new TestVolatile1();  
        new Thread(tv, "Neverending").start();  
        Thread.sleep(1000);  
        tv.stop();  
    }  
}  