package us.byeol.sarbob;

import java.util.ArrayList;

public class Week extends ArrayList<Day> {

    private final double budget;

    public Week(double budget) {
        super();
        this.budget = budget;
    }

    public double getWeekCost() {
        double total = 0;
        for (Day day : this)
            for (Shift shift : day)
                if (shift.getEmployee() != null)
                    total += shift.getLength() * shift.getEmployee().getHourly();
        return total;
    }

    public double getBudget() {
        return this.budget;
    }

}
