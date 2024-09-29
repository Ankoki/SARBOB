package us.byeol.sarbob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Employee {

    private final String fullName;
    private final String initials;
    private final String employeeId;
    private final int contractedHours; // Will go 5 either side of this.
    private final List<String> unavailable;
    private final List<String> guaranteed;
    private final double hourly;
    private final List<String> tags = new ArrayList<>();
    private final List<Day> daysWorking = new ArrayList<>();

    private double hours = 0;

    public Employee(String fullName, String employeeId, int contractedHours, List<String> unavailable, List<String> guaranteed, double hourly, String... tags) {
        this.fullName = fullName;
        this.initials = String.valueOf(Character.toUpperCase(fullName.split(" ")[0].toCharArray()[0]) + Character.toUpperCase(fullName.split(" ")[1].toCharArray()[0]));
        this.employeeId = employeeId;
        this.contractedHours = contractedHours;
        this.unavailable = unavailable;
        this.guaranteed = guaranteed;
        this.hourly = hourly;
        if (tags.length > 0)
            this.tags.addAll(Arrays.asList(tags));
    }

    public boolean canWork(Shift shift, Day day, boolean lastResort) {
        if (!lastResort)
            if (this.hours + shift.getLength() > this.contractedHours)
                return false;
        if (this.daysWorking.contains(day))
            return false;
        for (String entry : this.unavailable)
            if (entry.equalsIgnoreCase(day + " " + shift.getType()))
                return false;
        if (shift.getTags().isEmpty())
            return true;
        for (String tag : shift.getTags())
            if (!this.tags.contains(tag))
                return false;
        return true;
    }

    public List<String> getGuaranteed() {
        return this.guaranteed;
    }

    public void addHours(double amount, Day day) {
        this.hours += amount;
        if (("" + this.hours).endsWith(".3"))
            this.hours += 0.2;
        if (("" + this.hours).endsWith(".8"))
            this.hours += 0.2;
        this.daysWorking.add(day);
    }

    public double getHoursScheduled() {
        return this.hours;
    }

    public int getContractedHours() {
        return this.contractedHours;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public boolean isAtMaxHours() {
        return this.hours >= this.contractedHours;
    }

    public double getHourly() {
        return this.hourly;
    }

    @Override
    public String toString() {
        return this.fullName;
    }

}
