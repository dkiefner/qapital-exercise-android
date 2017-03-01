package de.dkiefner.qapital.exercise.ui.savinggoal.list;

import android.databinding.ObservableBoolean;

import java.util.ArrayList;
import java.util.List;

import de.dkiefner.qapital.exercise.common.CurrencyFormatter;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoal;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoalDataStore;
import io.reactivex.Completable;
import io.reactivex.subjects.BehaviorSubject;

public class SavingGoalListViewModel {

	public ObservableBoolean isLoading = new ObservableBoolean(false);
	public ObservableBoolean isEmpty = new ObservableBoolean(true);
	public final BehaviorSubject<List<SavingGoalItemViewModel>> savingGoalsSubject;

	private final SavingGoalDataStore savingGoalDataStore;
	private List<SavingGoalItemViewModel> savingGoalItemViewModels;

	private final CurrencyFormatter currencyFormatter;

	public SavingGoalListViewModel(SavingGoalDataStore savingGoalDataStore, CurrencyFormatter currencyFormatter) {
		this.savingGoalDataStore = savingGoalDataStore;
		this.currencyFormatter = currencyFormatter;
		savingGoalItemViewModels = new ArrayList<>();
		savingGoalsSubject = BehaviorSubject.create();
	}

	public Completable loadGoals() {
		return savingGoalDataStore.getSavingGoals()
				.doOnSubscribe(disposable -> setLoading(true))
				.doOnNext(this::updateSavingGoals)
				.doOnTerminate(() -> setLoading(false))
				.ignoreElements();
	}

	private void updateSavingGoals(List<SavingGoal> savingGoals) {
		savingGoalItemViewModels.clear();
		for (SavingGoal savingGoal : savingGoals) {
			savingGoalItemViewModels.add(new SavingGoalItemViewModel(savingGoal, currencyFormatter));
		}
		savingGoalsSubject.onNext(savingGoalItemViewModels);
		isEmpty.set(savingGoals.size() == 0);
		setLoading(isEmpty.get());
	}

	private void setLoading(boolean isLoading) {
		if(this.isLoading.get() != isLoading) {
			this.isLoading.set(isLoading);
		}
	}

	public SavingGoalItemViewModel getItem(int position) {
		return savingGoalItemViewModels.get(position);
	}

	public int getItemCount() {
		return savingGoalItemViewModels.size();
	}
}
