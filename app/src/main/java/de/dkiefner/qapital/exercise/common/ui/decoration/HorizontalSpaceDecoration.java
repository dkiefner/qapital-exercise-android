package de.dkiefner.qapital.exercise.common.ui.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HorizontalSpaceDecoration extends RecyclerView.ItemDecoration {

	private final int spaceWidth;

	public HorizontalSpaceDecoration(int spaceWidth) {
		this.spaceWidth = spaceWidth;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
							   RecyclerView.State state) {
		outRect.right = spaceWidth;
	}

}
