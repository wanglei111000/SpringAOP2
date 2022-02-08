package com.st.sp.impl;

import com.st.sp.api.Caculater;
import org.springframework.stereotype.Service;

@Service
public class MyCaculater implements Caculater {
    @Override
    public int add(int i, int j) {
        int result = i + j;
        System.out.println("方法内部执行");
        return result;
    }

    @Override
    public int min(int i, int j) {
        int result = i - j;
        System.out.println("方法内部执行");
        return result;
    }

    @Override
    public int multi(int i, int j) {
        int result = i * j;
        System.out.println("方法内部执行");
        return result;
    }

    @Override
    public int div(int i, int j) {
        int result = i / j;
        System.out.println("方法内部执行");
        return result;
    }
}
