package com.st.sp.bean;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class MyAspect2 {
    //切入点表达式  execution(访问权限 返回值 方法全路径(参数列表))
    //execution(public int com.st.sp.impl.MyCaculater.add(int,int))
    //还可以用 *  和  .. 来使用在表达式中
    //execution(public int com.st.sp.impl.MyCacul*r.*(int,int))   表示 路径中类以MyCacul 开头 ，r 结尾的类的 任意方法 ，但是 参数类型必须都是 int 的方法
    //execution(public int com.st.sp.impl.MyCacul*r.*(..))   表示 路径中类以MyCacul 开头 ，r 结尾的类的 任意方法 ，参数可以是任意的类型合格数
    //execution(public int *.MyCacul*r.*(..))   任意 路径 下 路径中类以MyCacul 开头 ，r 结尾的类的 任意方法 ，参数可以是任意的类型合格数
    //execution(public int com.st..MyCacul*r.*(..))  com.st包下 任意路径下 路径中类以MyCacul 开头 ，r 结尾的类的 任意方法 ，参数可以是任意的类型合格数

    //spring 对通知方法的要求不严格  execution 的返回类型 可以省去
    //  但是 使用 void  不行
    // 但是参数 列表 很重要 必须告诉切面 参数是用来干嘛的
    // 如 ： throwing = "e"   发生异常时用来接受异常信息的
    // returning = "result"   正确返回的时候 用来接收 执行结果
    //方法开始时调用时使用
    @Before("execution(public int com.st.sp.impl.MyCaculater.*(..))")
    public static void before(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();  //通过连接点获取方法名称
        Object[] parateters = joinPoint.getArgs();  //通过连接点获取参数列表
        System.out.println(methodName + "方法开始运行,参数是" + Arrays.asList(parateters));
    }

    //方法最终结束时使用
    @After("execution(public int com.st.sp.impl.MyCaculater.*(..))")
    public static void after(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();  //通过连接点获取方法名称
        System.out.println(methodName + "方法结束");
    }

    //方法异常时使用
    @AfterThrowing(value = "execution(public int com.st.sp.impl.MyCaculater.*(..))", throwing = "e")
    public static void afterThrowing(JoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();  //通过连接点获取方法名称
        System.out.println(methodName + "方法运行异常,异常信息:" + e);
    }

    //方法正常返回时使用
    @AfterReturning(value = "execution(public int com.st.sp.impl.MyCaculater.*(..))", returning = "result")
    public static void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();  //通过连接点获取方法名称
        Object[] parateters = joinPoint.getArgs();  //通过连接点获取参数列表
        System.out.println(methodName + "方法正常运行完成,参数是" + Arrays.asList(parateters) + ",结果是:" + result);
    }

}
