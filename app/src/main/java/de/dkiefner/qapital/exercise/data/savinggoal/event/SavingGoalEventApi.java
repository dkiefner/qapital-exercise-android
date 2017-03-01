package de.dkiefner.qapital.exercise.data.savinggoal.event;

import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEventsDto;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SavingGoalEventApi {

	@GET("/savingsgoals/{goalId}/feed")
	Observable<SavingGoalEventsDto> getSavingGoalEvents(@Path("goalId") int goalId);
}
