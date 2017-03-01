package de.dkiefner.qapital.exercise.ui.savinggoal.detail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;

import de.dkiefner.qapital.exercise.common.CurrencyFormatter;
import de.dkiefner.qapital.exercise.common.DateTimeParser;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEvent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SavingGoalEventListItemViewModelTest {

	@Mock
	private SavingGoalEvent savingGoalEvent;
	@Mock
	private CurrencyFormatter currencyFormatter;

	@InjectMocks
	private SavingGoalEventListItemViewModel testee;

	@Test
	public void thatGetMessageWorks_whenOneIsGiven() {
		// given
		String expectedMessage = "foo";

		when(savingGoalEvent.message()).thenReturn(expectedMessage);

		// when
		String result = testee.getMessage();

		// then
		assertThat(result).isEqualTo(expectedMessage);
	}

	@Test
	public void thatGetTimestampWorks_whenValidOneIsGiven() throws ParseException {
		// given
		String timestamp = "2015-03-05T05:33:55.025Z";
		long expectedTimestamp = DateTimeParser.toMillis(timestamp);

		when(savingGoalEvent.timestamp()).thenReturn(timestamp);

		// when
		long result = testee.getTimestamp();

		// then
		assertThat(result).isEqualTo(expectedTimestamp);
	}

	@Test
	public void thatGetTimestampReturnsZero_whenInvalidOneIsGiven() throws ParseException {
		// given
		String timestamp = "foo";
		long expectedTimestamp = 0L;

		when(savingGoalEvent.timestamp()).thenReturn(timestamp);

		// when
		long result = testee.getTimestamp();

		// then
		assertThat(result).isEqualTo(expectedTimestamp);
	}

	@Test
	public void thatGetAmountWorks_whenOneIsGiven() {
		// given
		float amount = 1f;
		String expectedAmount = "$1.00";

		when(savingGoalEvent.amount()).thenReturn(amount);
		when(currencyFormatter.format(eq(amount))).thenReturn(expectedAmount);

		// when
		String result = testee.getAmount();

		// then
		assertThat(result).isEqualTo(expectedAmount);
	}

}