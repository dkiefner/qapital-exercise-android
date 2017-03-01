package de.dkiefner.qapital.exercise.ui.savinggoal.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.common.FragmentHelper;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoal;

public class SavingGoalDetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plain_fragment);
		showContentFragment(parseGoalAsArguments());
	}

	@NonNull
	private Bundle parseGoalAsArguments() {
		Bundle arguments = new Bundle();
		final SavingGoal savingGoal = (SavingGoal) getIntent().getSerializableExtra(SavingGoalDetailFragment.KEY_SAVING_GOAL);
		arguments.putSerializable(SavingGoalDetailFragment.KEY_SAVING_GOAL, savingGoal);
		return arguments;
	}

	private void showContentFragment(Bundle arguments) {
		FragmentHelper.attachFragment(getSupportFragmentManager(),
				SavingGoalDetailFragment.TAG,
				R.id.fragment_container,
				SavingGoalDetailFragment.class,
				arguments);
	}
}
