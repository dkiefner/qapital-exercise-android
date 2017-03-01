package de.dkiefner.qapital.exercise.ui.savinggoal.detail.rules;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.common.ui.DataBindingViewHolder;
import de.dkiefner.qapital.exercise.databinding.SavingGoalDetailRuleItemBinding;

public class SavingGoalRuleListAdapter extends RecyclerView.Adapter<DataBindingViewHolder> {

	private final SavingGoalRuleListViewModel viewModel;

	public SavingGoalRuleListAdapter(SavingGoalRuleListViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		final ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.saving_goal_detail_rule_item, parent, false);
		return new DataBindingViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(DataBindingViewHolder holder, int position) {
		SavingGoalRuleItemViewModel savingGoalRuleItemViewModel = viewModel.getItem(position);
		SavingGoalDetailRuleItemBinding binding = holder.getBinding();
		binding.setViewModel(savingGoalRuleItemViewModel);
		binding.executePendingBindings();
	}

	@Override
	public int getItemCount() {
		return viewModel.getItemCount();
	}
}
