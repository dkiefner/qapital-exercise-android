package de.dkiefner.qapital.exercise.ui.savinggoal.detail;

import java.text.ParseException;

import de.dkiefner.qapital.exercise.common.CurrencyFormatter;
import de.dkiefner.qapital.exercise.common.DateTimeParser;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEvent;
import timber.log.Timber;

public class SavingGoalEventListItemViewModel {

	private final SavingGoalEvent savingGoalEvent;
	private final CurrencyFormatter currencyFormatter;

	public SavingGoalEventListItemViewModel(SavingGoalEvent savingGoalEvent, CurrencyFormatter currencyFormatter) {
		this.savingGoalEvent = savingGoalEvent;
		this.currencyFormatter = currencyFormatter;
	}

	public String getMessage() {
		return savingGoalEvent.message();
	}

	public long getTimestamp() {
		try {
			return DateTimeParser.toMillis(savingGoalEvent.timestamp());
		} catch (ParseException e) {
			Timber.e(e, "Error parsing timestamp: " + savingGoalEvent.timestamp());
			return 0L;
		}
	}

	public String getAmount() {
		return currencyFormatter.format(savingGoalEvent.amount());
	}
}
