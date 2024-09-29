package us.byeol.sarbob;

import java.util.ArrayList;

public class Day extends ArrayList<Shift> {

    private final String day;

    public Day(String day) {
        super();
        this.day = day;
    }

    @Override
    public String toString() {
        return this.day;
    }

}