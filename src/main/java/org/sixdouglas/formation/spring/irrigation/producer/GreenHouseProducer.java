package org.sixdouglas.formation.spring.irrigation.producer;

import org.sixdouglas.formation.spring.irrigation.Drop;
import org.sixdouglas.formation.spring.irrigation.Dropper;
import org.sixdouglas.formation.spring.irrigation.GreenHouse;
import org.sixdouglas.formation.spring.irrigation.Row;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class GreenHouseProducer {
    private static final List<GreenHouse> greenHouses = List.of(GreenHouse.builder()
                .id(1)
                .name("In-House plants")
                .row(Row.builder()
                        .id(1)
                        .name("A")
                        .dropper(Dropper.builder().id(1).name("I-A-1").build())
                        .dropper(Dropper.builder().id(2).name("I-A-2").build())
                        .dropper(Dropper.builder().id(3).name("I-A-3").build())
                        .dropper(Dropper.builder().id(4).name("I-A-4").build())
                        .build())
                .row(Row.builder()
                        .id(2)
                        .name("B")
                        .dropper(Dropper.builder().id(1).name("I-B-1").build())
                        .dropper(Dropper.builder().id(2).name("I-B-2").build())
                        .dropper(Dropper.builder().id(3).name("I-B-3").build())
                        .build())
                .build(),
                GreenHouse.builder()
                .id(2)
                .name("Bamboos")
                .row(Row.builder()
                        .id(1)
                        .name("A")
                        .dropper(Dropper.builder().id(1).name("B-A-1").build())
                        .dropper(Dropper.builder().id(2).name("B-A-2").build())
                        .dropper(Dropper.builder().id(3).name("B-A-3").build())
                        .dropper(Dropper.builder().id(4).name("B-A-4").build())
                        .dropper(Dropper.builder().id(5).name("B-A-5").build())
                        .dropper(Dropper.builder().id(6).name("B-A-6").build())
                        .dropper(Dropper.builder().id(7).name("B-A-7").build())
                        .build())
                .build(),
                GreenHouse.builder()
                .id(3)
                .name("Fruit trees")
                .row(Row.builder()
                        .id(1)
                        .name("A")
                        .dropper(Dropper.builder().id(1).name("F-A-1").build())
                        .dropper(Dropper.builder().id(4).name("F-A-4").build())
                        .build())
                .row(Row.builder()
                        .id(2)
                        .name("B")
                        .dropper(Dropper.builder().id(1).name("F-B-1").build())
                        .dropper(Dropper.builder().id(2).name("F-B-2").build())
                        .dropper(Dropper.builder().id(3).name("F-B-3").build())
                        .build())
                .row(Row.builder()
                        .id(3)
                        .name("C")
                        .dropper(Dropper.builder().id(1).name("F-C-1").build())
                        .dropper(Dropper.builder().id(2).name("F-C-2").build())
                        .dropper(Dropper.builder().id(2).name("F-C-2").build())
                        .dropper(Dropper.builder().id(3).name("F-C-3").build())
                        .build())
                .build());


    public static Flux<Drop> getDrops() {
        List<Flux<Drop>> fluxes = new ArrayList<>();

        greenHouses.forEach(greenHouse -> {
            greenHouse.getRows().forEach(row -> {
                row.getDroppers().forEach(dropper -> {
                    Flux<Drop> map = Flux.interval(Duration.ofMillis(10))
                            .log(null, Level.FINE)
                            .onBackpressureDrop()
                            .map(i -> Drop.builder().greenHouseId(greenHouse.getId()).rowId(row.getId()).dropperId(dropper.getId()).broken(false)
                                    .instant(Instant.now()).build());
                    fluxes.add(map);
                });
            });
        });

        return Flux.merge(fluxes);
    }

    private static Mono<Drop> buildDrop(GreenHouse greenHouse, Row row, Dropper dropper) {
        return Mono.just(Drop.builder()
                .greenHouseId(greenHouse.getId())
                .rowId(row.getId())
                .dropperId(dropper.getId())
                .instant(Instant.now())
                .build());
    }
}
