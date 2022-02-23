package com.provider.demo.service.lc_new;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.decorators.LruCache;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Muyi
 * @dep 云端技术部
 * @desc
 * @company 浙江亿咖通科技有限公司  Zhejiang e-CarX Technology Co., Ltd
 * @address 浙江省杭州市滨江区泰安路杭州印B座17楼(华联.星光大道2期西)
 * @date 2019-04-17.
 */
@Slf4j
public class Dy {
    /**
     * 1
     * 2   3
     * 6 n n 5
     */
    private static TreeNode t1 = new TreeNode(1, new TreeNode(2, new TreeNode(6), null), new TreeNode(3, null, new TreeNode(5)));
    /**
     * 5
     * 3  6
     * 2  4
     * 1
     */
    private static TreeNode t2 = new TreeNode(5, new TreeNode(3, new TreeNode(2, new TreeNode(1), null), new TreeNode(4)), new TreeNode(6));
    private static TreeNode t3 = new TreeNode(-2, null, new TreeNode(-3));
    private static ListNode l1 = new ListNode(1, new ListNode(3, new ListNode(6, new ListNode(9))));
    private static ListNode l2 = new ListNode(1, new ListNode(2, new ListNode(4, new ListNode(7))));
    private static Node n1 = new Node(4, new Node(2, new Node(1), new Node(3)), new Node(5));

    public static void main(String[] args) {
        int[][] intArr = new int[3][4];
        intArr[0] = new int[]{1, 3, 7, 11};
        intArr[1] = new int[]{2, 5, 8, 12};
        intArr[2] = new int[]{3, 6, 9, 14};
        ListNode n1 = new ListNode(1, new ListNode(3, new ListNode(6, new ListNode(9))));
        char[][] board = new char[3][4];
        board[0] = new char[]{'A', 'B', 'C', 'E'};
        board[1] = new char[]{'S', 'F', 'C', 'S'};
        board[2] = new char[]{'A', 'D', 'E', 'E'};

//        DoubleList l = new DoubleList(-1,-1);
//        DoubleList ll = l;
//        for (int i = 1; i< 5;i++) {
//            DoubleList node = new DoubleList(i, i);
//            ll.next = node;
//            node.pre = ll;
//            ll = ll.next;
//        }

        LRUCache cache = new LRUCache(3);
        cache.put(1,1);
        cache.put(2,2);
        cache.put(3,3);
        cache.put(4,4);

        log.info(cache.head.toString());
        cache.get(2);

        log.info(cache.head.toString());
//        cache.get(3);
//
//        log.info(cache.head.toString());
//        cache.get(2);
//
//        log.info(cache.head.toString());
//        cache.get(1);
//
//        log.info(cache.head.toString());
//        log.info("=="+cache.cache.size());
//        cache.put(5,5);
//        log.info(cache.head.toString());
//        cache.get(1);
//        cache.get(2);
//        cache.get(3);
//        cache.get(4);
//        cache.get(5);



        //111 11
//        log.info("======" + (new Dy()).GetUglyNumberNew(3));
//        log.info("======" + (new Dy().lowestCommonAncestorNew(t2, new TreeNode(1), new TreeNode(4)).toString()));
//        log.info("======" + JSONObject.toJSONString(new Dy().getLeastNumbersNew(new int[]{0,0,1,2,5}, 4)));
//        log.info("======" + JSONObject.toJSONString(new Dy().treeToDoublyList(n1).toString()));
//        log.info("======" + JSONObject.toJSONString(new Dy().exist(board, "ABCCEDC")));
//        log.info("======" + JSONObject.toJSONString(new Dy().reverseWords(" heelo  das  neamd   ow   ")));
//        log.info("======" + JSONObject.toJSONString(new Dy().twoSum(new int[] {2,4,7,8}, 12)));
//        log.info("======" + JSONObject.toJSONString(new Dy().getIntersectionNode(l2, l1).toString()));
//        log.info("======" + JSONObject.toJSONString(new Dy().lengthOfLongestSubstring("pwwkew")));
//        log.info("======" + (new Dy().levelOrderTree(t1).toString()));
//        log.info("======" + JSONObject.toJSONString(new Dy().isSubStructure(t1, t3)));
//        log.info("======" + (new Dy()).firstUniqChar("abaccdeff"));
//        log.info("======" + (new Dy()).minArray(new int[]{10,1,10,10,10}));
//        log.info("======" + (new Dy()).findNumberIn2DArray(intArr, 15));
//        log.info("======" + (new Dy()).missingNumber(new int[] {0,2,3,4}));
//        log.info("======" + (new Dy()).search(new int[] {1,3,5,6,7,8,8,9}, 8));
    }

