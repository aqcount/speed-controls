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
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.ThrowingCallable;

public class TokenTest {
    @Test
    public void shouldNotAllowNullToken() {
        // when
        ThrowingCallable tc = () -> Token.of(null);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldNotAllowIllegalCharacters() {
        // when
        ThrowingCallable tc = () -> Token.of("");

        // then
        assertThatThrownBy(tc).isInstanceOf(IllegalArgumentException.class)
                              .hasMessage("token must match regex [0-9a-zA-Z\\-]{1,36}: ''");
    }

    @Test
    public void shouldCreateRandom() {
        // given
        Token token;
        UUID theUuid = UUID.randomUUID();

        // when
        try (MockedStatic<UUID> mockUuid = Mockito.mockStatic(UUID.class)) {
            mockUuid.when(UUID::randomUUID)
                    .thenReturn(theUuid);

            token = Token.random();
        }

        // then
        assertThat(token.toString()).isEqualTo(theUuid.toString());
    }

    @Test
    public void shouldImplementEqualsContract() {
        EqualsVerifier.forClass(Token.class)
                      .verify();
    }
}
