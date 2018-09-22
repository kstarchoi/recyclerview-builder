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

import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.List;

/**
 * @author Gwangseong Choi
 * @since 2018-09-22
 */

final class ViewBindHelper {

    private final SparseArray<ViewBinderImpl> viewBinderImplSparseArray = new SparseArray<>();
    private final SparseIntArray layoutResSparseArray = new SparseIntArray();


    void put(@LayoutRes int layoutRes, ViewBinder<?> viewBinder) {
        ViewBinderImpl<?> viewBinderImpl = new ViewBinderImpl<>(viewBinder);
        String name = String.format("ViewBinder<%s>", viewBinderImpl.getDataTypeName());
        int viewType = viewBinderImpl.getViewType();
        AssertionHelper.notExist(name, viewType, viewBinderImplSparseArray);

        viewBinderImplSparseArray.put(viewType, viewBinderImpl);
        layoutResSparseArray.put(viewType, layoutRes);
    }


    int getLayoutRes(int viewType) {
        return layoutResSparseArray.get(viewType);
    }

    int getViewType(Object data) {
        return ViewBinderImpl.getViewType(data);
    }

    ViewBinderImpl<?> getViewBinderImpl(Object data) {
        return viewBinderImplSparseArray.get(getViewType(data));
    }


    void checkBoundDataType(Object data, Object... dataArray) {
        AssertionHelper.bound(data.getClass().getName(), getViewType(data),
                viewBinderImplSparseArray);

        for (Object aData : dataArray) {
            AssertionHelper.bound(aData.getClass().getName(), getViewType(aData),
                    viewBinderImplSparseArray);
        }
    }

    void checkBoundDataType(List<?> dataList) {
        for (Object aData : dataList) {
            AssertionHelper.bound(aData.getClass().getName(), getViewType(aData),
                    viewBinderImplSparseArray);
        }
    }
}
