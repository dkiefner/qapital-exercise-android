package de.dkiefner.qapital.exercise.common.ui;

import android.support.annotation.StringRes;

import org.jetbrains.annotations.Nullable;

public class StringResource {

	@StringRes
	private Integer resourceId;
	private String[] items;
	private String rawString;

	public StringResource(@StringRes int resourceId, String[] items) {
		this.resourceId = resourceId;
		this.items = items;
	}

	public StringResource(@StringRes int resourceId) {
		this(resourceId, new String[0]);
	}

	public StringResource(String rawString) {
		this.rawString = rawString;
		this.items = new String[0];
	}

	@Nullable
	@StringRes
	public Integer getResourceId() {
		return resourceId;
	}

	public String[] getItems() {
		return items;
	}

	@Nullable
	public String getRawString() {
		return rawString;
	}
}
