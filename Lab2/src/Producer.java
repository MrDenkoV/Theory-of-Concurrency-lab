import java.util.Queue;
import java.util.Random;

public class Producer implements Runnable{
    Queue<Integer> buffer;
    int its;
    Random gen = new Random();

    public Producer(Queue<Integer> buffer, int its){
        this.buffer = buffer;
        this.its = its;
    }

    @Override
    public synchronized void run() {
        for(int i=0; i<its; i++){
            synchronized (buffer) {
                while(buffer.size()>7){
                    try {
                        buffer.wait();
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                buffer.add(i);
                try {
                    Thread.sleep(gen.nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Produced " + i);
                buffer.notifyAll();
            }
        }
    }
}
