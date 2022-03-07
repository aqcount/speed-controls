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
import com.aqcount.vc.domain.common.Token;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class CounterIdTest {
    @Test
    public void shouldNotAllowNullVelocityControlToken() {
        // when
        ThrowableAssert.ThrowingCallable tc = () -> CounterId.builder()
                                                             .speedControlToken(null)
                                                             .userToken(mock(Token.class))
                                                             .version(mock(PositiveNumber.class))
                                                             .build();

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("speedControlToken is marked non-null but is null");
    }

    @Test
    public void shouldNotAllowNullUserToken() {
        // when
        ThrowableAssert.ThrowingCallable tc = () -> CounterId.builder()
                                                             .speedControlToken(mock(Token.class))
                                                             .userToken(null)
                                                             .version(mock(PositiveNumber.class))
                                                             .build();

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("userToken is marked non-null but is null");
    }

    @Test
    public void shouldNotAllowNullVersion() {
        // when
        ThrowableAssert.ThrowingCallable tc = () -> CounterId.builder()
                                                             .speedControlToken(mock(Token.class))
                                                             .userToken(mock(Token.class))
                                                             .version(null)
                                                             .build();

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("version is marked non-null but is null");
    }

}
