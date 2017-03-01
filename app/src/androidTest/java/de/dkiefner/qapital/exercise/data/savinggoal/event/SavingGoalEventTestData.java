package de.dkiefner.qapital.exercise.data.savinggoal.event;

public final class SavingGoalEventTestData {

	public static final String LIST_WITH_TWO_ENTRIES_AND_DIFFERENT_RULES = "{\n" +
			"    \"feed\": [\n" +
			"        {\n" +
			"            \"id\": \"3fbacba1-4aa6-49b8-8a67-f31b27dc905f\",\n" +
			"            \"type\": \"saving\",\n" +
			"            \"timestamp\": \"2015-03-06T22:00:16.025Z\",\n" +
			"            \"message\": \"<strong>You</strong> didn't resist a guilty pleasure at <strong>Starbucks</strong>.\",\n" +
			"            \"amount\": 4,\n" +
			"            \"userId\": 1,\n" +
			"            \"savingsRuleId\": 2\n" +
			"        },\n" +
			"        {\n" +
			"            \"id\": \"ae7d59a7-e3e5-49a0-9b7c-25067ebb5ee7\",\n" +
			"            \"type\": \"saving\",\n" +
			"            \"timestamp\": \"2015-03-06T22:00:16.025Z\",\n" +
			"            \"message\": \"<strong>You</strong> made a roundup.\",\n" +
			"            \"amount\": 0.23,\n" +
			"            \"userId\": 1,\n" +
			"            \"savingsRuleId\": 1\n" +
			"        }\n" +
			"    ]\n" +
			"}";

	public static final String EMPTY_LIST = "{\n" +
			"    \"feed\": [\n" +
			"    ]\n" +
			"}";

	private SavingGoalEventTestData() {
	}
}
