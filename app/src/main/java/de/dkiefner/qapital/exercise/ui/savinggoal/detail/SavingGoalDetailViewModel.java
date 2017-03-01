package de.dkiefner.qapital.exercise.ui.savinggoal.detail;

import android.databinding.ObservableBoolean;

import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;

import de.dkiefner.qapital.exercise.common.CurrencyFormatter;
import de.dkiefner.qapital.exercise.common.LocalClock;
import de.dkiefner.qapital.exercise.common.ui.StringResource;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoal;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEvent;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEventDataStore;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRule;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRuleDataStore;
import de.dkiefner.qapital.exercise.ui.savinggoal.detail.rules.SavingGoalRuleListViewModel;
import de.dkiefner.qapital.exercise.ui.savinggoal.list.SavingGoalItemViewModel;
import io.reactivex.Completable;
import io.reactivex.subjects.BehaviorSubject;

public class SavingGoalDetailViewModel {

	public ObservableBoolean isLoading = new ObservableBoolean(true);
	public ObservableBoolean hasEvents = new ObservableBoolean(false);
	public final BehaviorSubject<List<SavingGoalEventListItemViewModel>> savingGoalEventsSubject;

	private final SavingGoal savingGoal;

	private final SavingsRuleDataStore savingsRuleDataStore;
	private final SavingGoalEventDataStore savingGoalEventDataStore;

	private final CurrencyFormatter currencyFormatter;
	private final LocalClock clock;

	private List<SavingGoalEventListItemViewModel> savingGoalEventListItemViewModels;
	private SavingGoalRuleListViewModel savingGoalRuleListViewModel;
	private SavingGoalItemViewModel savingGoalItemViewModel;
	private SavingGoalWeekSumViewModel savingGoalWeekSumViewModel;

	public SavingGoalDetailViewModel(SavingGoal savingGoal, SavingsRuleDataStore savingsRuleDataStore,
									 SavingGoalEventDataStore savingGoalEventDataStore, CurrencyFormatter currencyFormatter,
									 LocalClock clock) {
		this.savingGoal = savingGoal;
		this.savingsRuleDataStore = savingsRuleDataStore;
		this.savingGoalEventDataStore = savingGoalEventDataStore;
		this.currencyFormatter = currencyFormatter;
		this.clock = clock;

		savingGoalEventsSubject = BehaviorSubject.create();
		savingGoalEventListItemViewModels = new ArrayList<>();
		savingGoalRuleListViewModel = new SavingGoalRuleListViewModel();
		savingGoalItemViewModel = new SavingGoalItemViewModel(savingGoal, currencyFormatter);
		savingGoalWeekSumViewModel = new SavingGoalWeekSumViewModel(currencyFormatter);
	}

	public Completable loadSavingGoalEvents() {
		Completable rulesCompletable = savingsRuleDataStore.getSavingsRules()
				.doOnNext(this::updateSavingsRules)
				.ignoreElements();
		Completable eventsCompletable = savingGoalEventDataStore.getSavingGoalEvents(savingGoal.id())
				.doOnNext(this::updateSavingGoalEvents)
				.ignoreElements();

		return Completable.mergeArrayDelayError(rulesCompletable, eventsCompletable)
				.doOnSubscribe(disposable -> setLoading(true))
				.doOnTerminate(() -> setLoading(false));
	}

	private void updateSavingsRules(List<SavingsRule> savingsRules) {
		savingGoalRuleListViewModel.updateSavingRules(savingsRules);
	}

	private void setLoading(boolean isLoading) {
		if (this.isLoading.get() != isLoading) {
			this.isLoading.set(isLoading);
		}
	}

	private void updateSavingGoalEvents(List<SavingGoalEvent> savingGoalEvents) {
		savingGoalEventListItemViewModels.clear();

		float weekSum = 0f;
		for (SavingGoalEvent savingGoalEvent : savingGoalEvents) {
			savingGoalEventListItemViewModels.add(new SavingGoalEventListItemViewModel(savingGoalEvent, currencyFormatter));
			weekSum += getAmountIfInCurrentWeek(savingGoalEvent);
		}

		savingGoalWeekSumViewModel.updateWeekSum(weekSum);
		savingGoalEventsSubject.onNext(savingGoalEventListItemViewModels);
		boolean eventsAvailable = savingGoalEvents.size() > 0;
		hasEvents.set(eventsAvailable);
		setLoading(!eventsAvailable);
	}

	private float getAmountIfInCurrentWeek(SavingGoalEvent savingGoalEvent) {
		ZonedDateTime timestamp = ZonedDateTime.parse(savingGoalEvent.timestamp());
		ZonedDateTime now = clock.nowAsZonedDateTime();
		if (now.minus(1, ChronoUnit.WEEKS).isBefore(timestamp)) {
			return savingGoalEvent.amount();
		}
		return 0f;
	}

	public int getProgressActual() {
		return Math.round(savingGoal.currentBalance());
	}

	public int getProgressMax() {
		if (savingGoal.targetAmount() != null) {
			return Math.round(savingGoal.targetAmount());
		} else {
			return getProgressActual();
		}
	}

	public String getName() {
		return savingGoalItemViewModel.getName();
	}

	public StringResource getStatus() {
		return savingGoalItemViewModel.getStatus();
	}

	public String getImageUrl() {
		return savingGoalItemViewModel.getImageUrl();
	}

	public boolean isProgressVisible() {
		return savingGoal.targetAmount() != null;
	}

	public int getEventItemCount() {
		return savingGoalEventListItemViewModels.size();
	}

	public SavingGoalEventListItemViewModel getEventItem(int position) {
		return savingGoalEventListItemViewModels.get(position);
	}

	public SavingGoalRuleListViewModel getSavingRulesItem() {
		return savingGoalRuleListViewModel;
	}

	public SavingGoalWeekSumViewModel getWeekSumItem() {
		return savingGoalWeekSumViewModel;
	}
}
