package com.example.spendsmart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Fragment for adding new expenses.
 * Uses the Validator utility for centralized validation and currency formatting.
 */
public class AddExpenseFragment extends Fragment {

    // UI Components - Using TextInputLayout for Material UI error handling
    private TextInputLayout tilAmount;
    private Spinner spCategory;
    private MaterialButton btnSave;
    private MaterialButton btnReset;

    // Error messages - centralized for easy maintenance
    private static final String ERROR_AMOUNT_EMPTY = "Please enter an amount";
    private static final String ERROR_AMOUNT_INVALID = "Please enter a valid positive amount";
    private static final String ERROR_CATEGORY_NOT_SELECTED = "Please select a category";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        // Initialize views - Note: Using TextInputLayout for proper error display
        tilAmount = view.findViewById(R.id.etAmount_layout);
        spCategory = view.findViewById(R.id.spCategory);
        btnSave = view.findViewById(R.id.btnSave);
        btnReset = view.findViewById(R.id.btnReset);

        // Set click listeners
        btnSave.setOnClickListener(v -> saveExpense());
        btnReset.setOnClickListener(v -> resetForm());

        return view;
    }

    /**
     * Validates and saves the expense.
     * Uses Validator utility for all validation and formatting.
     */
    private void saveExpense() {
        // Validate amount using Validator utility with TextInputLayout error handling
        boolean isAmountValid = Validator.validateAmount(
                tilAmount,
                ERROR_AMOUNT_EMPTY,
                ERROR_AMOUNT_INVALID
        );

        // Validate category selection
        boolean isCategoryValid = Validator.validateCategorySelected(spCategory);
        if (!isCategoryValid) {
            Toast.makeText(getContext(), ERROR_CATEGORY_NOT_SELECTED, Toast.LENGTH_SHORT).show();
        }

        // Only proceed if all validations pass
        if (!isAmountValid || !isCategoryValid) {
            return;
        }

        // Parse and format the amount to two decimal places
        double amount = Validator.parseAmount(tilAmount);
        String formattedAmount = Validator.formatCurrency(amount);

        // Get selected category
        String category = spCategory.getSelectedItem().toString();

        // Create expense with the parsed amount
        Expense expense = new Expense(amount, category);

        // Display confirmation with formatted currency (always shows 2 decimal places)
        String confirmationMessage = String.format(
                "Saved: %s - KSh %s",
                expense.getCategory(),
                formattedAmount
        );
        Toast.makeText(getContext(), confirmationMessage, Toast.LENGTH_SHORT).show();

        // Reset form after successful save
        resetForm();
    }

    /**
     * Resets all form fields to their initial state.
     * Clears any error messages displayed on TextInputLayouts.
     */
    private void resetForm() {
        // Clear amount field and its errors using Validator utility
        Validator.clearText(tilAmount);
        Validator.clearError(tilAmount);

        // Reset spinner to first item (placeholder)
        spCategory.setSelection(0);
    }
}
