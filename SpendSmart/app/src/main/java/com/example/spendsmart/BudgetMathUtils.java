package com.example.spendsmart;

public class BudgetMathUtils {

    /**
     * Calculates the percentage of the budget spent.
     * @param totalSpent The current total expenses.
     * @param budgetLimit The maximum budget allowed.
     * @return An integer representing the percentage (0 to 100).
     */
    public static int calculateSpendingPercentage(double totalSpent, double budgetLimit) {
        if (budgetLimit <= 0) {
            return 0; // Prevent division by zero
        }

        double percentage = (totalSpent / budgetLimit) * 100;

        // Cap the percentage at 100% so the ProgressBar doesn't overflow
        return Math.min((int) Math.round(percentage), 100);
    }
}