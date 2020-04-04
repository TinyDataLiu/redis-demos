package com.alice.springboot.redis;


public class Test {
    public static void main(String[] args) {
        String str = "10245abc";
        char[] value = new char[20];
        str.getChars(0, str.length(), value, 0);
        System.out.println(new String(value));
    }
}
