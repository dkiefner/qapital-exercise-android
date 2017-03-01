package de.dkiefner.qapital.exercise;

import org.junit.BeforeClass;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

public class BaseRxUnitTest {

	@BeforeClass
	public static void setupClass() {
		RxAndroidPlugins.setInitMainThreadSchedulerHandler(
				__ -> Schedulers.trampoline());
	}
}
