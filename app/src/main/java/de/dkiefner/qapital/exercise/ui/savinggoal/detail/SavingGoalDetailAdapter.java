package de.dkiefner.qapital.exercise.ui.savinggoal.detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.common.ui.DataBindingViewHolder;
import de.dkiefner.qapital.exercise.common.ui.decoration.HorizontalSpaceDecoration;
import de.dkiefner.qapital.exercise.databinding.SavingGoalDetailRulesItemBinding;
import de.dkiefner.qapital.exercise.databinding.SavingGoalDetailSumItemBinding;
import de.dkiefner.qapital.exercise.databinding.SavingGoalDetailEventItemBinding;
import de.dkiefner.qapital.exercise.ui.savinggoal.detail.rules.SavingGoalRuleListAdapter;
import de.dkiefner.qapital.exercise.ui.savinggoal.detail.rules.SavingGoalRuleListViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SavingGoalDetailAdapter extends RecyclerView.Adapter<DataBindingViewHolder> {

	private static final int VIEW_TYPE_RULES = 1;
	private static final int VIEW_TYPE_WEEK_SUM = 2;
	private static final int VIEW_TYPE_EVENT = 3;

	private final SavingGoalDetailViewModel viewModel;

	public SavingGoalDetailAdapter(SavingGoalDetailViewModel viewModel) {
		this.viewModel = viewModel;
		viewModel.savingGoalEventsSubject
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(savingGoalListItemViewModels -> {
					notifyDataSetChanged();
				});
	}

	@Override
	public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		ViewDataBinding binding;
		if (viewType == VIEW_TYPE_RULES) {
			binding = DataBindingUtil.inflate(layoutInflater, R.layout.saving_goal_detail_rules_item, parent, false);
		} else if (viewType == VIEW_TYPE_WEEK_SUM) {
			binding = DataBindingUtil.inflate(layoutInflater, R.layout.saving_goal_detail_sum_item, parent, false);
		} else {
			binding = DataBindingUtil.inflate(layoutInflater, R.layout.saving_goal_detail_event_item, parent, false);
		}

		return new DataBindingViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(DataBindingViewHolder holder, int position) {
		if (isRules(position)) {
			bindRules(holder, viewModel.getSavingRulesItem());
		} else if (isWeekSum(position)) {
			bindWeekSum(holder, viewModel.getWeekSumItem());
		} else {
			int eventPosition = position - 2;
			if (eventPosition <= viewModel.getEventItemCount()) {
				bindEvent(holder, viewModel.getEventItem(eventPosition));
			}
		}
	}

	private void bindRules(DataBindingViewHolder holder, SavingGoalRuleListViewModel savingGoalRuleListViewModel) {
		SavingGoalDetailRulesItemBinding binding = holder.getBinding();
		binding.setViewModel(savingGoalRuleListViewModel);
		binding.executePendingBindings();

		Context context = binding.getRoot().getContext();
		RecyclerView recyclerView = binding.recycler;
		SavingGoalRuleListAdapter adapter = new SavingGoalRuleListAdapter(savingGoalRuleListViewModel);
		recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
		recyclerView.addItemDecoration(new HorizontalSpaceDecoration(context.getResources().getDimensionPixelSize(R.dimen.margin_small)));
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(adapter);
	}

	private void bindWeekSum(DataBindingViewHolder holder, SavingGoalWeekSumViewModel savingGoalWeekSumViewModel) {
		SavingGoalDetailSumItemBinding binding = holder.getBinding();
		binding.setViewModel(savingGoalWeekSumViewModel);
		binding.executePendingBindings();
	}

	private void bindEvent(DataBindingViewHolder holder, SavingGoalEventListItemViewModel savingGoalEventListItemViewModel) {
		SavingGoalDetailEventItemBinding binding = holder.getBinding();
		binding.setViewModel(savingGoalEventListItemViewModel);
		binding.executePendingBindings();
	}

	@Override
	public int getItemViewType(int position) {
		if (isRules(position)) {
			return VIEW_TYPE_RULES;
		} else if (isWeekSum(position)) {
			return VIEW_TYPE_WEEK_SUM;
		} else {
			return VIEW_TYPE_EVENT;
		}
	}

	private boolean isRules(int position) {
		return position == 0;
	}

	private boolean isWeekSum(int position) {
		return position == 1;
	}

	@Override
	public int getItemCount() {
		return viewModel.getEventItemCount() + 2;
	}
}
