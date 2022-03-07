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
import lombok.Value;

import static java.lang.Integer.parseInt;

/**
 * Encapsulates a string that has four digits. Can accommodate "0000" through "9999" inclusive.
 */
@Value
public class FourDigitString {
    String string;

    public static FourDigitString of(@NonNull String string) {
        return new FourDigitString(string, parseInt(string));
    }

    public static FourDigitString of(int i) {
        return new FourDigitString(String.format("%04d", i), i);
    }

    private FourDigitString(String formatted, int i) {
        if (i < 0 || i > 9999) {
            throw new IllegalArgumentException(String.format("must be >= 0 and <= 9999: %d", i));
        }

        this.string = formatted;
    }

    @Override
    public String toString() {
        return string;
    }
}
