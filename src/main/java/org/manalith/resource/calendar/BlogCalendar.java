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
public class BlogCalendar {
    private List<Year> years = null;
    
    public void add(Year year){
        if(years == null) years = new ArrayList<Year>();
        years.add(year);
        Collections.sort(years);
    }
    
    public Year get(int i){
        return (Year) years.get(i);
    }
    
    public List<Year> getYears(){
        return years;
    }
}
