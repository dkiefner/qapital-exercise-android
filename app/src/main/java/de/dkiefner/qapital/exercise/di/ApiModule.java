package de.dkiefner.qapital.exercise.di;

import android.support.annotation.VisibleForTesting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import de.dkiefner.qapital.exercise.QapitalExercise;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoalApi;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEventApi;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRuleApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

	private String baseUrl = "http://qapital-ios-testtask.herokuapp.com/";

	private Retrofit retrofit;
	private Gson gson;

	public ApiModule() {
	}

	@VisibleForTesting
	public ApiModule(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Provides
	@PerApp
	SavingGoalApi provideSavingGoalApi(Retrofit retrofit) {
		return retrofit.create(SavingGoalApi.class);
	}

	@Provides
	@PerApp
	SavingsRuleApi provideSavingsRuleApi(Retrofit retrofit) {
		return retrofit.create(SavingsRuleApi.class);
	}

	@Provides
	@PerApp
	SavingGoalEventApi provideSavingGoalEventApi(Retrofit retrofit) {
		return retrofit.create(SavingGoalEventApi.class);
	}

	@Provides
	@PerApp
	OkHttpClient provideOkHttpClient(QapitalExercise application) {
		final HttpLoggingInterceptor.Level logLevel =
				application.isDebug()
						? HttpLoggingInterceptor.Level.BODY
						: HttpLoggingInterceptor.Level.NONE;

		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(logLevel);

		return new OkHttpClient.Builder()
				.addInterceptor(loggingInterceptor)
				.build();
	}

	@Provides
	Retrofit provideRetrofit(OkHttpClient okHttpClient) {
		if (retrofit == null) {

			retrofit = new Retrofit.Builder()
					.addConverterFactory(GsonConverterFactory.create(getGson()))
					.callbackExecutor(Executors.newCachedThreadPool())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.baseUrl(baseUrl)
					.client(okHttpClient)
					.build();
		}
		return retrofit;
	}

	private Gson getGson() {
		if (gson == null) {
			gson = new GsonBuilder().create();
		}
		return gson;
	}
}