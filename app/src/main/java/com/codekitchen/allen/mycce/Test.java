package com.codekitchen.allen.mycce;

/**
 * Created by User on 2015/10/4.
 */
public class Test {

    int i;
    int j = 7;


    public static void main(String[] args){

        Test t = new Test();
        System.out.println("t.i(1) = "+t.i);

        t.i = t.i%t.j;

        System.out.println("t.i(2) = "+t.i);
    }
}
