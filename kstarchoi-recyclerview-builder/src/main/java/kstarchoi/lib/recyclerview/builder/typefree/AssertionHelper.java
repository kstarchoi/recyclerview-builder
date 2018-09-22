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


    static void notExist(String name, Object object, List<?> baseList) {
        Assert.assertTrue(String.format("%s already exist", name), !baseList.contains(object));
    }


    static void greaterThanOrEqualTo(String name, int value, int baseValue) {
        String message = String.format(Locale.getDefault(),
                "%s must be ≥ %d (was %d)", name, baseValue, value);
        Assert.assertTrue(message, value >= baseValue);
    }
}
