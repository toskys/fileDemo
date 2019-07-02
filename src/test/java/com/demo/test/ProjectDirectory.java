package com.demo.test;

import org.junit.Test;

public class ProjectDirectory extends BaseTest {

    @Test
    public void test() {
        System.out.println(System.getProperty("user.dir")+"\\src\\main\\fileData");
    }
}
