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

import com.aqcount.vc.domain.common.Token;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssociationTest {
    public static final Token TEST_USER_TOKEN = mock(Token.class);
    public static final Token TEST_CARD_PRODUCT_TOKEN = mock(Token.class);
    private static final String TEST_PROGRAM_SHORT_CODE = "PROG";

    @Test
    public void shouldThrownOnNullUserToken() {
        // when
        ThrowingCallable tc = () -> Association.UserToken.of(null);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("userToken is marked non-null but is null");
    }

    @Test
    public void shouldThrownOnNullCardProductToken() {
        // when
        ThrowingCallable tc = () -> Association.CardProductToken.of(null);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("cardProductToken is marked non-null but is null");
    }


    @Test
    public void shouldThrownOnNullProgramShortCode() {
        // when
        ThrowingCallable tc = () -> Association.ProgramShortCode.of(null);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("programShortCode is marked non-null but is null");
    }

    @Test
    public void shouldMatchUserToken() {
        // given
        Association.UserToken sut = Association.UserToken.of(TEST_USER_TOKEN);
        AuthorizationRequest mockAuthorizationRequest = createMockAuthorizationRequest(true);

        // when
        boolean matches = sut.compatibleWith(mockAuthorizationRequest);

        // then
        assertThat(matches).isTrue();
    }

    @Test
    public void shouldNotMatchUserToken() {
        // given
        Association.UserToken sut = Association.UserToken.of(TEST_USER_TOKEN);
        AuthorizationRequest mockAuthorizationRequest = createMockAuthorizationRequest(false);

        // when
        boolean matches = sut.compatibleWith(mockAuthorizationRequest);

        // then
        assertThat(matches).isFalse();
    }

    @Test
    public void shouldMatchCardProductToken() {
        // given
        Association.CardProductToken sut = Association.CardProductToken.of(TEST_CARD_PRODUCT_TOKEN);
        AuthorizationRequest mockAuthorizationRequest = createMockAuthorizationRequest(true);

        // when
        boolean matches = sut.compatibleWith(mockAuthorizationRequest);

        // then
        assertThat(matches).isTrue();
    }

    @Test
    public void shouldNotMatchCardProductToken() {
        // given
        Association.CardProductToken sut = Association.CardProductToken.of(TEST_CARD_PRODUCT_TOKEN);
        AuthorizationRequest mockAuthorizationRequest = createMockAuthorizationRequest(false);

        // when
        boolean matches = sut.compatibleWith(mockAuthorizationRequest);

        // then
        assertThat(matches).isFalse();
    }

    @Test
    public void shouldMatchProgramShortCode() {
        // given
        Association.ProgramShortCode sut = Association.ProgramShortCode.of(TEST_PROGRAM_SHORT_CODE);
        AuthorizationRequest mockAuthorizationRequest = createMockAuthorizationRequest(true);

        // when
        boolean matches = sut.compatibleWith(mockAuthorizationRequest);

        // then
        assertThat(matches).isTrue();
    }

    @Test
    public void shouldNotMatchProgramShortCode() {
        // given
        Association.ProgramShortCode sut = Association.ProgramShortCode.of(TEST_PROGRAM_SHORT_CODE);
        AuthorizationRequest mockAuthorizationRequest = createMockAuthorizationRequest(false);

        // when
        boolean matches = sut.compatibleWith(mockAuthorizationRequest);

        // then
        assertThat(matches).isFalse();
    }

    //


    private AuthorizationRequest createMockAuthorizationRequest(boolean deterministic) {
        AuthorizationRequest mockAuthorizationRequest = mock(AuthorizationRequest.class);

        when(mockAuthorizationRequest.getUserToken()).thenReturn(deterministic ? TEST_USER_TOKEN : mock(Token.class));
        when(mockAuthorizationRequest.getCardProductToken()).thenReturn(deterministic ? TEST_CARD_PRODUCT_TOKEN : mock(Token.class));
        when(mockAuthorizationRequest.getProgramShortCode()).thenReturn(deterministic ? TEST_PROGRAM_SHORT_CODE : "NOT" + TEST_PROGRAM_SHORT_CODE);

        return mockAuthorizationRequest;
    }
}
