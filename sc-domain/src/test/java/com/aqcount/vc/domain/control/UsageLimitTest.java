package com.aqcount.vc.domain.control;

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

import com.aqcount.vc.domain.control.UsageLimit.IntegerUsageLimit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.ThrowingCallable;

public class UsageLimitTest {
    @Test
    public void shouldReturnEmpty() {
        assertThat(UsageLimit.NONE.get()).isEmpty();
    }

    @Test
    public void shouldReturnValue() {
        // given
        int controlValue = 114;

        // when
        IntegerUsageLimit sut = new IntegerUsageLimit(controlValue);

        // then
        assertThat(sut.get()).hasValue(controlValue);
    }

    @Test
    public void shouldRejectZeroValue() {
        // when
        ThrowingCallable tc = () -> new IntegerUsageLimit(0);

        // then
        assertThatThrownBy(tc).isInstanceOf(IllegalArgumentException.class)
                              .hasMessage("number must be > 0: 0");
    }

    @Test
    public void shouldRejectNegativeValue() {
        // when
        ThrowingCallable tc = () -> new IntegerUsageLimit(-1);

        // then
        assertThatThrownBy(tc).isInstanceOf(IllegalArgumentException.class)
                              .hasMessage("number must be > 0: -1");
    }
}
