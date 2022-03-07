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

import com.aqcount.vc.domain.common.FourDigitString;
import com.aqcount.vc.domain.common.Token;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MerchantScopeTest {
    private static final Token TEST_MERCHANT_ID_TOKEN = mock(Token.class);
    private static final FourDigitString TEST_MERCHANT_CATEGORY_CODE = mock(FourDigitString.class);
    private static final String TEST_MERCHANT_CATEGORY_CODE_GROUP = "GROUP";

    @Test
    public void shouldThrowOnNullId() {
        // when
        ThrowingCallable tc = () -> MerchantScope.Id.of(null);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("id is marked non-null but is null");
    }

    @Test
    public void shouldThrowOnNullCategoryCode() {
        // when
        ThrowingCallable tc = () -> MerchantScope.CategoryCode.of(null);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("categoryCode is marked non-null but is null");
    }


    @Test
    public void shouldThrowOnNullCategoryCodeGroup() {
        // when
        ThrowingCallable tc = () -> MerchantScope.CategoryCodeGroup.of(null);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("categoryCodeGroup is marked non-null but is null");
    }

    @Test
    public void shouldBeCompatible_MerchantId() {
        // given
        MerchantScope.Id sut = MerchantScope.Id.of(TEST_MERCHANT_ID_TOKEN);
        AuthorizationRequest mockAuthorizationRequest = createMockAuthorizationRequest(true);

        // when
        boolean matches = sut.compatibleWith(mockAuthorizationRequest);

        // then
        assertThat(matches).isTrue();
    }


    @Test
    public void shouldNotBeCompatible_MerchantId() {
        // given
        MerchantScope.Id sut = MerchantScope.Id.of(TEST_MERCHANT_ID_TOKEN);
        AuthorizationRequest mockAuthorizationRequest = createMockAuthorizationRequest(false);

        // when
        boolean matches = sut.compatibleWith(mockAuthorizationRequest);

        // then
        assertThat(matches).isFalse();
    }

    @Test
    public void shouldBeCompatible_MerchantCategoryCode() {
        // given
        MerchantScope.CategoryCode sut = MerchantScope.CategoryCode.of(TEST_MERCHANT_CATEGORY_CODE);
        AuthorizationRequest mockAuthorizationRequest = createMockAuthorizationRequest(true);

        // when
        boolean matches = sut.compatibleWith(mockAuthorizationRequest);

        // then
        assertThat(matches).isTrue();
    }


    @Test
    public void shouldNotBeCompatible_MerchantCategoryCode() {
        // given
        MerchantScope.CategoryCode sut = MerchantScope.CategoryCode.of(TEST_MERCHANT_CATEGORY_CODE);
        AuthorizationRequest mockAuthorizationRequest = createMockAuthorizationRequest(false);

        // when
        boolean matches = sut.compatibleWith(mockAuthorizationRequest);

        // then
        assertThat(matches).isFalse();
    }

    @Test
    public void shouldBeCompatible_MerchantCategoryCodeGroup() {
        // given
        MerchantScope.CategoryCodeGroup sut = MerchantScope.CategoryCodeGroup.of(TEST_MERCHANT_CATEGORY_CODE_GROUP);
        AuthorizationRequest mockAuthorizationRequest = createMockAuthorizationRequest(true);

        // when
        boolean matches = sut.compatibleWith(mockAuthorizationRequest);

        // then
        assertThat(matches).isTrue();
    }


    @Test
    public void shouldNotBeCompatible_MerchantCategoryCodeGroup() {
        // given
        MerchantScope.CategoryCodeGroup sut = MerchantScope.CategoryCodeGroup.of(TEST_MERCHANT_CATEGORY_CODE_GROUP);
        AuthorizationRequest mockAuthorizationRequest = createMockAuthorizationRequest(false);

        // when
        boolean matches = sut.compatibleWith(mockAuthorizationRequest);

        // then
        assertThat(matches).isFalse();
    }

    //

    private AuthorizationRequest createMockAuthorizationRequest(boolean deterministic) {
        AuthorizationRequest mock = mock(AuthorizationRequest.class);

        when(mock.getMerchantId()).thenReturn(deterministic ? TEST_MERCHANT_ID_TOKEN : mock(Token.class));
        when(mock.getMerchantCategoryCode()).thenReturn(deterministic ? TEST_MERCHANT_CATEGORY_CODE: mock(FourDigitString.class));
        when(mock.getMerchantCategoryCodeGroups()).thenReturn(deterministic ? List.of(TEST_MERCHANT_CATEGORY_CODE_GROUP) : emptyList());

        return mock;
    }
}
