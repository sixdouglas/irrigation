package org.sixdouglas.formation.spring.irrigation;

import java.time.Instant;
import java.util.UUID;

public record DetailedDrop(UUID uuid, GreenHouse greenHouse, Instant instant) {}
