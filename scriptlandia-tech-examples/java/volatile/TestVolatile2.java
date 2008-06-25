public class TestVolatile2 implements Runnable {  
    private boolean stopRequested = false;
//    private volatile boolean stopRequested = false;    
    private int i = 0;  
  
    public void run() {  
        while(!stopRequested) { 
            System.out.println(i++);  // call to System.out.println masks the nessecity of "volatile"
        }  
    }  
  
    public void stop() {  
        stopRequested = true;  
    }  
  
    public static void main(String[] args) throws InterruptedException {  
        TestVolatile2 tv = new TestVolatile2();  
        new Thread(tv, "Neverending").start();  
        Thread.sleep(1000);  
        tv.stop();  
    }  
}  