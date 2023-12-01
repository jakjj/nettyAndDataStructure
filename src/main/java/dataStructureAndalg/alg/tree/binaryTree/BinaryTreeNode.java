package dataStructureAndalg.alg.tree.binaryTree;

public class BinaryTreeNode {
    int value;
    BinaryTreeNode left;
    BinaryTreeNode right;

    public BinaryTreeNode() {
    }

    public BinaryTreeNode(int value) {
        this.value = value;
    }

    public void inFixOrder(){
        if(this.left != null){
            this.left.inFixOrder();
        }
        System.out.println("node[ " + value + "]");
        if(this.right != null){
            this.right.inFixOrder();
        }
    }
}
