package com.serious.example.main;

import java.util.regex.Pattern;

/**
 * Created by Serious on 2017/6/29.
 */
public class Main {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
        System.out.println(pattern.matcher("lovemd@vip.qq.com").matches());
    }
}
