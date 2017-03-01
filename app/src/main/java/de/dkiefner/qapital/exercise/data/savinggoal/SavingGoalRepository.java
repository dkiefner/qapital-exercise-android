package de.dkiefner.qapital.exercise.data.savinggoal;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import de.dkiefner.qapital.exercise.data.Repository;

public class SavingGoalRepository extends Repository<SavingGoal> {

	public SavingGoalRepository(StorIOSQLite storIOSQLite) {
		super(SavingGoal.class, storIOSQLite);
	}

	@Override
	protected String getEntityTableName() {
		return SavingGoal.TABLE_NAME;
	}

}
