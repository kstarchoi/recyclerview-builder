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

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gwangseong Choi
 * @since 2017-07-22
 */

public class RecyclerViewBuilder<Data> {

    private final RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private ViewBinder<Data> mViewBinder;
    private List<RecyclerView.ItemDecoration> mItemDecorationList;
    private RecyclerView.ItemAnimator mItemAnimator;

    public RecyclerViewBuilder(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mLayoutManager = recyclerView.getLayoutManager();
        mViewBinder = new DefaultViewBinder<>();
        mItemDecorationList = new ArrayList<>();
    }

    public RecyclerViewBuilder<Data> setLayoutManager(
            @NonNull RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
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
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        mRecyclerView.setLayoutManager(layoutManager);

        GenericAdapter<Data> genericAdapter = new GenericAdapter<>(mViewBinder);
        mRecyclerView.setAdapter(genericAdapter);

        for (RecyclerView.ItemDecoration itemDecoration : mItemDecorationList) {
            mRecyclerView.addItemDecoration(itemDecoration);
        }

        mRecyclerView.setItemAnimator(mItemAnimator);

        return genericAdapter;
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if (mLayoutManager != null) {
            return mLayoutManager;
        }

        return new LinearLayoutManager(mRecyclerView.getContext());
    }


    public ViewAdapter<Data> build(@NonNull List<Data> dataList) {
        ViewAdapter<Data> viewAdapter = build();
        viewAdapter.setDataList(dataList);
        return viewAdapter;
    }
}
