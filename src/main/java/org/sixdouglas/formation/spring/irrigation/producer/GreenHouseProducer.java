package org.sixdouglas.formation.spring.irrigation.producer;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import org.sixdouglas.formation.spring.irrigation.Drop;
import org.sixdouglas.formation.spring.irrigation.Dropper;
import org.sixdouglas.formation.spring.irrigation.GreenHouse;
import org.sixdouglas.formation.spring.irrigation.Row;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public final class GreenHouseProducer {

    private static final List<GreenHouse> greenHouses = List.of(
        new GreenHouse(
            1,
            "In-House plants",
            List.of(
                new Row(
                    1,
                    "A",
                    List.of(
                        new Dropper(1, "I-A-1", false),
                        new Dropper(2, "I-A-2", false),
                        new Dropper(3, "I-A-3", false),
                        new Dropper(4, "I-A-4", false)
                    )
                ),
                new Row(
                    2,
                    "B",
                    List.of(
                        new Dropper(1, "I-B-1", false),
                        new Dropper(2, "I-B-2", false),
                        new Dropper(3, "I-B-3", false)
                    )
                )
            )
        ),
        new GreenHouse(
            2,
            "Bamboos",
            List.of(
                new Row(
                    1,
                    "A",
                    List.of(
                        new Dropper(1, "B-A-1", false),
                        new Dropper(2, "B-A-2", false),
                        new Dropper(3, "B-A-3", false),
                        new Dropper(4, "B-A-4", false),
                        new Dropper(5, "B-A-5", false),
                        new Dropper(6, "B-A-6", false),
                        new Dropper(7, "B-A-7", false)
                    )
                )
            )
        ),
        new GreenHouse(
            3,
            "Fruit trees",
            List.of(
                new Row(
                    1,
                    "A",
                    List.of(
                        new Dropper(1, "F-A-1", false),
                        new Dropper(4, "F-A-4", false)
                    )
                ),
                new Row(
                    2,
                    "B",
                    List.of(
                        new Dropper(1, "F-B-1", false),
                        new Dropper(2, "F-B-2", false),
                        new Dropper(3, "F-B-3", false)
                    )
                ),
                new Row(
                    3,
                    "C",
                    List.of(
                        new Dropper(1, "F-C-1", false),
                        new Dropper(2, "F-C-2", false),
                        new Dropper(2, "F-C-2", false),
                        new Dropper(3, "F-C-3", false)
                    )
                )
            )
        )
    );

    public static Flux<Drop> getDrops() {
        Flux<Drop> dropsFlux = Flux.empty();

        //TODO go through all Greenhouses
        //TODO    go through all Row
        //TODO       go through all Dropper
        //TODO          Create a flux that will emit a Drop every 10 millis seconds using the buildDrop() function
        //TODO          then merge this new flux int the dropsFlux

        return dropsFlux;
    }

    private static Mono<Drop> buildDrop(
        GreenHouse greenHouse,
        Row row,
        Dropper dropper
    ) {
        return Mono.just(
            new Drop(
                UUID.randomUUID(),
                greenHouse.id(),
                row.id(),
                dropper.id(),
                false,
                Instant.now()
            )
        );
    }
}
