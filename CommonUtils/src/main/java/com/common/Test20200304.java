package com.common;

import java.util.Scanner;

public class Test20200304 {

    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);

        System.out.print("水仙花数有：");
        for(int num = 100; num < 1000; num++){
            if(isNarcissisticNum(num))
                System.out.println(" " + num);
        }
    }

    //一个判断正整数是否为水仙花数的方法
    private static boolean isNarcissisticNum(int num) {
        // TODO Auto-generated method stub
        int a = num / 100;			//分离出百位 a
        int b = (num / 10) % 10;	//分离出十位 b
        int c = num % 10;			//分离出个位 c
        int sum = a * a * a + b * b * b + c * c * c;
        if(sum == num)
            return true;
        else
            return false;
    }

}
