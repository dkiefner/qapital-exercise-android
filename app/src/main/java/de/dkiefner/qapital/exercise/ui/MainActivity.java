package de.dkiefner.qapital.exercise.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.common.FragmentHelper;
import de.dkiefner.qapital.exercise.ui.savinggoal.list.SavingGoalListFragment;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plain_fragment);
		showContentFragment();
	}

	private void showContentFragment() {
		FragmentHelper.attachFragment(getSupportFragmentManager(),
				SavingGoalListFragment.TAG,
				R.id.fragment_container,
				SavingGoalListFragment.class);
	}
}
