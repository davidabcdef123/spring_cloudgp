package com.david.spring.reactive.loader;

import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

/**
 * Created by sc on 2/27/2019.
 */
public class DataLoader {

    public final void load(){
        long startTime=System.currentTimeMillis();
        doLoad();
        long endTime=System.currentTimeMillis();
        System.out.println("load()总耗时:"+(endTime-startTime)+"毫秒");
    }

    protected void doLoad() {//穿行计算
        loadConfigurations();//耗时1s
        loadUsers();//耗时2s
        loadOrders();//耗时3s
    }//总耗时 1s+2s+3s=6s

    protected void loadOrders() {
        loadMock("loadOrders()",3);
    }

    protected void loadUsers() {
        loadMock("loadUsers()",2);
    }

    protected void loadConfigurations() {
        loadMock("loadConfigurations()",1);
    }

    protected void loadMock(String event, int senond) {
        long startTime=System.currentTimeMillis();
        long sleepMillion=TimeUnit.SECONDS.toMillis(senond);
        try {
            Thread.sleep(sleepMillion);
            long costTime=System.currentTimeMillis()-startTime;
            System.out.printf("[线程：%s] %s 耗时 ： %d 毫秒\n",Thread.currentThread().getName(),event,costTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String add(String aaa){
        return null;
    }

    public static void main(String[] args) {
        new DataLoader().load();
    }
}
