package com.king.pagecontrol

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlin.math.roundToInt

class PageControlView(context: Context) : View(context) {

  var onPageChange: ((Int) -> Unit)? = null

  var numberOfPages: Int = 0
    set(value) {
      field = value
      requestLayout()
      invalidate()
    }

  // colors are driven by props (design tokens live in JS)
  var pageColor: Int = Color.TRANSPARENT
    set(value) {
      field = value
      invalidate()
    }

  var currentPageColor: Int = Color.TRANSPARENT
    set(value) {
      field = value
      invalidate()
    }

  var hidesForSinglePage: Boolean = false
    set(value) {
      field = value
      invalidate()
    }

  private var currentPage: Int = 0
  // animated position of the active dot, in page units
  private var activePosition: Float = 0f
  private var animator: ValueAnimator? = null

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val density = context.resources.displayMetrics.density

  private val dotSize = DOT_SIZE_DP * density
  private val dotGap = DOT_GAP_DP * density

  fun setCurrentPage(page: Int) {
    val target = page.coerceIn(0, (numberOfPages - 1).coerceAtLeast(0))
    if (target == currentPage && activePosition == target.toFloat()) return

    currentPage = target

    animator?.cancel()
    animator = ValueAnimator.ofFloat(activePosition, target.toFloat()).apply {
      duration = ANIMATION_DURATION_MS
      interpolator = DecelerateInterpolator()
      addUpdateListener {
        activePosition = it.animatedValue as Float
        invalidate()
      }
      start()
    }
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val contentWidth = (numberOfPages * dotSize + (numberOfPages - 1).coerceAtLeast(0) * dotGap).roundToInt()
    val contentHeight = (DEFAULT_HEIGHT_DP * density).roundToInt()

    setMeasuredDimension(
      resolveSize(contentWidth, widthMeasureSpec),
      resolveSize(contentHeight, heightMeasureSpec),
    )
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    if (numberOfPages <= 0 || (hidesForSinglePage && numberOfPages == 1)) return

    val radius = dotSize / 2f
    val step = dotSize + dotGap
    val contentWidth = numberOfPages * dotSize + (numberOfPages - 1) * dotGap
    val startX = (width - contentWidth) / 2f + radius
    val centerY = height / 2f

    paint.color = pageColor
    for (page in 0 until numberOfPages) {
      canvas.drawCircle(startX + page * step, centerY, radius, paint)
    }

    paint.color = currentPageColor
    canvas.drawCircle(startX + activePosition * step, centerY, radius, paint)
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    if (event.actionMasked == MotionEvent.ACTION_UP && numberOfPages > 1) {
      val step = dotSize + dotGap
      val contentWidth = numberOfPages * dotSize + (numberOfPages - 1) * dotGap
      val startX = (width - contentWidth) / 2f + dotSize / 2f

      val page = ((event.x - startX) / step).roundToInt().coerceIn(0, numberOfPages - 1)

      if (page != currentPage) {
        setCurrentPage(page)
        onPageChange?.invoke(page)
      }

      performClick()
      return true
    }

    return event.actionMasked == MotionEvent.ACTION_DOWN || super.onTouchEvent(event)
  }

  override fun onDetachedFromWindow() {
    animator?.cancel()
    super.onDetachedFromWindow()
  }

  private companion object {
    const val DOT_SIZE_DP = 8f
    const val DOT_GAP_DP = 8f
    const val DEFAULT_HEIGHT_DP = 26f
    const val ANIMATION_DURATION_MS = 200L
  }
}
