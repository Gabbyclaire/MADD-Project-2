package com.example.spendsmart;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Model class representing an expense entry.
 * Stores the amount and category of an expense.
 */
@Entity(tableName = "expense_table")
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private double amount;
    private String category;
    private long timestamp;

    /**
     * Creates a new Expense instance.
     *
     * @param title The expense title
     * @param amount The expense amount
     * @param category The expense category
     * @param timestamp The timestamp
     */
    public Expense(String title, double amount, String category, long timestamp) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the raw expense amount.
     *
     * @return The expense amount as a double
     */
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}