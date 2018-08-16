package com.bruce.metaauditor;

import java.util.Random;

/**
 * @Description
 * @Author Bruce Jiang
 * @Date 2018/8/15 22:04
 * @Version
 */
public class AppMain {
    public static void main(String[] args) {
        Random random = new Random();
        for(int i = 0; i < 10; i ++) {
            int x = random.nextInt(10) + 1;
            float y = random.nextFloat();
            System.out.println(x);
            System.out.println(y);
            System.out.println((int)(x*y*1024));
        }
    }
}
