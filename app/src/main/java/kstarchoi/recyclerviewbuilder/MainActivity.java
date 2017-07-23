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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kstarchoi.lib.recyclerview.builder.DefaultViewBinder;
import kstarchoi.lib.recyclerview.builder.RecyclerViewBuilder;
import kstarchoi.lib.recyclerview.builder.ViewProvider;

/**
 * @author Gwangseong Choi
 * @since 2017-07-22
 */

public class MainActivity extends AppCompatActivity {

    private int mSelectableItemBackgroundRes = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        new RecyclerViewBuilder<ActivityInfo>(recyclerView)
                .setViewBinder(new DefaultViewBinder<ActivityInfo>() {
                    @Override
                    public void bind(ViewProvider provider, int index, ActivityInfo activityInfo) {
                        TextView textView = provider.get(android.R.id.text1);
                        textView.setText(activityInfo.getTitleResId());

                        View rootView = provider.getRoot();
                        rootView.setBackgroundResource(getSelectableItemBackgroundRes());

                        final Class<?> activityClass = activityInfo.getActivityClass();
                        rootView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(v.getContext(), activityClass);
                                startActivity(intent);
                            }
                        });
                    }
                })
                .addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
                .build(createActivityInfoList());
    }

    private List<ActivityInfo> createActivityInfoList() {
        List<ActivityInfo> activityInfoList = new ArrayList<>();
        activityInfoList.add(new ActivityInfo(R.string.example_basic, BasicExampleActivity.class));
        activityInfoList.add(new ActivityInfo(R.string.example_data_control, DataControlExampleActivity.class));
        activityInfoList.add(new ActivityInfo(R.string.example_layout_manager, LayoutManagerExampleActivity.class));
        return activityInfoList;
    }

    private int getSelectableItemBackgroundRes() {
        if (mSelectableItemBackgroundRes == -1) {
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
            mSelectableItemBackgroundRes = typedValue.resourceId;
        }

        return mSelectableItemBackgroundRes;
    }


    private class ActivityInfo {

        private int mTitleResId;
        private Class<?> mActivityClass;

        ActivityInfo(@StringRes int titleResId, Class<?> activityClass) {
            mTitleResId = titleResId;
            mActivityClass = activityClass;
        }

        int getTitleResId() {
            return mTitleResId;
        }

        Class<?> getActivityClass() {
            return mActivityClass;
        }
    }
}
