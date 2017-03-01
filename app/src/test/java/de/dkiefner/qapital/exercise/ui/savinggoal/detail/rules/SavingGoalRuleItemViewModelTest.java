package de.dkiefner.qapital.exercise.ui.savinggoal.detail.rules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SavingGoalRuleItemViewModelTest {

	@Mock
	private SavingsRule savingsRule;

	@InjectMocks
	private SavingGoalRuleItemViewModel testee;

	@Test
	public void thatGetImageIdReturnsRoundUp_whenRuleIsRoundUp() {
		// given
		int expectedImageId = R.drawable.rule_round_up;

		when(savingsRule.type()).thenReturn(SavingsRule.Type.ROUND_UP);

		// when
		int result = testee.getImageId();

		// then
		assertThat(result).isEqualTo(expectedImageId);
	}

	@Test
	public void thatGetImageIdReturnsGuiltyPleasure_whenRuleIsGuiltyPleasure() {
		// given
		int expectedImageId = R.drawable.rule_guilty_pleasure;

		when(savingsRule.type()).thenReturn(SavingsRule.Type.GUILTY_PLEASURE);

		// when
		int result = testee.getImageId();

		// then
		assertThat(result).isEqualTo(expectedImageId);
	}

	@Test
	public void thatGetImageIdReturnsUnknown_whenRuleIsUnknown() {
		// given
		int expectedImageId = R.drawable.rule_unknown;

		when(savingsRule.type()).thenReturn("foo");

		// when
		int result = testee.getImageId();

		// then
		assertThat(result).isEqualTo(expectedImageId);
	}

	@Test
	public void thatGetImageIdReturnsUnknown_whenRuleIsNull() {
		// given
		int expectedImageId = R.drawable.rule_unknown;

		when(savingsRule.type()).thenReturn(null);

		// when
		int result = testee.getImageId();

		// then
		assertThat(result).isEqualTo(expectedImageId);
	}

}