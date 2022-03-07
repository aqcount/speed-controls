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

import lombok.*;

import java.math.BigDecimal;

/**
 * An immutable {@link Number} that is positive.
 */
@Value
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PositiveNumber<T extends Number & Comparable<T>> extends Number implements Comparable<PositiveNumber<T>> {
    T number;

    public static PositiveNumber<BigDecimal> of(@NonNull BigDecimal number) {
        if (number.signum() != 1) {
            throw new IllegalArgumentException(String.format("number must be > 0: %s", number));
        }

        return new PositiveNumber<>(number);
    }

    public static PositiveNumber<Integer> of(@NonNull Integer number) {
        if (number < 1) {
            throw new IllegalArgumentException(String.format("number must be > 0: %s", number));
        }

        return new PositiveNumber<>(number);
    }

    @Override
    public int intValue() {
        return number.intValue();
    }

    @Override
    public long longValue() {
        return number.longValue();
    }

    @Override
    public float floatValue() {
        return number.floatValue();
    }

    @Override
    public double doubleValue() {
        return number.doubleValue();
    }

    @Override
    public int compareTo(PositiveNumber<T> other) {
        return this.number.compareTo(other.number);
    }
}
