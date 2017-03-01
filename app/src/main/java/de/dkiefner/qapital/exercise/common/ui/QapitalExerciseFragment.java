package de.dkiefner.qapital.exercise.common.ui;

import android.support.v4.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;

public class QapitalExerciseFragment extends Fragment {

	protected final CompositeDisposable disposables = new CompositeDisposable();

	@Override
	public void onDestroy() {
		super.onDestroy();
		disposables.clear();
	}
}
