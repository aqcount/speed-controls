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
import lombok.NonNull;
import lombok.Value;

/**
 * Models the concern(s) that a {@link SpeedControl} is associated with.
 */
public interface Association {
    boolean compatibleWith(AuthorizationRequest authorizationRequest);

    /**
     * An association based on a {@code user token}.
     */
    @Value(staticConstructor = "of")
    class UserToken implements Association {
        @NonNull
        Token userToken;

        @Override
        public boolean compatibleWith(AuthorizationRequest authorizationRequest) {
            return this.userToken.equals(authorizationRequest.getUserToken());
        }
    }

    /**
     * An association based on a {@code card product token}.
     */
    @Value(staticConstructor = "of")
    class CardProductToken implements Association {
        @NonNull
        Token cardProductToken;

        @Override
        public boolean compatibleWith(AuthorizationRequest authorizationRequest) {
            return this.cardProductToken.equals(authorizationRequest.getCardProductToken());
        }
    }

    /**
     * An association based on a {@code program short code}.
     */
    @Value(staticConstructor = "of")
    class ProgramShortCode implements Association {
        @NonNull
        String programShortCode;

        @Override
        public boolean compatibleWith(AuthorizationRequest authorizationRequest) {
            return this.programShortCode.equals(authorizationRequest.getProgramShortCode());
        }
    }
}
