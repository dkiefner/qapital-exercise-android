package de.dkiefner.qapital.exercise;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import de.dkiefner.qapital.exercise.data.QapitalExerciseSQLiteHelper;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoal;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEvent;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRule;
import de.dkiefner.qapital.exercise.ui.MainActivity;
import okhttp3.mockwebserver.MockWebServer;

public abstract class BaseTest {

	@Rule
	public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class, true, false);

	protected MockWebServer server;

	@Before
	public void setup() throws Exception {
		server = new MockWebServer();
		server.start();

		TestQapitalExercise application = (TestQapitalExercise) getApplicationContext();
		application.updateBaseUrl(server.url("/").toString());

		clearDataBase();
		launchMain();
	}

	protected void launchMain() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		activityTestRule.launchActivity(intent);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}

	protected Context getApplicationContext() {
		return InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
	}

	protected void clearDataBase() {
		QapitalExerciseSQLiteHelper sqLiteHelper = new QapitalExerciseSQLiteHelper(getApplicationContext());
		sqLiteHelper.getWritableDatabase().execSQL("DELETE FROM " + SavingGoal.TABLE_NAME);
		sqLiteHelper.getWritableDatabase().execSQL("DELETE FROM " + SavingsRule.TABLE_NAME);
		sqLiteHelper.getWritableDatabase().execSQL("DELETE FROM " + SavingGoalEvent.TABLE_NAME);
	}

	protected static Matcher<View> childAtPosition(
			final Matcher<View> parentMatcher, final int position) {

		return new TypeSafeMatcher<View>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("Child at position " + position + " in parent ");
				parentMatcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(View view) {
				ViewParent parent = view.getParent();
				return parent instanceof ViewGroup && parentMatcher.matches(parent)
						&& view.equals(((ViewGroup) parent).getChildAt(position));
			}
		};
	}
}
