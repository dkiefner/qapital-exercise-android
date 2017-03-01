package de.dkiefner.qapital.exercise.ui.savinggoal.list;

import android.databinding.ObservableBoolean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.dkiefner.qapital.exercise.BaseRxUnitTest;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoal;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoalDataStore;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SavingGoalListViewModelTest extends BaseRxUnitTest {

	@Mock
	private SavingGoalDataStore savingGoalDataStore;

	@InjectMocks
	private SavingGoalListViewModel testee;

	@Test
	public void thatLoadGoalsWorks_whenTwoItemsWillBeReturned() throws Exception {
		// given
		List<SavingGoal> savingGoals = new ArrayList<>();
		savingGoals.add(mock(SavingGoal.class));
		savingGoals.add(mock(SavingGoal.class));

		when(savingGoalDataStore.getSavingGoals()).thenReturn(Observable.just(savingGoals));

		TestObserver testObserver = new TestObserver();

		// when
		testee.loadGoals().subscribe(testObserver);

		// then
		testObserver.assertNoErrors();
		assertThat(testee.isLoading.get()).isFalse();
		assertThat(testee.isEmpty.get()).isFalse();
	}

	@Test
	public void thatLoadGoalsWorks_whenNoItemWillBeReturned() throws Exception {
		// given
		when(savingGoalDataStore.getSavingGoals()).thenReturn(Observable.just(Collections.emptyList()));

		TestObserver testObserver = new TestObserver();

		// when
		testee.loadGoals().subscribe(testObserver);

		// then
		testObserver.assertNoErrors();
		assertThat(testee.isLoading.get()).isFalse();
		assertThat(testee.isEmpty.get()).isTrue();
	}

	@Test
	public void thatGetItemReturnsCorrect_whenDataStoreHasTwoItems() throws Exception {
		// given
		List<SavingGoal> savingGoals = new ArrayList<>();
		savingGoals.add(mock(SavingGoal.class));
		SavingGoal secondSavingGoal = mock(SavingGoal.class);
		savingGoals.add(secondSavingGoal);

		when(savingGoalDataStore.getSavingGoals()).thenReturn(Observable.just(savingGoals));

		TestObserver testObserver = new TestObserver();
		testee.loadGoals().subscribe(testObserver);

		// when
		final SavingGoalItemViewModel result = testee.getItem(1);

		// then
		assertThat(result.getSavingGoal()).isEqualTo(secondSavingGoal);
	}

	@Test
	public void thatGetItemCountIsCorrect_whenDataStoreHasTwoItems() throws Exception {
		// given
		List<SavingGoal> savingGoals = new ArrayList<>();
		savingGoals.add(mock(SavingGoal.class));
		savingGoals.add(mock(SavingGoal.class));
		final int expectedCount = savingGoals.size();

		when(savingGoalDataStore.getSavingGoals()).thenReturn(Observable.just(savingGoals));

		TestObserver testObserver = new TestObserver();
		testee.loadGoals().subscribe(testObserver);

		// when
		final int result = testee.getItemCount();

		// then
		assertThat(result).isEqualTo(expectedCount);
	}

	@Test
	public void thatIsLoadingIsTrue_whenCacheEmittedContainsNoItem() throws Exception {
		// given
		ObservableBoolean isLoadingSpy = spy(ObservableBoolean.class);
		testee.isLoading = isLoadingSpy;

		Observable<List<SavingGoal>> observableChain = Observable.concat(Observable.fromCallable(Collections::emptyList),
				getMockedSavingGoalsObservable());

		when(savingGoalDataStore.getSavingGoals()).thenReturn(observableChain);

		TestObserver testObserver = new TestObserver();

		// when
		testee.loadGoals().subscribe(testObserver);

		// then
		testObserver.assertNoErrors();
		verify(isLoadingSpy, times(1)).set(true);
	}

	@Test
	public void thatIsLoadingIsFalse_whenCacheEmittedContainsItem() throws Exception {
		// given
		ObservableBoolean isLoadingSpy = spy(ObservableBoolean.class);
		testee.isLoading = isLoadingSpy;

		Observable<List<SavingGoal>> observableChain = Observable.concat(getMockedSavingGoalsObservable(),
				getMockedSavingGoalsObservable());

		when(savingGoalDataStore.getSavingGoals()).thenReturn(observableChain);

		TestObserver testObserver = new TestObserver();

		// when
		testee.loadGoals().subscribe(testObserver);

		// then
		testObserver.assertNoErrors();
		verify(isLoadingSpy, times(1)).set(false);
	}

	@Test
	public void thatPublicToSubjectWorks_whenEmits() throws Exception {
		// given
		when(savingGoalDataStore.getSavingGoals()).thenReturn(getMockedSavingGoalsObservable());

		TestObserver<List<SavingGoalItemViewModel>> testObserver = new TestObserver<>();

		// when
		testee.savingGoalsSubject.subscribe(testObserver);
		testee.loadGoals().subscribe();

		// then
		testObserver.assertValueCount(1);
	}

	private Observable<List<SavingGoal>> getMockedSavingGoalsObservable() {
		return Observable.fromCallable(() -> {
			List<SavingGoal> savingGoals = new ArrayList<>();
			savingGoals.add(mock(SavingGoal.class));
			return savingGoals;
		});
	}

}