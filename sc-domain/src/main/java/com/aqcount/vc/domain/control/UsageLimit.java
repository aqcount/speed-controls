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

import com.aqcount.vc.domain.common.PositiveNumber;
import lombok.Value;

import java.util.OptionalInt;

/**
 * Defines how many times a {@link SpeedControl}'s counter can be utilized.
 */
public interface UsageLimit {
    UsageLimit NONE = new NoUsageLimit();

    OptionalInt get();

    /**
     * There's no limit to the number of times a SpeedControl's counter can be utilized.
     */
    class NoUsageLimit implements UsageLimit {
        private NoUsageLimit() {
        
        }

        @Override
        public OptionalInt get() {
            return OptionalInt.empty();
        }
    }

    /**
     * A SpeedControl's counter can only be utilized a certain number of times.
     */
    @Value
    class IntegerUsageLimit implements UsageLimit {
        PositiveNumber<Integer> limit;

        public IntegerUsageLimit(int limit) {
            this.limit = PositiveNumber.of(limit);
        }

        @Override
        public OptionalInt get() {
            return OptionalInt.of(limit.intValue());
        }
    }
}
