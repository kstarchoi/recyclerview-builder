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

import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gwangseong Choi
 * @since 2017-07-22
 */

class GenericViewHolder extends RecyclerView.ViewHolder
        implements ViewPreparer, ViewProvider {

    private int mViewType;
    private SparseArray<View> mViewSparseArray = new SparseArray<>();
    private List<Object> mPayloadList = new ArrayList<>();

    GenericViewHolder(View itemView, int viewType) {
        super(itemView);
        mViewType = viewType;
    }

    // =============================================================================================
    // ViewPreparer, ViewProvider
    // =============================================================================================

    @Override
    public int getViewType() {
        return mViewType;
    }

    // =============================================================================================
    // ViewPreparer
    // =============================================================================================

    @Override
    public void reserve(@IdRes int... idResArray) {
        for (int idRes : idResArray) {
            View view = itemView.findViewById(idRes);
            mViewSparseArray.put(idRes, view);
        }
    }

    // =============================================================================================
    // ViewProvider
    // =============================================================================================

    @SuppressWarnings("unchecked")
    @Override
    public <T extends View> T getRoot() {
        return (T) itemView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends View> T get(@IdRes int idRes) {
        return (T) mViewSparseArray.get(idRes);
    }

    void setPayloads(@NonNull List<Object> payloadList) {
        if (payloadList.isEmpty()) {
            return;
        }

        Object[] payloads = (Object[]) payloadList.get(0);
        Collections.addAll(mPayloadList, payloads);
    }

    @Override
    public boolean hasPayload() {
        return !mPayloadList.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getPayload(@IntRange(from = 0) int index) {
        if (index < 0 || index >= mPayloadList.size()) {
            return null;
        }

        return (T) mPayloadList.get(index);
    }

    void clearPayloads() {
        if (mPayloadList.isEmpty()) {
            return;
        }

        mPayloadList.clear();
    }
}
