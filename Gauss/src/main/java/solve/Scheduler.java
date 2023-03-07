package solve;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Scheduler {
    private ExecutorService serv;
    private int size;

    public Scheduler(int size){
        this.size = size;
        this.serv = Executors.newFixedThreadPool(size);
    }

    public void end(){
        serv.shutdown();
    }


    public void run(List<Runnable> op){
        try {
            List<Future<?>> mirai = op.stream().map(serv::submit).collect(Collectors.toList());
            while (!mirai.stream().allMatch(Future::isDone))
                Thread.sleep(100);
        } catch (Exception e) {
            System.out.println("RUN");
            e.printStackTrace();
            System.exit(2);
        }
    }

    private Double unpack(Future<Double> el){
        try{
            return el.get();
        } catch (Exception e){
            System.out.println("UNPACK");
            e.printStackTrace();
            System.exit(3);
        }
        return 0.0;
    }

    public List<Double> call(List<Callable<Double>> op){
        List<Future<Double>> mirai = op.stream().map(serv::submit).collect(Collectors.toList());
        try {
            while (!mirai.stream().allMatch(Future::isDone))
                Thread.sleep(100);
        } catch (Exception e) {
            System.out.println("CALL");
            e.printStackTrace();
            System.exit(2);
        }
        return mirai.stream().map(this::unpack).collect(Collectors.toList());
    }
}
