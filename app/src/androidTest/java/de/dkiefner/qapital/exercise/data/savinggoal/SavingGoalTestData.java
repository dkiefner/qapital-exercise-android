package de.dkiefner.qapital.exercise.data.savinggoal;

public final class SavingGoalTestData {

	public static final String LIST_WITH_SINGLE_ENTRY = "{\n" +
			"  \"savingsGoals\": [\n" +
			"    {\n" +
			"      \"goalImageURL\": \"https://static.qapitalapp.net/assets/ios-staging.api.qapital.com/images/goal/6dc5befb-5389-4d89-ab37-205c83ccf79c.jpg\",\n" +
			"      \"userId\": 1,\n" +
			"      \"targetAmount\": 100,\n" +
			"      \"currentBalance\": 10,\n" +
			"      \"created\": [\n" +
			"        2016,\n" +
			"        12,\n" +
			"        5\n" +
			"      ],\n" +
			"      \"status\": \"active\",\n" +
			"      \"name\": \"foo\",\n" +
			"      \"id\": 1\n" +
			"    }\n" +
			"  ]\n" +
			"}";

	public static final String EMPTY_LIST = "{\n" +
			"  \"savingsGoals\": [\n" +
			"  ]\n" +
			"}";

	private SavingGoalTestData() {
	}
}
