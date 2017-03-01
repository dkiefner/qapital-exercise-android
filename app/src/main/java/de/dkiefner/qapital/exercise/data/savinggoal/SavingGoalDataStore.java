package de.dkiefner.qapital.exercise.data.savinggoal;

import java.util.List;

import io.reactivex.Observable;

public class SavingGoalDataStore {

	private SavingGoalApi savingGoalApi;
	private SavingGoalRepository savingGoalRepository;

	public SavingGoalDataStore(SavingGoalApi savingGoalApi, SavingGoalRepository savingGoalRepository) {
		this.savingGoalApi = savingGoalApi;
		this.savingGoalRepository = savingGoalRepository;
	}

	public Observable<List<SavingGoal>> getSavingGoals() {
		return Observable.mergeDelayError(savingGoalRepository.findAll(), loadAndCacheFromApi());
	}

	private Observable<List<SavingGoal>> loadAndCacheFromApi() {
		return savingGoalApi.getSavingGoals().retry(3)
				.map(SavingGoalMapper::map)
				.flatMap(savingGoalRepository::saveAll);
	}

}
