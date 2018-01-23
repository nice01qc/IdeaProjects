package leedecode;

import sun.reflect.generics.tree.Tree;

import java.util.*;

public class Solution {

    private String[] dealString(String str) {
        String[] result = str.split(",");
        result[0] = result[0].replaceAll("[{,}]", "");
        result[result.length - 1] = result[result.length - 1].replaceAll("[{,}]", "");
        return result;
    }

    public TreeNode deserialize(String data) {
        if (data.equals("")) return null;
        String[] datas = dealString(data);
        TreeNode root = new TreeNode(Integer.parseInt(datas[0]));
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        int i = 1;
        while (i < datas.length) {
            boolean pan = i<datas.length;
            Stack<TreeNode> stack0 = new Stack<TreeNode>();

            while (!stack.isEmpty()) {
                TreeNode temp = stack.pop();
                if (temp == null) {
                    continue;

                } else {
                    if (i < datas.length && !datas[i].equals("#")) {
                        temp.left = new TreeNode(Integer.parseInt(datas[i]));
                        stack0.push(temp.left);
                    }else{
                        stack0.push(null);
                    }
                    i++;

                    if (i < datas.length && !datas[i].equals("#")) {
                        temp.right = new TreeNode(Integer.parseInt(datas[i]));
                        stack0.push(temp.right);
                    }else{
                        stack0.push(null);
                    }
                    i++;
                }
            }
            while (!stack0.isEmpty()) {
                stack.push(stack0.pop());
            }
        }
        return root;
    }


    public String serialize(TreeNode root) {
        if (root == null) return "";

        String result = "{";
        Stack<TreeNode> stack = new Stack<TreeNode>();

        stack.push(root);
        int state = 1;

        while (state == 1) {
            Stack<TreeNode> stack0 = new Stack<TreeNode>();
            state = 0;
            while (!stack.isEmpty()) {
                TreeNode temp = stack.pop();
                if (temp == null) {
                    if (state == 0 && stack.isEmpty()) break;
                    result += "#,";



                } else {
                    result += temp.val + ",";
                    if (stack.isEmpty() && temp.left == null && temp.right == null){
                        break;
                    }

                    if (temp.left != null) {
                        stack0.push(temp.left);
                        state = 1;
                    } else {
                        stack0.push(null);
                    }
                    if (temp.right != null) {
                        stack0.push(temp.right);
                        state = 1;
                    } else {
                        stack0.push(null);
                    }
                }
            }

            while (!stack0.isEmpty()) stack.push(stack0.pop());
        }

        result = result.substring(0, result.lastIndexOf(","))+"}";

        return result;
    }


    public static void main(String[] args) {




    }


}
