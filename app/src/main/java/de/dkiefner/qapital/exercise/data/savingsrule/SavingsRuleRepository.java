package de.dkiefner.qapital.exercise.data.savingsrule;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import de.dkiefner.qapital.exercise.data.Repository;

public class SavingsRuleRepository extends Repository<SavingsRule> {

	public SavingsRuleRepository(StorIOSQLite storIOSQLite) {
		super(SavingsRule.class, storIOSQLite);
	}

	@Override
	protected String getEntityTableName() {
		return SavingsRule.TABLE_NAME;
	}

}
