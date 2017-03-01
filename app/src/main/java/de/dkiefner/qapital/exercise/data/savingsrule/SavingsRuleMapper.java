package de.dkiefner.qapital.exercise.data.savingsrule;

import java.util.ArrayList;
import java.util.List;

public final class SavingsRuleMapper {

	private SavingsRuleMapper() {
	}

	public static SavingsRule map(SavingsRuleDto savingsRuleDto) {
		return SavingsRule.create(savingsRuleDto.getId(),
				savingsRuleDto.getType(),
				savingsRuleDto.getAmount());
	}

	public static List<SavingsRule> map(SavingsRulesDto savingsRulesDto) {
		List<SavingsRule> savingGoals = new ArrayList<>();
		for (SavingsRuleDto savingsRuleDto : savingsRulesDto.getSavingsRules()) {
			savingGoals.add(map(savingsRuleDto));
		}
		return savingGoals;
	}
}
