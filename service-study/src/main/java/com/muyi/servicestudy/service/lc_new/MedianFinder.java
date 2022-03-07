package com.muyi.servicestudy.service.lc_new;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 数据流中的中位数-hard
 */
public class MedianFinder {
    Queue<Integer> A;
    Queue<Integer> B;
    public MedianFinder() {
        A = new PriorityQueue<>();  //小顶堆，存储较大的一半
        B = new PriorityQueue<>(Comparator.reverseOrder());  //大顶堆，存储较小的一半
    }

    public void addNum(int num) {
        if (A.size() == B.size()) {  //相等时需往A中存较大的B，通过B.offer和B.poll获取
            B.offer(num);
            A.offer(B.poll());
        } else {  //不等时需往B中添加较小的A，通过A.offer和A.poll获取
            A.offer(num);
            B.offer(A.poll());
        }
    }

    public double findMedian() {
        return A.size() == B.size() ? (A.peek()+B.peek())/2.0:A.peek();
    }
}
