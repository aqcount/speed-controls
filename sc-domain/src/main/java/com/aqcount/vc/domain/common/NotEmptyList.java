package com.aqcount.vc.domain.common;

/*-
 * #%L
 * sc-domain
 * %%
 * Copyright (C) 2022 Aqcount
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import lombok.NonNull;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A {@link List}. That is guaranteed never to be empty.
 */
public class NotEmptyList<E> extends AbstractList<E> {
    private final List<E> delegate;

    public NotEmptyList(List<E> list) {
        this(list, ArrayList::new);
    }

    public NotEmptyList(@NonNull List<E> list, Supplier<List<E>> listConstructor) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("list can not be empty");
        }

        delegate = listConstructor.get();
        delegate.addAll(list);
    }

    @Override
    public E get(int index) {
        return delegate.get(index);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public E remove(int index) {
        if (size() == 1) {
            throw new IllegalStateException("cannot remove last element");
        }

        return delegate.remove(index);
    }
}
