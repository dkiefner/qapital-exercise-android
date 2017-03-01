package de.dkiefner.qapital.exercise.ui.savinggoal.detail.rules;

import android.support.annotation.DrawableRes;

import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRule;

public class SavingGoalRuleItemViewModel {

	private SavingsRule savingsRule;

	public SavingGoalRuleItemViewModel(SavingsRule savingsRule) {
		this.savingsRule = savingsRule;
	}

	@DrawableRes
	public int getImageId() {
		if (SavingsRule.Type.ROUND_UP.equals(savingsRule.type())) {
			return R.drawable.rule_round_up;
		} else if (SavingsRule.Type.GUILTY_PLEASURE.equals(savingsRule.type())) {
			return R.drawable.rule_guilty_pleasure;
		} else {
			return R.drawable.rule_unknown;
		}
	}
}
