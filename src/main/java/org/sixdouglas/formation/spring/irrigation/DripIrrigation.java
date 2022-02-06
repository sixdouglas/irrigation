package org.sixdouglas.formation.spring.irrigation;

import org.sixdouglas.formation.spring.irrigation.producer.GreenHouseProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;

@Component
public class DripIrrigation {
    private static Logger LOGGER = LoggerFactory.getLogger(DripIrrigation.class);

    public Flux<Drop> followDrops() {
        return Flux.interval(Duration.ofMillis(20))
                .log(null, Level.FINE)
                .map(i -> new Drop(1, 1, 1, false, Instant.now()));
    }

    public Flux<Drop> followDropper(int greenHouseId, int rowId, int dropperId) {
        Flux<Drop> drops = GreenHouseProducer.getDrops();
        return drops.log().filter(drop -> drop.getGreenHouseId() == greenHouseId && drop.getRowId() == rowId && drop.getDropperId() == dropperId);
    }
}
