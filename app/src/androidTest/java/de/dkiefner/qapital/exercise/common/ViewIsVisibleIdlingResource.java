package de.dkiefner.qapital.exercise.common;

import android.support.annotation.IdRes;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ViewIsVisibleIdlingResource implements IdlingResource {

	private ResourceCallback callback;
	private View view;

	public ViewIsVisibleIdlingResource(AppCompatActivity activity, @IdRes int viewId) {
		view = activity.findViewById(viewId);
	}

	@Override
	public String getName() {
		return view.toString();
	}

	@Override
	public boolean isIdleNow() {
		callback.onTransitionToIdle();
		return view.getVisibility() == View.VISIBLE;
	}

	@Override
	public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
		this.callback = resourceCallback;
	}

}
