package com.margelo.nitro.pagecontrol

import com.facebook.proguard.annotations.DoNotStrip
import com.facebook.react.uimanager.ThemedReactContext
import com.king.pagecontrol.PageControlView

@DoNotStrip
class HybridPageControl(val context: ThemedReactContext) : HybridPageControlSpec() {
  override val view = PageControlView(context)

  override var numberOfPages: Double
    get() = view.numberOfPages.toDouble()
    set(value) { view.numberOfPages = value.toInt() }
}
