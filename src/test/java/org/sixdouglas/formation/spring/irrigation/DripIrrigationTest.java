package org.sixdouglas.formation.spring.irrigation;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class DripIrrigationTest {

    private DripIrrigation dripIrrigation;

    @BeforeEach
    public void setup() {
        dripIrrigation = new DripIrrigation();
    }

    @Test
    void testFollowDrops() {
        Flux<Drop> dropFlux = dripIrrigation
            .followDrops()
            .take(5)
            .timeout(Duration.ofMillis(105));

        StepVerifier.create(dropFlux)
            .assertNext(drop -> {
                assertEquals(
                    1,
                    drop.greenHouseId(),
                    "Greenhouse ID should be 1"
                );
                assertEquals(1, drop.rowId(), "Row ID should be 1");
                assertEquals(1, drop.dropperId(), "Dropper ID should be 1");
                assertNotNull(
                    drop.uuid(),
                    "Drop UUID should looks like '01234567-9ABC-DEF0-1234-56789ABCDEF0'"
                );
                assertNotNull(drop.instant(), "Instant should not be null");
                Instant nowInstant = Instant.now();
                assertTrue(
                    nowInstant.isAfter(drop.instant()),
                    "Instant should be before now"
                );
                Instant truncatedNowInstant = nowInstant.truncatedTo(
                    ChronoUnit.MILLIS
                );
                assertTrue(
                    truncatedNowInstant.toEpochMilli() -
                            drop
                                .instant()
                                .truncatedTo(ChronoUnit.MILLIS)
                                .toEpochMilli() <=
                        300,
                    "Instant [" +
                        drop.instant().truncatedTo(ChronoUnit.MILLIS) +
                        "] should be less than 300 milli-seconds appart from now [" +
                        truncatedNowInstant +
                        "]"
                );
            })
            .expectNextCount(4)
            .expectComplete()
            .verify();
    }
}
