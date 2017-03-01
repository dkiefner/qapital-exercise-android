package de.dkiefner.qapital.exercise.data.savinggoal.event;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteCreator;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.io.Serializable;

@AutoValue
@StorIOSQLiteType(table = SavingGoalEvent.TABLE_NAME)
public abstract class SavingGoalEvent implements Serializable {

	public static final String TABLE_NAME = "saving_goal_event";

	public static final class FieldInfo {
		public static final String ID = "id";
		public static final String TYPE = "type";
		public static final String TIMESTAMP = "timestamp";
		public static final String MESSAGE = "message";
		public static final String AMOUNT = "amount";
		public static final String FK_SAVINGS_RULE = "fk_savings_rule";
		public static final String FK_SAVING_GOAL = "fk_saving_goal";
	}

	@StorIOSQLiteColumn(name = FieldInfo.ID, key = true)
	public abstract String id();

	@StorIOSQLiteColumn(name = FieldInfo.TYPE)
	public abstract String type();

	@StorIOSQLiteColumn(name = FieldInfo.TIMESTAMP)
	public abstract String timestamp();

	@StorIOSQLiteColumn(name = FieldInfo.MESSAGE)
	public abstract String message();

	@StorIOSQLiteColumn(name = FieldInfo.AMOUNT)
	public abstract float amount();

	@Nullable
	@StorIOSQLiteColumn(name = FieldInfo.FK_SAVINGS_RULE)
	public abstract Integer savingsRuleId();

	@StorIOSQLiteColumn(name = FieldInfo.FK_SAVING_GOAL)
	public abstract int savingGoalId();

	@StorIOSQLiteCreator
	static SavingGoalEvent create(String id, String type, String timestamp, String message, float amount, Integer savingsRuleId, int savingGoalId) {
		return new AutoValue_SavingGoalEvent(id, type, timestamp, message, amount, savingsRuleId, savingGoalId);
	}

}
