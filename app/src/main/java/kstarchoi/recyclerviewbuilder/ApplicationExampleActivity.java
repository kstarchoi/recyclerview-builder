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
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.WeakHashMap;

import kstarchoi.lib.recyclerview.builder.DefaultViewBinder;
import kstarchoi.lib.recyclerview.builder.RecyclerViewBuilder;
import kstarchoi.lib.recyclerview.builder.ViewPreparer;
import kstarchoi.lib.recyclerview.builder.ViewProvider;

/**
 * @author Gwangseong Choi
 * @since 2017-07-23
 */

public class ApplicationExampleActivity extends AppCompatActivity {

    private WeakHashMap<String, Drawable> mIconDrawableMap = new WeakHashMap<>();
    private WeakHashMap<String, CharSequence> mLabelMap = new WeakHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        new RecyclerViewBuilder<ResolveInfo>(recyclerView)
                .setViewBinder(new DefaultViewBinder<ResolveInfo>() {
                    @Override
                    public int getViewLayoutRes(int viewType) {
                        return R.layout.item_application;
                    }

                    @Override
                    public void init(ViewPreparer preparer) {
                        preparer.reserve(android.R.id.icon1,
                                android.R.id.text1, android.R.id.text2);
                    }

                    @Override
                    public void bind(ViewProvider provider, int index, final ResolveInfo resolveInfo) {
                        ImageView iconView = provider.get(android.R.id.icon1);
                        iconView.setImageDrawable(getIconDrawable(resolveInfo));

                        TextView nameView = provider.get(android.R.id.text1);
                        nameView.setText(getLabel(resolveInfo));

                        TextView packageNameView = provider.get(android.R.id.text2);
                        packageNameView.setText(resolveInfo.activityInfo.packageName);

                        provider.getRoot().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(resolveInfo);
                            }
                        });
                    }
                })
                .addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
                .build(getCategoryLauncherResolveInfoList());
    }

    private List<ResolveInfo> getCategoryLauncherResolveInfoList() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return getPackageManager().queryIntentActivities(intent, 0);
    }

    private Drawable getIconDrawable(ResolveInfo resolveInfo) {
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        String activityName = activityInfo.name;
        if (mIconDrawableMap.containsKey(activityName)) {
            return mIconDrawableMap.get(activityName);
        }

        Drawable iconDrawable = activityInfo.loadIcon(getPackageManager());
        mIconDrawableMap.put(activityName, iconDrawable);
        return iconDrawable;
    }

    private CharSequence getLabel(ResolveInfo resolveInfo) {
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        String activityName = activityInfo.name;
        if (mLabelMap.containsKey(activityName)) {
            return mLabelMap.get(activityName);
        }

        CharSequence label = activityInfo.loadLabel(getPackageManager());
        mLabelMap.put(activityName, label);
        return label;
    }

    private void startActivity(ResolveInfo resolveInfo) {
        String packageName = resolveInfo.activityInfo.packageName;
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        startActivity(intent);
    }
}
