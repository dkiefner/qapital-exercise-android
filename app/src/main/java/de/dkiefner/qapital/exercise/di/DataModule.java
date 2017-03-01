package de.dkiefner.qapital.exercise.di;

import android.app.Application;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import dagger.Module;
import dagger.Provides;
import de.dkiefner.qapital.exercise.data.QapitalExerciseSQLiteHelper;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoal;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoalApi;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoalDataStore;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoalStorIOSQLiteDeleteResolver;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoalStorIOSQLiteGetResolver;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoalStorIOSQLitePutResolver;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEvent;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEventApi;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEventDataStore;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEventStorIOSQLiteDeleteResolver;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEventStorIOSQLiteGetResolver;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEventStorIOSQLitePutResolver;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRule;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRuleApi;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRuleDataStore;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRuleStorIOSQLiteDeleteResolver;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRuleStorIOSQLiteGetResolver;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRuleStorIOSQLitePutResolver;

@Module
public class DataModule {

	@Provides
	@PerApp
	SavingGoalDataStore provideGoalDataStore(SavingGoalApi savingGoalApi, StorIOSQLite storIOSQLite) {
		return new SavingGoalDataStore(savingGoalApi, storIOSQLite);
	}

	@Provides
	@PerApp
	SavingsRuleDataStore provideSavingsRuleDataStore(SavingsRuleApi savingsRuleApi, StorIOSQLite storIOSQLite) {
		return new SavingsRuleDataStore(savingsRuleApi, storIOSQLite);
	}

	@Provides
	@PerApp
	SavingGoalEventDataStore provideSavingGoalEventDataStore(SavingGoalEventApi savingGoalEventApi, StorIOSQLite storIOSQLite) {
		return new SavingGoalEventDataStore(savingGoalEventApi, storIOSQLite);
	}

	@Provides
	@PerApp
	StorIOSQLite provideStorIOSQLite(QapitalExerciseSQLiteHelper sqLiteHelper) {
		return DefaultStorIOSQLite.builder()
				.sqliteOpenHelper(sqLiteHelper)
				.addTypeMapping(SavingGoal.class, createSavingGoalMapper())
				.addTypeMapping(SavingsRule.class, createSavingsRuleMapper())
				.addTypeMapping(SavingGoalEvent.class, createSavingGoalEventMapper())
				.build();
	}

	@Provides
	@PerApp
	QapitalExerciseSQLiteHelper provideQapitalExerciseSQLiteHelper(Application application) {
		return new QapitalExerciseSQLiteHelper(application);
	}

	private SQLiteTypeMapping<SavingGoal> createSavingGoalMapper() {
		return SQLiteTypeMapping.<SavingGoal>builder()
				.putResolver(new SavingGoalStorIOSQLitePutResolver())
				.getResolver(new SavingGoalStorIOSQLiteGetResolver())
				.deleteResolver(new SavingGoalStorIOSQLiteDeleteResolver())
				.build();
	}

	private SQLiteTypeMapping<SavingsRule> createSavingsRuleMapper() {
		return SQLiteTypeMapping.<SavingsRule>builder()
				.putResolver(new SavingsRuleStorIOSQLitePutResolver())
				.getResolver(new SavingsRuleStorIOSQLiteGetResolver())
				.deleteResolver(new SavingsRuleStorIOSQLiteDeleteResolver())
				.build();
	}

	private SQLiteTypeMapping<SavingGoalEvent> createSavingGoalEventMapper() {
		return SQLiteTypeMapping.<SavingGoalEvent>builder()
				.putResolver(new SavingGoalEventStorIOSQLitePutResolver())
				.getResolver(new SavingGoalEventStorIOSQLiteGetResolver())
				.deleteResolver(new SavingGoalEventStorIOSQLiteDeleteResolver())
				.build();
	}

}