package dataStructureAndalg.alg.tree.binaryTree;

public class MyBinaryTree {

    BinaryTreeNode root;

    public static void main(String[] args) {
        int[] arr = {12,3,5,9,53,24,18,35,65,69,62,45,46,42,87,2};

        MyBinaryTree myBinaryTree = new MyBinaryTree();

        for (int i = 0; i < arr.length; i++) {
            myBinaryTree.add(new BinaryTreeNode(arr[i]));
        }
        myBinaryTree.inFixOrder();
        System.out.println("-------------");
        myBinaryTree.remove(12);
        myBinaryTree.remove(2);
        myBinaryTree.remove(62);
        myBinaryTree.inFixOrder();
    }

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

    public void inFixOrder(){
        root.inFixOrder();
    }

    public void remove(int value){

        BinaryTreeNode parentNode = null;
        BinaryTreeNode currNode = root;
        boolean isLeft = true;
        //查找目标节点和父节点
        while(true){
            if(currNode.value == value){
                break;
            }
            if(currNode.value > value){
                if(currNode.left == null){
                    //异常
                    return;
                }else{
                    if(currNode.left.value == value){
                        parentNode = currNode;
                    }
                    currNode = currNode.left;
                }
            }else {
                if(currNode.right == null){
                    //异常
                    return;
                }else{
                    if(currNode.right.value == value){
                        isLeft = false;
                        parentNode = currNode;
                    }
                    currNode = currNode.right;
                }
            }
        }
        if(parentNode == null){
            //目标节点为根节点
            //右子树为空，root等于左子树，为null也无所谓
            if(currNode.right == null){
                root = currNode.left;
            }else{
                //找右子树中最小的结点替换root
                BinaryTreeNode min = findMin(currNode.right);
                min.left = currNode.left;
                min.right = currNode.right;
                root = min;
            }
        }else{
            //目标节点不是root
            //目标节点是叶子结点
            if(currNode.left == null && currNode.right == null){
                if(isLeft){
                    parentNode.left = null;
                }else{
                    parentNode.right = null;
                }
                return;
            }
            //目标节点存在一个子节点
            if(currNode.left == null || currNode.right == null){
                BinaryTreeNode maybeNode = currNode.left == null ? currNode.right : currNode.left;
                if(isLeft){
                    parentNode.left = maybeNode;
                }else{
                    parentNode.right = maybeNode;
                }
                return;
            }
            //目标节点存在双子节点,找右子树最小替换当前节点
            BinaryTreeNode min = findMin(currNode.right);
            min.left = currNode.left;
            min.right = currNode.right;
            if(isLeft){
                parentNode.left = min;
            }else{
                parentNode.right = min;
            }
        }
    }

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

    public BinaryTreeNode search(int value){

        BinaryTreeNode currNode = root;
        if(root.value == value){
            return root;
        }
        while(true){
            if(currNode.value == value){
                return currNode;
            }
            if(currNode.value > value){
                if(currNode.left == null){
                    return null;
                }else{
                    currNode = currNode.left;
                }
            }else {
                if(currNode.right == null){
                    return null;
                }else{
                    currNode = currNode.right;
                }
            }
        }
    }

}
