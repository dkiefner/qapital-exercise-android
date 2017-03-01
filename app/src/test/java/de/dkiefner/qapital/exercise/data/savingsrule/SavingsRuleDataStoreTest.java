package de.dkiefner.qapital.exercise.data.savingsrule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SavingsRuleDataStoreTest {

	@Mock
	private SavingsRuleApi savingsRuleApi;
	@Mock
	private SavingsRuleRepository savingsRuleRepository;

	@InjectMocks
	private SavingsRuleDataStore testee;

	@Test
	public void thatGetSavingsRulesWorks_whenApiAndCacheEmitOneItemEach() {
		// given
		when(savingsRuleApi.getSavingsRules()).thenReturn(getMockedSavingsRulesDtoObservable());
		when(savingsRuleRepository.saveAll(anyList())).thenReturn(getMockedSavingsRulesObservable());
		when(savingsRuleRepository.findAll()).thenReturn(getMockedSavingsRulesObservable());

		TestObserver<List<SavingsRule>> testObserver = new TestObserver<>();

		// when
		testee.getSavingsRules().subscribe(testObserver);

		// then
		testObserver.assertNoErrors();
		testObserver.assertValueCount(2);
	}

	@Test
	public void thatGetSavingsRulesWorks_whenApiIsEmptyAndCacheEmitsOne() {
		// given
		when(savingsRuleApi.getSavingsRules()).thenReturn(Observable.fromCallable(() -> {
			throw new IOException();
		}));
		when(savingsRuleRepository.findAll()).thenReturn(getMockedSavingsRulesObservable());

		TestObserver<List<SavingsRule>> testObserver = new TestObserver<>();

		// when
		testee.getSavingsRules().subscribe(testObserver);

		// then
		testObserver.assertError(IOException.class);
		testObserver.assertValueCount(1);
	}

	private Observable<SavingsRulesDto> getMockedSavingsRulesDtoObservable() {
		return Observable.fromCallable(() -> {
			SavingsRuleDto savingsRuleDto = new SavingsRuleDto();
			savingsRuleDto.setId(1);
			savingsRuleDto.setType(SavingsRule.Type.ROUND_UP);
			savingsRuleDto.setAmount(1f);

			SavingsRulesDto savingsRulesDto = new SavingsRulesDto();
			savingsRulesDto.setSavingsRules(Collections.singletonList(savingsRuleDto));

			return savingsRulesDto;
		});
	}

	private Observable<List<SavingsRule>> getMockedSavingsRulesObservable() {
		return Observable.fromCallable(() -> {
			List<SavingsRule> savingsRules = new ArrayList<>();
			savingsRules.add(mock(SavingsRule.class));
			return savingsRules;
		});
	}
}