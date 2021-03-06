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
    public void onBindViewHolder(GenericViewHolder holder, int position, List<Object> payloads) {
        holder.setPayloads(payloads);
        super.onBindViewHolder(holder, position, payloads);
        holder.clearPayloads();
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

    @Override
    public Data getData(@IntRange(from = 0) int index) {
        if (index < 0 || index >= mDataList.size()) {
            return null;
        }

        return mDataList.get(index);
    }

    @Override
    public int getDataIndex(@NonNull Data data) {
        return mDataList.indexOf(data);
    }

    @Override
    public int getDataCount() {
        return mDataList.size();
    }

    @Override
    public boolean insertData(@IntRange(from = 0) int index, @NonNull Data data) {
        if (index < 0 || index > mDataList.size()) {
            return false;
        }

        mDataList.add(index, data);
        notifyItemInserted(index);
        return true;
    }

    @Override
    public boolean insertData(@IntRange(from = 0) int index, @NonNull List<Data> dataList) {
        if (index < 0 || index > mDataList.size()) {
            return false;
        }

        if (dataList.isEmpty()) {
            return false;
        }

        mDataList.addAll(index, dataList);
        notifyItemRangeInserted(index, dataList.size());
        return true;
    }

    @Override
    public boolean removeData(@IntRange(from = 0) int index) {
        if (index < 0 || index >= mDataList.size()) {
            return false;
        }

        mDataList.remove(index);
        notifyItemRemoved(index);
        return true;
    }

    @Override
    public boolean removeData(@IntRange(from = 0) int index, @IntRange(from = 1) int dataCount) {
        if (index < 0 || index >= mDataList.size()) {
            return false;
        }

        if (dataCount < 1) {
            return false;
        }

        int lastIndex = index + dataCount - 1;
        if (lastIndex >= mDataList.size()) {
            return false;
        }

        for (int i = lastIndex; i >= index; i--) {
            mDataList.remove(i);
        }
        notifyItemRangeRemoved(index, dataCount);
        return true;
    }

    @Override
    public boolean changeData(@IntRange(from = 0) int index, @NonNull Data data,
                              Object... payloads) {
        if (index < 0 || index >= mDataList.size()) {
            return false;
        }

        mDataList.set(index, data);
        notifyItemChanged(index, (payloads.length == 0) ? null : payloads);
        return true;
    }

    @Override
    public boolean changeData(@IntRange(from = 0) int index, @NonNull List<Data> dataList,
                              Object... payloads) {
        if (index < 0 || index >= mDataList.size()) {
            return false;
        }

        int dataCount = dataList.size();
        if (dataCount < 1) {
            return false;
        }

        int lastIndex = index + dataCount - 1;
        if (lastIndex >= mDataList.size()) {
            return false;
        }

        for (int i = 0; i < dataCount; i++) {
            mDataList.set(index + i, dataList.get(i));
        }
        notifyItemRangeChanged(index, dataCount, (payloads.length == 0) ? null : payloads);
        return true;
    }

    @Override
    public boolean moveData(@IntRange(from = 0) int fromIndex, @IntRange(from = 0) int toIndex) {
        if (fromIndex < 0 || fromIndex >= mDataList.size()) {
            return false;
        }

        if (toIndex < 0 || toIndex >= mDataList.size()) {
            return false;
        }

        if (fromIndex == toIndex) {
            return false;
        }

        Data data = mDataList.remove(fromIndex);
        mDataList.add(toIndex, data);
        notifyItemMoved(fromIndex, toIndex);
        return true;
    }
}
