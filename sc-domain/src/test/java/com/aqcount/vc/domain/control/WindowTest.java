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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class WindowTest {
    @Nested
    @DisplayName("DAY")
    class DAY {
        private final Window sut = Window.DAY;

        @Test
        public void shouldBeAppliedRetroactively() {
            assertThat(sut.isAppliedRetroactively()).isTrue();
        }

        @Test
        public void shouldComputeTemporalAsTodayUtc() {
            // given
            ZonedDateTime someDateTime = ZonedDateTime.parse("2022-01-01T15:00:00Z");
            LocalDate expected = LocalDate.parse("2022-01-01");

            // when
            Temporal temporal = sut.computeTemporal(someDateTime);

            // then
            assertThat(temporal).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("WEEK")
    class WEEK {
        private final Window sut = Window.WEEK;

        @Test
        public void shouldBeAppliedRetroactively() {
            assertThat(sut.isAppliedRetroactively()).isTrue();
        }

        @Test
        public void shouldComputeTemporalAsBeginningOfLastMonday() {
            // given
            ZonedDateTime someDateTime = ZonedDateTime.parse("2022-03-22T15:00:00Z"); // 22 March is Tuesday
            LocalDate expected = LocalDate.parse("2022-03-21"); // 21 March is last Monday

            // when
            Temporal temporal = sut.computeTemporal(someDateTime);

            // then
            assertThat(temporal).isEqualTo(expected);
        }

        @Test
        public void shouldComputeTemporalAsBeginningOfTodayMonday() {
            // given
            ZonedDateTime someDateTime = ZonedDateTime.parse("2022-03-21T15:00:00Z"); // 21 March is Monday
            LocalDate expected = LocalDate.parse("2022-03-21"); // 21 March is last Monday

            // when
            Temporal temporal = sut.computeTemporal(someDateTime);

            // then
            assertThat(temporal).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("MONTH")
    class MONTH {
        private final Window sut = Window.MONTH;

        @Test
        public void shouldBeAppliedRetroactively() {
            assertThat(sut.isAppliedRetroactively()).isTrue();
        }

        @Test
        public void shouldComputeTemporalAsBeginningOfMonth() {
            // given
            ZonedDateTime someDateTime = ZonedDateTime.parse("2022-03-21T15:00:00Z");
            LocalDate expected = LocalDate.parse("2022-03-01");

            // when
            Temporal temporal = sut.computeTemporal(someDateTime);

            // then
            assertThat(temporal).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("LIFETIME")
    class LIFETIME {
        private final Window sut = Window.LIFETIME;

        @Test
        public void shouldNotBeAppliedRetroactively() {
            assertThat(sut.isAppliedRetroactively()).isFalse();
        }

        @Test
        public void shouldComputeTemporalAsBeginningOfTime() {
            // given
            LocalDate expected = LocalDate.parse("1970-01-01");

            // when
            Temporal temporal = sut.computeTemporalNowUtc();

            // then
            assertThat(temporal).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("TRANSACTION")
    class TRANSACTION {
        private final Window sut = Window.TRANSACTION;

        @Test
        public void shouldNotBeAppliedRetroactively() {
            assertThat(sut.isAppliedRetroactively()).isFalse();
        }

        @Test
        public void shouldNotHaveTemporal() {
            // then
            assertThat(sut.computeTemporalNowUtc()).isNull();
        }
    }
}
