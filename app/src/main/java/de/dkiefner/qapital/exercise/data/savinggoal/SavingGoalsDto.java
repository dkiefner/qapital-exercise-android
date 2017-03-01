package de.dkiefner.qapital.exercise.data.savinggoal;

import java.util.List;

public class SavingGoalsDto {

	List<SavingGoalDto> savingsGoals;

	public List<SavingGoalDto> getSavingsGoals() {
		return savingsGoals;
	}

	public void setSavingsGoals(List<SavingGoalDto> savingsGoals) {
		this.savingsGoals = savingsGoals;
	}
}
