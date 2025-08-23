package com.bankapp.exchange.config;

import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "exchange")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ExchangeRateProperties {

    Map<String, RateRange> rateRanges;

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class RateRange {
        int min;
        int max;
    }

} 