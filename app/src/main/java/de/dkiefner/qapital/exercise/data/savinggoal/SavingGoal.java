package de.dkiefner.qapital.exercise.data.savinggoal;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteCreator;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.io.Serializable;

@AutoValue
@StorIOSQLiteType(table = SavingGoal.TABLE_NAME)
public abstract class SavingGoal implements Serializable {

	public static final String TABLE_NAME = "saving_goal";

	public static final class FieldInfo {
		public static final String ID = "id";
		public static final String GOAL_IMAGE_URL = "goal_image_url";
		public static final String TARGET_AMOUNT = "target_amount";
		public static final String CURRENT_BALANCE = "current_balance";
		public static final String STATUS = "status";
		public static final String NAME = "name";
	}

	@StorIOSQLiteColumn(name = FieldInfo.ID, key = true)
	public abstract int id();

	@StorIOSQLiteColumn(name = FieldInfo.GOAL_IMAGE_URL)
	public abstract String goalImageURL();

	@Nullable
	@StorIOSQLiteColumn(name = FieldInfo.TARGET_AMOUNT)
	public abstract Float targetAmount();

	@StorIOSQLiteColumn(name = FieldInfo.CURRENT_BALANCE)
	public abstract float currentBalance();

	@StorIOSQLiteColumn(name = FieldInfo.STATUS)
	public abstract String status();

	@StorIOSQLiteColumn(name = FieldInfo.NAME)
	public abstract String name();

	@StorIOSQLiteCreator
	static SavingGoal create(int id, String goalImageURL, Float targetAmount, float currentBalance, String status, String name) {
		return new AutoValue_SavingGoal(id, goalImageURL, targetAmount, currentBalance, status, name);
	}

	public boolean isActive() {
		return "active".equals(status());
	}

}
