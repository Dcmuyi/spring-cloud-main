package com.muyi.servicestudy.service.lc_new;

import lombok.Data;

/**
 * 双向链表
 */
@Data
public class Node {
    int val;
    Node left;
    Node right;
    public Node() {}
    public Node(int val) { this.val = val; }
    public Node(int val, Node left, Node right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(String.valueOf(val));
        Node tmp = right;
        int i = 50;
        while (tmp != null && i > 0) {
            s.append(",").append(tmp.val);
            tmp = tmp.right;
            i--;
        }

        return s.toString();
    }
}
