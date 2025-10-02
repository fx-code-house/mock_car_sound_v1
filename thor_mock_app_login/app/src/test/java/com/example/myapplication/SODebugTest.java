package com.example.myapplication;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * SO库单元测试
 * 运行命令: ./gradlew test --tests SODebugTest
 */
public class SODebugTest {
    
    private SODebugger debugger;
    
    @Before
    public void setUp() {
        // 在真实设备测试时需要加载so库
        try {
            System.loadLibrary("bridge");
            debugger = new SODebugger();
        } catch (UnsatisfiedLinkError e) {
            System.out.println("警告: 无法加载libbridge.so - " + e.getMessage());
            System.out.println("这个测试需要在Android设备上运行");
        }
    }
    
    @Test
    public void testAesInitialization() {
        if (debugger != null) {
            // 测试AES初始化不会抛出异常
            assertDoesNotThrow(() -> {
                debugger.testAesInit();
            });
        }
    }
    
    @Test 
    public void testAesEncryption() {
        if (debugger != null) {
            // 测试AES加密不会抛出异常
            assertDoesNotThrow(() -> {
                debugger.testAesEncrypt();
            });
        }
    }
    
    @Test
    public void testUnlockCommandBytes() {
        if (debugger != null) {
            // 测试解锁命令参数
            assertDoesNotThrow(() -> {
                debugger.testUnlockCommand();
            });
        }
    }
    
    @Test
    public void testAllCommands() {
        if (debugger != null) {
            // 测试所有命令
            assertDoesNotThrow(() -> {
                debugger.testMultipleCommands();
            });
        }
    }
    
    // Java 8兼容的assertDoesNotThrow实现
    private void assertDoesNotThrow(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            fail("Expected no exception, but got: " + e.getMessage());
        }
    }
}