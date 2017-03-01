package de.dkiefner.qapital.exercise.data.savinggoal;

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
public class SavingGoalDataStoreTest {

	@Mock
	private SavingGoalApi savingGoalApi;
	@Mock
	private SavingGoalRepository savingGoalRepository;

	@InjectMocks
	private SavingGoalDataStore testee;

	@Test
	public void thatGetSavingGoalsWorks_whenApiAndCacheEmitOneItemEach() {
		// given
		when(savingGoalApi.getSavingGoals()).thenReturn(getMockedSavingGoalsDtoObservable());
		when(savingGoalRepository.saveAll(anyList())).thenReturn(getMockedSavingGoalsObservable());
		when(savingGoalRepository.findAll()).thenReturn(getMockedSavingGoalsObservable());

		TestObserver<List<SavingGoal>> testObserver = new TestObserver<>();

		// when
		testee.getSavingGoals().subscribe(testObserver);

		// then
		testObserver.assertNoErrors();
		testObserver.assertValueCount(2);
	}

	@Test
	public void thatGetSavingGoalsWorks_whenApiIsEmptyAndCacheEmitsOne() {
		// given
		when(savingGoalApi.getSavingGoals()).thenReturn(Observable.fromCallable(() -> {
			throw new IOException();
		}));
		when(savingGoalRepository.findAll()).thenReturn(getMockedSavingGoalsObservable());

		TestObserver<List<SavingGoal>> testObserver = new TestObserver<>();

		// when
		testee.getSavingGoals().subscribe(testObserver);

		// then
		testObserver.assertError(IOException.class);
		testObserver.assertValueCount(1);
	}

	private Observable<SavingGoalsDto> getMockedSavingGoalsDtoObservable() {
		return Observable.fromCallable(() -> {
			SavingGoalDto savingGoalDto = new SavingGoalDto();
			savingGoalDto.setId(1);
			savingGoalDto.setCurrentBalance(1f);
			savingGoalDto.setGoalImageURL("http://foo.bar");
			savingGoalDto.setName("Foo");
			savingGoalDto.setStatus("active");

			SavingGoalsDto savingGoalsDto = new SavingGoalsDto();
			savingGoalsDto.setSavingsGoals(Collections.singletonList(savingGoalDto));

			return savingGoalsDto;
		});
	}

	private Observable<List<SavingGoal>> getMockedSavingGoalsObservable() {
		return Observable.fromCallable(() -> {
			List<SavingGoal> savingGoals = new ArrayList<>();
			savingGoals.add(mock(SavingGoal.class));
			return savingGoals;
		});
	}

}