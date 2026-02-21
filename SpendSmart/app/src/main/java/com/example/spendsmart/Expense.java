package com.example.spendsmart;

/**
 * Model class representing an expense entry.
 * Stores the amount and category of an expense.
 */
public class Expense {

    private double amount;
    private String category;

    /**
     * Creates a new Expense instance.
     *
     * @param amount The expense amount
     * @param category The expense category
     */
    public Expense(double amount, String category) {
        this.amount = amount;
        this.category = category;
    }

    /**
     * Gets the raw expense amount.
     *
     * @return The expense amount as a double
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the expense amount formatted to two decimal places.
     * Uses the Validator utility for consistent formatting.
     *
     * @return The formatted amount string (e.g., "50.00")
     */
    public String getFormattedAmount() {
        return Validator.formatCurrency(amount);
    }

    /**
     * Gets the expense amount with currency prefix.
     *
     * @param prefix The currency prefix (e.g., "KSh", "$")
     * @return The formatted amount with prefix (e.g., "KSh 50.00")
     */
    public String getFormattedAmountWithPrefix(String prefix) {
        return Validator.formatCurrencyWithPrefix(amount, prefix);
    }

    /**
     * Gets the expense category.
     *
     * @return The expense category
     */
    public String getCategory() {
        return category;
    }
}