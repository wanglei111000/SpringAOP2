package com.st.sp.test;

import com.st.sp.api.Caculater;
import com.st.sp.impl.MyCaculater2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Tester {
    ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:/conf/applicationContext.xml");
    @Test
    public void test(){
        //出现这个异常是缺少了aop 的jar 包
        //org.springframework.beans.factory.BeanDefinitionStoreException: Unexpected exception parsing XML
        // document from class path resource [conf/applicationContext.xml];
        // nested exception is java.lang.NoClassDefFoundError: org/springframework/aop/TargetSource
        //如果被代理的类是实现了接口的类 ，则需要 获取的时候使用其接口类来作或获取对象  jdk 动态代理
        //如果被代理的类是没有实现接口的类 ， 直接使用 其对象来获取对象  CGLIB 动态代理

        Caculater caculater = (Caculater)ioc.getBean(Caculater.class);
        caculater.add(1,2);
        caculater.min(10,6);
        caculater.multi(10,6);
        caculater.div(2,1);
        caculater.div(2,0);
//执行结果
//        add方法开始运行,参数是[1, 2]
//        add方法结束
//        add方法正常运行完成,参数是[1, 2],结果是:3
//        min方法开始运行,参数是[10, 6]
//        min方法结束
//        min方法正常运行完成,参数是[10, 6],结果是:4
//        multi方法开始运行,参数是[10, 6]
//        multi方法结束
//        multi方法正常运行完成,参数是[10, 6],结果是:60
//        div方法开始运行,参数是[2, 1]
//        div方法结束
//        div方法正常运行完成,参数是[2, 1],结果是:2
//        div方法开始运行,参数是[2, 0]
//        div方法结束
//        div方法运行异常,异常信息:java.lang.ArithmeticException: / by zero


//        System.out.println(caculater);
//        System.out.println(caculater.getClass());
//        执行结果:
//        com.st.sp.impl.MyCaculater@d9345cd
//        class com.sun.proxy.$Proxy9

//        MyCaculater2 myCaculater2 = (MyCaculater2)ioc.getBean(MyCaculater2.class);
//        myCaculater2.add(1,2);
//        System.out.println(myCaculater2);
//        System.out.println(myCaculater2.getClass());

//执行结果：
//        com.st.sp.impl.MyCaculater2@290222c1
//        class com.st.sp.impl.MyCaculater2
//可以看出 直接对对象做切面的时候 使用的是MyCaculater2 类本身 ,这里实际上是 时使用的 字节码的增强技术 通过子类来调用了父类的方法

    }
}
