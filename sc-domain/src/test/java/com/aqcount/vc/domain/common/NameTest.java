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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class NameTest {
    @Test
    public void shouldNotAllowNullName() {
        // when
        ThrowingCallable tc = () -> Name.of(null);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldNotAllowNamesLongerThanMaxLenChars() {
        // given
        String longName = IntStream.rangeClosed(1, 256)
                                   .mapToObj(x -> "A")
                                   .collect(joining());
        // when
        ThrowingCallable tc = () -> Name.of(longName);

        // then
        assertThatThrownBy(tc).isInstanceOf(IllegalArgumentException.class)
                              .hasMessage("name length must be <= 255: 256");
    }

    @Test
    public void shouldCaptureName() {
        // given
        String theName = IntStream.rangeClosed(1, 255)
                                  .mapToObj(x -> "A")
                                  .collect(joining());

        // when
        Name name = Name.of(theName);

        // then
        assertThat(name.toString()).isEqualTo(theName);
    }

    @Test
    public void shouldImplementEqualsContract() {
        EqualsVerifier.forClass(Name.class)
                      .verify();
    }
}
