package de.dkiefner.qapital.exercise;

import de.dkiefner.qapital.exercise.di.ApiModule;
import de.dkiefner.qapital.exercise.di.AppModule;
import de.dkiefner.qapital.exercise.di.DaggerAppComponent;
import de.dkiefner.qapital.exercise.di.DataModule;

public class TestQapitalExercise extends QapitalExercise {

	public void updateBaseUrl(String baseUrl) {
		appComponent = DaggerAppComponent.builder()
				.appModule(new AppModule(this))
				.apiModule(new ApiModule(baseUrl))
				.dataModule(new DataModule())
				.build();
	}
}
