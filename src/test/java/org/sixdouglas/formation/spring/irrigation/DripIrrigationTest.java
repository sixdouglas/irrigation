package org.sixdouglas.formation.spring.irrigation;

import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DripIrrigationTest {

    private DripIrrigation dripIrrigation;
    private Pattern pattern = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

    @BeforeEach
    public void setup() {
         dripIrrigation = new DripIrrigation();
    }

    @Test
    @Order(1)
    void testFollowDrops() {


        Flux<Drop> dropFlux = dripIrrigation.followDrops()
                .limitRequest(5)
                .timeout(Duration.ofMillis(600));


        VirtualTimeScheduler virtualTimeScheduler = VirtualTimeScheduler.create();

        StepVerifier.withVirtualTime(() -> dropFlux, () -> virtualTimeScheduler, 5)
                .thenAwait(Duration.ofMillis(10000))
                .assertNext(drop -> {
            assertEquals(1, drop.getGreenHouseId(), "Greenhouse ID should be 1");
            assertEquals(1, drop.getRowId(), "Row ID should be 1");
            assertEquals(1, drop.getDropperId(), "Dropper ID should be 1");
            assertTrue(pattern.matcher(drop.getUuid()).matches(), "Drop UUID should looks like '01234567-9ABC-DEF0-1234-56789ABCDEF0'");
            assertNotNull(drop.getInstant(), "Instant should not be null");
            Instant nowInstant = Instant.now();
            assertTrue(nowInstant.isAfter(drop.getInstant()), "Instant should be before now");
            Instant truncatedNowInstant = nowInstant.truncatedTo(ChronoUnit.MILLIS);
            assertTrue(truncatedNowInstant.toEpochMilli() - drop.getInstant().truncatedTo(ChronoUnit.MILLIS).toEpochMilli() <= 300, "Instant [" + drop.getInstant().truncatedTo(ChronoUnit.MILLIS) + "] should be less than 300 milli-seconds appart from now [" + truncatedNowInstant + "]");
        })
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    @Order(2)
    void followDropper() {

        Flux<Drop> dropFlux = dripIrrigation.followDropper(2, 1, 6)
                .limitRequest(8)
                .timeout(Duration.ofMillis(400));


        VirtualTimeScheduler virtualTimeScheduler = VirtualTimeScheduler.create();

        StepVerifier.withVirtualTime(() -> dropFlux, () -> virtualTimeScheduler, 8)
                .thenAwait(Duration.ofMillis(10000))
                .assertNext(drop -> {
            assertEquals(2, drop.getGreenHouseId(), "Greenhouse ID should be 2");
            assertEquals(1, drop.getRowId(), "Row ID should be 1");
            assertEquals(6, drop.getDropperId(), "Dropper ID should be 6");
            assertTrue(pattern.matcher(drop.getUuid()).matches(), "Drop UUID should looks like '01234567-9ABC-DEF0-1234-56789ABCDEF0'");
            assertNotNull(drop.getInstant(), "Instant should not be null");
            Instant nowInstant = Instant.now();
            assertTrue(nowInstant.isAfter(drop.getInstant()), "Instant should be before now");
            Instant truncatedNowInstant = nowInstant.truncatedTo(ChronoUnit.MILLIS);
            assertTrue(truncatedNowInstant.toEpochMilli() - drop.getInstant().truncatedTo(ChronoUnit.MILLIS).toEpochMilli() <= 300, "Instant [" + drop.getInstant().truncatedTo(ChronoUnit.MILLIS) + "] should be less than 300 milli-seconds appart from now [" + truncatedNowInstant + "]");
        })
                .expectNextCount(7)
                .verifyComplete();
    }

}