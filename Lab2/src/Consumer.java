import java.util.Queue;
import java.util.Random;

public class Consumer implements Runnable {
    Queue<Integer> buffer;
    int its;
    Random gen = new Random();

    public Consumer(Queue<Integer> buffer, int its){
        this.buffer = buffer;
        this.its = its;
    }

    @Override
    public synchronized void run() {
        Integer current;
        for(int i=0; i<its; i++) {
            synchronized (buffer) {
                while (buffer.isEmpty()){
                    try{
                        buffer.wait();
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                current = buffer.remove();
                System.out.println("Consumed " + current);
                buffer.notifyAll();
                try {
                    Thread.sleep(gen.nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
