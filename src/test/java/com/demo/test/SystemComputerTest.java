package com.demo.test;

import com.demo.system.SystemComputerConfg;
import org.junit.Test;

public class SystemComputerTest extends BaseTest {

    @Test
    public void test() {
        SystemComputerConfg systemComputerConfg = new SystemComputerConfg();
        System.out.println(systemComputerConfg.getSystemAvailableMemorySize());
        System.out.println(systemComputerConfg.getSystemMemorySize());
    }
}
