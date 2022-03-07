package com.muyi.servicestudy.service.lc_new;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * linkedHashMap实现，通过accessOrder和重写removeEldestEntry方法实现
 */
class LRUCacheL extends LinkedHashMap<Integer, Integer> {
    //容量
    private int capacity;

    public LRUCacheL(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}