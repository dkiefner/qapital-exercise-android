package de.dkiefner.qapital.exercise.data.savinggoal.event;

import java.util.List;

import io.reactivex.Observable;

public class SavingGoalEventDataStore {

	private SavingGoalEventApi savingGoalEventApi;
	private SavingGoalEventRepository savingGoalEventRepository;

	public SavingGoalEventDataStore(SavingGoalEventApi savingGoalEventApi, SavingGoalEventRepository savingGoalEventRepository) {
		this.savingGoalEventApi = savingGoalEventApi;
		this.savingGoalEventRepository = savingGoalEventRepository;
	}

	public Observable<List<SavingGoalEvent>> getSavingGoalEvents(int savingGoalId) {
		return Observable.mergeDelayError(savingGoalEventRepository.findAllBySavingGoal(savingGoalId), loadAndCacheFromApi(savingGoalId));
	}

	private Observable<List<SavingGoalEvent>> loadAndCacheFromApi(int savingGoalId) {
		return savingGoalEventApi.getSavingGoalEvents(savingGoalId).retry(3)
				.map(savingGoalEventsDto -> SavingGoalEventMapper.map(savingGoalEventsDto, savingGoalId))
				.flatMap(savingGoalEventRepository::saveAll);
	}

}
