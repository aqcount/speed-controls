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

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class FourDigitStringTest {
    @Nested
    @DisplayName("from int")
    class FromInt {

        @Test
        public void shouldThrowIfBelow0() {
            // when
            ThrowingCallable tc = () -> FourDigitString.of(-1);

            // then
            assertThatThrownBy(tc).isInstanceOf(IllegalArgumentException.class)
                                  .hasMessage("must be >= 0 and <= 9999: -1");
        }

        @Test
        public void shouldThrowIfAbove9999() {
            // when
            ThrowingCallable tc = () -> FourDigitString.of(10000);

            // then
            assertThatThrownBy(tc).isInstanceOf(IllegalArgumentException.class)
                                  .hasMessage("must be >= 0 and <= 9999: 10000");
        }

        @Test
        public void shouldWorkForUniverse() {
            // given
            List<String> collected = new ArrayList<>();

            // when
            for (int i = 0; i < 10000; i++) {
                collected.add(FourDigitString.of(i)
                                             .toString());
            }

            // then
            for (int i = 0; i < 10000; i++) {
                assertThat(parseInt(collected.get(i))).isEqualTo(i);
            }
        }
    }

    @Nested
    @DisplayName("from String")
    class FromString {
        @Test
        public void shouldParse() {
            // given
            String s = "0123";

            // when
            FourDigitString sut = FourDigitString.of(s);

            // then
            assertThat(sut.getString()).isEqualTo("0123");
        }

        @Test
        public void shouldThrowOnNullString() {
            // when
            ThrowingCallable tc = () -> FourDigitString.of(null);

            // then
            assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                                  .hasMessage("string is marked non-null but is null");
        }
    }
}
