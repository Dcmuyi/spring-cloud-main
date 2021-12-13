package com.muyi.servicestudy.service.lc;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;

/**
 * @author Muyi
 * @desc ListNode demo for LC
 * @date 2019-04-17.
 */
@Slf4j
public class ListNodeServiceImpl {
    private static ListNode l1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(2, new ListNode(1)))));
    private static ListNode l2 = new ListNode(4, new ListNode(5, new ListNode(5, new ListNode(2))));

    public static void main(String[] args) {
//        log.info("===add two list==="+ addTwoNumbers(l1, l2));
//        ListNode cycleList = getListNodeForCycle(l1);

        log.info("==="+isPalindrome(l2));

    }

    /**
     * 是否回文链表：快慢指针，通过fast定位中间节点，slow反转前半段链表rev，最后对比rev和slow
     * @param head
     * @return
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode fast = head;
        ListNode slow = head;
        ListNode rev = null;
        while (fast != null && fast.next!=null) {
            ListNode tmp = slow;
            slow = slow.next;
            fast = fast.next.next;

            tmp.next = rev;
            rev = tmp;
        }

        //非偶数节点需要后移
        if (fast != null) {
            slow = slow.next;
        }

        log.info("slow:"+slow);
        log.info("rev:"+rev);
        while (slow != null && rev != null) {
            if (slow.val != rev.val) {
                log.info("-=-=-=-");
                return false;
            }

            slow = slow.next;
            rev = rev.next;
        }

        return true;
    }

    /**
     * 合并2个有序链表：遍历双链表，采用虚拟节点+头插法
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }

        ListNode virtualNode = new ListNode(0, l1);
        ListNode p = virtualNode;
        while (l2 != null) {
            if (p.next == null) {
                p.next = l2;
                break;
            }

            if (p.next.val >= l2.val) {
                ListNode tmp = l2.next;
                l2.next = p.next;
                p.next = l2;
                l2 = tmp;
                p = p.next;
            } else {
                p = p.next;
            }
        }

        return virtualNode.next;
    }

    /**
     * 俩俩交换链表节点：虚拟节点+头插法，每次反转2个节点，反转后移动指针
     * @param head
     * @return
     */
    public static ListNode swapPairs(ListNode head) {
        ListNode virtualNode = new ListNode(0, head);
        ListNode g = virtualNode, p = virtualNode.next;

        while (p != null && p.next != null) {
            ListNode tmp = p.next;
            p.next = p.next.next;

            tmp.next = p;
            g.next = tmp;

            p = p.next;
            g = g.next.next;
        }

        return virtualNode.next;
    }

    /**
     * 反转链表2：虚拟节点+头插法
     * @param head
     * @param m
     * @param n
     * @return
     */
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        //虚拟节点
        ListNode virtualNode = new ListNode(0, head);

        ListNode g = virtualNode, p = virtualNode.next;
        for (int i = 0; i < m - 1; i++) {
            g = g.next;
            p = p.next;
        }

        for (int i = 0; i < n - m; i++) {
            ListNode tmp = p.next;
            p.next = p.next.next;

            tmp.next = g.next;
            g.next = tmp;
        }

        return virtualNode.next;
    }

    /**
     * 链表是否有环：快慢指针，若二指针相遇则存在环，注意空链表
     * @param head
     * @return
     */
    public static Boolean whetherCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast!= null && fast.next != null && fast.next.next!=null) {
            fast = fast.next.next;
            slow = slow.next;
            log.info(""+fast+"==="+slow);
            if (fast == slow) {
                return true;
            }
        }

        return false;
    }

    ArrayList<Integer> arrayList = new ArrayList<Integer>();
    /**
     * 链表值反转为int[]
     * @param node
     * @return
     */
    public int[] reservePrt(ListNode node) {
        revNodeToArr(node);
        int[] ints = new int[arrayList.size()];
        for (int i = 0; i< arrayList.size(); i++) {
            ints[i] = arrayList.get(i);
        }

        return ints;
    }

    /**
     * 链表反转为数组：递归反转，塞入数组
     * @param node
     * @return
     */
    public ArrayList<Integer> revNodeToArr(ListNode node) {
        if (node != null) {
            revNodeToArr(node.next);
            arrayList.add(node.val);
        }

        return arrayList;
    }

    /**
     * 删除指定值：双指针，前指针判断是否需要删除，后指针执行删除。注意删除后后指针需要移动
     * @param head
     * @param val
     * @return
     */
    private static ListNode removeElements(ListNode head, int val) {
        ListNode tmp = new ListNode(val - 1, head);

        ListNode cur = tmp.next;
        ListNode pre = tmp;
        while (null != cur) {
            if (cur.val == val) {
                pre.next = cur.next;
                cur = cur.next;
            } else {
                pre = pre.next;
                cur = cur.next;
            }
        }

        return tmp.next;
    }

    /**
     * 删除倒数第n个节点：双指针，2个指针间隔为n，当前指针到终点时后指针的next节点即为待删除节点
     * @param head
     * @param n
     * @return
     */
    private static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pre = new ListNode(0, head);
        ListNode start = pre, end = pre;

        while (n>0) {
            end = end.next;
            n--;
        }

        while (end.next != null) {
            end = end.next;
            start = start.next;
        }

        start.next = start.next.next;

        return pre.next;
    }

    /**
     * 返回倒数第k个节点：双指针，end指针后移k位，然后同时移动，end指针结束是start对应倒数K节点
     * @param head
     * @param k
     * @return
     */
    private static ListNode getKthFromEnd(ListNode head, int k) {
        ListNode end = head, start = head;

        while (k > 0) {
            end = end.next;
            k--;
        }

        while (end != null) {
            end = end.next;
            start = start.next;
        }

        return start;
    }

    /**
     * 删除节点：双指针，前指针定位节点，后指针修改next节点实现删除
     * @param head
     * @param n
     * @return
     */
    private static ListNode removeNth(ListNode head, int n) {
        if (n == 1) {
            head = head.next;
            return head;
        }

        ListNode cur = head.next;
        ListNode pre = head;
        int index = 2;
        while (null != cur) {
            if (index == n) {
                pre.next = cur.next;
                break;
            }

            index++;
            pre = pre.next;
            cur = cur.next;
        }

        return head;
    }

    /**
     * 链表反转：双指针，前指针遍历，后指针作为反转后的头节点
     * @param head
     * @return
     */
    private static ListNode reverse(ListNode head) {
        ListNode pre = null, tmp;
        ListNode cur = head;
        while (cur != null) {
            tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }

        return pre;
    }

    /**
     * 链表排序：双重循环，内循环排序，类似冒泡
     * @param head
     * @return
     */
    private static ListNode sort(ListNode head) {
        ListNode listNode = head;
        while (listNode != null) {
            ListNode listNode1 = listNode.next;
            while (listNode1 != null) {
                int tmp = listNode.val;
                listNode.val = Math.min(tmp, listNode1.val);
                listNode1.val = Math.max(tmp, listNode1.val);

                listNode1 = listNode1.next;
            }

            listNode = listNode.next;
        }

        return head;
    }

    /**
     * 添加节点：遍历链表，最后节点执行添加操作
     * @param l1
     * @return
     */
    private static ListNode put(ListNode l1) {
        ListNode listNode = l1;
        while (listNode.next != null) {
            listNode = listNode.next;
        }

        listNode.next = new ListNode(1);

        return l1;
    }

    /**
     * 链表相加：遍历链表求和，进位最多为1并向后传递，注意最后的进位需要单独存入链表
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0, sum, x, y;
        ListNode listNode = new ListNode();
        ListNode cur = listNode;
        while (null != l1 || null !=l2) {
            x = l1 != null ? l1.val : 0;
            y = l2 != null ? l2.val : 0;

            sum = x + y + carry;
            carry = sum > 9 ? 1 : 0;
            sum = sum % 10;
            cur.next = new ListNode(sum);
            cur = cur.next;

            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;
        }

        if (carry > 0) {
            cur.next = new ListNode(carry);
        }

        return listNode.next;
    }

    /**
     * 获取带环链表
     * @param head
     * @return
     */
    public static ListNode getListNodeForCycle(ListNode head) {
        ListNode tmp = head;

        while (tmp.next != null) {
            tmp = tmp.next;
        }

        tmp.next = head.next.next;

        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder(String.valueOf(val));
            ListNode tmp = next;
            int i = 50;
            while (tmp != null && i > 0) {
                s.append(",").append(tmp.val);
                tmp = tmp.next;
                i--;
            }

            return s.toString();
        }
    }
}
