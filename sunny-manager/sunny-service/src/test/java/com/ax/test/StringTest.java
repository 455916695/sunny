package com.ax.test;

import org.junit.Test;

public class StringTest {

    @Test
    public void test() {
        String name = "123.234";
        name = name.substring(name.lastIndexOf('.'));
        System.out.println(name);
    }

}
