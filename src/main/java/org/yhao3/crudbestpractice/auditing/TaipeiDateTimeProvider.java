package org.yhao3.crudbestpractice.auditing;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

@Component
public class TaipeiDateTimeProvider implements DateTimeProvider {

    /**
     * (non-Javadoc)
     * @see org.springframework.data.auditing.DateTimeProvider#getNow()
     */
    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(Instant.now().plus(8, ChronoUnit.HOURS));
    }
}