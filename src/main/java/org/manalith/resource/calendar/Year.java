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
public class Year implements Comparable {
    private int year;
    private List<Month> months = null;

    public Year(int year) {
        this.year = year;
    }

    public String toString() {
        return Integer.toString(year);
    }

    public int toInt() {
        return year;
    }

    public void add(Month month) {
        if (months == null) months = new ArrayList<Month>();
        months.add(month);
        Collections.sort(months);
    }

    public Month get(int i) {
        return (Month) months.get(i);
    }

    public List<Month> getMonths() {
        return months;
    }

    public int compareTo(Object year) {
        int thisVal = toInt();
        int anotherVal = ((Year) year).toInt();
        return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
    }
}
