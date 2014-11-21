package com.pieronex.smartcontact;

/**
 * Created by win.thitiwat on 11/22/2014.
 */

import java.util.Comparator;

public class PhoneNoComparator implements Comparator{
    @Override
    public int compare(Object obj1, Object obj2) {
        Account acc1 = (Account) obj1;
        Account acc2 = (Account) obj2;
        return acc1.getPhoneNo().compareTo(acc2.getPhoneNo());
    }
}
