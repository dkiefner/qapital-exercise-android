package de.dkiefner.qapital.exercise.ui.savinggoal.detail;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.dkiefner.qapital.exercise.BaseTest;
import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.common.ElapsedTimeIdlingResource;
import de.dkiefner.qapital.exercise.common.RecyclerViewMatcher;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEventTestData;
import de.dkiefner.qapital.exercise.data.savingrule.SavingsRuleTestData;
import de.dkiefner.qapital.exercise.ui.savinggoal.list.GoToSavingGoalDetailsFixture;
import okhttp3.mockwebserver.MockResponse;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SavingGoalDetailTest extends BaseTest {

	private GoToSavingGoalDetailsFixture goToSavingGoalDetailsFixture;
	private ElapsedTimeIdlingResource waitForFragmentIdlingResource;

	@Override
	public void setup() throws Exception {
		super.setup();
		goToSavingGoalDetailsFixture = new GoToSavingGoalDetailsFixture(server, activityTestRule);
		goToSavingGoalDetailsFixture.load();

		waitForFragmentIdlingResource = new ElapsedTimeIdlingResource(10L);
		Espresso.registerIdlingResources(waitForFragmentIdlingResource);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		goToSavingGoalDetailsFixture.tearDown();
		Espresso.unregisterIdlingResources(waitForFragmentIdlingResource);
	}

	@Test
	public void thatHeaderIstCorrect_whenElementHasTargetAmount() {
		// given
		String expectedTitleValue = "foo";
		String expectedStatusValue = "$10.00 of $100.00";

		// when
		ViewInteraction headerTitle = onView(withText(expectedTitleValue));
		ViewInteraction headerStatusText = onView(withText(expectedStatusValue));
		ViewInteraction headerProgress = onView(withId(R.id.goal_progress));

		// then
		headerTitle.check(matches(withText(expectedTitleValue)));
		headerStatusText.check(matches(withText(expectedStatusValue)));
		headerProgress.check(matches(isDisplayed()));
	}

	@Test
	public void thatRulesAreDisplayed_whenTwoRulesAreAvailable() {
		// given
		loadRulesAndEventsWithContent();

		// when
		ViewInteraction ruleRoundUpImage = onView(
				allOf(withId(R.id.rule_image),
						getRuleImageAtPosition(0)));

		ViewInteraction ruleGuiltyPleasureImage = onView(
				allOf(withId(R.id.rule_image),
						getRuleImageAtPosition(1)));

		// then
		ruleRoundUpImage.check(matches(isDisplayed()));
		ruleGuiltyPleasureImage.check(matches(isDisplayed()));
	}

	@Test
	public void thatWeekSumIsCorrect_whenNoSavingThisWeek() {
		// given
		String expectedWeekSumText = "$0.00";
		loadRulesAndEventsWithContent();

		// when
		ViewInteraction textView = onView(allOf(withText(expectedWeekSumText), getListItemAtPosition(1)));

		// then
		textView.check(matches(withText(expectedWeekSumText)));
	}

	@Test
	public void thatEventsAreDisplayed_whenTwoEventsAreAvailable() {
		// given
		int eventOnePosition = 2;
		String expectedEventOneMessage = "You didn't resist a guilty pleasure at Starbucks.";
		String expectedEventOneDate = "March 6, 2015";
		String expectedEventOneAmount = "$4.00";

		int eventTwoPosition = 3;
		String expectedEventTwoMessage = "You made a roundup.";
		String expectedEventTwoDate = "March 6, 2015";
		String expectedEventTwoAmount = "$0.23";

		loadRulesAndEventsWithContent();

		RecyclerViewMatcher recyclerViewMatcher = new RecyclerViewMatcher(R.id.recycler);

		// when
		ViewInteraction eventOneMessageView = onView(recyclerViewMatcher.atPositionOnView(eventOnePosition, R.id.message));
		ViewInteraction eventOneDateView = onView(recyclerViewMatcher.atPositionOnView(eventOnePosition, R.id.date));
		ViewInteraction eventOneAmountView = onView(recyclerViewMatcher.atPositionOnView(eventOnePosition, R.id.amount));

		ViewInteraction eventTwoMessageView = onView(recyclerViewMatcher.atPositionOnView(eventTwoPosition, R.id.message));
		ViewInteraction eventTwoDateView = onView(recyclerViewMatcher.atPositionOnView(eventTwoPosition, R.id.date));
		ViewInteraction eventTwoAmountView = onView(recyclerViewMatcher.atPositionOnView(eventTwoPosition, R.id.amount));

		// then
		eventOneMessageView.check(matches(withText(expectedEventOneMessage)));
		eventOneDateView.check(matches(withText(expectedEventOneDate)));
		eventOneAmountView.check(matches(withText(expectedEventOneAmount)));

		eventTwoMessageView.check(matches(withText(expectedEventTwoMessage)));
		eventTwoDateView.check(matches(withText(expectedEventTwoDate)));
		eventTwoAmountView.check(matches(withText(expectedEventTwoAmount)));
	}

	@Test
	public void thatEmptyScreenIsShown_whenNoElementInList() {
		// given
		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setBody(SavingsRuleTestData.EMPTY_LIST));
		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setBody(SavingGoalEventTestData.EMPTY_LIST));

		// when
		ViewInteraction emptyView = onView(withId(R.id.empty_view));

		// then
		emptyView.check(matches(isDisplayed()));
	}

	private void loadRulesAndEventsWithContent() {
		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setBody(SavingsRuleTestData.LIST_WITH_TWO_ENTRIES));
		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setBody(SavingGoalEventTestData.LIST_WITH_TWO_ENTRIES_AND_DIFFERENT_RULES));
	}

	@NonNull
	private Matcher<View> getRuleImageAtPosition(int position) {
		return childAtPosition(
				allOf(withId(R.id.recycler),
						childAtPosition(
								IsInstanceOf.instanceOf(android.widget.LinearLayout.class),
								1)),
				position);
	}

	@NonNull
	private Matcher<View> getListItemAtPosition(int position) {
		return childAtPosition(
				childAtPosition(
						withId(R.id.recycler),
						1),
				position);
	}
}
