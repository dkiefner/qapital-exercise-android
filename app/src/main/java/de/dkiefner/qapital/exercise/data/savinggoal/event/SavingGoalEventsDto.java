package de.dkiefner.qapital.exercise.data.savinggoal.event;

import java.util.List;

public class SavingGoalEventsDto {

	List<SavingGoalEventDto> feed;

	public List<SavingGoalEventDto> getFeed() {
		return feed;
	}

	public void setFeed(List<SavingGoalEventDto> feed) {
		this.feed = feed;
	}
}
