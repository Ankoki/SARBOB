package us.byeol.sarbob;

import mx.kenzie.argo.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Shift {

    public static Week getWeekOutline() {
        try (final Json json = new Json(Main.class.getResourceAsStream("/data.json"))) {
            Map<String, Object> root = json.toMap();
            Map<String, Object> inner = (Map<String, Object>) root.get("days");
            Week week = new Week((double) inner.get("budget"));
            for (String rawDay : "sunday.monday.tuesday.wednesday.thursday.friday.saturday".split("\\.")) {
                Map<String, Object> shifts = (Map<String, Object>) inner.get(rawDay);
                Day day = new Day(rawDay);
                for (Map<String, Object> raw : (List<Map<String, Object>>) shifts.get("shifts")) {
                    day.add(new Shift((String) raw.get("type"),
                            (double) raw.get("start"),
                            (double) raw.get("end"),
                            (double) raw.get("length"),
                            ((List<String>) raw.getOrDefault("tags", new ArrayList<String>())).toArray(new String[0])));
                }
                week.add(day);
            }
            return week;
        }
    }

    private final String type;
    private final double start;
    private final double end;
    private final double length;
    private final List<String> tags = new ArrayList<>();

    public Employee employee = null;

    public Shift(String type, double start, double end, double length, String... tags) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.length = length;
        if (tags.length > 0)
            this.tags.addAll(Arrays.asList(tags));
    }

    public String getType() {
        return type;
    }

    public double getStart() {
        return this.start;
    }

    public double getEnd() {
        return this.end;
    }

    public double getLength() {
        return this.length;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public List<String> getTags() {
        return tags;
    }

    public void markUnassigned() {
        this.employee = null;
    }

    public void assign(Employee employee, Day day) {
        this.employee = employee;
        employee.addHours(this.length, day);
    }

    @Override
    public String toString() {
        return this.start + "-" + this.end + " " + this.type;
    }
}
