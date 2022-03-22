package com.aqcount.vc.domain.counter;

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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class CounterSliceTest {
    @Test
    public void shouldReserveWhenRemaining() {
        // given
        CounterSlice sut = new CounterSlice(10);

        // when
        boolean reserved = sut.tryReserve(5);

        // then
        assertThat(reserved).isTrue();
    }

    @Test
    public void shouldNotReserveMoreThanInitiallyRemaining() {
        // given
        CounterSlice sut = new CounterSlice(10);

        // when
        boolean reserved = sut.tryReserve(15);

        // then
        assertThat(reserved).isFalse();
    }

    @Test
    public void shouldReserveAllInitiallyRemaining() {
        // given
        CounterSlice sut = new CounterSlice(10);

        // when
        boolean reserved = sut.tryReserve(10);

        // then
        assertThat(reserved).isTrue();
    }

    @Test
    public void shouldNotIncrementallyReserveMoreThanRemaining() {
        // given
        CounterSlice sut = new CounterSlice(10);
        sut.tryReserve(10);

        // when
        boolean reserved = sut.tryReserve(5);

        // then
        assertThat(reserved).isFalse();
    }
}
