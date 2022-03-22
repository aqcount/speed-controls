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

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.*;

public class CurrencyAmountTest {
    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency JPY = Currency.getInstance("JPY");

    @Test
    public void shouldRejectNullCurrency() {
        // when
        ThrowingCallable tc = () -> CurrencyAmount.of(null, BigDecimal.TEN);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("currency is marked non-null but is null");
    }

    @Test
    public void shouldRejectNullAmount() {
        // when
        ThrowingCallable tc = () -> CurrencyAmount.of(USD, null);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("amount is marked non-null but is null");
    }

    @Test
    public void shouldComputeUnitsForUsd() {
        // given
        CurrencyAmount sut = CurrencyAmount.of(USD, BigDecimal.valueOf(1.15d));

        // when
        long units = sut.computeCurrencyUnits();

        // then
        assertThat(units).isEqualTo(115);
    }

    @Test
    public void shouldComputeUnitsForJpy() {
        // given
        CurrencyAmount sut = CurrencyAmount.of(JPY, BigDecimal.valueOf(192d));

        // when
        long units = sut.computeCurrencyUnits();

        // then
        assertThat(units).isEqualTo(192);
    }

    @Test
    public void shouldThrowIfOverflows() {
        // given
        CurrencyAmount sut = CurrencyAmount.of(USD, new BigDecimal("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"));

        // when
        ThrowingCallable tc = () -> sut.computeCurrencyUnits();

        // then
        assertThatThrownBy(tc).isInstanceOf(ArithmeticException.class);
    }

}
