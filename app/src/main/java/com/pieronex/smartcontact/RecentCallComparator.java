package com.pieronex.smartcontact;

/**
 * Created by win.thitiwat on 11/20/2014.
 */



import java.util.Comparator;

public class RecentCallComparator implements Comparator{
    @Override
    public int compare(Object obj1, Object obj2) {
        Account acc1 = (Account) obj1;
        Account acc2 = (Account) obj2;

        return String.valueOf(acc1.getStat_of_search()).compareTo(String.valueOf(acc2.getStat_of_search()));
    }
}
