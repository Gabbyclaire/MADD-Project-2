package com.example.spendsmart;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExpenseRepository {
    private ExpenseDao expenseDao;
    private LiveData<List<Expense>> allExpenses;
    private LiveData<Double> totalSpending;
    private ExecutorService executorService;

    public ExpenseRepository(Application application) {
        SpendSmartDatabase database = SpendSmartDatabase.getInstance(application);
        expenseDao = database.expenseDao();
        allExpenses = expenseDao.getAllExpenses();
        totalSpending = expenseDao.getTotalSpending();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Expense expense) {
        executorService.execute(() -> {
            expenseDao.insert(expense);
            // Log successful insertion
            android.util.Log.d("ExpenseRepository", "Expense inserted: " + expense.getTitle());
        });
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

    public LiveData<Double> getTotalSpending() {
        return totalSpending;
    }
}