import java.util.concurrent.TimeUnit;  
  
public class TestVolatile4 implements Runnable {  
    private A wrapper = new A();  // same as example 1: should be volatile or A.stopRequest should be volatile
//    private volatile A wrapper = new A();  
  
    public void run() {  
        while(!wrapper.stopRequested) {  
            // do something  
        }  
    }  
  
    public void stop() {  
        wrapper.stopRequested = true;  
    }  
  
    public static void main(String[] args) throws InterruptedException {  
        TestVolatile4 tv = new TestVolatile4();  
        new Thread(tv, "Neverending").start();  
        // let main thread sleep for 1 second before requesting  
        // Neverending to stop  
        TimeUnit.SECONDS.sleep(1);  
        tv.stop();  
    }  
}  
  
class A {  
    //volatile boolean stopRequested = false;
    boolean stopRequested = false;    
}  