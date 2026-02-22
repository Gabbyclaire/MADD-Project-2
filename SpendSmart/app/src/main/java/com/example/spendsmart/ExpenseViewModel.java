package com.example.spendsmart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseRepository repository;
    private LiveData<List<Expense>> allExpenses;
    private LiveData<Double> totalSpending;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExpenseRepository(application);
        allExpenses = repository.getAllExpenses();
        totalSpending = repository.getTotalSpending();
    }

    public void insert(Expense expense) {
        repository.insert(expense);
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

    public LiveData<Double> getTotalSpending() {
        return totalSpending;
    }
}