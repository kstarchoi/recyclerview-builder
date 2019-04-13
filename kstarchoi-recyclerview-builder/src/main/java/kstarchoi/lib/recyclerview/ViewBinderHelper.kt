/*
 * MIT License
 *
 * Copyright (c) 2019 Gwangseong Choi
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

package kstarchoi.lib.recyclerview

import java.lang.reflect.Type

internal class ViewBinderHelper {

    private val viewBinderMap = mutableMapOf<Int, ViewBinder<*>>()

    fun register(viewBinder: ViewBinder<*>) {
        val dataType: Type = ReflectionHelper.getGenericSuperclassParameterType(viewBinder)
        val viewType = dataType.hashCode()
        viewBinderMap[viewType] = viewBinder
    }

    fun getViewBinder(viewType: Int): ViewBinder<*> {
        return viewBinderMap[viewType]!!
    }

    fun getViewBinder(data: Any): ViewBinder<*> {
        return viewBinderMap[getViewType(data)]!!
    }

    fun getViewType(data: Any): Int {
        val dataType: Type = data.javaClass
        return dataType.hashCode()
    }
}