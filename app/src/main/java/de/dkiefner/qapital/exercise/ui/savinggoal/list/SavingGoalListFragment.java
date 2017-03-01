package de.dkiefner.qapital.exercise.ui.savinggoal.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import de.dkiefner.qapital.exercise.QapitalExercise;
import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.common.CurrencyFormatter;
import de.dkiefner.qapital.exercise.common.ui.QapitalExerciseFragment;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoalDataStore;
import de.dkiefner.qapital.exercise.databinding.SavingGoalListFragmentBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SavingGoalListFragment extends QapitalExerciseFragment {

	public static final String TAG = SavingGoalListFragment.class.getSimpleName();

	@Inject
	CurrencyFormatter currencyFormatter;
	@Inject
	SavingGoalDataStore savingGoalDataStore;

	private SavingGoalListViewModel viewModel;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		QapitalExercise.getAppInjector().inject(this);
		viewModel = new SavingGoalListViewModel(savingGoalDataStore, currencyFormatter);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.saving_goal_list_fragment, null);
		SavingGoalListFragmentBinding binding = DataBindingUtil.bind(view);
		binding.setViewModel(viewModel);
		initAdapter(binding.recycler);
		return view;
	}

	private void initAdapter(RecyclerView recyclerView) {
		SavingGoalListAdapter adapter = new SavingGoalListAdapter(viewModel);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		disposables.add(viewModel.loadGoals()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(() -> {
				}, this::onLoadingGoalsFailed));
	}

	private void onLoadingGoalsFailed(Throwable throwable) {
		Timber.e(throwable, throwable.getMessage());
	}

}
