package org.iproute.dcl;

/**
 * DoubleCheckLockSingleton
 *
 * @author winterfell
 * @since 2022/2/8
 */
public class DoubleCheckLockSingleton {

    //    private static DoubleCheckLockSingleton instance;
    // 问题： 为什么要加 volatile
    private static volatile DoubleCheckLockSingleton instance;

    private DoubleCheckLockSingleton() {
    }

    public static DoubleCheckLockSingleton getInstance() {
        /*
        11 getstatic #2 <org/iproute/dcl/DoubleCheckLockSingleton.instance : Lorg/iproute/dcl/DoubleCheckLockSingleton;>

        14 ifnonnull 27 (+13)

        17 new #3 <org/iproute/dcl/DoubleCheckLockSingleton>

        20 dup

        21 invokespecial #4 <org/iproute/dcl/DoubleCheckLockSingleton.<init> : ()V>

        24 putstatic #2 <org/iproute/dcl/DoubleCheckLockSingleton.instance : Lorg/iproute/dcl/DoubleCheckLockSingleton;>

        27 aload_0
         */
        if (instance == null) {
            synchronized (DoubleCheckLockSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckLockSingleton();
                }
            }
        }
        return instance;
    }
}
/*
invokespecial：
Invoke instance method; direct invocation of instance initialization methods and methods of the current class and its supertypes

putstatic：
Set static field in class

invokespecial 和 putstatic 的指令重排序不会影响单线程下的对象创建和赋值
但是在多线程环境中，如果发生了指令重排序

假设发生了重排序：
成员变量还没有赋真正的值，构造方法还没有初始化

会导致另外一个线程获取到了半初始化状态的instance对象


instance = new DoubleCheckLockSingleton();可以分解为：

memory = allocate(); //  1. 分配对象内存
ctorInstance(memory); // 2. 初始化对象
instance = memory     // 3. 设置instance指向刚刚分配的内存


这里的问题就在于 2和3 会发生重排序，as-if-serial语义只满足于单线程
假设发生了2、3重排序，单线程不会出现问题，但是多线程可能就会拿到一个初始化好的对象

加入了volatile保证了 2和3不会重排序



 */