package de.dkiefner.qapital.exercise.data.savinggoal;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reactivex.Observable;

public class SavingGoalDataStore {

	private SavingGoalApi savingGoalApi;
	private StorIOSQLite storIOSQLite;

	public SavingGoalDataStore(SavingGoalApi savingGoalApi, StorIOSQLite storIOSQLite) {
		this.savingGoalApi = savingGoalApi;
		this.storIOSQLite = storIOSQLite;
	}

	public Observable<List<SavingGoal>> getSavingGoals() {
		return Observable.merge(loadFromCache(), loadAndCacheFromApi());
	}

	private Observable<List<SavingGoal>> loadFromCache() {
		return Observable.fromCallable(() -> storIOSQLite
				.get()
				.listOfObjects(SavingGoal.class)
				.withQuery(Query.builder()
						.table(SavingGoal.TABLE_NAME)
						.build())
				.prepare()
				.executeAsBlocking());
	}

	private Observable<List<SavingGoal>> loadAndCacheFromApi() {
		return savingGoalApi.getSavingGoals().retry(3)
				.map(SavingGoalMapper::map)
				.flatMap(this::saveToCache);
	}

	private Observable<List<SavingGoal>> saveToCache(List<SavingGoal> savingGoals) {
		return Observable.fromCallable(() -> {
			storIOSQLite
					.put()
					.objects(savingGoals)
					.prepare()
					.executeAsBlocking();
			return savingGoals;
		});
	}
}
