package de.dkiefner.qapital.exercise.common.ui.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.dkiefner.qapital.exercise.R;

/**
 * An {@link RecyclerView.ItemDecoration} that adds a drawable as divider between items.
 * <p>
 * see http://stackoverflow.com/a/27037230/1096567
 */
public class VerticalListDividerDecoration extends RecyclerView.ItemDecoration {
	private final int oneDp;
	private Drawable mDivider;

	@SuppressWarnings("deprecation")
	public VerticalListDividerDecoration(Context ctx) {
		this(new ColorDrawable(ctx.getResources().getColor(R.color.grey_light)));
	}

	@SuppressWarnings("deprecation")
	public VerticalListDividerDecoration(Context ctx, @ColorInt int color) {
		this(new ColorDrawable(color));
	}

	public VerticalListDividerDecoration(Drawable dividerDrawable) {
		this.mDivider = dividerDrawable;
		this.oneDp = 2;
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		int left = parent.getPaddingLeft();
		int right = parent.getWidth() - parent.getPaddingRight();

		int childCount = parent.getChildCount();
		for (int i = 0; i < childCount - 1; i++) {
			View child = parent.getChildAt(i);

			RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

			int top = child.getBottom() + params.bottomMargin;
			int bottom = top + Math.max(mDivider.getIntrinsicHeight(), oneDp);

			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		outRect.bottom = Math.max(mDivider.getIntrinsicHeight(), oneDp);
	}
}
