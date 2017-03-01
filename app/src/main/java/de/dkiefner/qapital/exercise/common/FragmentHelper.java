package de.dkiefner.qapital.exercise.common;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import timber.log.Timber;

public final class FragmentHelper {

	private FragmentHelper() {
	}

	public static boolean attachFragment(FragmentManager fragmentManager, String tag, @IdRes int id, Class<? extends Fragment> fragmentClass) {
		return attachFragment(fragmentManager, tag, id, fragmentClass, new Bundle());
	}

	public static boolean attachFragment(FragmentManager fragmentManager, String tag, @IdRes int id, Class<? extends Fragment> fragmentClass, Bundle arguments) {
		Fragment fragment = fragmentManager.findFragmentByTag(tag);
		if (fragment == null || fragment.isDetached()) {
			try {
				fragment = fragmentClass.newInstance();
				fragment.setArguments(arguments);
			} catch (InstantiationException e) {
				Timber.e(e, "Cannot instantiate Fragment");
				return false;
			} catch (IllegalAccessException e) {
				Timber.e(e, "Cannot instantiate Fragment");
				return false;
			}
		}
		fragmentManager.beginTransaction().replace(id, fragment, tag).commit();
		return true;
	}
}
