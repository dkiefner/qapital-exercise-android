package de.dkiefner.qapital.exercise;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import de.dkiefner.qapital.exercise.di.ApiModule;
import de.dkiefner.qapital.exercise.di.AppComponent;
import de.dkiefner.qapital.exercise.di.AppModule;
import de.dkiefner.qapital.exercise.di.DaggerAppComponent;
import de.dkiefner.qapital.exercise.di.DataModule;
import timber.log.Timber;

public class QapitalExercise extends Application {

	private static QapitalExercise qapitalExercise;
	protected AppComponent appComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		qapitalExercise = this;

		AndroidThreeTen.init(this);
		initTimber();
		initDependencyInjection();
	}

	private void initTimber() {
		if (isDebug()) {
			Timber.plant(new Timber.DebugTree());
		}
	}

	public boolean isDebug() {
		return BuildConfig.BUILD_TYPE.equals("debug");
	}

	private void initDependencyInjection() {
		appComponent = DaggerAppComponent.builder()
				.appModule(new AppModule(this))
				.apiModule(new ApiModule())
				.dataModule(new DataModule())
				.build();
	}

	public static AppComponent getAppInjector() {
		return qapitalExercise.appComponent;
	}

}
