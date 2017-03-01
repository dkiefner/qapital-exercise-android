package de.dkiefner.qapital.exercise.ui.savinggoal.detail.rules;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRule;

public class SavingGoalRuleListViewModel {

	private List<SavingGoalRuleItemViewModel> savingGoalRuleItemViewModels;

	public SavingGoalRuleListViewModel() {
		savingGoalRuleItemViewModels = new ArrayList<>();
	}

	public void updateSavingRules(List<SavingsRule> savingRules) {
		this.savingGoalRuleItemViewModels.clear();
		for (SavingsRule savingRule : savingRules) {
			savingGoalRuleItemViewModels.add(new SavingGoalRuleItemViewModel(savingRule));
		}
	}

	@Nullable
	public SavingGoalRuleItemViewModel getItem(int position) {
		if (position < savingGoalRuleItemViewModels.size()) {
			return savingGoalRuleItemViewModels.get(position);
		}
		return null;
	}

	public int getItemCount() {
		return savingGoalRuleItemViewModels.size();
	}
}
