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

import android.content.res.Resources;
import android.support.annotation.AnyRes;
import android.util.SparseArray;

import junit.framework.Assert;

import java.util.List;
import java.util.Locale;

/**
 * @author Gwangseong Choi
 * @since 2018-09-22
 */

final class AssertionHelper {

    private AssertionHelper() {
    }


    static void notNull(String name, Object object) {
        Assert.assertNotNull(String.format("%s should not be null", name), object);
    }


    static void exist(String name, @AnyRes int anyRes, Resources resources) {
        String resourceName = getResourceName(resources, anyRes);
        exist(name, resourceName != null);
    }

    static void exist(String name, boolean condition) {
        Assert.assertTrue(String.format("%s not exist", name), condition);
    }


    static void notExist(String name, Object object, List<?> baseList) {
        notExist(name, !baseList.contains(object));
    }

    static void notExist(String name, int key, SparseArray<?> sparseArray) {
        notExist(name, sparseArray.get(key) == null);
    }

    private static void notExist(String name, boolean condition) {
        Assert.assertTrue(String.format("%s already exist", name), condition);
    }

    private static String getResourceName(Resources resources, @AnyRes int resId) {
        try {
            return resources.getResourceName(resId);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }


    static void bound(String name, int key, SparseArray<?> sparseArray) {
        Assert.assertTrue(String.format("%s not bound", name), sparseArray.get(key) != null);
    }


    static void notContains(String name, Object object, Object[] baseArray) {
        boolean found = false;
        for (Object baseObject : baseArray) {
            if (found = (baseObject == object)) {
                break;
            }
        }
        notContains(name, !found);
    }

    static void notContains(String name, Object object, List<?> baseList) {
        boolean found = false;
        for (Object baseObject : baseList) {
            if (found = (baseObject == object)) {
                break;
            }
        }
        notContains(name, !found);
    }

    private static void notContains(String name, boolean condition) {
        Assert.assertTrue(String.format("%s should not be contained", name), condition);
    }


    static void greaterThanOrEqualTo(String name, int value, int baseValue) {
        String message = String.format(Locale.getDefault(),
                "%s must be ≥ %d (was %d)", name, baseValue, value);
        Assert.assertTrue(message, value >= baseValue);
    }


    static void interior(String name, int value, int low, int high) {
        String message = String.format(Locale.getDefault(),
                "%s must be %d ≤ value ≤ %d (was %d)", name, low, high, value);
        Assert.assertTrue(message, (value >= low) && (value <= high));
    }
}
