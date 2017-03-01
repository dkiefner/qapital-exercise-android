package de.dkiefner.qapital.exercise.data.savingsrule;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface SavingsRuleApi {

	@GET("/savingsrules")
	Observable<SavingsRulesDto> getSavingsRules();
}
