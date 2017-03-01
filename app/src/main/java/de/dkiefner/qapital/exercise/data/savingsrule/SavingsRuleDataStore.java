package de.dkiefner.qapital.exercise.data.savingsrule;

import java.util.List;

import io.reactivex.Observable;

public class SavingsRuleDataStore {

	private SavingsRuleApi savingsRuleApi;
	private SavingsRuleRepository savingsRuleRepository;

	public SavingsRuleDataStore(SavingsRuleApi savingsRuleApi, SavingsRuleRepository savingsRuleRepository) {
		this.savingsRuleApi = savingsRuleApi;
		this.savingsRuleRepository = savingsRuleRepository;
	}

	public Observable<List<SavingsRule>> getSavingsRules() {
		return Observable.mergeDelayError(savingsRuleRepository.findAll(), loadAndCacheFromApi());
	}

	private Observable<List<SavingsRule>> loadAndCacheFromApi() {
		return savingsRuleApi.getSavingsRules().retry(3)
				.map(SavingsRuleMapper::map)
				.flatMap(savingsRuleRepository::saveAll);
	}

}
