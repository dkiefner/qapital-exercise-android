package de.dkiefner.qapital.exercise.data.savinggoal;

import java.util.ArrayList;
import java.util.List;

public final class SavingGoalMapper {

	private SavingGoalMapper() {
	}

	public static SavingGoal map(SavingGoalDto savingGoalDto) {
		return SavingGoal.create(savingGoalDto.getId(),
				savingGoalDto.getGoalImageURL(),
				savingGoalDto.getTargetAmount(),
				savingGoalDto.getCurrentBalance(),
				savingGoalDto.getStatus(),
				savingGoalDto.getName());
	}

	public static List<SavingGoal> map(SavingGoalsDto savingGoalsDto) {
		List<SavingGoal> savingGoals = new ArrayList<>();
		for (SavingGoalDto savingGoalDto : savingGoalsDto.getSavingsGoals()) {
			savingGoals.add(map(savingGoalDto));
		}
		return savingGoals;
	}
}
