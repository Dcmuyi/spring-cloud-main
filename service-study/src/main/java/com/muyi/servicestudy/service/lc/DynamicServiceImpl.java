package com.muyi.servicestudy.service.lc;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import java.util.*;

/**
 * @author Muyi, dcmuyi@qq.com
 * @desc dynamic demo for LC
 * @date 2019-04-17.
 */
@Slf4j
public class DynamicServiceImpl {

    public static void main(String[] args) {
        log.info("===begin===");
//        int[] ints = new int[] {1,5,2,5,3,8,31};open

        int[][] intArr = new int[3][3];
        intArr[0] = new int[]{1,3,1};
        intArr[1] = new int[]{1,5,1};
        intArr[2] = new int[]{4,2,1};
        //new int[]{2, 1, 3}, new int[]{4, 2, 3}, 4, 3

        log.info("======" + (new DynamicServiceImpl()).longestPalindromeSubseq("sdad"));
    }

    /**
     * 最长回文子序列：dp[i][j]->子串s[i...j]的最长回文序列长度，dp[i][i]=1
     * @param s
     * @return
     */
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        for (int l = 0; l < n; l++) {
            dp[l][l] = 1;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(j) == s.charAt(i)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                }
            }
        }

        int index=0;
        for (int[] d : dp) {
            log.info(index+++":" + JSONObject.toJSONString(d));
        }

        return dp[0][n - 1];
    }

    /**
     * 最短路径：dp[m][n]->从grid[0][0]移动到grid[m][n]的最短距离
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        for (int l = 0; l<grid.length; l++) {
            Arrays.fill(dp[l], -1);
        }
        dp[0][0] = grid[0][0];

        return minPathRe(grid, m-1, n-1, dp);
    }

    public int minPathRe(int[][] grid, int m, int n, int[][] dp) {
        if (m<0 || n<0) {
            return Integer.MAX_VALUE;
        }

        if (dp[m][n] != -1) {
            return dp[m][n];
        }

        dp[m][n] = Math.min(minPathRe(grid, m-1, n, dp), minPathRe(grid, m, n-1, dp)) + grid[m][n];
        return dp[m][n];
    }

    /**
     * 最大子数组和：dp[i]->以num[i-1]结尾的子数组和
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length + 1];
        dp[1] = nums[0];
        int res = dp[1];
        for (int i = 2; i <= nums.length; i++) {
            dp[i] = Math.max(nums[i - 1], dp[i - 1] + nums[i - 1]);
            res = Math.max(res, dp[i]);
        }

        return res;
    }

    /**
     * lcs：dp[i][j]->s1[0...i]与s2[0...j]的lcs长度
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int[][] memo = new int[text1.length() + 1][text2.length() + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        for (int i = 0; i <= text1.length(); i++) {
            memo[i][0] = 0;
        }
        for (int i = 0; i <= text2.length(); i++) {
            memo[0][i] = 0;
        }

        return lcsDown(text1, text1.length(), text2, text2.length(), memo);
    }

    //自下向上lcs
    public int lcsDown(String s1, int m, String s2, int n, int[][] memo) {
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    memo[i][j] = 1 + memo[i - 1][j - 1];
                } else {
                    memo[i][j] = Math.max(memo[i - 1][j], memo[i][j - 1]);
                }
            }
        }

        return memo[m][n];
    }

    //自顶向下lcs
    public int lcsUp(String s1, int m, String s2, int n, int[][] memo) {
        if (memo[m][n] != -1) {
            return memo[m][n];
        }

        if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
            memo[m][n] = 1 + lcsUp(s1, m - 1, s2, n - 1, memo);
        } else {
            memo[m][n] = Math.max(lcsUp(s1, m - 1, s2, n, memo), lcsUp(s1, m, s2, n - 1, memo));
        }

        return memo[m][n];
    }

    /**
     * 距离编辑：dp[i][j]：str1[0...i]与str2[0...j]的最短距离
     *
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    //dp[i-1][j] 增加  dp[i][j-1] 删除  dp[i-1][j-1] 替换
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }

        return dp[m][n];
    }

    /**
     * 最长递增子列；dp[i]->以nums[i]结尾的子列长度
     *
     * @param nums
     * @return
     */
    private int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length + 1];
        int res = 1;
        Arrays.fill(dp, 1);

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }

        return res;
    }

    /**
     * 自下向上跳楼梯：dp[i]->第i阶楼梯跳法（i-1和i-2阶跳法和）
     *
     * @param n
     * @return
     */
    private int jumpNew(int n) {
        if (n < 3) return n;

        int pre = 1, cur = 2;
        for (int i = 3; i <= n; i++) {
            int tmp = cur + pre;
            pre = cur;
            cur = tmp;
        }

        return cur;
    }

    /**
     * 自上向下跳楼梯：dp[i]->第i阶楼梯跳法（i-1和i-2阶跳法和）
     *
     * @param n
     * @return
     */
    private int jump(int n) {
        if (n < 0) return -1;

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        return dynamicJump(n, dp);
    }

    private int dynamicJump(Integer n, int[] dp) {
        if (dp[n] > 0) {
            return dp[n];
        }

        dp[n] = dynamicJump(n - 1, dp) + dynamicJump(n - 2, dp);

        return dp[n];
    }

    static List<String> res = new ArrayList<>();


}
























































