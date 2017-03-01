package de.dkiefner.qapital.exercise.data.savinggoal.event;

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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SavingGoalEventDataStoreTest {

	@Mock
	private SavingGoalEventApi savingGoalEventApi;
	@Mock
	private SavingGoalEventRepository savingGoalEventRepository;

	@InjectMocks
	private SavingGoalEventDataStore testee;

	@Test
	public void thatGetSavingGoalEventsWorks_whenApiAndCacheEmitOneItemEach() {
		// given
		when(savingGoalEventApi.getSavingGoalEvents(anyInt())).thenReturn(getMockedSavingGoalEventsDtoObservable());
		when(savingGoalEventRepository.saveAll(anyList())).thenReturn(getMockedSavingGoalEventsObservable());
		when(savingGoalEventRepository.findAllBySavingGoal(anyInt())).thenReturn(getMockedSavingGoalEventsObservable());

		TestObserver<List<SavingGoalEvent>> testObserver = new TestObserver<>();

		// when
		testee.getSavingGoalEvents(1).subscribe(testObserver);

		// then
		testObserver.assertNoErrors();
		testObserver.assertValueCount(2);
	}

	@Test
	public void thatGetSavingGoalEventsWorks_whenApiIsEmptyAndCacheEmitsOne() {
		// given
		when(savingGoalEventApi.getSavingGoalEvents(anyInt())).thenReturn(Observable.fromCallable(() -> {
			throw new IOException();
		}));
		when(savingGoalEventRepository.findAllBySavingGoal(anyInt())).thenReturn(getMockedSavingGoalEventsObservable());

		TestObserver<List<SavingGoalEvent>> testObserver = new TestObserver<>();

		// when
		testee.getSavingGoalEvents(1).subscribe(testObserver);

		// then
		testObserver.assertError(IOException.class);
		testObserver.assertValueCount(1);
	}

	private Observable<SavingGoalEventsDto> getMockedSavingGoalEventsDtoObservable() {
		return Observable.fromCallable(() -> {
			SavingGoalEventDto savingGoalEventDto = new SavingGoalEventDto();
			savingGoalEventDto.setId("1");
			savingGoalEventDto.setType("save");
			savingGoalEventDto.setAmount(1f);
			savingGoalEventDto.setMessage("Foo");
			savingGoalEventDto.setTimestamp("2015-03-05T05:33:55.025Z");

			SavingGoalEventsDto savingGoalEventsDto = new SavingGoalEventsDto();
			savingGoalEventsDto.setFeed(Collections.singletonList(savingGoalEventDto));

			return savingGoalEventsDto;
		});
	}

	private Observable<List<SavingGoalEvent>> getMockedSavingGoalEventsObservable() {
		return Observable.fromCallable(() -> {
			List<SavingGoalEvent> savingGoalEvents = new ArrayList<>();
			savingGoalEvents.add(mock(SavingGoalEvent.class));
			return savingGoalEvents;
		});
	}

}