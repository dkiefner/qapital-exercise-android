package de.dkiefner.qapital.exercise.ui.savinggoal.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import de.dkiefner.qapital.exercise.QapitalExercise;
import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.common.CurrencyFormatter;
import de.dkiefner.qapital.exercise.common.LocalClock;
import de.dkiefner.qapital.exercise.common.ui.QapitalExerciseFragment;
import de.dkiefner.qapital.exercise.common.ui.decoration.VerticalListDividerDecoration;
import de.dkiefner.qapital.exercise.data.savinggoal.SavingGoal;
import de.dkiefner.qapital.exercise.data.savinggoal.event.SavingGoalEventDataStore;
import de.dkiefner.qapital.exercise.data.savingsrule.SavingsRuleDataStore;
import de.dkiefner.qapital.exercise.databinding.SavingGoalDetailFragmentBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SavingGoalDetailFragment extends QapitalExerciseFragment {

	public static final String TAG = SavingGoalDetailFragment.class.getSimpleName();

	public static final String KEY_SAVING_GOAL = "saving_goal";

	@Inject
	SavingsRuleDataStore savingsRuleDataStore;
	@Inject
	SavingGoalEventDataStore savingGoalEventDataStore;
	@Inject
	CurrencyFormatter currencyFormatter;
	@Inject
	LocalClock clock;

	private SavingGoalDetailViewModel viewModel;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		QapitalExercise.getAppInjector().inject(this);
		createViewModel();
	}

	private void createViewModel() {
		SavingGoal savingGoal = (SavingGoal) getArguments().getSerializable(KEY_SAVING_GOAL);
		viewModel = new SavingGoalDetailViewModel(savingGoal, savingsRuleDataStore, savingGoalEventDataStore, currencyFormatter, clock);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.saving_goal_detail_fragment, null);
		SavingGoalDetailFragmentBinding binding = DataBindingUtil.bind(view);
		binding.setViewModel(viewModel);
		Picasso.with(getActivity())
				.load(viewModel.getImageUrl())
				.placeholder(R.drawable.saving_goal_placeholder)
				.into(binding.goalImage);
		initAdapter(binding.recycler);
		return view;
	}

	private void initAdapter(RecyclerView recyclerView) {
		SavingGoalDetailAdapter adapter = new SavingGoalDetailAdapter(viewModel);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.addItemDecoration(new VerticalListDividerDecoration(getContext()));
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		disposables.add(viewModel.loadSavingGoalEvents()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(() -> {
				}, this::onLoadingGoalsFailed));
	}

	private void onLoadingGoalsFailed(Throwable throwable) {
		Timber.e(throwable, throwable.getMessage());
	}
}
