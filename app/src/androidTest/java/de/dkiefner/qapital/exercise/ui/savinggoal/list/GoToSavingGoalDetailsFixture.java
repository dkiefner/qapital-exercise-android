package de.dkiefner.qapital.exercise.ui.savinggoal.list;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;

import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.common.Fixture;
import de.dkiefner.qapital.exercise.common.ViewIsVisibleIdlingResource;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoalTestData;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class GoToSavingGoalDetailsFixture implements Fixture {

	private MockWebServer server;
	private ActivityTestRule<? extends AppCompatActivity> activityTestRule;

	private ViewIsVisibleIdlingResource viewIsVisibleIdlingResource;

	public GoToSavingGoalDetailsFixture(MockWebServer server, ActivityTestRule<? extends AppCompatActivity> activityTestRule) {
		this.server = server;
		this.activityTestRule = activityTestRule;
	}

	@Override
	public void load() {
		viewIsVisibleIdlingResource = new ViewIsVisibleIdlingResource(activityTestRule.getActivity(), R.id.recycler);
		Espresso.registerIdlingResources(viewIsVisibleIdlingResource);

		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setBody(SavingGoalTestData.LIST_WITH_SINGLE_ENTRY));

		ViewInteraction recyclerView = onView(allOf(withId(R.id.recycler), isDisplayed()));
		recyclerView.perform(actionOnItemAtPosition(0, click()));
	}

	@Override
	public void tearDown() {
		Espresso.unregisterIdlingResources(viewIsVisibleIdlingResource);
	}
}
