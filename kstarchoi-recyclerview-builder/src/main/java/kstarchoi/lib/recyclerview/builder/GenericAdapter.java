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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gwangseong Choi
 * @since 2017-07-22
 */

class GenericAdapter<Data> extends RecyclerView.Adapter<GenericViewHolder>
        implements ViewAdapter<Data> {

    private final ViewBinder<Data> mViewBinder;

    private List<Data> mDataList = new ArrayList<>();

    GenericAdapter(ViewBinder<Data> viewBinder) {
        mViewBinder = viewBinder;
    }

    @Override
    public int getItemViewType(int position) {
        return mViewBinder.getViewType(position, mDataList.get(position));
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutRes = mViewBinder.getViewLayoutRes(viewType);
        View itemView = LayoutInflater.from(context).inflate(layoutRes, parent, false);

        GenericViewHolder genericViewHolder = new GenericViewHolder(itemView, viewType);
        mViewBinder.init(genericViewHolder);
        return genericViewHolder;
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        mViewBinder.bind(holder, position, mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    // =============================================================================================
    // ViewAdapter
    // =============================================================================================

    @Override
    public void setDataList(List<Data> dataList) {
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }
}
