package de.dkiefner.qapital.exercise.ui.savinggoal.list;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.dkiefner.qapital.exercise.BaseTest;
import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.common.ElapsedTimeIdlingResource;
import de.dkiefner.qapital.exercise.common.RecyclerViewMatcher;
import de.dkiefner.qapital.exercise.common.ViewIsVisibleIdlingResource;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoalTestData;
import okhttp3.mockwebserver.MockResponse;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SavingGoalListTest extends BaseTest {

	@Test
	public void thatTextRangeIsSetForFirstElement_whenElementHasTargetAmount() {
		// given
		ViewIsVisibleIdlingResource viewIsVisibleIdlingResource = new ViewIsVisibleIdlingResource(activityTestRule.getActivity(), R.id.recycler);
		Espresso.registerIdlingResources(viewIsVisibleIdlingResource);

		String expectedName = "foo";
		String expectedStatus = "$10.00 of $100.00";

		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setBody(SavingGoalTestData.LIST_WITH_SINGLE_ENTRY));

		RecyclerViewMatcher recyclerViewMatcher = new RecyclerViewMatcher(R.id.recycler);

		// when
		ViewInteraction nameView = onView(recyclerViewMatcher.atPositionOnView(0, R.id.name));
		ViewInteraction statusView = onView(recyclerViewMatcher.atPositionOnView(0, R.id.status));

		// then
		nameView.check(matches(withText(expectedName)));
		statusView.check(matches(withText(expectedStatus)));

		Espresso.unregisterIdlingResources(viewIsVisibleIdlingResource);
	}

	@Test
	public void thatTransitionToDetailWorks_whenOneElementIsInList() {
		// given
		ViewIsVisibleIdlingResource viewIsVisibleIdlingResource = new ViewIsVisibleIdlingResource(activityTestRule.getActivity(), R.id.recycler);
		Espresso.registerIdlingResources(viewIsVisibleIdlingResource);

		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setBody(SavingGoalTestData.LIST_WITH_SINGLE_ENTRY));

		ViewInteraction recyclerView = onView(allOf(withId(R.id.recycler), isDisplayed()));

		// when
		recyclerView.perform(actionOnItemAtPosition(0, click()));
		ViewInteraction toolbarOfDetail = onView(withId(R.id.collapsing_toolbar));

		// then
		toolbarOfDetail.check(matches(isDisplayed()));

		Espresso.unregisterIdlingResources(viewIsVisibleIdlingResource);
	}

	@Test
	public void thatEmptyScreenIsShown_whenNoElementInList() {
		// given
		ElapsedTimeIdlingResource waitForFragmentIdlingResource = new ElapsedTimeIdlingResource(10L);
		Espresso.registerIdlingResources(waitForFragmentIdlingResource);

		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setBody(SavingGoalTestData.EMPTY_LIST));

		// when
		ViewInteraction emptyView = onView(withId(R.id.empty_view));

		// then
		emptyView.check(matches(isDisplayed()));

		Espresso.unregisterIdlingResources(waitForFragmentIdlingResource);
	}

}
