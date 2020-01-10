/*
 * Created on 2005. 5. 14
 */
package org.manalith.resource.calendar;

/**
 * @author setzer
 */
public class Day implements Comparable<Day> {

    private int day;

    public Day(int day) {
        this.day = day;
    }

    public String toString() {
        return Integer.toString(day);
    }

    public int toInt() {
        return day;
    }

    public int compareTo(Day day) {
        int thisVal = toInt();
        int anotherVal = day.toInt();
        return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
    }
}
