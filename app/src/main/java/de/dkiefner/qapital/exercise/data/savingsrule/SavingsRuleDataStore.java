package de.dkiefner.qapital.exercise.data.savingsrule;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reactivex.Observable;

public class SavingsRuleDataStore {

	private SavingsRuleApi savingsRuleApi;
	private StorIOSQLite storIOSQLite;

	public SavingsRuleDataStore(SavingsRuleApi savingsRuleApi, StorIOSQLite storIOSQLite) {
		this.savingsRuleApi = savingsRuleApi;
		this.storIOSQLite = storIOSQLite;
	}

	public Observable<List<SavingsRule>> getSavingsRules() {
		return Observable.merge(loadFromCache(), loadAndCacheFromApi());
	}

	private Observable<List<SavingsRule>> loadFromCache() {
		return Observable.fromCallable(() -> storIOSQLite
				.get()
				.listOfObjects(SavingsRule.class)
				.withQuery(Query.builder()
						.table(SavingsRule.TABLE_NAME)
						.build())
				.prepare()
				.executeAsBlocking());
	}

	private Observable<List<SavingsRule>> loadAndCacheFromApi() {
		return savingsRuleApi.getSavingsRules().retry(3)
				.map(SavingsRuleMapper::map)
				.flatMap(this::saveToCache);
	}

	private Observable<List<SavingsRule>> saveToCache(List<SavingsRule> savingsRules) {
		return Observable.fromCallable(() -> {
			storIOSQLite
					.put()
					.objects(savingsRules)
					.prepare()
					.executeAsBlocking();
			return savingsRules;
		});
	}
}
