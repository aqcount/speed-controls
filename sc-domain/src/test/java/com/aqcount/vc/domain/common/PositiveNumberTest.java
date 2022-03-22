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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.ThrowingCallable;

public class PositiveNumberTest {
    @Nested
    @DisplayName("For BigDecimal")
    class ForBigDecimal {
        @Test
        public void shouldRejectZeroValue() {
            // when
            ThrowingCallable tc = () -> PositiveNumber.of(BigDecimal.ZERO);

            // then
            assertThatThrownBy(tc).isInstanceOf(IllegalArgumentException.class)
                                  .hasMessage("number must be > 0: 0");
        }

        @Test
        public void shouldRejectNegativeValue() {
            // when
            ThrowingCallable tc = () -> PositiveNumber.of(BigDecimal.valueOf(-0.001d));

            // then
            assertThatThrownBy(tc).isInstanceOf(IllegalArgumentException.class)
                                  .hasMessage("number must be > 0: -0.001");
        }

        @Test
        public void shouldReturn() {
            // when
            PositiveNumber sut = PositiveNumber.of(BigDecimal.ONE);

            // then
            assertThat(sut.getNumber()).isEqualTo(BigDecimal.ONE);
        }
    }

    @Nested
    @DisplayName("For Integer")
    class ForMeasured {
        @Test
        public void shouldRejectZeroValue() {
            // when
            ThrowingCallable tc = () -> PositiveNumber.of(0);

            // then
            assertThatThrownBy(tc).isInstanceOf(IllegalArgumentException.class)
                                  .hasMessage("number must be > 0: 0");
        }

        @Test
        public void shouldRejectNegativeValue() {
            // when
            ThrowingCallable tc = () -> PositiveNumber.of(-1);

            // then
            assertThatThrownBy(tc).isInstanceOf(IllegalArgumentException.class)
                                  .hasMessage("number must be > 0: -1");
        }

        @Test
        public void shouldReturn() {
            // when
            PositiveNumber sut = PositiveNumber.of(1);

            // then
            assertThat(sut.getNumber()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("For all types")
    class ForAllTypes {

        @Test
        public void shouldDelegate() {
            // given
            BigDecimal control = BigDecimal.valueOf(15);

            // when
            PositiveNumber<BigDecimal> sut = PositiveNumber.of(control);

            // then
            assertThat(sut.intValue()).isEqualTo(control.intValue());
            assertThat(sut.longValue()).isEqualTo(control.intValue());
            assertThat(sut.floatValue()).isEqualTo(control.intValue());
            assertThat(sut.doubleValue()).isEqualTo(control.intValue());
        }

        @Test
        public void shouldBeComparable() {
            // given
            PositiveNumber<BigDecimal> one = PositiveNumber.of(BigDecimal.valueOf(1));
            PositiveNumber<BigDecimal> ten = PositiveNumber.of(BigDecimal.valueOf(10));

            // when
            int compareTo = one.compareTo(ten);

            // then
            assertThat(compareTo).isLessThan(0);
        }
    }
}
