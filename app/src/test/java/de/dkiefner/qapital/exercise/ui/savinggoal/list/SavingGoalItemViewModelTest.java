package de.dkiefner.qapital.exercise.ui.savinggoal.list;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import de.dkiefner.qapital.exercise.common.CurrencyFormatter;
import de.dkiefner.qapital.exercise.common.ui.StringResource;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SavingGoalItemViewModelTest {

	@Mock
	private SavingGoal savingGoal;
	@Mock
	private CurrencyFormatter currencyFormatter;

	@InjectMocks
	private SavingGoalItemViewModel testee;

	@Test
	public void thatGetStatusIsCorrect_whenCurrentBalanceAndTargetAmountIsGiven() {
		// given
		float currentBalance = 1f;
		float targetAmount = 2f;
		String expectedCurrentBalance = "$1.00";
		String expectedTargetAmount = "$2.00";

		when(savingGoal.currentBalance()).thenReturn(currentBalance);
		when(savingGoal.targetAmount()).thenReturn(targetAmount);
		when(currencyFormatter.format(eq(currentBalance))).thenReturn(expectedCurrentBalance);
		when(currencyFormatter.format(eq(targetAmount))).thenReturn(expectedTargetAmount);

		// when
		StringResource result = testee.getStatus();

		// then
		assertThat(result.getResourceId()).isNotNull();
		assertThat(result.getItems().length).isEqualTo(2);
		assertThat(result.getItems()[0]).isEqualTo(expectedCurrentBalance);
		assertThat(result.getItems()[1]).isEqualTo(expectedTargetAmount);
	}

	@Test
	public void thatGetStatusIsCorrect_whenJustCurrentBalanceIsGiven() {
		// given
		float currentBalance = 1f;
		String expectedCurrentBalance = "$1.00";

		when(savingGoal.currentBalance()).thenReturn(currentBalance);
		when(savingGoal.targetAmount()).thenReturn(null);
		when(currencyFormatter.format(eq(currentBalance))).thenReturn(expectedCurrentBalance);

		// when
		StringResource result = testee.getStatus();

		// then
		assertThat(result.getResourceId()).isNull();
		assertThat(result.getItems()).isEmpty();
		assertThat(result.getRawString()).isEqualTo(expectedCurrentBalance);
	}

	@Test
	public void thatGetNameIsCorrect_whenOneIsGiven() {
		// given
		String expectedName = "foo";

		when(savingGoal.name()).thenReturn(expectedName);

		// when
		String result = testee.getName();

		// then
		assertThat(result).isEqualTo(expectedName);
	}

	@Test
	public void thatGetImageUrlIsCorrect_whenOneIsGiven() {
		// given
		String expectedImageUrl = "http://foo.bar";

		when(savingGoal.goalImageURL()).thenReturn(expectedImageUrl);

		// when
		String result = testee.getImageUrl();

		// then
		assertThat(result).isEqualTo(expectedImageUrl);
	}

}