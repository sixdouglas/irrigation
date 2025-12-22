package org.sixdouglas.formation.spring.irrigation;

import java.time.Instant;
import java.util.UUID;

public record Drop(
    UUID uuid,
    int greenHouseId,
    int rowId,
    int dropperId,
    boolean broken,
    Instant instant
) {}
