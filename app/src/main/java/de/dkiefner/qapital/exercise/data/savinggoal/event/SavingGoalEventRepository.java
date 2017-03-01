package de.dkiefner.qapital.exercise.data.savinggoal.event;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import de.dkiefner.qapital.exercise.data.Repository;
import io.reactivex.Observable;

public class SavingGoalEventRepository extends Repository<SavingGoalEvent> {

	public SavingGoalEventRepository(StorIOSQLite storIOSQLite) {
		super(SavingGoalEvent.class, storIOSQLite);
	}

	@Override
	protected String getEntityTableName() {
		return SavingGoalEvent.TABLE_NAME;
	}

	public Observable<List<SavingGoalEvent>> findAllBySavingGoal(final int savingGoalId) {
		return Observable.fromCallable(() -> storIOSQLite
				.get()
				.listOfObjects(entityClass)
				.withQuery(Query.builder()
						.table(getEntityTableName())
						.where(SavingGoalEvent.FieldInfo.FK_SAVING_GOAL + "=?")
						.whereArgs(savingGoalId)
						.orderBy(SavingGoalEvent.FieldInfo.TIMESTAMP)
						.build())
				.prepare()
				.executeAsBlocking());
	}

}
