package com.example.spendsmart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 1. Inflate the layout
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // 2. Find both the ProgressBar and the TextView from XML
        ProgressBar budgetProgressBar = view.findViewById(R.id.budgetProgress);
        TextView remainingBalanceText = view.findViewById(R.id.tvRemainingBalance);

        // 3. Set up some dummy data for testing (Later, pull this from your database)
        double currentSpent = 750.0; // Assume you spent $750
        double budgetLimit = 1000.0; // Assume your budget is $1000

        // 4. Call your math utility
        int progressPercentage = BudgetMathUtils.calculateSpendingPercentage(currentSpent, budgetLimit);
        double remainingBalance = budgetLimit - currentSpent;

        // 5. Apply the calculated percentage to the ProgressBar
        if (budgetProgressBar != null) {
            budgetProgressBar.setProgress(progressPercentage);
        }

        if (remainingBalanceText != null) {
            // Format the number to look like currency (e.g., $250.00)
            remainingBalanceText.setText(String.format("Remaining: $%.2f", remainingBalance));
        }

        return view;
    }
}