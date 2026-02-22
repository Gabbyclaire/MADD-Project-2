package com.example.spendsmart;

import android.text.TextUtils;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Validator utility class for SpendSmart app.
 * Provides centralized validation and currency formatting logic.
 * 
 * This class handles:
 * - Empty field validation with Material UI error display
 * - Amount validation (numeric checks)
 * - Category selection validation
 * - Currency formatting to two decimal places
 */
public class Validator {

    // Currency formatter for consistent two decimal place display
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("0.00");

    /**
     * Validates that a TextInputLayout field is not empty.
     * If empty, displays an error message on the TextInputLayout.
     *
     * @param inputLayout The TextInputLayout to validate
     * @param errorMessage The error message to display if validation fails
     * @return true if the field has content, false if empty
     */
    public static boolean validateNotEmpty(TextInputLayout inputLayout, String errorMessage) {
        if (inputLayout == null || inputLayout.getEditText() == null) {
            return false;
        }

        String text = inputLayout.getEditText().getText() != null
                ? inputLayout.getEditText().getText().toString().trim()
                : "";

        if (TextUtils.isEmpty(text)) {
            inputLayout.setError(errorMessage);
            return false;
        }

        // Clear any previous error
        inputLayout.setError(null);
        inputLayout.setErrorEnabled(false);
        return true;
    }

    /**
     * Validates that a TextInputLayout field contains a valid positive number.
     * If invalid, displays an error message on the TextInputLayout.
     *
     * @param inputLayout The TextInputLayout to validate
     * @param emptyErrorMessage Error message for empty field
     * @param invalidErrorMessage Error message for invalid number
     * @return true if valid number, false otherwise
     */
    public static boolean validateAmount(TextInputLayout inputLayout, 
                                         String emptyErrorMessage, 
                                         String invalidErrorMessage) {
        if (inputLayout == null || inputLayout.getEditText() == null) {
            return false;
        }

        String text = inputLayout.getEditText().getText() != null
                ? inputLayout.getEditText().getText().toString().trim()
                : "";

        // Check for empty field
        if (TextUtils.isEmpty(text)) {
            inputLayout.setError(emptyErrorMessage);
            return false;
        }

        // Check for valid number
        try {
            double amount = Double.parseDouble(text);
            if (amount <= 0) {
                inputLayout.setError(invalidErrorMessage);
                return false;
            }
        } catch (NumberFormatException e) {
            inputLayout.setError(invalidErrorMessage);
            return false;
        }

        // Clear any previous error
        inputLayout.setError(null);
        inputLayout.setErrorEnabled(false);
        return true;
    }

    /**
     * Validates that a spinner has a valid selection (not the default/first item).
     *
     * @param spinner The Spinner to validate
     * @return true if a valid category is selected (position > 0), false otherwise
     */
    public static boolean validateCategorySelected(Spinner spinner) {
        if (spinner == null) {
            return false;
        }

        // First item is typically "Select Category" placeholder
        return spinner.getSelectedItemPosition() > 0;
    }

    /**
     * Gets the text from a TextInputLayout's EditText.
     *
     * @param inputLayout The TextInputLayout to get text from
     * @return The trimmed text, or empty string if null
     */
    public static String getText(TextInputLayout inputLayout) {
        if (inputLayout == null || inputLayout.getEditText() == null) {
            return "";
        }

        return inputLayout.getEditText().getText() != null
                ? inputLayout.getEditText().getText().toString().trim()
                : "";
    }

    /**
     * Parses the amount from a TextInputLayout.
     * Should be called after validateAmount() returns true.
     *
     * @param inputLayout The TextInputLayout containing the amount
     * @return The parsed double value, or 0.0 if parsing fails
     */
    public static double parseAmount(TextInputLayout inputLayout) {
        String text = getText(inputLayout);
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Formats a currency value to always show two decimal places.
     * Examples: 50 -> "50.00", 50.5 -> "50.50", 99.999 -> "100.00"
     *
     * @param amount The amount to format
     * @return Formatted string with two decimal places
     */
    public static String formatCurrency(double amount) {
        return CURRENCY_FORMAT.format(amount);
    }

    /**
     * Formats a currency value with a prefix (e.g., "KSh").
     *
     * @param amount The amount to format
     * @param prefix The currency prefix (e.g., "KSh", "$")
     * @return Formatted string like "KSh 50.00"
     */
    public static String formatCurrencyWithPrefix(double amount, String prefix) {
        return prefix + " " + formatCurrency(amount);
    }

    /**
     * Clears error state from a TextInputLayout.
     *
     * @param inputLayout The TextInputLayout to clear errors from
     */
    public static void clearError(TextInputLayout inputLayout) {
        if (inputLayout != null) {
            inputLayout.setError(null);
            inputLayout.setErrorEnabled(false);
        }
    }

    /**
     * Sets an error message on a TextInputLayout.
     *
     * @param inputLayout The TextInputLayout to set error on
     * @param errorMessage The error message to display
     */
    public static void setError(TextInputLayout inputLayout, String errorMessage) {
        if (inputLayout != null) {
            inputLayout.setError(errorMessage);
        }
    }

    /**
     * Clears the text content of a TextInputLayout.
     *
     * @param inputLayout The TextInputLayout to clear
     */
    public static void clearText(TextInputLayout inputLayout) {
        if (inputLayout != null && inputLayout.getEditText() != null) {
            inputLayout.getEditText().setText("");
        }
    }

    /**
     * Formats an amount string input to standardized two decimal places.
     * Useful for formatting user input before saving.
     *
     * @param amountString The raw amount string from user input
     * @return Formatted string with two decimal places, or "0.00" if invalid
     */
    public static String formatInputAmount(String amountString) {
        if (TextUtils.isEmpty(amountString)) {
            return "0.00";
        }

        try {
            double amount = Double.parseDouble(amountString.trim());
            return formatCurrency(amount);
        } catch (NumberFormatException e) {
            return "0.00";
        }
    }
}
