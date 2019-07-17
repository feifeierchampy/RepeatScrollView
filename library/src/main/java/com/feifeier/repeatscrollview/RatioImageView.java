package com.feifeier.repeatscrollview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/***************************************************************************************************
 * 描述：宽需固定 高度按图片宽高比自适应
 *
 * 作者：Evolet
 *
 * 时间：2019/7/17
 **************************************************************************************************/

public class RatioImageView extends AppCompatImageView {

  public RatioImageView(Context context) {
    super(context);
  }

  public RatioImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    Drawable drawable = getDrawable();
    if (drawable != null) {
      int width = MeasureSpec.getSize(widthMeasureSpec);
      int height = (int) (width * drawable.getIntrinsicHeight() * 1f / drawable.getIntrinsicWidth());
      setMeasuredDimension(width, height);
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  }
}
