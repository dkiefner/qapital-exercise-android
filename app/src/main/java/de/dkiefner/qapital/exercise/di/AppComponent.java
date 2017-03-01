package de.dkiefner.qapital.exercise.di;

import dagger.Component;
import de.dkiefner.qapital.exercise.QapitalExercise;
import de.dkiefner.qapital.exercise.ui.savinggoal.detail.SavingGoalDetailFragment;
import de.dkiefner.qapital.exercise.ui.savinggoal.list.SavingGoalListFragment;

@PerApp
@Component(
		modules = {
				AppModule.class,
				ApiModule.class,
				DataModule.class
		}
)
public interface AppComponent {
	QapitalExercise application();

	void inject(SavingGoalListFragment fragment);

	void inject(SavingGoalDetailFragment fragment);
}