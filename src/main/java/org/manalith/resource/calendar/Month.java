/*
 * Created on 2005. 5. 14
 */
package org.manalith.resource.calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author setzer
 */
public class Month implements Comparable {

    private List<Day> days = null;
    private int month;

    public Month(int month) {
        this.month = month;
    }

    public String toString() {
        return Integer.toString(month);
    }

    public int toInt() {
        return month;
    }

    public void add(Day day) {
        if (days == null) days = new ArrayList<Day>();
        days.add(day);
        Collections.sort(days);
    }

    public Day get(int i) {
        return (Day) days.get(i);
    }

    public List<Day> getDays() {
        return days;
    }

    public int compareTo(Object month) {
        int thisVal = toInt();
        int anotherVal = ((Month) month).toInt();
        return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
    }
}
