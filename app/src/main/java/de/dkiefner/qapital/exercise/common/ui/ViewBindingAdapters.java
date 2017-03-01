package de.dkiefner.qapital.exercise.common.ui;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public final class ViewBindingAdapters {

	private ViewBindingAdapters() {
	}

	@BindingAdapter("visible")
	public static void setVisible(View view, boolean visible) {
		if (visible) {
			view.setVisibility(View.VISIBLE);
		} else {
			view.setVisibility(View.GONE);
		}
	}

	@BindingAdapter("html")
	public static void setHtml(View view, String rawHtml) {
		if (view instanceof TextView) {
			TextView textView = (TextView) view;
			textView.setText(Html.fromHtml(rawHtml));
		}
	}

	@BindingAdapter("descriptive_date")
	public static void setDescriptiveDate(View view, long timestamp) {
		if (view instanceof TextView) {
			long now = System.currentTimeMillis();
			CharSequence relativeDate = DateUtils.getRelativeTimeSpanString(timestamp,
					now,
					DateUtils.SECOND_IN_MILLIS,
					DateUtils.FORMAT_ABBREV_RELATIVE);
			TextView textView = (TextView) view;
			textView.setText(relativeDate);
		}
	}

	@BindingAdapter("imageResource")
	public static void setImageResource(View view, @DrawableRes int resourceId) {
		if (view instanceof ImageView) {
			ImageView imageView = (ImageView) view;
			imageView.setImageResource(resourceId);
		}
	}

	@BindingAdapter("string_resource")
	public static void setStringResource(View view, StringResource stringResource) {
		if (view instanceof TextView) {
			TextView textView = (TextView) view;

			String text;
			if (stringResource.getResourceId() != null) {
				Resources resources = view.getContext().getResources();
				text = resources.getString(stringResource.getResourceId(), stringResource.getItems());
			} else {
				text = stringResource.getRawString();
			}

			textView.setText(text);
		}
	}
}
