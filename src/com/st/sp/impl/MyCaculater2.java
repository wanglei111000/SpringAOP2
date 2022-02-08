package com.st.sp.impl;


import org.springframework.stereotype.Service;

//@Service
public class MyCaculater2  {

    public int add(int i, int j) {
        int result = i + j;
        return result;
    }


    public int min(int i, int j) {
        int result = i - j;
        return result;
    }


    public int multi(int i, int j) {
        int result = i * j;
        return result;
    }

    public int div(int i, int j) {
        int result = i / j;
        return result;
    }
}
