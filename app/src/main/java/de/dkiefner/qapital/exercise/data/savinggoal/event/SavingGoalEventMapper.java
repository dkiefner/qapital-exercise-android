package de.dkiefner.qapital.exercise.data.savinggoal.event;

import java.util.ArrayList;
import java.util.List;

public final class SavingGoalEventMapper {

	private SavingGoalEventMapper() {
	}

	public static SavingGoalEvent map(SavingGoalEventDto savingGoalEventDto, int savingGoalId) {
		return SavingGoalEvent.create(savingGoalEventDto.getId(),
				savingGoalEventDto.getType(),
				savingGoalEventDto.getTimestamp(),
				savingGoalEventDto.getMessage(),
				savingGoalEventDto.getAmount(),
				savingGoalEventDto.getSavingsRuleId(),
				savingGoalId);
	}

	public static List<SavingGoalEvent> map(SavingGoalEventsDto savingGoalEventsDto, int savingGoalId) {
		List<SavingGoalEvent> savingGoals = new ArrayList<>();
		for (SavingGoalEventDto savingGoalEventDto : savingGoalEventsDto.getFeed()) {
			savingGoals.add(map(savingGoalEventDto, savingGoalId));
		}
		return savingGoals;
	}
}
