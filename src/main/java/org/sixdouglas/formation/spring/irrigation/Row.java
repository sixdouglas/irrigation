package org.sixdouglas.formation.spring.irrigation;

import java.util.List;

public record Row(int id, String name, List<Dropper> droppers) {}
