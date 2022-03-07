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

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.function.Function;

import static java.time.DayOfWeek.MONDAY;
import static java.time.ZoneOffset.UTC;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

/**
 * The window of time a {@link SpeedControl} is concerned with.
 * <p/>
 * At this time, pegged to UTC / Monday beginning of week.
 */
public enum Window {
    DAY(true,
        ZonedDateTime::toLocalDate),

    WEEK(true,
         zdt -> zdt.with(previousOrSame(MONDAY))
                   .toLocalDate()),

    MONTH(true,
          zdt -> zdt.with(firstDayOfMonth())
                    .toLocalDate()),

    LIFETIME(false,
             zdt -> LocalDate.ofInstant(Instant.ofEpochMilli(0), UTC)),

    TRANSACTION(false, x -> null);


    private final boolean appliedRetroactively;
    private final Function<ZonedDateTime, Temporal> temporalExtractor;

    Window(boolean appliedRetroactively, Function<ZonedDateTime, Temporal> temporalExtractor) {
        this.appliedRetroactively = appliedRetroactively;
        this.temporalExtractor = temporalExtractor;
    }

    public boolean isAppliedRetroactively() {
        return appliedRetroactively;
    }

    public Temporal computeTemporalNowUtc() {
        return computeTemporal(ZonedDateTime.now(UTC));
    }

    //

    Temporal computeTemporal(ZonedDateTime zdt) {
        return temporalExtractor.apply(zdt);
    }
}
