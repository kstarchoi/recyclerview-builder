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
 * @since 2018-09-22
 */

final class ViewAdapterImpl extends RecyclerView.Adapter<ViewHolderImpl> implements ViewAdapter {

    private final ArrayList<Object> dataList = new ArrayList<>();
    private final ViewBindHelper viewBindHelper;

    ViewAdapterImpl(ViewBindHelper viewBindHelper) {
        this.viewBindHelper = viewBindHelper;
    }


    private Object getData(int index) {
        return dataList.get(index);
    }

    @Override
    public int getItemViewType(int position) {
        return viewBindHelper.getViewType(getData(position));
    }

    @Override
    public ViewHolderImpl onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutRes = viewBindHelper.getLayoutRes(viewType);

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutRes, parent, false);
        return new ViewHolderImpl(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderImpl holder, int position) {
        Object data = getData(position);
        ViewBinderImpl<?> viewBinderImpl = viewBindHelper.getViewBinderImpl(data);
        viewBinderImpl.bind(holder, data, position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    // =============================================================================================
    // ViewAdapter interface
    // =============================================================================================

    @Override
    public void setDataList(@NonNull List<?> dataList) {
        AssertionHelper.notNull("dataList", dataList);

        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void insertData(@NonNull Object data, Object... dataArray) {
        insertDataTo(dataList.size(), data, dataArray);
    }

    @Override
    public void insertDataTo(@IntRange(from = 0) int index,
                             @NonNull Object data, Object... dataArray) {
        AssertionHelper.interior("index", index, 0, dataList.size());
        AssertionHelper.notNull("data", data);
        AssertionHelper.notContains("null", null, dataArray);

        viewBindHelper.checkBoundDataType(data, dataArray);

        for (int i = dataArray.length - 1; i >= 0; i--) {
            dataList.add(index, dataArray[i]);
        }
        dataList.add(index, data);
        notifyItemRangeInserted(index, 1 + dataArray.length);
    }

    @Override
    public void insertDataAll(@NonNull List<?> dataList) {
        insertDataAllTo(this.dataList.size(), dataList);
    }

    @Override
    public void insertDataAllTo(@IntRange(from = 0) int index, @NonNull List<?> dataList) {
        AssertionHelper.interior("index", index, 0, this.dataList.size());
        AssertionHelper.notNull("dataList", dataList);
        AssertionHelper.notContains("null", null, dataList);

        viewBindHelper.checkBoundDataType(dataList);

        this.dataList.addAll(index, dataList);
        notifyItemRangeInserted(index, dataList.size());
    }
}
