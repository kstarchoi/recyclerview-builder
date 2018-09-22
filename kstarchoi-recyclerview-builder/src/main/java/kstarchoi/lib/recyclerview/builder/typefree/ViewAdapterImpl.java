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
import java.util.Collections;
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getData(@IntRange(from = 0) int index) {
        AssertionHelper.interior("index", index, 0, getLastDataIndex());

        return (T) dataList.get(index);
    }

    @Override
    public int getDataIndex(@NonNull Object data) {
        AssertionHelper.notNull("data", data);

        return dataList.indexOf(data);
    }

    private int getLastDataIndex() {
        return getDataCount() - 1;
    }

    @Override
    public int getDataCount() {
        return getItemCount();
    }

    @Override
    public void insertData(@NonNull Object data, Object... dataArray) {
        insertDataTo(getDataCount(), data, dataArray);
    }

    @Override
    public void insertDataTo(@IntRange(from = 0) int index,
                             @NonNull Object data, Object... dataArray) {
        AssertionHelper.interior("index", index, 0, getDataCount());
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
        insertDataAllTo(getDataCount(), dataList);
    }

    @Override
    public void insertDataAllTo(@IntRange(from = 0) int index, @NonNull List<?> dataList) {
        AssertionHelper.interior("index", index, 0, getDataCount());
        AssertionHelper.notNull("dataList", dataList);
        AssertionHelper.notContains("null", null, dataList);

        viewBindHelper.checkBoundDataType(dataList);

        this.dataList.addAll(index, dataList);
        notifyItemRangeInserted(index, dataList.size());
    }

    @Override
    public void removeData(@NonNull Object data, Object... dataArray) {
        ArrayList<Integer> dataIndexList = new ArrayList<>();
        dataIndexList.add(dataList.indexOf(data));
        for (Object aData : dataArray) {
            dataIndexList.add(dataList.indexOf(aData));
        }

        removeData(dataIndexList);
    }

    @Override
    public void removeDataAll(@NonNull List<?> dataList) {
        AssertionHelper.notNull("dataList", dataList);

        ArrayList<Integer> dataIndexList = new ArrayList<>();
        for (Object data : dataList) {
            dataIndexList.add(this.dataList.indexOf(data));
        }

        removeData(dataIndexList);
    }

    @Override
    public void removeDataAt(@IntRange(from = 0) int index, int... indexArray) {
        int lastDataIndex = getLastDataIndex();
        AssertionHelper.interior("index", index, 0, lastDataIndex);
        AssertionHelper.interior("index", indexArray, 0, lastDataIndex);

        ArrayList<Integer> dataIndexList = new ArrayList<>();
        dataIndexList.add(index);
        for (int aIndex : indexArray) {
            dataIndexList.add(aIndex);
        }

        removeData(dataIndexList);
    }

    private void removeData(List<Integer> dataIndexList) {
        Collections.sort(dataIndexList);

        for (int i = dataIndexList.size() - 1; i >= 0; i--) {
            int dataIndex = dataIndexList.get(i);
            if (dataIndex < 0) {
                break;
            }

            dataList.remove(dataIndex);
            notifyItemRemoved(dataIndex);
        }
    }

    @Override
    public void removeDataFrom(@IntRange(from = 0) int index, @IntRange(from = 1) int dataCount) {
        int lastDataIndex = getLastDataIndex();
        int lastIndex = index + dataCount - 1;
        AssertionHelper.interior("index", index, 0, lastDataIndex);
        AssertionHelper.greaterThanOrEqualTo("dataCount", dataCount, 1);
        AssertionHelper.interior("lastIndex", lastIndex, 0, lastDataIndex);

        for (int i = lastIndex; i >= index; i--) {
            dataList.remove(i);
        }
        notifyItemRangeRemoved(index, dataCount);
    }
}
