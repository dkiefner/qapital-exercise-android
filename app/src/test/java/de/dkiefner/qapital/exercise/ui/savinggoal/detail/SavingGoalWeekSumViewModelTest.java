package de.dkiefner.qapital.exercise.ui.savinggoal.detail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import de.dkiefner.qapital.exercise.common.CurrencyFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SavingGoalWeekSumViewModelTest {

	@Mock
	private CurrencyFormatter currencyFormatter;

	@InjectMocks
	private SavingGoalWeekSumViewModel testee;

	@Test
	public void thatWeekSumTextIsZero_whenViewModelWasInstantiated() {
		// given
		String expectedWeekSum = "$1.00";

		when(currencyFormatter.format(anyFloat())).thenReturn(expectedWeekSum);

		// when
		testee = new SavingGoalWeekSumViewModel(currencyFormatter);

		// then
		String result = testee.weekSumText.get();
		assertThat(result).isEqualTo(expectedWeekSum);
	}

	@Test
	public void thatUpdateWeekSumWorks_whenThereIsANewWeekSumGiven() {
		// given
		float newWeekSum = 2f;
		String expectedWeekSum = "$2.00";

		when(currencyFormatter.format(anyFloat())).thenReturn(expectedWeekSum);

		// when
		testee.updateWeekSum(newWeekSum);

		// then
		String result = testee.weekSumText.get();
		assertThat(result).isEqualTo(expectedWeekSum);
	}

}