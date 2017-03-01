package de.dkiefner.qapital.exercise.ui.savinggoal.detail;

import android.databinding.ObservableBoolean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.dkiefner.qapital.exercise.BaseRxUnitTest;
import de.dkiefner.qapital.exercise.common.CurrencyFormatter;
import de.dkiefner.qapital.exercise.common.LocalClock;
import de.dkiefner.qapital.exercise.common.ui.StringResource;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoal;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEvent;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEventDataStore;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRule;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRuleDataStore;
import de.dkiefner.qapital.exercise.ui.savinggoal.detail.rules.SavingGoalRuleListViewModel;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SavingGoalDetailViewModelTest extends BaseRxUnitTest {

	private static final String TEST_TIMESTAMP = "2015-03-05T05:33:55.025Z";
	private static final ZonedDateTime TEST_NOW = ZonedDateTime.parse("2015-03-05T05:33:55.025Z");

	@Mock
	private SavingGoal savingGoal;
	@Mock
	private SavingsRuleDataStore savingsRuleDataStore;
	@Mock
	private SavingGoalEventDataStore savingGoalEventDataStore;
	@Mock
	private CurrencyFormatter currencyFormatter;
	@Mock
	private LocalClock clock;

	@InjectMocks
	private SavingGoalDetailViewModel testee;

	@Test
	public void thatLoadSavingGoalEventsWorks_whenNameIsGiven() {
		// given
		when(savingGoalEventDataStore.getSavingGoalEvents(anyInt())).thenReturn(getMockedEventsObservable());
		when(savingsRuleDataStore.getSavingsRules()).thenReturn(getMockedRulesObservable());
		when(clock.nowAsZonedDateTime()).thenReturn(TEST_NOW);

		TestObserver testObserver = new TestObserver();

		// when
		testee.loadSavingGoalEvents().subscribe(testObserver);

		// then
		testObserver.assertNoErrors();
		assertThat(testee.isLoading.get()).isFalse();
		assertThat(testee.hasEvents.get()).isTrue();
	}

	@Test
	public void thatIsLoadingIsTrue_whenEventCacheEmittedContainsNoItem() throws Exception {
		// given
		ObservableBoolean isLoadingSpy = spy(ObservableBoolean.class);
		testee.isLoading = isLoadingSpy;

		Observable<List<SavingGoalEvent>> eventsChain = Observable.concat(Observable.fromCallable(Collections::emptyList),
				getMockedEventsObservable());
		Observable<List<SavingsRule>> rulesChain = Observable.concat(getMockedRulesObservable(),
				getMockedRulesObservable());

		when(savingGoalEventDataStore.getSavingGoalEvents(anyInt())).thenReturn(eventsChain);
		when(savingsRuleDataStore.getSavingsRules()).thenReturn(rulesChain);
		when(clock.nowAsZonedDateTime()).thenReturn(TEST_NOW);

		TestObserver testObserver = new TestObserver();

		// when
		testee.loadSavingGoalEvents().subscribe(testObserver);

		// then
		testObserver.assertNoErrors();
		verify(isLoadingSpy, times(1)).set(true);
	}

	@Test
	public void thatIsLoadingIsFalse_whenEventsCacheEmittedContainsItem() throws Exception {
		// given
		ObservableBoolean isLoadingSpy = spy(ObservableBoolean.class);
		testee.isLoading = isLoadingSpy;

		Observable<List<SavingGoalEvent>> eventsChain = Observable.concat(getMockedEventsObservable(),
				getMockedEventsObservable());
		Observable<List<SavingsRule>> rulesChain = Observable.concat(getMockedRulesObservable(),
				getMockedRulesObservable());

		when(savingGoalEventDataStore.getSavingGoalEvents(anyInt())).thenReturn(eventsChain);
		when(savingsRuleDataStore.getSavingsRules()).thenReturn(rulesChain);
		when(clock.nowAsZonedDateTime()).thenReturn(TEST_NOW);

		TestObserver testObserver = new TestObserver();

		// when
		testee.loadSavingGoalEvents().subscribe(testObserver);

		// then
		testObserver.assertNoErrors();
		verify(isLoadingSpy, times(1)).set(false);
	}

	@Test
	public void thatIsLoadingIsFalse_whenNoDataIsAvailable() throws Exception {
		// given
		ObservableBoolean isLoadingSpy = spy(ObservableBoolean.class);
		testee.isLoading = isLoadingSpy;

		Observable<List<SavingGoalEvent>> eventsChain = Observable.concat(Observable.fromCallable(Collections::emptyList),
				Observable.fromCallable(Collections::emptyList));
		Observable<List<SavingsRule>> rulesChain = Observable.concat(Observable.fromCallable(Collections::emptyList),
				Observable.fromCallable(Collections::emptyList));

		when(savingGoalEventDataStore.getSavingGoalEvents(anyInt())).thenReturn(eventsChain);
		when(savingsRuleDataStore.getSavingsRules()).thenReturn(rulesChain);

		TestObserver testObserver = new TestObserver();

		// when
		testee.loadSavingGoalEvents().subscribe(testObserver);

		// then
		testObserver.assertNoErrors();
		verify(isLoadingSpy, times(1)).set(false);
	}

	@Test
	public void thatWeekSumIsCorrect_whenSomethingIsInCurrentWeek() throws Exception {
		// given
		float amount = 1f;
		String expectedWeekSumText = "$1.00";

		ZonedDateTime now = ZonedDateTime.parse(TEST_TIMESTAMP).plusDays(1L);
		List<SavingGoalEvent> savingGoalEvents = new ArrayList<>();
		SavingGoalEvent savingGoalEvent = mock(SavingGoalEvent.class);
		savingGoalEvents.add(savingGoalEvent);

		Observable<List<SavingGoalEvent>> eventsChain = Observable.fromCallable(() -> savingGoalEvents);
		Observable<List<SavingsRule>> rulesChain = getMockedRulesObservable();

		when(savingGoalEventDataStore.getSavingGoalEvents(anyInt())).thenReturn(eventsChain);
		when(savingsRuleDataStore.getSavingsRules()).thenReturn(rulesChain);
		when(savingGoalEvent.timestamp()).thenReturn(TEST_TIMESTAMP);
		when(clock.nowAsZonedDateTime()).thenReturn(now);
		when(savingGoalEvent.amount()).thenReturn(amount);
		when(currencyFormatter.format(eq(amount))).thenReturn(expectedWeekSumText);

		// when
		testee.loadSavingGoalEvents().subscribe();

		// then
		assertThat(testee.getWeekSumItem().weekSumText.get()).isEqualTo(expectedWeekSumText);
	}

	@Test
	public void thatPublicToSubjectWorks_whenEmits() throws Exception {
		// given
		when(savingsRuleDataStore.getSavingsRules()).thenReturn(getMockedRulesObservable());
		when(savingGoalEventDataStore.getSavingGoalEvents(anyInt())).thenReturn(getMockedEventsObservable());
		when(clock.nowAsZonedDateTime()).thenReturn(TEST_NOW);

		TestObserver<List<SavingGoalEventListItemViewModel>> testObserver = new TestObserver<>();

		// when
		testee.savingGoalEventsSubject.subscribe(testObserver);
		testee.loadSavingGoalEvents().subscribe();

		// then
		testObserver.assertValueCount(1);
	}

	@Test
	public void thatGetProgressActualIsCorrect_whenSimpleCurrentBalanceIsGiven() {
		// given
		float currentBalance = 1f;
		int expectedProgressActual = 1;

		when(savingGoal.currentBalance()).thenReturn(currentBalance);

		// when
		int result = testee.getProgressActual();

		// then
		assertThat(result).isEqualTo(expectedProgressActual);
	}

	@Test
	public void thatGetProgressActualIsRoundingUp_whenCurrentBalanceFirstDecimalPlaceIsFive() {
		// given
		float currentBalance = 1.5f;
		int expectedProgressActual = 2;

		when(savingGoal.currentBalance()).thenReturn(currentBalance);

		// when
		int result = testee.getProgressActual();

		// then
		assertThat(result).isEqualTo(expectedProgressActual);
	}

	@Test
	public void thatGetProgressMaxIsCorrect_whenTargetAmountIsSet() {
		// given
		float targetAmount = 1f;
		int expectedProgressMax = 1;

		when(savingGoal.targetAmount()).thenReturn(targetAmount);

		// when
		int result = testee.getProgressMax();

		// then
		assertThat(result).isEqualTo(expectedProgressMax);
	}

	@Test
	public void thatGetProgressMaxIsRoundingUp_whenTargetAmountFirstDecimalPlaceIsFive() {
		// given
		float targetAmount = 1.5f;
		int expectedProgressMax = 2;

		when(savingGoal.targetAmount()).thenReturn(targetAmount);

		// when
		int result = testee.getProgressMax();

		// then
		assertThat(result).isEqualTo(expectedProgressMax);
	}

	@Test
	public void thatGetProgressMaxIsSetToProgressActual_whenNoTargetAmountIsSet() {
		// given
		float currentBalance = 1f;
		int expectedProgressMax = 1;

		when(savingGoal.currentBalance()).thenReturn(currentBalance);
		when(savingGoal.targetAmount()).thenReturn(null);

		// when
		int result = testee.getProgressMax();

		// then
		assertThat(result).isEqualTo(expectedProgressMax);
	}

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

	@Test
	public void thatIsProgressVisibleIsTrue_whenTargetAmountIsGiven() {
		// given
		when(savingGoal.targetAmount()).thenReturn(1f);

		// when
		boolean result = testee.isProgressVisible();

		// then
		assertThat(result).isTrue();
	}

	@Test
	public void thatIsProgressVisibleIsFalse_whenTargetAmountIsNotGiven() {
		// given
		when(savingGoal.targetAmount()).thenReturn(null);

		// when
		boolean result = testee.isProgressVisible();

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void thatGetSavingGoalRuleListViewModelIsNotNull() {
		// given

		// when
		SavingGoalRuleListViewModel result = testee.getSavingRulesItem();

		// then
		assertThat(result).isNotNull();
	}

	private Observable<List<SavingGoalEvent>> getMockedEventsObservable() {
		return Observable.fromCallable(() -> {
			List<SavingGoalEvent> savingGoalEvents = new ArrayList<>();
			SavingGoalEvent savingGoalEvent = mock(SavingGoalEvent.class);
			savingGoalEvents.add(savingGoalEvent);
			when(savingGoalEvent.timestamp()).thenReturn(TEST_TIMESTAMP);
			return savingGoalEvents;
		});
	}

	private Observable<List<SavingsRule>> getMockedRulesObservable() {

		return Observable.fromCallable(() -> {
			List<SavingsRule> savingsRules = new ArrayList<>();
			savingsRules.add(mock(SavingsRule.class));
			return savingsRules;
		});
	}

}