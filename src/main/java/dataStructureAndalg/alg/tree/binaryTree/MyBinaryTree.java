package dataStructureAndalg.alg.tree.binaryTree;

import java.util.HashMap;
import java.util.Map;

public class MyBinaryTree {

    BinaryTreeNode root;

    public static void main(String[] args) {
//        int[] arr = {12,15,2,3,5,4,98,65,52,43,28,6,87};
        int[] arr = {12,88,2,99,44,33,48,35,36,37};
//        int[] arr = {12,15,2,3};

        MyBinaryTree myBinaryTree = new MyBinaryTree();

        for (int i = 0; i < arr.length; i++) {
            myBinaryTree.add(new BinaryTreeNode(arr[i]));
        }
        myBinaryTree.inFixOrder();
        System.out.println("height: " + myBinaryTree.getSubHeight(myBinaryTree.root));
        myBinaryTree.turnTree();
        myBinaryTree.inFixOrder();
        System.out.println("height: " + myBinaryTree.getSubHeight(myBinaryTree.root));
        System.out.println("-------------");
    }

    /**
     * 新增节点
     * @param node
     */
    public void add(BinaryTreeNode node){
        if(root == null){
            root = node;
            return;
        }
        BinaryTreeNode currNode = root;
        while(true){
            if(currNode.value >= node.value){
                if(currNode.left == null){
                    currNode.left = node;
                    return;
                }else{
                    currNode = currNode.left;
                }
            }else{
                if(currNode.right == null){
                    currNode.right = node;
                    return;
                }else{
                    currNode = currNode.right;
                }
            }
        }
    }

    /**
     * 中序遍历
     */
    public void inFixOrder(){
        if(root == null){
            return ;
        }
        root.inFixOrder();
    }

    public void remove(int value){
        if(root == null){
            return;
        }
        Map<String,BinaryTreeNode> map = new HashMap<>();
        map.put("parentNode",null);
        map.put("currNode",root);
        try {
            boolean isLeft = processSearchNode(value, map);
            processDelNode(map,isLeft);
        } catch (Exception e) {

        }
    }

    /**
     * 处理删除的节点
     * @param map
     * @param isLeft
     */
    public void processDelNode(Map<String,BinaryTreeNode> map, boolean isLeft){

        BinaryTreeNode parentNode = map.get("parentNode");
        BinaryTreeNode currNode = map.get("currNode");

        if(parentNode == null){
            //目标节点为根节点
            //右子树为空，root等于左子树，为null也无所谓
            if(currNode.right == null){
                root = currNode.left;
            }else{
                //找右子树中最小的结点替换root
                BinaryTreeNode min = findMin(currNode.right);
                min.left = currNode.left;
                //判断右子树最小结点是他本身
                if(min != currNode.right){
                    min.right = currNode.right;
                }
                root = min;
            }
        }else{
            //目标节点不是root
            BinaryTreeNode newSubNode = null;
            //目标节点是叶子结点
            if(currNode.left == null && currNode.right == null){

            }else if(currNode.left == null || currNode.right == null){
                //目标节点存在一个子节点
                newSubNode = currNode.left == null ? currNode.right : currNode.left;
            }else{
                //目标节点存在双子节点,找右子树最小替换当前节点
                newSubNode = findMin(currNode.right);
                newSubNode.left = currNode.left;
                //判断不是他自身
                if(newSubNode != currNode.right){
                    newSubNode.right = currNode.right;
                }
            }

            if(isLeft){
                parentNode.left = newSubNode;
            }else{
                parentNode.right = newSubNode;
            }
        }
    }

    /**
     * 查询目标树下最小节点
     * @param subNode
     * @return
     */
    public BinaryTreeNode findMin(BinaryTreeNode subNode){
        BinaryTreeNode parentNode = subNode;
        while(true){
            //找到目标
            if(subNode.left == null){
                //断绝关系
                parentNode.left = null;
                return subNode;
            }
            //错位遍历
            if(parentNode != subNode){
                parentNode = parentNode.left;
            }
            subNode = subNode.left;
        }
    }

    /**
     * 查询目标节点和其父节点
     * @param value
     * @param map
     * @return
     */
    public boolean processSearchNode(int value, Map<String,BinaryTreeNode> map){

        BinaryTreeNode parentNode = map.get("parentNode");
        BinaryTreeNode currNode = map.get("currNode");

        boolean isLeft = true;
        //查找目标节点和父节点
        while(true){
            if(currNode.value == value){
                break;
            }
            if(currNode.value > value){
                if(currNode.left == null){
                    //异常
                    throw new RuntimeException("无匹配结点");
                }else{
                    if(currNode.left.value == value){
                        parentNode = currNode;
                        isLeft = true;
                    }
                    currNode = currNode.left;
                }
            }else {
                if(currNode.right == null){
                    //异常
                    throw new RuntimeException("无匹配结点");
                }else{
                    if(currNode.right.value == value){
                        parentNode = currNode;
                        isLeft = false;
                    }
                    currNode = currNode.right;
                }
            }
        }
        map.put("parentNode",parentNode);
        map.put("currNode",currNode);
        return isLeft;
    }

    public int getHeight(){
        return getSubHeight(root);
    }

    private int getSubHeight(BinaryTreeNode node){
        if(node == null){
            return 0;
        }
        if(node.left == null && node.right== null){
            return 1;
        }
        return node.getHeight();
    }

    public void turnTree(){
        if(root == null){
            return;
        }
        root.turnTree();
    }
}
