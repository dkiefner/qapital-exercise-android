package de.dkiefner.qapital.exercise.common.ui;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public class DataBindingViewHolder extends RecyclerView.ViewHolder {

	private final ViewDataBinding binding;

	public DataBindingViewHolder(@NonNull ViewDataBinding binding) {
		super(binding.getRoot());
		this.binding = binding;
	}

	public <T extends ViewDataBinding> T getBinding() {
		return (T) binding;
	}
}
