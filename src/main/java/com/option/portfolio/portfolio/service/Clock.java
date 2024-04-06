package com.option.portfolio.portfolio.service;

import java.time.Instant;

public interface Clock {

    Instant getNow();

    Instant getInitDateTime();
}
