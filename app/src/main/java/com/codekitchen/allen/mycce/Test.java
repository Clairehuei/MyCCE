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

        int a = 130, b = 870;//中心座標
        int m = 220, n = 800;//當前座標
        int p = m-a, q = n-b;
        int moveX = 0, moveY=0;
        int acc = 15;//速度

        double z = Math.sqrt((p*p)+(q*q));

        moveX = (int)(p*acc/z);
        moveY = (int)Math.sqrt((acc*acc)-(moveX*moveX));

        System.out.println("z = "+z);
        System.out.println("moveX = "+moveX);
        System.out.println("moveY = "+moveY);
    }
}
