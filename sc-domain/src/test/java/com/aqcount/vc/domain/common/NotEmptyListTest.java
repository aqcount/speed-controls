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

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.ThrowingCallable;

public class NotEmptyListTest {
    @Test
    public void shouldNotAllowEmptyConstruction() {
        // when
        ThrowingCallable tc = () -> new NotEmptyList<>(List.of());

        // then
        assertThatThrownBy(tc).isInstanceOf(IllegalArgumentException.class)
                              .hasMessage("list can not be empty");
    }

    @Test
    public void shouldDelegate() {
        // given
        String theString = "HELLO";

        // when
        List<String> sut = new NotEmptyList<>(List.of(theString));

        // then
        assertThat(sut.get(0)).isEqualTo(theString);
        assertThat(sut.size()).isEqualTo(1);
    }

    @Test
    public void shouldRejectNullList() {
        // when
        ThrowingCallable tc = () -> new NotEmptyList<>(null);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("list is marked non-null but is null");
    }

    @Test
    public void shouldAllowRemoveIfDoesNotLeaveEmpty() {
        // given
        List<String> sut = new NotEmptyList<>(List.of("HELLO", "THERE"));

        // when
        String removed = sut.remove(0);

        // then
        assertThat(sut.size()).isEqualTo(1);
        assertThat(removed).isEqualTo("HELLO");
    }

    @Test
    public void shouldThrowIfRemoveWouldLeaveListEmpty() {
        // given
        List<String> sut = new NotEmptyList<>(List.of("HELLO"));

        // when
        ThrowingCallable tc = () -> sut.remove(0);

        // then
        assertThatThrownBy(tc).isInstanceOf(IllegalStateException.class)
                              .hasMessage("cannot remove last element");
    }
}
