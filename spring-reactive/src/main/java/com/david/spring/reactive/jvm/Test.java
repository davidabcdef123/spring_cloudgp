package com.david.spring.reactive.jvm;

/**
 * Created by sc on 2/28/2019.
 */
public class Test {

    public static void main(String[] args) {
        Test test=new Test();
        int data=9;
        BirthData birthData = new BirthData(30, 12, 2018);
        BirthData birthData1 = new BirthData(1, 2, 2000);
        test.change1(data);
        System.out.println("main birthData="+birthData);
        test.change2(birthData);
        test.change3(birthData1);
    }

    public void change1(int i){
        i=1234;
    }

    public void change2(BirthData b){
        System.out.println("change2 before birthData="+b);
        b = new BirthData(22, 2, 2004);
        System.out.println("change2 after birthData="+b);
    }
    public void change3(BirthData b){
        b.setDay(33);
    }
}
