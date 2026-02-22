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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Fragment for adding new expenses.
 * Uses the Validator utility for centralized validation and currency formatting.
 */
public class AddExpenseFragment extends Fragment {

    // UI Components - Using TextInputLayout for Material UI error handling
    private TextInputLayout tilTitle;
    private TextInputLayout tilAmount;
    private Spinner spCategory;
    private MaterialButton btnSave;
    private MaterialButton btnReset;

    // ViewModel
    private ExpenseViewModel expenseViewModel;

    // Error messages - centralized for easy maintenance
    private static final String ERROR_TITLE_EMPTY = "Please enter a title";
    private static final String ERROR_AMOUNT_EMPTY = "Please enter an amount";
    private static final String ERROR_AMOUNT_INVALID = "Please enter a valid positive amount";
    private static final String ERROR_CATEGORY_NOT_SELECTED = "Please select a category";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        // Initialize ViewModel
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        // Initialize views - Note: Using TextInputLayout for proper error display
        tilTitle = view.findViewById(R.id.etTitle_layout);
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
        // Validate title
        boolean isTitleValid = Validator.validateNotEmpty(tilTitle, ERROR_TITLE_EMPTY);

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
        if (!isTitleValid || !isAmountValid || !isCategoryValid) {
            return;
        }

        // Get input values
        String title = tilTitle.getEditText().getText().toString().trim();
        double amount = Validator.parseAmount(tilAmount);
        String category = spCategory.getSelectedItem().toString();

        // Create expense object
        Expense expense = new Expense(title, amount, category, System.currentTimeMillis());

        // Save to database via ViewModel
        expenseViewModel.insert(expense);

        // Display confirmation
        String confirmationMessage = String.format(
                "Expense saved to database: %s - KSh %s",
                expense.getTitle(),
                Validator.formatCurrency(amount)
        );
        Toast.makeText(getContext(), confirmationMessage, Toast.LENGTH_LONG).show();

        // Reset form after successful save
        resetForm();
    }

    /**
     * Resets all form fields to their initial state.
     * Clears any error messages displayed on TextInputLayouts.
     */
    private void resetForm() {
        // Clear title field and its errors
        Validator.clearText(tilTitle);
        Validator.clearError(tilTitle);

        // Clear amount field and its errors using Validator utility
        Validator.clearText(tilAmount);
        Validator.clearError(tilAmount);

        // Reset spinner to first item (placeholder)
        spCategory.setSelection(0);
    }
}
