package us.byeol.sarbob;

import mx.kenzie.argo.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try (final Json json = new Json(Main.class.getResourceAsStream("/data.json"))) {
            Map<String, Object> root = json.toMap();
            List<Employee> employees = new ArrayList<>();
            List<Map<String, Object>> raw = (List<Map<String, Object>>) root.get("employees");
            for (Map<String, Object> map : raw) {
                employees.add(new Employee((String) map.get("full-name"),
                        (String) map.get("employee-id"),
                        (int) map.get("contracted-hours"),
                        (List<String>) map.getOrDefault("unavailable", new ArrayList<String>()),
                        (List<String>) map.getOrDefault("guaranteed", new ArrayList<String>()),
                        (double) map.get("hourly"),
                        ((List<String>) map.getOrDefault("tags", new ArrayList<String>())).toArray(new String[0])));
            }
            Week week = Generator.generateWeek(Shift.getWeekOutline(), employees.toArray(new Employee[0]));
            for (int i = 0; i < week.size(); i++) {
                int numeration = 0;
                Day day = null;
                while (true) {
                    day = week.get(numeration);
                    if (DayEnum.valueOf(day.toString().toUpperCase()).ordinal() == i)
                        break;
                    else
                        numeration++;
                }
                System.out.println();
                String dayString = day.toString();
                StringBuilder builder = new StringBuilder();
                for (String word : dayString.split(" ")) {
                    String[] split = word.split("");
                    split[0] = split[0].toUpperCase();
                    builder.append(String.join("", split)).append(" ");
                }
                builder.setLength(builder.length() - 1);
                System.out.println(builder);
                for (Shift shift : day)
                    System.out.println(shift.getEmployee() + " " + shift.getStart() + "-" + shift.getEnd() + ".");
                System.out.println();
            }
            System.out.println("£" + week.getWeekCost() + " / £" + week.getBudget());
            System.out.println();
            for (Employee employee : employees)
                System.out.println(employee + " - " + employee.getHoursScheduled() + " Hours");
        }
    }

    public enum DayEnum {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY;
    }

}