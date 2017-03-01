package de.dkiefner.qapital.exercise.ui.savinggoal.list;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import de.dkiefner.qapital.exercise.R;
import de.dkiefner.qapital.exercise.common.ui.DataBindingViewHolder;
import de.dkiefner.qapital.exercise.databinding.SavingGoalItemBinding;
import de.dkiefner.qapital.exercise.ui.savinggoal.detail.SavingGoalDetailActivity;
import de.dkiefner.qapital.exercise.ui.savinggoal.detail.SavingGoalDetailFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SavingGoalListAdapter extends RecyclerView.Adapter<DataBindingViewHolder> {

	private final SavingGoalListViewModel viewModel;

	public SavingGoalListAdapter(SavingGoalListViewModel viewModel) {
		this.viewModel = viewModel;
		viewModel.savingGoalsSubject
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(savingGoalListItemViewModels -> {
					notifyDataSetChanged();
				});
	}

	@Override
	public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		final ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.saving_goal_item, parent, false);
		return new DataBindingViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(DataBindingViewHolder holder, int position) {
		SavingGoalItemViewModel savingGoalItemViewModel = viewModel.getItem(position);
		SavingGoalItemBinding binding = holder.getBinding();
		binding.contentContainer.setOnClickListener(v -> openSavingGoalDetails(v, savingGoalItemViewModel));
		Picasso.with(binding.getRoot().getContext())
				.load(savingGoalItemViewModel.getImageUrl())
				.placeholder(R.drawable.saving_goal_placeholder)
				.into(binding.image);

		binding.setViewModel(savingGoalItemViewModel);
		binding.executePendingBindings();
	}

	private void openSavingGoalDetails(View view, SavingGoalItemViewModel savingGoalItemViewModel) {
		final Context context = view.getContext();
		Intent openDetailView = new Intent(context, SavingGoalDetailActivity.class);
		openDetailView.putExtra(SavingGoalDetailFragment.KEY_SAVING_GOAL, savingGoalItemViewModel.getSavingGoal());
		context.startActivity(openDetailView);
	}

	@Override
	public int getItemCount() {
		return viewModel.getItemCount();
	}
}
