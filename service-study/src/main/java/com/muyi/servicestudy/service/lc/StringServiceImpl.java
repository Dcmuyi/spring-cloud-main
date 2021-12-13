package com.muyi.servicestudy.service.lc;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author Muyi, dcmuyi@qq.com
 * @desc String demo for LC
 * @date 2019-04-17.
 */
@Slf4j
public class StringServiceImpl {
    /**
     * 1
     * 2  8
     * 3  4
     * 2
     */
    private static TreeNode t1 = new TreeNode(1, new TreeNode(2, new TreeNode(3, new TreeNode(2), null), new TreeNode(4)), new TreeNode(8
    ));



    public static void main(String[] args) {
//        char[] result = reverseString(new char[]{'a', 'b', 'c', 'd', 'e'});
        log.info("===begin===");

        log.info("======" + (new StringServiceImpl()).preMute(new int[]{1,2,3}));
    }














    /**
     * BFS暴力遍历算法-二叉树遍历
     */
    List<List<Integer>> resBfs = new LinkedList<>();
    public List<List<Integer>> preMute(int[] nums) {
        LinkedList<Integer> path = new LinkedList<>();
        bfsRe(nums, path);

        return resBfs;
    }

    public void bfsRe(int[] nums, LinkedList<Integer> path) {
        if (path.size() == nums.length) {
            resBfs.add(new LinkedList<>(path));
            return ;
        }

        for (int i = 0; i<nums.length; i++) {
            if (path.contains(nums[i])) {
                continue;
            }

            path.addLast(nums[i]);
            bfsRe(nums, path);

            path.removeLast();
        }

    }

    /**
     * KMP算法暴力破解
     *
     * @param s
     * @param t
     * @return
     */
    public int KMP(String s, String t) {
        int[] next = getNext(t);
        int i = 0;
        int j = 0;

        while (i < s.length() && j < t.length()) {
            if (j == -1 || s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }

        if (j == t.length()) {
            return i - j;
        } else {
            return -1;
        }
    }

    /**
     * KMP算法next转换
     *
     * @param ps
     * @return
     */
    public int[] getNext(String ps) {
        char[] p = ps.toCharArray();
        int[] next = new int[p.length];
        next[0] = -1;
        int j = 0;
        int k = -1;

        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                next[++j] = ++k;
            } else {
                k = next[k];
            }
        }
        log.info("next:"+JSONObject.toJSONString(next));

        return next;
    }

    static List<String> res = new ArrayList<>();

    /**
     * 括号的生成：参考二叉树遍历，判断正确组合
     *
     * @param n
     * @return
     */
    private List<String> generateParenthesis(int n) {
        if (n <= 0) return null;

        def("", 0, 0, n);

        return res;
    }

    public static void def(String paths, int left, int right, int n) {
        if (left > n || right > left) {
            return;
        }

        if (paths.length() == n * 2) {
            res.add(paths);
            return;
        }

        def(paths + "(", left + 1, right, n);
        def(paths + ")", left, right + 1, n);
    }

    /**
     * 最长回文字符串：遍历，注意aabb和aba格式
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        int i = 0, size = s.length();
        int start = 0, length = 0;
        while (i < size) {
            int min = i, max = i + 1;
            while (min >= 0 && max < size) {
                if (s.charAt(min) == s.charAt(max)) {
                    if (max - min > length) {
                        length = max - min;
                        start = min;
                    }

                    min--;
                    max++;
                } else {
                    break;
                }
            }

            min = i - 1;
            max = i + 1;
            while (min >= 0 && max < size) {
                if (s.charAt(min) == s.charAt(max)) {
                    if (max - min > length) {
                        length = max - min;
                        start = min;
                    }

                    min--;
                    max++;
                } else {
                    break;
                }
            }

            i++;
        }

        log.info("st" + start + "=" + length);
        return s.substring(start, start + length + 1);
    }

    List<String> result = new ArrayList<>();
    LinkedList<String> paths = new LinkedList<>();

    /**
     * 遍历二叉树返回链路
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        binaryTree(root);

        return result;
    }

    public void binaryTree(TreeNode root) {
        if (root != null) {
            paths.addLast(String.valueOf(root.val));
            if (root.left == null && root.right == null) {
                String re = String.join("->", paths);
                result.add(re);
            }

            binaryTree(root.left);
            binaryTree(root.right);

            paths.removeLast();
        }

    }

    /**
     * 前序遍历二叉树
     *
     * @param root
     * @return
     */
    public static List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> preList = new ArrayList<>();
        preOrder(root, preList);

        return preList;
    }

    public static void preOrder(TreeNode root, List<Integer> preList) {
        if (root != null) {
            preList.add(root.val);
            preOrder(root.left, preList);
            preOrder(root.right, preList);
        }
    }

    /**
     * 反正字符串
     *
     * @param s
     * @return
     */
    private static char[] reverseString(char[] s) {
        int st = 0, end = s.length - 1;
        while (end > st) {
            char tmp = s[st];
            s[st] = s[end];
            s[end] = tmp;

            end--;
            st++;
        }

        return s;
    }

    /**
     * 有效的括号
     *
     * @param s
     * @return
     */
    private static boolean isValid(String s) {
        HashMap<Character, Character> hashMap = new HashMap() {{
            put('{', '}');
            put('[', ']');
            put('(', ')');
        }};
        Stack<Character> stack = new Stack<>();

        int i = 0, l = s.length();
        while (i < l) {
            Character c = s.charAt(i);
            if (hashMap.containsKey(c)) {
                stack.push(hashMap.get(c));
            } else {
                if (stack.empty() || !stack.pop().equals(c)) {
                    return false;
                }
            }

            i++;
        }

        return stack.empty();
    }

    /**
     * 最长公共子串
     *
     * @param strs
     * @return
     */
    private static String longestCommonPrefix(String[] strs) {
        String re = strs[0];
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];
            int length = Math.min(re.length(), s.length());
            int ii = 0;
            while (ii < length) {
                if (s.charAt(ii) != re.charAt(ii)) {
                    break;
                }
                ii++;
            }
            re = re.substring(0, ii);
        }

        return re;
    }

    private static HashMap<Character, Integer> romanList = new HashMap() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
        put('a', 4);
        put('b', 9);
        put('c', 40);
        put('d', 90);
        put('e', 400);
        put('f', 900);
    }};

    public static int romanToInt(String s) {
        s = s.replace("IV", "a");
        s = s.replace("IX", "b");
        s = s.replace("XL", "c");
        s = s.replace("XC", "d");
        s = s.replace("CD", "e");
        s = s.replace("CM", "f");

        int length = s.length();
        int index = 0;
        int sum = 0;
        while (index < length) {
            sum += romanList.get(s.charAt(index));
            index++;
        }

        return sum;
    }

    /**
     * 最后单词长度（空格）：
     *
     * @param s
     * @return
     */
    public static int lengthOfLastWord(String s) {
        s = s.trim();
        int last = s.length() - 1;
        int a = 0;
        while (last >= 0 && s.charAt(last) != ' ') {
            last--;
            a++;
        }

        return a;
    }

    /**
     * 无重复最长子串：遍历，map存储字符，遇重复清空
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        int start = 0, end, max = 0, n = s.length();
        HashMap<Character, Integer> hashMap = new HashMap<>();

        for (end = 0; end < n; end++) {
            Character c = s.charAt(end);
            if (hashMap.containsKey(c)) {
                start = Math.max(start, hashMap.get(c) + 1);
            }

            hashMap.put(c, end);
            max = Math.max(max, end - start + 1);
        }

        return max;
    }
}
























































