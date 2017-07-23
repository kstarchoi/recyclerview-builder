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

package kstarchoi.recyclerviewbuilder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import kstarchoi.lib.recyclerview.builder.DefaultViewBinder;
import kstarchoi.lib.recyclerview.builder.RecyclerViewBuilder;
import kstarchoi.lib.recyclerview.builder.ViewProvider;
import kstarchoi.recyclerviewbuilder.util.DummyHelper;
import kstarchoi.recyclerviewbuilder.util.StringHelper;

/**
 * @author Gwangseong Choi
 * @since 2017-07-23
 */

public class LayoutManagerExampleActivity extends AppCompatActivity {

    private List<Integer> mDataList;
    private RecyclerViewBuilder<Integer> mRecyclerViewBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        mDataList = DummyHelper.createIntegerList(100);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerViewBuilder = new RecyclerViewBuilder<>(recyclerView);

        setLinearLayoutManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.layout_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_layout_manager_linear: {
                item.setChecked(true);
                setLinearLayoutManager();
                return true;
            }
            case R.id.menu_layout_manager_grid: {
                item.setChecked(true);
                setGridLayoutManager();
                return true;
            }
            case R.id.menu_layout_manager_staggered_grid: {
                item.setChecked(true);
                setStaggeredGridLayoutManager();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void setLinearLayoutManager() {
        mRecyclerViewBuilder.setVerticalLinearLayoutManager(false)
                .setViewBinder(new DefaultViewBinder<Integer>())
                .build(mDataList);
    }

    private void setGridLayoutManager() {
        mRecyclerViewBuilder.setVerticalGridLayoutManager(2, false)
                .setViewBinder(new DefaultViewBinder<Integer>())
                .build(mDataList);
    }

    private void setStaggeredGridLayoutManager() {
        mRecyclerViewBuilder.setVerticalStaggeredGridLayoutManager(3)
                .setViewBinder(new DefaultViewBinder<Integer>() {
                    private int[] mBackgroundColors = new int[]{
                            Color.WHITE,
                            Color.LTGRAY,
                            Color.GRAY,
                            Color.DKGRAY,
                            Color.BLACK,
                    };

                    @Override
                    public void bind(ViewProvider provider, int index, Integer integer) {
                        String message = StringHelper.format("Data: %d", integer);

                        StringBuilder stringBuilder = new StringBuilder(message);
                        int repeatCount = (integer % 5);
                        for (int i = 0; i < repeatCount; i++) {
                            stringBuilder.append("\n");
                            stringBuilder.append(message);
                        }

                        TextView textView = provider.get(android.R.id.text1);
                        textView.setText(stringBuilder.toString());

                        int backgroundColor = mBackgroundColors[repeatCount];
                        if (backgroundColor == Color.BLACK) {
                            textView.setTextColor(Color.LTGRAY);
                        } else {
                            textView.setTextColor(Color.BLACK);
                        }

                        provider.getRoot().setBackgroundColor(backgroundColor);
                    }
                })
                .build(mDataList);
    }
}
