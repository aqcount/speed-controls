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

import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * A (String) token that satisfies {@value #REGEX}.
 */
@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class Token {
    static final String REGEX = "[0-9a-zA-Z\\-]{1,36}";
    static final Pattern PATTERN = Pattern.compile(REGEX);

    String token;

    public static Token random() {
        UUID randomUuid = UUID.randomUUID();

        return Token.of(randomUuid.toString());
    }

    public Token(@NonNull String token) {
        if (!PATTERN.matcher(token)
                    .matches()) {
            throw new IllegalArgumentException(String.format("token must match regex %s: '%s'", REGEX, token));
        }

        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }
}
