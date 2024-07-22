package com.loony;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Alg alg=new Alg();
        Random random = new Random();
        alg.start(random);
    }
}