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

import android.widget.TextView;

import java.util.Locale;

/**
 * @author Gwangseong Choi
 * @since 2017-07-22
 */

public class DefaultViewBinder<Data> implements ViewBinder<Data> {

    @Override
    public int getViewType(int index, Data data) {
        return 0;
    }

    @Override
    public int getViewLayoutRes(int viewType) {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    public void init(ViewPreparer preparer) {
        preparer.reserve(android.R.id.text1);
    }

    @Override
    public void bind(ViewProvider provider, int index, Data data) {
        TextView textView = provider.get(android.R.id.text1);
        String message = String.format(Locale.getDefault(), "%d. Default item", (index + 1));
        textView.setText(message);
    }
}
