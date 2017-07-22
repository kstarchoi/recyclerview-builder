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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kstarchoi.lib.recyclerview.builder.DefaultViewBinder;
import kstarchoi.lib.recyclerview.builder.RecyclerViewBuilder;
import kstarchoi.lib.recyclerview.builder.ViewAdapter;
import kstarchoi.lib.recyclerview.builder.ViewProvider;

public class MainActivity extends AppCompatActivity {

    private int lastInteger = 5;
    private ViewAdapter<Integer> mViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < lastInteger; i++) {
            integerList.add(i);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mViewAdapter = new RecyclerViewBuilder<Integer>(recyclerView)
                .setViewBinder(new DefaultViewBinder<Integer>() {
                    @Override
                    public void bind(ViewProvider provider, int index, Integer integer) {
                        String message = String.format(Locale.getDefault(), "Data: %5d", integer);
                        TextView textView = provider.get(android.R.id.text1);
                        textView.setText(message);
                    }
                })
                .build(integerList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.data_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_data_control_insert_data: {
                mViewAdapter.insertData(0, lastInteger++);
                return true;
            }
            case R.id.menu_data_control_insert_multiple_data: {
                List<Integer> integerList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    integerList.add(lastInteger++);
                }
                mViewAdapter.insertData(0, integerList);
                return true;
            }
            case R.id.menu_data_control_remove_data: {
                mViewAdapter.removeData(0);
                return true;
            }
            case R.id.menu_data_control_remove_multiple_data: {
                mViewAdapter.removeData(0, 3);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
