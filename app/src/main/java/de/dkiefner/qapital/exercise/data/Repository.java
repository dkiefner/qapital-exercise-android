package de.dkiefner.qapital.exercise.data;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reactivex.Observable;

public abstract class Repository<T> {

	protected final Class<T> entityClass;
	protected final StorIOSQLite storIOSQLite;

	public Repository(Class<T> entityClass, StorIOSQLite storIOSQLite) {
		this.entityClass = entityClass;
		this.storIOSQLite = storIOSQLite;
	}

	protected abstract String getEntityTableName();

	public Observable<List<T>> findAll() {
		return Observable.fromCallable(() -> storIOSQLite
				.get()
				.listOfObjects(entityClass)
				.withQuery(Query.builder()
						.table(getEntityTableName())
						.build())
				.prepare()
				.executeAsBlocking());
	}

	public Observable<List<T>> saveAll(List<T> listOfEntities) {
		return Observable.fromCallable(() -> {
			storIOSQLite
					.put()
					.objects(listOfEntities)
					.prepare()
					.executeAsBlocking();
			return listOfEntities;
		});
	}
}
