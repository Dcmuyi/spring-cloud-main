package com.muyi.code.design;

//装饰器模式-把一个类的接口装饰成另一个类的接口，使其可以兼容原本接口
interface target {
    void method1();
    void method2();
}

class Adaptee {
    public void method1() {
        System.out.println("method1");
    }
}

public class Adapter extends Adaptee implements target {
    @Override
    public void method1() {
        System.out.println("method 11");
    }

    public void method2() {
        System.out.println("method2");
    }
}
