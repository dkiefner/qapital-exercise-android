package de.dkiefner.qapital.exercise.ui.savinggoal.list;

import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.common.CurrencyFormatter;
import de.dkiefner.qapital.exercise.common.ui.StringResource;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoal;

public class SavingGoalItemViewModel {

	private final SavingGoal savingGoal;

	private final CurrencyFormatter currencyFormatter;

	public SavingGoalItemViewModel(SavingGoal savingGoal, CurrencyFormatter currencyFormatter) {
		this.savingGoal = savingGoal;
		this.currencyFormatter = currencyFormatter;
	}

	public String getName() {
		return savingGoal.name();
	}

	public StringResource getStatus() {
		StringResource stringResource;
		String formattedCurrentBalance = currencyFormatter.format(savingGoal.currentBalance());
		if (savingGoal.targetAmount() != null) {
			String formattedTargetAmount = currencyFormatter.format(savingGoal.targetAmount());
			stringResource = new StringResource(R.string.amount_of, new String[]{formattedCurrentBalance, formattedTargetAmount});
		} else {
			stringResource = new StringResource(formattedCurrentBalance);
		}

		return stringResource;
	}

	public SavingGoal getSavingGoal() {
		return savingGoal;
	}

	public String getImageUrl() {
		return savingGoal.goalImageURL();
	}
}
