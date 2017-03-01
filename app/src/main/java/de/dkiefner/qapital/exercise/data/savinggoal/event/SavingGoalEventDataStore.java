package de.dkiefner.qapital.exercise.data.savinggoal.event;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reactivex.Observable;

public class SavingGoalEventDataStore {

	private SavingGoalEventApi savingGoalEventApi;
	private StorIOSQLite storIOSQLite;

	public SavingGoalEventDataStore(SavingGoalEventApi savingGoalEventApi, StorIOSQLite storIOSQLite) {
		this.savingGoalEventApi = savingGoalEventApi;
		this.storIOSQLite = storIOSQLite;
	}

	public Observable<List<SavingGoalEvent>> getSavingGoalEvents(int savingGoalId) {
		return Observable.merge(loadFromCache(savingGoalId), loadAndCacheFromApi(savingGoalId));
	}

	private Observable<List<SavingGoalEvent>> loadFromCache(final int savingGoalId) {
		return Observable.fromCallable(() -> storIOSQLite
				.get()
				.listOfObjects(SavingGoalEvent.class)
				.withQuery(Query.builder()
						.table(SavingGoalEvent.TABLE_NAME)
						.where(SavingGoalEvent.FieldInfo.ID + "=?")
						.whereArgs(savingGoalId)
						.orderBy(SavingGoalEvent.FieldInfo.TIMESTAMP)
						.build())
				.prepare()
				.executeAsBlocking());
	}

	private Observable<List<SavingGoalEvent>> loadAndCacheFromApi(int savingGoalId) {
		return savingGoalEventApi.getSavingGoalEvents(savingGoalId).retry(3)
				.map(SavingGoalEventMapper::map)
				.flatMap(this::saveToCache);
	}

	private Observable<List<SavingGoalEvent>> saveToCache(List<SavingGoalEvent> savingGoalEvents) {
		return Observable.fromCallable(() -> {
			storIOSQLite
					.put()
					.objects(savingGoalEvents)
					.prepare()
					.executeAsBlocking();
			return savingGoalEvents;
		});
	}
}
