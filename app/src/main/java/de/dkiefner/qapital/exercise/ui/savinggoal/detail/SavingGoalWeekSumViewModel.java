package de.dkiefner.qapital.exercise.ui.savinggoal.detail;

import android.databinding.ObservableField;

import de.dkiefner.qapital.exercise.common.CurrencyFormatter;

public class SavingGoalWeekSumViewModel {

	public final ObservableField<String> weekSumText;

	private final CurrencyFormatter currencyFormatter;

	public SavingGoalWeekSumViewModel(CurrencyFormatter currencyFormatter) {
		this.currencyFormatter = currencyFormatter;
		weekSumText = new ObservableField<>(currencyFormatter.format(0f));
	}

	public void updateWeekSum(float weekSum) {
		weekSumText.set(currencyFormatter.format(weekSum));
	}
}
