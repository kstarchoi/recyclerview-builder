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

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author Gwangseong Choi
 * @since 2017-07-22.
 */

public interface ViewAdapter<Data> {

    void setDataList(List<Data> dataList);

    Data getData(@IntRange(from = 0) int index);

    int getDataIndex(@NonNull Data data);

    int getDataCount();

    boolean insertData(@IntRange(from = 0) int index, @NonNull Data data);

    boolean insertData(@IntRange(from = 0) int index, @NonNull List<Data> dataList);

    boolean removeData(@IntRange(from = 0) int index);

    boolean removeData(@IntRange(from = 0) int index, @IntRange(from = 1) int dataCount);

    boolean changeData(@IntRange(from = 0) int index, @NonNull Data data, Object... payloads);

    boolean changeData(@IntRange(from = 0) int index, @NonNull List<Data> dataList, Object... payloads);

    boolean moveData(@IntRange(from = 0) int fromIndex, @IntRange(from = 0) int toIndex);
}
