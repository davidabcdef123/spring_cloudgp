package com.david.spring.reactive.loader;

import java.util.concurrent.*;

/**
 * Created by sc on 2/27/2019.
 */
public class ParallelDataLoader extends DataLoader {

    @Override
    protected void doLoad(){
        ExecutorService executorService= Executors.newFixedThreadPool(3);//创建线程池
        CompletionService completionService=new ExecutorCompletionService(executorService);
        completionService.submit(super::loadConfigurations,null);
        completionService.submit(super::loadUsers, null);
        completionService.submit(super::loadOrders, null);

        int count=0;
        while (count<3){
            if(completionService.poll()!=null){//代表完成
                count++;
            }
        }
        executorService.shutdown();
    }

    public static void main(String[] args) {
        new ParallelDataLoader().load();
    }
}
