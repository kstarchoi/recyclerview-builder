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

/**
 * @author Gwangseong Choi
 * @since 2018-09-22
 */

final class ViewBinderImpl<Data> {

    private final String dataTypeName;
    private final int viewType;
    private final ViewBinder<Data> viewBinder;

    ViewBinderImpl(ViewBinder<Data> viewBinder) {
        Class<?> dataTypeClass = ReflectionHelper.getGenericInterfaceParameterType(viewBinder);
        this.dataTypeName = dataTypeClass.getName();
        this.viewType = getViewType(dataTypeClass);
        this.viewBinder = viewBinder;
    }

    String getDataTypeName() {
        return dataTypeName;
    }

    int getViewType() {
        return viewType;
    }


    @SuppressWarnings("unchecked")
    void bind(ViewHolder viewHolder, Object data, int index) {
        viewBinder.bind(viewHolder, (Data) data, index);
    }


    static int getViewType(Object object) {
        return getViewType(object.getClass());
    }

    private static int getViewType(Class<?> aClass) {
        return aClass.hashCode();
    }
}
