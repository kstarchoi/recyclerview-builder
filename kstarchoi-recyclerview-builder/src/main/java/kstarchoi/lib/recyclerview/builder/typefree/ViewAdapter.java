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

import java.util.List;

/**
 * @author Gwangseong Choi
 * @since 2018-09-22
 */

public interface ViewAdapter {

    void setDataList(@NonNull List<?> dataList);

    void insertData(@NonNull Object data, Object... dataArray);

    void insertDataTo(@IntRange(from = 0) int index, @NonNull Object data, Object... dataArray);

    void insertDataAll(@NonNull List<?> dataList);

    void insertDataAllTo(@IntRange(from = 0) int index, @NonNull List<?> dataList);

    void removeData(@NonNull Object data, Object... dataArray);

    void removeDataAll(@NonNull List<?> dataList);

    void removeDataAt(@IntRange(from = 0) int index, int... indexArray);

    void removeDataFrom(@IntRange(from = 0) int index, @IntRange(from = 1) int dataCount);
}
