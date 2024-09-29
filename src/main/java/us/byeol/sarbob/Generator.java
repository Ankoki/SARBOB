package us.byeol.sarbob;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Generator {

    public static Week generateWeek(Week week, Employee... employeeArr) {
        List<Employee> employees = Arrays.stream(employeeArr)
                .sorted(Comparator.comparing((emp -> emp.getHoursScheduled() - emp.getContractedHours())))
                .collect(Collectors.toList());
        Collections.shuffle(week);
        for (Day day : week) {
            for (Shift shift : day) {
                for (Employee employee : employees) {
                    if (!employee.getGuaranteed().isEmpty()) {
                        for (String guaranteed : employee.getGuaranteed()) {
                            if (guaranteed.equalsIgnoreCase(day + " " + shift.getStart() + "-" + shift.getEnd())) {
                                shift.assign(employee, day);
                                break;
                            }
                        }
                    }
                }
                for (Employee employee : employees) {
                    if (shift.getEmployee() != null)
                        continue;
                    if (employee.isAtMaxHours())
                        continue;
                    if (employee.canWork(shift, day, false)) {
                        shift.assign(employee, day);
                        break;
                    }
                }
                employees = employees.stream()
                        .sorted(Comparator.comparing((emp -> emp.getHoursScheduled() - emp.getContractedHours())))
                        .collect(Collectors.toList());
                Collections.shuffle(employees);
                if (shift.getEmployee() == null) {
                    for (Employee employee : employees) {
                        if (employee.canWork(shift, day, true)) {
                            shift.assign(employee, day);
                            break;
                        }
                    }
                }
            }
        }
        return week;
    }

}