    //丑数
    public int GetUglyNumberNew(int n) {
        //动态规划数组
        int[] dp = new int[n];
        dp[0] = 1;
        //定义abc分别表示2,3,5对应的丑数值
        int a = 0,b=0,c=0;
        for (int i = 1; i<n; i++) {
            //丑数必为已有丑数*2,3,5，初始为1
            int an = dp[a]*2,bn = dp[b]*3,cn = dp[c]*5;

            //取得最小数
            dp[i] = Math.min(Math.min(an, bn), cn);
            //最小值对应的是a，b，c
            if (dp[i] == an) a++;
            if (dp[i] == bn) b++;
            if (dp[i] == cn) c++;
        }

        return dp[n-1];
    }
    public int GetUglyNumber_Solution(int index) {
        int[] nums = new int[]{2,3,5};
        //最小优先队列
        PriorityQueue<Long> queue = new PriorityQueue<>();
        queue.offer(1L);
        //去重集合
        Set<Long> uniqueSet = new HashSet<>();
        //通过队列排序，每次队列值*2,3,5累计
        for (int i=0;i<index;i++) {
            Long num = queue.poll();
            if (i == index -1) return Integer.parseInt(num.toString());
            for (int nu : nums) {
                Long n = nu *num;
                if (!uniqueSet.contains(n)) {
                    queue.offer(n);
                    uniqueSet.add(n);
                }
            }
        }

        return 0;
    }

    //!!不用加减乘除做加法
    public int add(int a, int b) {
        while (b != 0) {
            int c = (a & b) << 1;  //进位
            a = a ^ b;  //保留非进位数据
            b = c;  //b作为进位参与运算，知道b=0
            log.info("a:" + a + " b:" + b);
        }

        return a;
    }


    //无符号数的二进制中1的个数
    public int hammingWeightNew(int n) {
        int res = 0;
        while (n != 0) {
            n = n & (n - 1);
            res++;
        }
        return res;
    }

    public int hammingWeight(int n) {
        int res = 0;
        while (n != 0) {
            res += n & 1;
            n = n >>> 1;
        }
        return res;
    }


    /**
     * 搜索与回溯-中等
     */
    //！！二叉树最近公共祖先
    public TreeNode lowestCommonAncestorNew(TreeNode root, TreeNode p, TreeNode q) {
        //root为空或者p、q，返回当前节点，然后判断p q是否在同一侧
        if (null == root || root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        return null == left ? right : null == right ? left : root;
    }

    //搜索树最近公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (null != root) {
            //root节点大于目标节点，公共节点在左
            if (root.val > p.val && root.val > q.val) {
                root = root.left;
            } else if (root.val < p.val && root.val < q.val) {  //root节点小于目标节点，公共节点在右
                root = root.right;
            } else {  //当前节点则为最新公共节点
                break;
            }
        }

        return root;
    }

    //求1+2+…+n 不能使用* /和条件语句
    int sum = 0;

    public int sumNums(int n) {
        Boolean x = n > 1 && sumNums(n - 1) > 0;
        sum = sum + n;

        return sum;
    }

    //是否平衡二叉树
    public boolean isBalanced(TreeNode root) {
        if (root == null) return false;

        return Math.abs(maxDepth(root.left) - maxDepth(root.right)) < 2 && isBalanced(root.left) && isBalanced(root.right);
    }

    //树的深度
    public int maxDepth(TreeNode root) {
        if (null == root) return 0;

        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }


    /**
     * 排序
     */
    //数组中最小的k个数-优先队列
    public int[] getLeastNumbersNew(int[] arr, int k) {
        Queue<Integer> A = new PriorityQueue<>((o1, o2) -> o2 - o1);

        for (int i = 0; i < arr.length; i++) {
            A.offer(arr[i]);

            if (A.size() > k) A.poll();
        }

        int[] re = new int[k];
        for (int i = 0; i < k; i++) {
            re[i] = A.poll();
        }

        return re;
    }

    //数组中最小的k个数
    public int[] getLeastNumbers(int[] arr, int k) {
        quickSort(arr, 0, arr.length - 1);

        return Arrays.copyOf(arr, k);
    }

    //扑克牌中的顺子
    public boolean isStraight(int[] nums) {
        int min = 14, max = 0;
        Set<Integer> set = new HashSet();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])) return false;
            if (nums[i] == 0) continue;
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);

            set.add(nums[i]);
        }

        return max - min < 5;
    }

    //！！排序
    public int[] arraySort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
