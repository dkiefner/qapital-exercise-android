package de.dkiefner.qapital.exercise.ui.savinggoal.detail.rules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SavingGoalRuleListViewModelTest {

	@InjectMocks
	private SavingGoalRuleListViewModel testee;

	@Test
	public void thatGetItemIsNotNull_whenPositionExists() {
		// given
		testee.updateSavingRules(Collections.singletonList(mock(SavingsRule.class)));

		// when
		SavingGoalRuleItemViewModel result = testee.getItem(0);

		// then
		assertThat(result).isNotNull();
	}

	@Test
	public void thatGetItemIsNull_whenPositionDoesNotExists() {
		// given

		// when
		SavingGoalRuleItemViewModel result = testee.getItem(0);

		// then
		assertThat(result).isNull();
	}

	@Test
	public void thatGetItemCountIsCorrect_whenListOfSavingRulesAreGiven() {
		// given
		List<SavingsRule> savingRules = Arrays.asList(
				mock(SavingsRule.class),
				mock(SavingsRule.class)
		);
		testee.updateSavingRules(savingRules);
		int expectedSize = savingRules.size();

		// when
		int result = testee.getItemCount();

		// then
		assertThat(result).isEqualTo(expectedSize);
	}

}