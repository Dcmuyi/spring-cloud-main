package com.muyi.servicestudy.service.lc_new;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
class LRUCache {
    //容量
    private int capacity;
    //节点
    public DoubleList head;
    public DoubleList tail;
    //map缓存数据
    public Map<Integer, DoubleList> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        //初始化
        this.head = new DoubleList(-1,-1);
        this.tail = new DoubleList(-1,-1);
        this.head.next = this.tail;
        this.tail.pre = this.head;
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            DoubleList node = cache.get(key);
            log.info(node.pre.toString());
            node.pre.next = node.next;
            node.next.pre = node.pre;
            log.info(head.toString());
            add(new DoubleList(node.key, node.val));
            return node.val;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        //节点已存在
        if (cache.containsKey(key)) {
            remove(cache.get(key));
            cache.remove(key);
        } else {
            //容量已满-删除头节点
            if (cache.size() == capacity) {
                DoubleList first = removeFirst();
                cache.remove(first.key);
            }
        }

        //重写添加到尾部和cache
        DoubleList cur = new DoubleList(key, value);
        add(cur);
        cache.put(key, cur);
    }

    //添加节点
    public void add(DoubleList node) {
        node.pre = tail.pre;
        tail.pre = node;
        node.pre.next = node;
        node.next = tail;
    }

    //删除节点
    public void remove(DoubleList node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    //删除头节点
    public DoubleList removeFirst() {
        DoubleList cur = head.next;
        head.next = head.next.next;
        return cur;
    }
}

//双向链表-简化节点删除
class DoubleList {
    public int key;
    public int val;
    public DoubleList pre;
    public DoubleList next;

    public DoubleList(int key, int val) {
        this.key = key;
        this.val = val;
        pre = null;
        next = null;
    }

    @Override
    public String toString() {
        DoubleList head = this;
        StringBuilder s = new StringBuilder();
        s.append(this.key+":"+this.val+", ");
        while (head.next != null) {
            head = head.next;
            s.append(head.key+":"+head.val+", ");
        }

        return s.toString();
    }
}