//        Arrays.sort(nums);

        return nums;
    }

    //快排
    public void quickSort(int[] nums, int l, int r) {
        if (l >= r) return;
        //定义i、j和对比值
        int i = l, j = r;
        int tmp = nums[l], tm;
        while (i < j) {
            //遍历2边，跳过符合条件部分
            while (nums[j] >= tmp && i < j) j--;
            while (nums[i] <= tmp && i < j) i++;

            //交换
            tm = nums[i];
            nums[i] = nums[j];
            nums[j] = tm;
        }

        //将对比值和num[i]交换，达成排序
        nums[l] = nums[i];
        nums[i] = tmp;


        //左右继续递归
        quickSort(nums, l, i - 1);
        quickSort(nums, i + 1, r);
    }


    //把数组排成最小数
    public String minNumber(int[] nums) {
        String[] list = new String[nums.length];
        //转成字符串数组-方便拼接
        for (int i = 0; i < nums.length; i++) {
            list[i] = Integer.toString(nums[i]);
        }

        //安装拼接大小排序
        Arrays.sort(list, ((o1, o2) -> (o1 + o2).compareTo(o2 + o1)));

        //重建
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < list.length; i++) {
            s.append(list[i]);
        }

        return s.toString();
    }


    /**
     * 搜索回溯-中等
     */
    //！！二叉搜索树转双向链表
    Node head, pre;

    public Node treeToDoublyList(Node root) {
        if (null == root) return null;
        //dfs遍历
        dfsDou(root);
        //遍历后head为头，pre为尾，建立连接
        head.left = pre;
        pre.right = head;

        return head;
    }

    public void dfsDou(Node node) {
        if (null == node) return;

        //从小到大：先递归left
        dfsDou(node.left);
        //首节点-定义head
        if (null == pre) {
            head = node;
        } else {  //建立前节点和后节点关联
            pre.right = node;
        }

        node.left = pre;
        //pre后移
        pre = node;

        //递归右
        dfsDou(node.right);
    }

    //二叉搜索树的第k大节点
    int th, res = 0;

    public int kthLargest(TreeNode root, int k) {
        this.th = k;
        dfsLar(root);
        return res;
    }

    public void dfsLar(TreeNode node) {
        if (node == null || th < 1) return;

        dfsLar(node.right);
        if (th == 1) {
            res = node.val;
        }
        th--;
        dfsLar(node.left);

    }

    //！！二叉树中和为target的路径
    List<List<Integer>> resSum = new ArrayList<>();
    int target;

    public List<List<Integer>> pathSum(TreeNode root, int target) {
        this.target = target;
        LinkedList<Integer> path = new LinkedList<>();

        dfsSum(root, path, 0);

        return resSum;
    }

    //递归
    public void dfsSum(TreeNode node, LinkedList<Integer> path, int sum) {
        log.info("sum:" + sum + " pa:" + path);
        if (node == null) return;

        sum += node.val;
        //val=负数情况下不可用
//        if (sum > target) {
//            return;
//        }
        path.add(node.val);
        if (node.left == null && node.right == null && sum == target) {
            //回溯时需要新建list存放，否则会随着path变化
            resSum.add(new LinkedList<>(path));
        }

        dfsSum(node.left, path, sum);
        dfsSum(node.right, path, sum);
        path.removeLast();
    }

    //！！可移动步数
    int m, n, k;
    public int movingCount(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        Boolean[][] visit = new Boolean[m][n];
        log.info("" + visit[0][0]);
        return dfsMov(0, 0, visit);
    }

    //计算ij数位和
    public static int movSum(int i, int j) {
        int sumI = i % 10 + i / 10;
        int sumJ = j % 10 + j / 10;
        return sumI + sumJ;
    }

    //dfs遍历
    public int dfsMov(int i, int j, Boolean[][] visit) {
        if (i >= m || j >= n || null != visit[i][j] || movSum(i, j) > k) return 0;

        visit[i][j] = true;
        return dfsMov(i + 1, j, visit) + dfsMov(i, j + 1, visit) + 1;
    }

    //！！矩阵路径-判断矩阵中是否存在单词
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        //遍历所有节点，若找到则返回
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfsExist(board, word, i, j, 0)) return true;
            }
        }

        return false;
    }

    public Boolean dfsExist(char[][] board, String word, int i, int j, int k) {
        //超出边界||不相等 直接返回
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word.charAt(k)) {
            return false;
        }

        if (k == word.length() - 1) return true;

        board[i][j] = ' ';
        if (dfsExist(board, word, i - 1, j, k + 1) || dfsExist(board, word, i + 1, j, k + 1)
                || dfsExist(board, word, i, j - 1, k + 1) || dfsExist(board, word, i, j + 1, k + 1)) return true;

        board[i][j] = word.charAt(k);

        return false;
    }


    /**
     * 双指针
     *
     * @param s
     * @return
     */
    //单词反转
    public String reverseWords(String s) {
        s = s.trim();
        String[] ss = s.split(" ");
        int l = ss.length - 1;
        StringBuilder stringBuilder = new StringBuilder();
        while (l >= 0) {
            if (!ss[l].equals("")) {
                stringBuilder.append(ss[l]).append(" ");
            }

            l--;
        }

        return stringBuilder.toString().trim();
    }

    //数组中和为s的2个数
    public int[] twoSum(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        while (i < j) {
            int dif = target - nums[i];
            while (dif < nums[j]) j--;
            if (dif == nums[j]) break;

            i++;
        }

        return new int[]{nums[i], nums[j]};
    }

    //数组奇数偶数排序-双指针同时遍历替换
    public int[] exchange(int[] nums) {
        int size = nums.length;
        //创建双指针-双端同时遍历
        int i = 0, j = size - 1, tmp;
        while (i < j) {
            while ((i < j) && (nums[j] & 1) == 0) j--;
            while ((i < j) && (nums[i] & 1) == 1) i++;

            tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }

        return nums;
    }

    //！！链表第一个公共节点-双指针，走完自己走对方，直到相遇或者最终null
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode A = headA, B = headB;
        while (A != B) {
            A = A == null ? headB : A.next;
            B = B == null ? headA : B.next;
        }

        return A;
    }

    //合并2个有序链表-以l1为基础，头插法遍历l2
    public ListNode mergeTwoListsNew(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }

        ListNode head = new ListNode(0);
        head.next = l1;
        //定义l1和中间链表
        ListNode cur = head, tmp;
        while (l2 != null) {
            //l1结束
            if (cur.next == null) {
                cur.next = l2;
                break;
            }

            if (cur.next.val <= l2.val) {
                cur = cur.next;
            } else {
                //头插
                tmp = l2.next;
                l2.next = cur.next;
                cur.next = l2;
                //next
                cur = cur.next;
                l2 = tmp;
            }
        }

        return head.next;
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(), tmp;
        ListNode re = head;

        while (l1 != null || l2 != null) {
            if (null == l2 || (l1 != null && l1.val <= l2.val)) {
                tmp = l1.next;
                l1.next = null;
                re.next = l1;
                re = re.next;
                l1 = tmp;
            } else {
                tmp = l2.next;
                l2.next = null;
                re.next = l2;
                re = re.next;
                l2 = tmp;
            }
        }

        return head.next;
    }

    //倒数第k个节点
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode pre = head;
        ListNode cur = pre;
        while (k-- > 0) {
            cur = cur.next;
        }

        while (cur != null) {
            cur = cur.next;
            pre = pre.next;
        }

        return pre;
    }

    //删除链表节点
    public ListNode deleteNode(ListNode head, int val) {
        if (head.val == val) {
            return head.next;
        }

        ListNode cur = head.next, pre = head;
        while (null != cur) {
            //双指针跳过删除节点
            if (cur.val == val) {
                pre.next = cur.next;
            }

            cur = cur.next;
            pre = pre.next;
        }

        return head;
    }


    /**
     * 动态规划
     *
     * @param s
     * @return
     */
    //最长不重复字符串
    public int lengthOfLongestSubstring(String s) {
        int res = 0, tmp = 0;
        //定义map存储字符上一次位置
        HashMap<Character, Integer> map = new HashMap<>();
        //遍历，判断该字符结尾的长度，并与上个字符的长度比较
        for (int j = 0; j < s.length(); j++) {
            char cur = s.charAt(j);
            int i = map.getOrDefault(cur, -1);
            tmp = tmp < j - i ? tmp + 1 : j - i;
            res = Math.max(res, tmp);
            map.put(cur, j);
        }

        return res;
    }

    //二维数组最长路线
    public int maxValue(int[][] grid) {
        //定义dp数组并初始化
        int h = grid.length, w = grid[0].length;
        int[][] dp = new int[h][w];
        dp[0][0] = grid[0][0];

        for (int i = 1; i < h; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int j = 1; j < w; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < h; i++) {
            for (int j = 1; j < w; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[h - 1][w - 1];
    }

    //最大子数组和
    public int maxSubArray(int[] nums) {
        //定义节点sum和result
        int sum = nums[0], res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            //要么继承前节点最大，要么重新开始
            sum = Math.max(nums[i], sum + nums[i]);
            //对比确认历史最大值
            res = Math.max(res, sum);
        }

        return res;
    }

    //股票的最大利润
    public int maxProfit(int[] prices) {
        //无法完成交易
        if (prices.length < 2) {
            return 0;
        }

        int[] dp = new int[prices.length];
        dp[0] = 0;
        int max = 0;

        for (int i = 1; i < prices.length; i++) {
            dp[i] = dp[i - 1] + (prices[i] - prices[i - 1]);
            dp[i] = Math.max(dp[i], 0);
            max = Math.max(dp[i], max);
        }

        return max;
    }

    public int maxProfitNew(int[] prices) {
        int profit = 0, result = 0;
        for (int i = 1; i < prices.length; i++) {
            profit = profit + (prices[i] - prices[i - 1]);
            profit = Math.max(profit, 0);

            result = Math.max(profit, result);
        }

        return result;
    }

    //跳台阶
    public int numWays(int n) {
        int a = 1, b = 1, sum;
        for (int i = 0; i < n; i++) {
            sum = (a + b) % 1000000007;
            a = b;
            b = sum;
        }

        return a;
    }

    //斐波那契数列
    public int fib(int n) {
        if (n < 2) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % 1000000007;
        }

        return dp[n];
    }


    /**
     * 树
     *
     * @param root
     * @return
     */
    //是否对称树
    public boolean isSymmetric(TreeNode root) {
        if (null == root) {
            return true;
        }

        return syRec(root.left, root.right);
    }

    //对称树递归
    public boolean syRec(TreeNode left, TreeNode right) {
        if (null == left && null == right) return true;

        if (null == left || null == right || left.val != right.val) return false;

        return syRec(left.left, right.right) && syRec(left.right, right.left);

    }

    //树的镜像
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        mirrorTree(root.left);
        mirrorTree(root.right);
        return root;
    }

    //树的子结构
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (null == A || null == B) {
            return false;
        }

        return subRec(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    //判断是否子结构
    public boolean subRec(TreeNode A, TreeNode B) {
        if (null == B) {
            return true;
        }

        if (null == A || A.val != B.val) {
            return false;
        }

        return subRec(A.left, B.left) && subRec(A.right, B.right);
    }

    //层级遍历树-按层/2返回
    public List<List<Integer>> levelOrderTree(TreeNode root) {
        if (null == root) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        LinkedList<TreeNode> nodeList = new LinkedList() {{
            add(root);
        }};
        int i = 1;
        while (!nodeList.isEmpty()) {
            List<Integer> tmp = new ArrayList<>();
            LinkedList<TreeNode> tmpNode = new LinkedList<>();
            while (!nodeList.isEmpty()) {
                TreeNode treeNode = nodeList.poll();
                tmp.add(treeNode.val);

                if ((i & 1) == 1) {
                    if (null != treeNode.left) tmpNode.push(treeNode.left);
                    if (null != treeNode.right) tmpNode.push(treeNode.right);
                } else {
                    if (null != treeNode.right) tmpNode.push(treeNode.right);
                    if (null != treeNode.left) tmpNode.push(treeNode.left);
                }
            }

            result.add(tmp);
            i++;
            nodeList = tmpNode;
        }

        return result;
    }

    //层级遍历树-按层返回
    public List<List<Integer>> levelOrderTwo(TreeNode root) {
        if (null == root) {
            return new ArrayList<>();
        }

        ArrayList<List<Integer>> arrayList = new ArrayList();
        LinkedList<TreeNode> list = new LinkedList() {{
            add(root);
        }};
        while (!list.isEmpty()) {
            //新建list循环处理，原list作为下级节点
            LinkedList<TreeNode> lineList = new LinkedList<>();
            ArrayList<Integer> array = new ArrayList();
            while (!list.isEmpty()) {
                TreeNode node = list.poll();
                array.add(node.val);

                if (null != node.left) lineList.add(node.left);
                if (null != node.right) lineList.add(node.right);
            }
            arrayList.add(array);
            list = lineList;
        }

        return arrayList;
    }

    //层级遍历树
    public int[] levelOrder(TreeNode root) {
        if (null == root) {
            return new int[0];
        }

        ArrayList<Integer> res = new ArrayList<>();
        LinkedList<TreeNode> treeNodes = new LinkedList() {{
            add(root);
        }};
        while (!treeNodes.isEmpty()) {
            TreeNode node = treeNodes.poll();

            res.add(node.val);
            //add: add last, push: add first
            if (null != node.left) treeNodes.add(node.left);
            if (null != node.right) treeNodes.add(node.right);
        }

        log.info(JSONObject.toJSONString(res));
        int[] result = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            result[i] = res.get(i);
        }

        return result;
    }

    /**
     * 查找算法
     *
     * @param s
     * @return
     */
    //第一个只出现一次的字符
    public char firstUniqChar(String s) {
        int[] map = new int[26];
        int n = s.length();
        for (int i = 0; i < n; i++) {
            map[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < n; i++) {
            if (map[s.charAt(i) - 'a'] == 1) {
                return s.charAt(i);
            }
        }

        return ' ';
    }

    //旋转数组的最小值 3452333
    public int minArray(int[] numbers) {
        int l = 0, r = numbers.length - 1, m;
        while (l < r) {
            m = (l + r) / 2;
            log.info("l:" + l + " r:" + r);
            if (numbers[m] > numbers[r]) {
                l = m + 1;
            } else if (numbers[m] < numbers[r]) {
                r = m;
            } else {
                r--;
            }
        }

        log.info("l:" + l + " r:" + r);
        return numbers[l];
    }

    //二维数组中的查找
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int i = matrix.length - 1, j = 0;
        while (i >= 0 && j < matrix[0].length) {
            if (matrix[i][j] == target) {
                return true;
            }

            if (matrix[i][j] > target) {
                i--;
            } else {
                j++;
            }
        }

        return false;
    }

    //0～n-1中缺失的数字 01245
    public int missingNumber(int[] nums) {
        int l = 0, r = nums.length - 1, m;

        while (l <= r) {
            m = (l + r) / 2;
            if (nums[m] == m) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        return l;
    }

    //在排序数组中查找数字 I
    public int search(int[] nums, int target) {
        //二分
        int l = 0, r = nums.length - 1, m;
        while (l <= r) {
            m = (l + r) / 2;
            if (nums[m] <= target) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        int right = l;

        l = 0;
        r = nums.length - 1;
        while (l <= r) {
            m = (l + r) / 2;
            if (nums[m] < target) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        int left = r;

        return right - left - 1;
    }

    //找到重复的数字
    public int findRepeatNumber(int[] nums) {
        int[] map = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (map[nums[i]] == 1) {
                return nums[i];
            }

            map[nums[i]] = 1;
        }

        return -1;
    }


    /**
     * 高级
     *
     * @param s
     * @param n
     * @return
     */
    //左旋转字符串
    public String reverseLeftWords(String s, int n) {
        return s.substring(n) + s.substring(0, n);
    }

    //字符串替换
    public String replaceSpace(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                sb.append("%20");
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    //最长公共子序列
    public int lcs(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i <= n; i++) {
            dp[0][i] = 0;
        }

        for (int j = 1; j <= m; j++) {
            for (int k = 1; k <= n; k++) {
                if (text1.charAt(j - 1) == text2.charAt(k - 1)) {
                    dp[j][k] = dp[j - 1][k - 1] + 1;
                } else {
                    dp[j][k] = Math.max(dp[j - 1][k], dp[j][k - 1]);
                }
            }
        }

        return dp[m][n];
    }

    //编辑距离
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i <= n; i++) {
            dp[0][i] = i;
        }
        log.info("==" + JSONObject.toJSONString(dp));


        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1])) + 1;
                }
            }
        }
        log.info("==" + JSONObject.toJSONString(dp));
        return dp[m][n];
    }

    //最长递增子序列
    private int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length + 1];

        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            for (int k = 0; k <= i; k++) {
                if (nums[i] > nums[k]) {
                    dp[i] = Math.max(dp[i], dp[k] + 1);
                }
            }
        }

        log.info("===" + JSONObject.toJSONString(dp));
        return 1;
    }
}

class ListNode {
    int val;
    ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

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