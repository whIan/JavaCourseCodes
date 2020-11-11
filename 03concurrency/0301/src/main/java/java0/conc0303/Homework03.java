package java0.conc0303;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework03 {
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        int result = CompletableFuture.supplyAsync(()-> {
            System.out.println("1.CompletableFuture");
            return sum();
        }).join();;
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("2.FutureTask");
                return sum();
            }
        });
        new Thread(task).start();
        System.out.println("异步计算结果为："+task.get());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> fresult = executor.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                System.out.println("3.Future");
                return sum();
            }
        });
        executor.shutdown();
        System.out.println("异步计算结果为："+fresult.get());


        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        
        // 然后退出main线程
    }
    
    private static int sum() {
        return fibo(36);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
