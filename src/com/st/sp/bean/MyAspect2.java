package com.st.sp.bean;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class MyAspect2 {

    //抽取公用的切入点表达式
    //1：任意编写一个 没有方法体的void 修饰的方法
    //2: 添加注解和切入点表达式 @Pointcut("execution(public int com.st.sp.impl.MyCaculater.*(..))")
    //3： 将 myEx()  加入到 每一个切面方法中 如：  @Before("myEx()")
    @Pointcut("execution(public int com.st.sp.impl.MyCaculater.*(..))")
    public void myEx(){};


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
    //@Before("myEx()")
    public static void before(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();  //通过连接点获取方法名称
        Object[] parateters = joinPoint.getArgs();  //通过连接点获取参数列表
        System.out.println(methodName + "方法开始运行,参数是" + Arrays.asList(parateters));
    }

    //方法最终结束时使用
    //@After("myEx()")
    public static void after(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();  //通过连接点获取方法名称
        System.out.println(methodName + "方法结束");
    }

    //方法异常时使用
    //@AfterThrowing(value = "myEx()", throwing = "e")
    public static void afterThrowing(JoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();  //通过连接点获取方法名称
        System.out.println(methodName + "方法运行异常,异常信息:" + e);
    }

    //方法正常返回时使用
    //@AfterReturning(value = "myEx()", returning = "result")
    public static void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();  //通过连接点获取方法名称
        Object[] parateters = joinPoint.getArgs();  //通过连接点获取参数列表
        System.out.println(methodName + "方法正常运行完成,参数是" + Arrays.asList(parateters) + ",结果是:" + result);
    }

    /***
     * 环绕通知 ： Spring 中最强大的通知类型
     * @Around  : 实际上就是 动态代理
     * 当别的通知类型和环绕通知都存在的时候 ： 环绕通知得优先级更高,因为其它通知也得等待
     * 环绕的proceedingJoinPoint.proceed(args); 这个方法执行了才能执行别的通知
     * 如果仅仅需要 通知，不需要执行动态代理的时候 用普通的通知 ，要执行动态代理用环绕通知
     * 如果有别的 切面类需要 使用同一个切面里面的方法时 ，需要在另一个切面中 添加 现有切面表达式的 全路径
     * @After("com.st.sp.bean.MyAspect2.myEx()")   写在需要引用的切面类中
     * **/
    @Around("myEx()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object[] args = proceedingJoinPoint.getArgs();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object result  = null;
        try {
            // 通过反射执行方法proceedingJoinPoint.proceed(args);
            System.out.println("环绕前置通知,"+methodName+" 方法开始执行"+" 参数是:"+Arrays.asList(args));
            result = proceedingJoinPoint.proceed(args);
            System.out.println("环绕返回通知,"+methodName+" 方法执行完成得到结果："+ result);
        }catch (Exception e){
            System.out.println("环绕异常通知,"+methodName+" 方法执行出现异常："+ e);
        }finally {
            System.out.println("环绕后置通知,"+methodName+" 方法执行结束");
        }
        return result;  //不能随意串改
    }

    // aop 的作用:
    // 记录日志
    // 权限校验
    // 安全校验
    // 事务控制
}
