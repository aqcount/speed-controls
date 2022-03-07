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

import com.aqcount.vc.domain.common.Name;
import com.aqcount.vc.domain.common.NotEmptyList;
import com.aqcount.vc.domain.common.PositiveNumber;
import com.aqcount.vc.domain.common.Token;
import com.aqcount.vc.domain.counter.CounterId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import static org.mockito.Mockito.*;

public class SpeedControlTest {
    private static final Name TEST_NAME = Name.of("NAME");
    private static final Token TEST_TOKEN = Token.random();
    private static final BigDecimal TEST_AMOUNT = BigDecimal.TEN;
    private static final Window TEST_WINDOW = spy(Window.LIFETIME);
    private static final Currency TEST_CURRENCY = Currency.getInstance("USD");
    private static final NotEmptyList<AuthorizationType> TEST_AUTHORIZATION_TYPES = new NotEmptyList<>(List.of(AuthorizationType.CASHBACK));
    private static final MerchantScope TEST_MERCHANT_SCOPE = mock(MerchantScope.class);
    private static final Association TEST_ASSOCIATION_SPECIFICATION = mock(Association.class);
    private static final UsageLimit TEST_USAGE_LIMIT = UsageLimit.NONE;
    private static final Token TEST_USER_TOKEN = Token.random();

    @Test
    public void shouldThrowIfBuiltWithNoToken() {
        // when
        ThrowingCallable tc = () -> buildTestSpeedControl(null, TEST_NAME, BigDecimal.TEN, TEST_WINDOW, TEST_CURRENCY, TEST_AUTHORIZATION_TYPES, TEST_MERCHANT_SCOPE, TEST_ASSOCIATION_SPECIFICATION, TEST_USAGE_LIMIT);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("token is marked non-null but is null");
    }

    @Test
    public void shouldThrowIfBuiltWithNoName() {
        // when
        ThrowingCallable tc = () -> buildTestSpeedControl(TEST_TOKEN, null, TEST_AMOUNT, TEST_WINDOW, TEST_CURRENCY, TEST_AUTHORIZATION_TYPES, TEST_MERCHANT_SCOPE, TEST_ASSOCIATION_SPECIFICATION, TEST_USAGE_LIMIT);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("name is marked non-null but is null");
    }

    @Test
    public void shouldThrowIfBuiltWithNoAmount() {
        // when
        ThrowingCallable tc = () -> buildTestSpeedControl(TEST_TOKEN, TEST_NAME, null, TEST_WINDOW, TEST_CURRENCY, TEST_AUTHORIZATION_TYPES, TEST_MERCHANT_SCOPE, TEST_ASSOCIATION_SPECIFICATION, TEST_USAGE_LIMIT);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("amount is marked non-null but is null");
    }

    @Test
    public void shouldThrowIfNullUsageLimit() {
        // when
        ThrowingCallable tc = () -> buildTestSpeedControl(TEST_TOKEN, TEST_NAME, TEST_AMOUNT, TEST_WINDOW, TEST_CURRENCY, TEST_AUTHORIZATION_TYPES, TEST_MERCHANT_SCOPE, TEST_ASSOCIATION_SPECIFICATION, null);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("usageLimit is marked non-null but is null");
    }

    @Test
    public void shouldThrowIfNoWindow() {
        // when
        ThrowingCallable tc = () -> buildTestSpeedControl(TEST_TOKEN, TEST_NAME, TEST_AMOUNT, null, TEST_CURRENCY, TEST_AUTHORIZATION_TYPES, TEST_MERCHANT_SCOPE, TEST_ASSOCIATION_SPECIFICATION, TEST_USAGE_LIMIT);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("window is marked non-null but is null");
    }

    @Test
    public void shouldThrowIfBuiltWithNoCurrency() {
        // when
        ThrowingCallable tc = () -> buildTestSpeedControl(TEST_TOKEN, TEST_NAME, TEST_AMOUNT, TEST_WINDOW, null, TEST_AUTHORIZATION_TYPES, TEST_MERCHANT_SCOPE, TEST_ASSOCIATION_SPECIFICATION, TEST_USAGE_LIMIT);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("currency is marked non-null but is null");
    }


