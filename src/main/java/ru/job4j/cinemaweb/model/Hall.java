package ru.job4j.cinemaweb.model;

import java.util.Map;
import java.util.Objects;

public class Hall {

    private int id;

    private String name;

    private String description;

    private int rows;

    private int columns;

    public Hall() {
    }

    public Hall(int id, String name, String description, int rows, int columns) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rows = rows;
        this.columns = columns;
    }

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "row_count", "rows",
            "place_count", "columns",
            "description", "description"
    );

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hall hall = (Hall) o;
        return id == hall.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
