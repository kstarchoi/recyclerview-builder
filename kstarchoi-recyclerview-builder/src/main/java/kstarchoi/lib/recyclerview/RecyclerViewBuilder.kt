/*
 * MIT License
 *
 * Copyright (c) 2019 Gwangseong Choi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package kstarchoi.lib.recyclerview

import android.support.annotation.IntRange
import android.support.annotation.NonNull
import android.support.v7.widget.*

class RecyclerViewBuilder(private val recyclerView: RecyclerView) {

    companion object {
        private const val VERTICAL = OrientationHelper.VERTICAL
        private const val HORIZONTAL = OrientationHelper.HORIZONTAL
        private const val MIN_SPAN_COUNT = 1
        private const val DEFAULT_REVERSE = false
    }

    private val layoutManagerInfo = LayoutManagerInfo()
    private val itemDecorationList = mutableListOf<RecyclerView.ItemDecoration>()
    private var itemAnimator: RecyclerView.ItemAnimator? = DefaultItemAnimator()
    private val viewBinderHelper = ViewBinderHelper()


    fun setVerticalLinearLayout(reverse: Boolean = false): RecyclerViewBuilder {
        layoutManagerInfo.setLinear(VERTICAL, reverse)
        return this
    }

    fun setHorizontalLinearLayout(reverse: Boolean = false): RecyclerViewBuilder {
        layoutManagerInfo.setLinear(HORIZONTAL, reverse)
        return this
    }

    fun setVerticalGridLayout(@IntRange(from = 1) spanCount: Int,
                              reverse: Boolean = false): RecyclerViewBuilder {
        layoutManagerInfo.setGrid(VERTICAL, spanCount, reverse)
        return this
    }

    fun setHorizontalGridLayout(@IntRange(from = 1) spanCount: Int,
                                reverse: Boolean = false): RecyclerViewBuilder {
        layoutManagerInfo.setGrid(HORIZONTAL, spanCount, reverse)
        return this
    }

    fun setVerticalStaggeredGridLayout(@IntRange(from = 1) spanCount: Int): RecyclerViewBuilder {
        layoutManagerInfo.setStaggeredGrid(VERTICAL, spanCount)
        return this
    }

    fun setHorizontalStaggeredGridLayout(@IntRange(from = 1) spanCount: Int): RecyclerViewBuilder {
        layoutManagerInfo.setStaggeredGrid(HORIZONTAL, spanCount)
        return this
    }

    fun setLayoutManager(@NonNull layoutManager: RecyclerView.LayoutManager): RecyclerViewBuilder {
        layoutManagerInfo.layoutManager = layoutManager
        return this
    }


    fun addItemDecoration(itemDecoration: RecyclerView.ItemDecoration): RecyclerViewBuilder {
        itemDecorationList.add(itemDecoration)
        return this
    }

    fun setItemAnimator(itemAnimator: RecyclerView.ItemAnimator): RecyclerViewBuilder {
        this.itemAnimator = itemAnimator
        return this
    }


    fun addViewBinder(itemViewBinder: ItemViewBinder<*>): RecyclerViewBuilder {
        viewBinderHelper.register(itemViewBinder)
        return this
    }


    fun build(): ViewAdapter {
        recyclerView.layoutManager = layoutManagerInfo.layoutManager

        recyclerView.invalidateItemDecorations()
        for (itemDecoration in itemDecorationList) {
            recyclerView.addItemDecoration(itemDecoration)
        }

        recyclerView.itemAnimator = itemAnimator

        val viewAdapterImpl = ViewAdapterImpl(recyclerView, viewBinderHelper)
        recyclerView.adapter = viewAdapterImpl

        return viewAdapterImpl
    }

    fun build(dataList: List<Any>): ViewAdapter {
        val viewAdapter = build()
        viewAdapter.setDataList(dataList)
        return viewAdapter
    }


    private enum class LayoutType {
        LINEAR, GRID, STAGGERED_GRID, CUSTOM
    }

    private inner class LayoutManagerInfo {

        var layoutType = LayoutType.LINEAR

        var orientation = VERTICAL

        var spanCount = MIN_SPAN_COUNT
            set(value) {
                field = if (value < MIN_SPAN_COUNT) MIN_SPAN_COUNT else value
            }

        var reverse = DEFAULT_REVERSE

        var layoutManager: RecyclerView.LayoutManager? = null
            set(value) {
                layoutType = LayoutType.CUSTOM
                field = value
            }
            get() {
                val context = recyclerView.context
                return when (layoutType) {
                    LayoutType.LINEAR -> LinearLayoutManager(context, orientation, reverse)
                    LayoutType.GRID -> GridLayoutManager(context, spanCount, orientation, reverse)
                    LayoutType.STAGGERED_GRID -> StaggeredGridLayoutManager(spanCount, orientation)
                    LayoutType.CUSTOM -> if (field == null) {
                        LinearLayoutManager(context, orientation, reverse)
                    } else {
                        field
                    }
                }
            }


        fun setLinear(orientation: Int, reverse: Boolean = DEFAULT_REVERSE) {
            this.layoutType = LayoutType.LINEAR
            this.orientation = orientation
            this.reverse = reverse
        }

        fun setGrid(orientation: Int, spanCount: Int = MIN_SPAN_COUNT,
                    reverse: Boolean = DEFAULT_REVERSE) {
            this.layoutType = LayoutType.GRID
            this.orientation = orientation
            this.spanCount = spanCount
            this.reverse = reverse
        }

        fun setStaggeredGrid(orientation: Int, spanCount: Int = MIN_SPAN_COUNT) {
            this.layoutType = LayoutType.STAGGERED_GRID
            this.orientation = orientation
            this.spanCount = spanCount
        }
    }
}