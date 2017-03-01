package de.dkiefner.qapital.exercise.di;

import android.app.Application;

import java.util.Locale;

import dagger.Module;
import dagger.Provides;
import de.dkiefner.qapital.exercise.QapitalExercise;
import de.dkiefner.qapital.exercise.common.LocalClock;
import de.dkiefner.qapital.exercise.common.CurrencyFormatter;

@Module
public class AppModule {

	private final QapitalExercise qapitalExercise;

	public AppModule(QapitalExercise qapitalExercise) {
		this.qapitalExercise = qapitalExercise;
	}

	@Provides
	@PerApp
	QapitalExercise provideQapitalExercise() {
		return qapitalExercise;
	}

	@Provides
	@PerApp
	Application provideApplication() {
		return qapitalExercise;
	}

	@Provides
	@PerApp
	CurrencyFormatter provideCurrencyFormatter() {
		return new CurrencyFormatter(Locale.getDefault());
	}

	@Provides
	@PerApp
	LocalClock provideLocalClock() {
		return new LocalClock();
	}

}