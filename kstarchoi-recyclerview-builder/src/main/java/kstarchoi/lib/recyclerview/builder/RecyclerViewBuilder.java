/*
 * MIT License
 *
 * Copyright (c) 2017 Gwangseong Choi
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

package kstarchoi.lib.recyclerview.builder;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gwangseong Choi
 * @since 2017-07-22
 */

public class RecyclerViewBuilder<Data> {

    private static final int HORIZONTAL = OrientationHelper.HORIZONTAL;
    private static final int VERTICAL = OrientationHelper.VERTICAL;

    private final RecyclerView mRecyclerView;

    private LayoutManagerInfo mLayoutManagerInfo;
    private ViewBinder<Data> mViewBinder;
    private List<RecyclerView.ItemDecoration> mItemDecorationList;
    private RecyclerView.ItemAnimator mItemAnimator;

    public RecyclerViewBuilder(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mLayoutManagerInfo = new LayoutManagerInfo(mRecyclerView);
        mViewBinder = new DefaultViewBinder<>();
        mItemDecorationList = new ArrayList<>();
        mItemAnimator = new DefaultItemAnimator();
    }

    public RecyclerViewBuilder<Data> setLayoutManager(
            @NonNull RecyclerView.LayoutManager layoutManager) {
        mLayoutManagerInfo.setLayoutManager(layoutManager);
        return this;
    }

    public RecyclerViewBuilder<Data> setHorizontalLinearLayoutManager(boolean reverseLayout) {
        mLayoutManagerInfo.setLinearLayoutInfo(HORIZONTAL, reverseLayout);
        return this;
    }

    public RecyclerViewBuilder<Data> setVerticalLinearLayoutManager(boolean reverseLayout) {
        mLayoutManagerInfo.setLinearLayoutInfo(VERTICAL, reverseLayout);
        return this;
    }

    public RecyclerViewBuilder<Data> setHorizontalGridLayoutManager(
            @IntRange(from = 1) int spanCount, boolean reverseLayout) {
        mLayoutManagerInfo.setGridLayoutInfo(HORIZONTAL, spanCount, reverseLayout);
        return this;
    }

    public RecyclerViewBuilder<Data> setVerticalGridLayoutManager(
            @IntRange(from = 1) int spanCount, boolean reverseLayout) {
        mLayoutManagerInfo.setGridLayoutInfo(VERTICAL, spanCount, reverseLayout);
        return this;
    }

    public RecyclerViewBuilder<Data> setHorizontalStaggeredGridLayoutManager(
            @IntRange(from = 1) int spanCount) {
        mLayoutManagerInfo.setStaggeredGridLayoutInfo(HORIZONTAL, spanCount);
        return this;
    }

    public RecyclerViewBuilder<Data> setVerticalStaggeredGridLayoutManager(
            @IntRange(from = 1) int spanCount) {
        mLayoutManagerInfo.setStaggeredGridLayoutInfo(VERTICAL, spanCount);
        return this;
    }

    public RecyclerViewBuilder<Data> setViewBinder(@NonNull ViewBinder<Data> viewBinder) {
        mViewBinder = viewBinder;
        return this;
    }

    public RecyclerViewBuilder<Data> addItemDecoration(
            @NonNull RecyclerView.ItemDecoration itemDecoration) {
        if (mItemDecorationList.indexOf(itemDecoration) == -1) {
            mItemDecorationList.add(itemDecoration);
        }
        return this;
    }

    public RecyclerViewBuilder<Data> setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        mItemAnimator = itemAnimator;
        return this;
    }

    public ViewAdapter<Data> build() {
        RecyclerView.LayoutManager layoutManager = mLayoutManagerInfo.getLayoutManager();
        mRecyclerView.setLayoutManager(layoutManager);

        GenericAdapter<Data> genericAdapter = new GenericAdapter<>(mViewBinder);
        mRecyclerView.setAdapter(genericAdapter);

        for (RecyclerView.ItemDecoration itemDecoration : mItemDecorationList) {
            mRecyclerView.addItemDecoration(itemDecoration);
        }

        mRecyclerView.setItemAnimator(mItemAnimator);

        return genericAdapter;
    }

    public ViewAdapter<Data> build(@NonNull List<Data> dataList) {
        ViewAdapter<Data> viewAdapter = build();
        viewAdapter.setDataList(dataList);
        return viewAdapter;
    }


    private static class LayoutManagerInfo {

        private enum LayoutManagerType {
            LINEAR, GRID, STAGGERED_GRID,
        }

        private final RecyclerView mRecyclerView;

        private RecyclerView.LayoutManager mLayoutManager;
        private LayoutManagerType mLayoutManagerType;
        private int mOrientation;
        private int mSpanCount;
        private boolean mReverseLayout;

        LayoutManagerInfo(@NonNull RecyclerView recyclerView) {
            mRecyclerView = recyclerView;

            setLinearLayoutInfo(VERTICAL, false);

            setLayoutManager(mRecyclerView.getLayoutManager());
        }

        void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
            mLayoutManager = layoutManager;
        }

        void setLinearLayoutInfo(int orientation, boolean reverseLayout) {
            mLayoutManager = null;
            mLayoutManagerType = LayoutManagerType.LINEAR;
            mOrientation = orientation;
            mReverseLayout = reverseLayout;
        }

        void setGridLayoutInfo(int orientation,
                               @IntRange(from = 1) int spanCount,
                               boolean reverseLayout) {
            mLayoutManager = null;
            mLayoutManagerType = LayoutManagerType.GRID;
            mOrientation = orientation;
            mSpanCount = (spanCount < 1) ? 1 : spanCount;
            mReverseLayout = reverseLayout;
        }

        void setStaggeredGridLayoutInfo(int orientation, @IntRange(from = 1) int spanCount) {
            mLayoutManager = null;
            mLayoutManagerType = LayoutManagerType.STAGGERED_GRID;
            mOrientation = orientation;
            mSpanCount = (spanCount < 1) ? 1 : spanCount;
            mReverseLayout = false;
        }

        private RecyclerView.LayoutManager getLayoutManager() {
            if (mLayoutManager != null) {
                return mLayoutManager;
            }

            Context context = mRecyclerView.getContext();
            switch (mLayoutManagerType) {
                case LINEAR:
                    return new LinearLayoutManager(context, mOrientation, mReverseLayout);
                case GRID:
                    return new GridLayoutManager(context, mSpanCount, mOrientation, mReverseLayout);
                case STAGGERED_GRID:
                    return new StaggeredGridLayoutManager(mSpanCount, mOrientation);
                default:
                    return new LinearLayoutManager(context, VERTICAL, false);
            }
        }
    }
}
