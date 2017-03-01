package de.dkiefner.qapital.exercise.data.savinggoal;

import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoalsDto;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface SavingGoalApi {

	@GET("/savingsgoals")
	Observable<SavingGoalsDto> getSavingGoals();
}
