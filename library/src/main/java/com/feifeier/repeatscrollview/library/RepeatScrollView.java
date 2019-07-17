package com.feifeier.repeatscrollview.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/***************************************************************************************************
 * 描述：背景图循环滚动的View
 *
 * 作者：Evolet
 *
 * 时间：2019/7/17
 **************************************************************************************************/

public class RepeatScrollView extends RecyclerView {

  private Context mContext;
  @DrawableRes
  private int mDrawableResId;
  // 几秒滚动一屏
  private float mScrollScreenMillis;

  public RepeatScrollView(Context context) {
    this(context, null);
  }

  public RepeatScrollView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RepeatScrollView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    mContext = context;
    TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.RepeatScrollView);
    mDrawableResId = a.getResourceId(R.styleable.RepeatScrollView_src, 0);
    mScrollScreenMillis = a.getFloat(R.styleable.RepeatScrollView_scroll_screen_time, 50_000f);
    a.recycle();

    setLayoutManager(new ScrollLayoutManager(mContext));
    setAdapter(new ScrollAdapter());
    smoothScrollToPosition(Integer.MAX_VALUE - 1);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    return true;
  }

  private class ScrollLayoutManager extends LinearLayoutManager {

    public ScrollLayoutManager(Context context) {
      super(context);
      setOrientation(VERTICAL);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
      LinearSmoothScroller linearSmoothScroller =
          new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
              return super.computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
              return mScrollScreenMillis / displayMetrics.heightPixels;
            }

          };
      linearSmoothScroller.setTargetPosition(position);
      startSmoothScroll(linearSmoothScroller);
    }
  }

  private class ScrollAdapter extends RecyclerView.Adapter<ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false);
      return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      holder.updateUI();
    }

    @Override
    public int getItemCount() {
      return Integer.MAX_VALUE;
    }
  }

  private class ViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImg;

    public ViewHolder(View itemView) {
      super(itemView);
      mImg = itemView.findViewById(R.id.img);
    }

    public void updateUI() {
      mImg.setImageResource(mDrawableResId);
    }
  }
}

