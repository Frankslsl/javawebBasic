package com.accountServlet.bean;

import java.util.Date;

/**
 *
 */
public class Account {
    private int id;
    private String name;
    private double balance;
    private Date birthday;

    public Account() {
    }

    public Account(int id, String name, double balance, Date birthday) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", birthday=" + birthday +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
