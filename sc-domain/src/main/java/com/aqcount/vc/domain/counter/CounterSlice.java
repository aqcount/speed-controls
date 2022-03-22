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

import com.aqcount.vc.domain.common.PositiveNumber;

public class CounterSlice {
    private int remaining;

    public CounterSlice(int remaining) {
        this(PositiveNumber.of(remaining));
    }

    public CounterSlice(PositiveNumber<Integer> initiallyRemaining) {
        this.remaining = initiallyRemaining.intValue();
    }

    public boolean tryReserve(int reservationSize) {
        if (remaining < reservationSize) {
            return false;
        }

        remaining -= reservationSize;

        return true;
    }
}
