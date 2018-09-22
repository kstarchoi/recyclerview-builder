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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Gwangseong Choi
 * @since 2018-09-22
 */

final class ReflectionHelper {

    private ReflectionHelper() {
    }


    static <T> T getGenericInterfaceParameterType(Object object) {
        return getGenericInterfaceParameterType(object, 0, 0);
    }

    @SuppressWarnings("unchecked")
    static <T> T getGenericInterfaceParameterType(Object object, int interfaceIndex, int argIndex) {
        Type[] interfaces = object.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) interfaces[interfaceIndex];
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        return (T) typeArguments[argIndex];
    }
}
