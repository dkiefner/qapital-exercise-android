package de.dkiefner.qapital.exercise.common;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import de.dkiefner.qapital.exercise.TestQapitalExercise;

public class QapitalExerciseTestRunner extends AndroidJUnitRunner {

	@Override
	public Application newApplication(ClassLoader cl, String className, Context context)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return super.newApplication(cl, TestQapitalExercise.class.getName(), context);
	}
}