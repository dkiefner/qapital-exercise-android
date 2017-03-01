package de.dkiefner.qapital.exercise.data.savinggoal.event;

import java.util.ArrayList;
import java.util.List;

public final class SavingGoalEventMapper {

	private SavingGoalEventMapper() {
	}

	public static SavingGoalEvent map(SavingGoalEventDto savingGoalEventDto) {
		return SavingGoalEvent.create(savingGoalEventDto.getId(),
				savingGoalEventDto.getType(),
				savingGoalEventDto.getTimestamp(),
				savingGoalEventDto.getMessage(),
				savingGoalEventDto.getAmount(),
				savingGoalEventDto.getSavingsRuleId());
	}

	public static List<SavingGoalEvent> map(SavingGoalEventsDto savingGoalEventsDto) {
		List<SavingGoalEvent> savingGoals = new ArrayList<>();
		for (SavingGoalEventDto savingGoalEventDto : savingGoalEventsDto.getFeed()) {
			savingGoals.add(map(savingGoalEventDto));
		}
		return savingGoals;
	}
}
