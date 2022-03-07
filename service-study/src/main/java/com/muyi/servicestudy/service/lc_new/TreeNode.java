package com.muyi.servicestudy.service.lc_new;

/**
 * @author Muyi, dcmuyi@qq.com
 * @desc tree node class
 * @date 2019-04-17.
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

//    @Override
//    public String toString() {
//        inOrderTraversal(this);
//        System.out.println();
//        return "";
//    }

    //从root开始遍历
    public static void preOrder(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        System.out.print(treeNode.val+">");
        preOrder(treeNode.left);
        preOrder(treeNode.right);
    }

    //从左向右遍历
    public static void inOrderTraversal(TreeNode node) {
        if (node == null)
            return;
        inOrderTraversal(node.left);
        System.out.print(node.val+">");
        inOrderTraversal(node.right);
    }
}