    @Test
    public void shouldThrowIfBuiltWithNullAuthorizationTypes() {
        // when
        ThrowingCallable tc = () -> buildTestSpeedControl(TEST_TOKEN, TEST_NAME, TEST_AMOUNT, TEST_WINDOW, TEST_CURRENCY, null, TEST_MERCHANT_SCOPE, TEST_ASSOCIATION_SPECIFICATION, TEST_USAGE_LIMIT);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("authorizationTypes is marked non-null but is null");
    }

    @Test
    public void shouldThrowIfBuiltWithNullMerchantScope() {
        // when
        ThrowingCallable tc = () -> buildTestSpeedControl(TEST_TOKEN, TEST_NAME, TEST_AMOUNT, TEST_WINDOW, TEST_CURRENCY, TEST_AUTHORIZATION_TYPES, null, TEST_ASSOCIATION_SPECIFICATION, TEST_USAGE_LIMIT);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("merchantScope is marked non-null but is null");
    }

    @Test
    public void shouldThrowIfBuiltWithNullAssociation() {
        // when
        ThrowingCallable tc = () -> buildTestSpeedControl(TEST_TOKEN, TEST_NAME, TEST_AMOUNT, TEST_WINDOW, TEST_CURRENCY, TEST_AUTHORIZATION_TYPES, TEST_MERCHANT_SCOPE, null, TEST_USAGE_LIMIT);

        // then
        assertThatThrownBy(tc).isInstanceOf(NullPointerException.class)
                              .hasMessage("association is marked non-null but is null");
    }

    @Test
    public void shouldComputeCounterId() {
        // given
        AuthorizationRequest mockAuthorizationRequest = createMockCompatibleAuthorizationRequest();

        Temporal expectedTemporal = LocalDate.now();
        when(TEST_WINDOW.computeTemporalNowUtc()).thenReturn(expectedTemporal);

        // when
        Optional<CounterId> maybeCounterId = buildTestSpeedControl(TEST_TOKEN, TEST_NAME, TEST_AMOUNT, TEST_WINDOW, TEST_CURRENCY, TEST_AUTHORIZATION_TYPES, TEST_MERCHANT_SCOPE, TEST_ASSOCIATION_SPECIFICATION, TEST_USAGE_LIMIT)
                .computeCounterId(mockAuthorizationRequest);

        // then
        assertThat(maybeCounterId).hasValueSatisfying(counterId -> {
            assertThat(counterId).extracting("speedControlToken", "userToken", "version", "temporal")
                                 .containsExactly(TEST_TOKEN, TEST_USER_TOKEN, PositiveNumber.of(1), expectedTemporal);
        });
    }

    //

    private SpeedControl buildTestSpeedControl(Token token,
                                               Name name,
                                               BigDecimal amount,
                                               Window window,
                                               Currency currency,
                                               NotEmptyList<AuthorizationType> authorizationTypes,
                                               MerchantScope merchantScope,
                                               Association association,
                                               UsageLimit usageLimit) {
        return SpeedControl.builder()
                           .token(token)
                           .name(name)
                           .amount(amount == null ? null : PositiveNumber.of(amount))
                           .window(window)
                           .currency(currency)
                           .authorizationTypes(authorizationTypes)
                           .merchantScope(merchantScope)
                           .association(association)
                           .usageLimit(usageLimit)
                           .build();
    }


    private AuthorizationRequest createMockCompatibleAuthorizationRequest() {
        AuthorizationRequest mockAuthorizationRequest = mock(AuthorizationRequest.class);

        when(mockAuthorizationRequest.getCurrency()).thenReturn(TEST_CURRENCY);
        when(mockAuthorizationRequest.getAuthorizationType()).thenReturn(TEST_AUTHORIZATION_TYPES.get(0));
        when(TEST_MERCHANT_SCOPE.compatibleWith(mockAuthorizationRequest)).thenReturn(true);
        when(TEST_ASSOCIATION_SPECIFICATION.compatibleWith(mockAuthorizationRequest)).thenReturn(true);
        when(mockAuthorizationRequest.getUserToken()).thenReturn(TEST_USER_TOKEN);

        return mockAuthorizationRequest;
    }
}
