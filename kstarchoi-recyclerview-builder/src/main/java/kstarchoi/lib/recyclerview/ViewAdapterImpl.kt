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

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * @author Gwangseong Choi
 * @since 2019-04-13
 */

internal class ViewAdapterImpl(private val recyclerView: RecyclerView,
                               private val viewBinderHelper: ViewBinderHelper)
    : RecyclerView.Adapter<ViewHolderImpl>(), ViewAdapter {

    private val dataList = mutableListOf<Any>()

    override fun getItemViewType(position: Int): Int {
        return viewBinderHelper.getViewType(dataList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderImpl {
        val viewBinder = viewBinderHelper.getViewBinder(viewType)
        val layoutRes = viewBinder.getLayoutRes()
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return ViewHolderImpl(view)
    }

    override fun onBindViewHolder(holder: ViewHolderImpl, position: Int) {
        val data = dataList[position]
        val viewBinder = viewBinderHelper.getViewBinder(data)
        viewBinder.bindInternal(holder, data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getRecyclerView(): RecyclerView {
        return recyclerView
    }

    override fun setDataList(dataList: List<Any>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun getDataCount(): Int {
        return itemCount
    }

    override fun insertData(position: Int, data: Any) {
        this.dataList.add(position, data)
        notifyItemInserted(position)
    }

    override fun insertData(position: Int, dataList: List<Any>) {
        this.dataList.addAll(position, dataList)
        notifyItemRangeInserted(position, dataList.size)
    }

    override fun removeData(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun removeData(position: Int, count: Int) {
        for (i in 1..count) {
            this.dataList.removeAt(position)
        }
        notifyItemRangeRemoved(position, count)
    }
}