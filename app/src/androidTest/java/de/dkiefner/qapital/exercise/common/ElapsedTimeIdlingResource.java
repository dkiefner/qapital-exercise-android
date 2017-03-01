package de.dkiefner.qapital.exercise.common;

import android.support.test.espresso.IdlingResource;

// @see: http://stackoverflow.com/questions/30155227/espresso-how-to-wait-for-some-time1-hour
public class ElapsedTimeIdlingResource implements IdlingResource {
	private final long startTime;
	private final long waitingTime;
	private ResourceCallback resourceCallback;

	public ElapsedTimeIdlingResource(long waitingTimeInMillis) {
		this.startTime = System.currentTimeMillis();
		this.waitingTime = waitingTimeInMillis;
	}

	@Override
	public String getName() {
		return ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
	}

	@Override
	public boolean isIdleNow() {
		long elapsed = System.currentTimeMillis() - startTime;
		boolean idle = (elapsed >= waitingTime);
		if (idle) {
			resourceCallback.onTransitionToIdle();
		}
		return idle;
	}

	@Override
	public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
		this.resourceCallback = resourceCallback;
	}
}
