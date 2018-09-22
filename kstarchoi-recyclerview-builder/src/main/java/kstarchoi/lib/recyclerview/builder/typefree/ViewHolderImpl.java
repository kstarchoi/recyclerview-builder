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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @author Gwangseong Choi
 * @since 2018-09-22
 */

final class ViewHolderImpl extends RecyclerView.ViewHolder implements ViewHolder {

    private final SparseArray<View> viewSparseArray = new SparseArray<>();
    private final Resources resources;

    ViewHolderImpl(View itemView) {
        super(itemView);
        resources = itemView.getResources();
    }

    @Override
    public Context getContext() {
        return itemView.getContext();
    }

    @SuppressLint("DefaultLocale")
    @SuppressWarnings("unchecked")
    @Override
    public <T extends View> T get(@IdRes int idRes) {
        AssertionHelper.exist(String.format("id/%d", idRes), idRes, resources);

        View view = viewSparseArray.get(idRes);
        if (view == null) {
            view = itemView.findViewById(idRes);
            AssertionHelper.exist(resources.getResourceName(idRes), view != null);

            viewSparseArray.put(idRes, view);
        }

        return (T) view;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends View> T getRoot() {
        return (T) itemView;
    }
}
