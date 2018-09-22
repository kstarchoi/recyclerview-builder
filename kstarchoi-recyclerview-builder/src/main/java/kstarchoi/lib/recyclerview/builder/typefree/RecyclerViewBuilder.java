/*
 * MIT License
 *
 * Copyright (c) 2018 Gwangseong Choi
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

package kstarchoi.lib.recyclerview.builder.typefree;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

/**
 * @author Gwangseong Choi
 * @since 2018-09-22
 */

public class RecyclerViewBuilder {

    private static final int VERTICAL = OrientationHelper.VERTICAL;
    private static final int HORIZONTAL = OrientationHelper.HORIZONTAL;

    private static final int MIN_SPAN_COUNT = 1;

    private final RecyclerView recyclerView;
    private final LayoutInfo layoutInfo;
    private final ArrayList<ItemDecoration> itemDecorationList;
    private ItemAnimator itemAnimator;

    public RecyclerViewBuilder(@NonNull RecyclerView recyclerView) {
        AssertionHelper.notNull("recyclerView", recyclerView);

        this.recyclerView = recyclerView;
        this.layoutInfo = new LayoutInfo(recyclerView);
        this.itemDecorationList = new ArrayList<>();
        this.itemAnimator = new DefaultItemAnimator();
    }


    public RecyclerViewBuilder setLayoutManager(@NonNull LayoutManager layoutManager) {
        AssertionHelper.notNull("layoutManager", layoutManager);

        layoutInfo.setManager(layoutManager);
        return this;
    }


    public RecyclerViewBuilder setVerticalLinearLayout(boolean reverseLayout) {
        layoutInfo.setLinear(VERTICAL, reverseLayout);
        return this;
    }

    public RecyclerViewBuilder setHorizontalLinearLayout(boolean reverseLayout) {
        layoutInfo.setLinear(HORIZONTAL, reverseLayout);
        return this;
    }

    public RecyclerViewBuilder setVerticalGridLayout(
            @IntRange(from = MIN_SPAN_COUNT) int spanCount, boolean reverseLayout) {
        assertSpanCount(spanCount);

        layoutInfo.setGrid(VERTICAL, spanCount, reverseLayout);
        return this;
    }

    public RecyclerViewBuilder setHorizontalGridLayout(
            @IntRange(from = MIN_SPAN_COUNT) int spanCount, boolean reverseLayout) {
        assertSpanCount(spanCount);

        layoutInfo.setGrid(HORIZONTAL, spanCount, reverseLayout);
        return this;
    }

    public RecyclerViewBuilder setVerticalStaggeredGridLayout(
            @IntRange(from = MIN_SPAN_COUNT) int spanCount) {
        assertSpanCount(spanCount);

        layoutInfo.setStaggeredGrid(VERTICAL, spanCount);
        return this;
    }

    public RecyclerViewBuilder setHorizontalStaggeredGridLayout(
            @IntRange(from = MIN_SPAN_COUNT) int spanCount) {
        assertSpanCount(spanCount);

        layoutInfo.setStaggeredGrid(HORIZONTAL, spanCount);
        return this;
    }

    private static void assertSpanCount(int spanCount) {
        AssertionHelper.greaterThanOrEqualTo("spanCount", spanCount, MIN_SPAN_COUNT);
    }


    public RecyclerViewBuilder addItemDecoration(@NonNull ItemDecoration itemDecoration) {
        AssertionHelper.notNull("itemDecoration", itemDecoration);
        AssertionHelper.notExist("itemDecoration", itemDecoration, itemDecorationList);

        itemDecorationList.add(itemDecoration);
        return this;
    }

    public RecyclerViewBuilder setItemAnimator(@NonNull ItemAnimator itemAnimator) {
        AssertionHelper.notNull("itemAnimator", itemAnimator);

        this.itemAnimator = itemAnimator;
        return this;
    }


    public void build() {
        recyclerView.setLayoutManager(layoutInfo.getManager());

        recyclerView.invalidateItemDecorations();
        for (ItemDecoration itemDecoration : itemDecorationList) {
            recyclerView.addItemDecoration(itemDecoration);
        }

        recyclerView.setItemAnimator(itemAnimator);
    }


    private static class LayoutInfo {

        private enum Type {
            LINEAR, GRID, STAGGERED_GRID
        }

        private final RecyclerView recyclerView;
        private LayoutManager manager = null;
        private Type type = Type.LINEAR;
        private int orientation = VERTICAL;
        private boolean reverseLayout = false;
        private int spanCount = MIN_SPAN_COUNT;

        LayoutInfo(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        void setManager(LayoutManager layoutManager) {
            manager = layoutManager;
        }

        void setLinear(int orientation, boolean reverseLayout) {
            this.manager = null;
            this.type = Type.LINEAR;
            this.orientation = orientation;
            this.reverseLayout = reverseLayout;
        }

        void setGrid(int orientation, int spanCount, boolean reverseLayout) {
            this.manager = null;
            this.type = Type.GRID;
            this.orientation = orientation;
            this.reverseLayout = reverseLayout;
            this.spanCount = spanCount;
        }

        void setStaggeredGrid(int orientation, int spanCount) {
            this.manager = null;
            this.type = Type.STAGGERED_GRID;
            this.orientation = orientation;
            this.spanCount = spanCount;
        }

        LayoutManager getManager() {
            if (manager != null) {
                return manager;
            }

            Context context = recyclerView.getContext();
            switch (type) {
                case LINEAR:
                    return new LinearLayoutManager(context, orientation, reverseLayout);
                case GRID:
                    return new GridLayoutManager(context, spanCount, orientation, reverseLayout);
                case STAGGERED_GRID:
                    return new StaggeredGridLayoutManager(spanCount, orientation);
                default:
                    return new LinearLayoutManager(context);
            }
        }
    }
}
