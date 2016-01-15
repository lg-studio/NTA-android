package com.usinformatics.nytrip.ui.excercises;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by D1m11n on 16.07.2015.
 */
public class LinkedMap<K, V> extends LinkedHashMap<K, V>
{

    public V getValue(int i)
    {

        Map.Entry<K, V>entry = this.getEntry(i);
        if(entry == null) return null;

        return entry.getValue();
    }

    public Map.Entry<K, V> getEntry(int i)
    {
        // check if negetive index provided
        Set<Entry<K,V>> entries = entrySet();
        int j = 0;

        for(Map.Entry<K, V>entry : entries)
            if(j++ == i)return entry;

        return null;

    }

}
