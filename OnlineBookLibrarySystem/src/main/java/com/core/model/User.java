package com.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;

    @Enumerated(EnumType.STRING)
    private SubscriptionPlan subscriptionPlan;

    private int monthlyTransactionCount = 0;
    private int borrowedBooksCount = 0;
    private int borrowedMagazinesCount = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public int getMonthlyTransactionCount() {
        return monthlyTransactionCount;
    }

    public void setMonthlyTransactionCount(int monthlyTransactionCount) {
        this.monthlyTransactionCount = monthlyTransactionCount;
    }

    public int getBorrowedBooksCount() {
        return borrowedBooksCount;
    }

    public void setBorrowedBooksCount(int borrowedBooksCount) {
        this.borrowedBooksCount = borrowedBooksCount;
    }

    public int getBorrowedMagazinesCount() {
        return borrowedMagazinesCount;
    }

    public void setBorrowedMagazinesCount(int borrowedMagazinesCount) {
        this.borrowedMagazinesCount = borrowedMagazinesCount;
    }
}
