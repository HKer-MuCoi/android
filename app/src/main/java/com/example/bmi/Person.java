package com.example.bmi;

import android.widget.TextView;

import java.util.Date;

public class Person {
    private int Id;
    private String Ht;
    private String Gt;
    private int T;
    private Float Cc;
    private Float Cn;
    private String Date;

    //Phương thức khởi không có tham số

    public Person() {
    }

    //Phương thức khởi tảo có tham số


    public Person(int id, String ht, String gt, int t, Float cc, Float cn, String date) {
        Id = id;
        Ht = ht;
        Gt = gt;
        T = t;
        Cc = cc;
        Cn = cn;
        Date = date;

    }



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getHt() {
        return Ht;
    }

    public void setHt(String ht) {
        Ht = ht;
    }

    public String getGt() {
        return Gt;
    }

    public void setGt(String gt) {
        Gt = gt;
    }

    public int getT() {
        return T;
    }

    public void setT(int t) {
        T = t;
    }

    public Float getCc() {
        return Cc;
    }

    public void setCc(Float cc) {
        Cc = cc;
    }

    public Float getCn() {
        return Cn;
    }

    public void setCn(Float cn) {
        Cn = cn;
    }
    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
