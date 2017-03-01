package de.dkiefner.qapital.exercise.data.savingsrule;

import com.google.auto.value.AutoValue;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteCreator;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.io.Serializable;

@AutoValue
@StorIOSQLiteType(table = SavingsRule.TABLE_NAME)
public abstract class SavingsRule implements Serializable {

	public static final String TABLE_NAME = "savings_rule";

	public static final class FieldInfo {
		public static final String ID = "id";
		public static final String TYPE = "type";
		public static final String AMOUNT = "amount";
	}

	public static final class Type {
		public static final String ROUND_UP = "roundup";
		public static final String GUILTY_PLEASURE = "guilty_pleasure";
	}

	@StorIOSQLiteColumn(name = FieldInfo.ID, key = true)
	public abstract int id();

	@StorIOSQLiteColumn(name = FieldInfo.TYPE)
	public abstract String type();

	@StorIOSQLiteColumn(name = FieldInfo.AMOUNT)
	public abstract float amount();

	@StorIOSQLiteCreator
	static SavingsRule create(int id, String type, float amount) {
		return new AutoValue_SavingsRule(id, type, amount);
	}

}
