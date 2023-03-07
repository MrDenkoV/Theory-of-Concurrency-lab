import java.util.LinkedList;
import java.util.Queue;

public class ProdNCons {

    public static void main(String[] args){
//        oneToOne();
//        equalMore();
//        uneqMore(5, 3, 15);
        uneqMore(3, 5, 15);
    }

    public static void oneToOne(){
        Queue<Integer> buffer = new LinkedList<>();
        Thread producer = new Thread(new Producer(buffer, 20));
        Thread consumer = new Thread(new Consumer(buffer, 20));
        producer.start();
        consumer.start();
    }

    public static void equalMore(){
        Queue<Integer> buffer = new LinkedList<>();
        for(int i=0; i<10; i++){
            new Thread(new Producer(buffer, 10)).start();
            new Thread(new Consumer(buffer, 10)).start();
        }
    }

    public static void uneqMore(int pro, int cons, int sum){
        Queue<Integer> buffer = new LinkedList<>();
        for(int i=0; i<pro; i++){
            new Thread(new Producer(buffer, sum/pro)).start();
        }
        for(int i=0; i<cons; i++) {
            new Thread(new Consumer(buffer, sum/cons)).start();
        }
    }


}
