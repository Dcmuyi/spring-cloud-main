package com.muyi.servicestudy.service.lc;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Muyi, dcmuyi@qq.com
 * @desc Array demo for LC
 * @date 2019-04-17.
 */
@Slf4j
public class ArrayServiceImpl {
    public static void main(String[] args) throws Exception {
        int[][] grid = new int[4][5];
        grid[0] = new int[]{0,1,0,0,0};
        grid[1] = new int[]{1,1,1,0,0};
        grid[2] = new int[]{0,1,0,0,0};
        grid[3] = new int[]{1,1,0,0,0};

        int[][] grid2 = new int[4][5];
        grid2[0] = new int[]{1,0,1,1,1};
        grid2[1] = new int[]{0,1,0,0,0};
        grid2[2] = new int[]{1,0,1,0,1};
        grid2[3] = new int[]{1,0,1,1,0};

        log.info("======result======" + new ArrayServiceImpl().islandPerimeter(grid));
    }











    /**
     * 岛屿周长：找到岛屿，dfs遍历判断周边海水长度
     * @param grid
     * @return
     */
    public int islandPerimeter(int[][] grid) {
        int m = grid.length, n = grid[0].length, result = 0;
        //遍历
        for (int i =0;i<m;i++) {
            for (int j =0; j<n;j++) {
                if (grid[i][j] == 1) {
                    //统计岛屿周长
                    result = dfsLength(grid, i, j);
                }
            }
        }

        return result;
    }

    /**
     * 子岛屿数量：先遍历清除非子岛屿，再统计岛屿数量
     * @param grid1
     * @param grid2
     * @return
     */
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length, n = grid1[0].length, result = 0;
        //遍历
        for (int i =0;i<m;i++) {
            for (int j =0; j<n;j++) {
                if (grid1[i][j] == 0 && grid2[i][j] == 1) {
                    //清除非子岛屿：grid2岛屿&grid1非岛屿
                    dfsClear(grid2, i, j);
                }
            }
        }

        //清理后遍历统计grid2岛屿
        for (int i =0;i<m;i++) {
            for (int j =0; j<n;j++) {
                if (grid2[i][j] == 1) {
                    //统计岛屿数量
                    result++;
                    dfsClear(grid2, i, j);
                }
            }
        }

        return result;
    }

    /**
     * 岛屿最大面积：统计方法与岛屿数量一致，区别在清除岛屿时返回岛屿面积
     * @param grid
     * @return
     */
    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length, n = grid[0].length, result = 0;
        //遍历
        for (int i =0;i<m;i++) {
            for (int j =0; j<n;j++) {
                if (grid[i][j] == 1) {
                    //统计岛屿面积
                    result = Math.max(dfsClear(grid, i, j), result);
                }
            }
        }

        return result;
    }

    /**
     * 闭合岛屿：先清除边界岛屿，然后与岛屿数量方案一致
     * @param grid
     * @return
     */
    public int closedIsland(int[][] grid) {
        int m = grid.length, n = grid[0].length, result = 0;
        //清空左右
        for (int i = 0; i < m; i ++) {
            dfsClear(grid,i, 0);
            dfsClear(grid, i,n-1);
        }

        //清空上下
        for (int i = 0; i < n; i ++) {
            dfsClear(grid,0, i);
            dfsClear(grid,m-1, i);
        }

        //遍历
        for (int i =0;i<m;i++) {
            for (int j =0; j<n;j++) {
                if (grid[i][j] == 1) {
                    result++;
                    //清除
                    dfsClear(grid, i, j);
                }
            }
        }

        return result;
    }

    /**
     * 岛屿数量：遍历二位数组，遇到岛屿则记录并通过dfs消除本岛屿数据
     * @param grid
     * @return
     */
    int numIslands(int[][] grid) {
        int m = grid.length, n = grid[0].length, result = 0;
        //遍历
        for (int i =0;i<m;i++) {
            for (int j =0; j<n;j++) {
                if (grid[i][j] == 1) {
                    result++;
                    //清除
                    dfsClear(grid, i, j);
                }
            }
        }

        return result;
    }

    /**
     * dfs清除岛屿
     * @param grid
     * @param i
     * @param j
     */
    public int dfsClear(int[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            return 0;
        }

        if (grid[i][j] == 0) {
            return 0;
        }

        grid[i][j] = 0;
        return dfsClear(grid, i-1, j) + dfsClear(grid, i, j-1)
                + dfsClear(grid, i, j+1) + dfsClear(grid, i+1, j) + 1;
    }

    /**
     * dfs求周长：判断上下左右若为海则周长+1，否则+0
     * @param grid
     * @param i
     * @param j
     * @return
     */
    public int dfsLength(int[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n || grid[i][j] == 0) {
            return 1;
        }

        if (grid[i][j] == 2) {
            return 0;
        }

        grid[i][j] = 2;
        return dfsLength(grid, i-1, j) + dfsLength(grid, i, j-1)
                + dfsLength(grid, i, j+1) + dfsLength(grid, i+1, j);
    }

    /**
     * find two value in #{nums} who`s sum is #{target}
     * @param nums
     * @param target
     * @return
     */
    private static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            Integer search = target - nums[i];
            if (map.containsKey(search)) {
                return new int[]{map.get(search), i};
            }

            map.put(nums[i], i);
        }

        return null;
    }
}
