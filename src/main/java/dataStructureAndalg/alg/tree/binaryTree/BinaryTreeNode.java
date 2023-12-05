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

    /**
     * 结点获取高度
     * @return
     */
    public int getHeight(){
        if(left == null && right == null){
            return 1;
        }
        int leftHeight = 0;
        int heightHeight = 0;
        if(left != null){
            leftHeight = 1+left.getHeight();
        }
        if(right != null){
            heightHeight = 1+right.getHeight();
        }
        return Math.max(leftHeight, heightHeight);
    }

    //平衡树
    public void turnTree(){
        int leftHeight = this.left==null?0:left.getHeight();
        int rightHeight = this.right==null?0:right.getHeight();

        //0：不需要旋转；1：左旋；2右旋；
        int isTurn = 0;
        if(rightHeight - leftHeight > 1){
            isTurn = 1;
        }else if(leftHeight - rightHeight > 1){
            isTurn = 2;
        }

        if(isTurn == 0){
            return;
        }
        turnTree(isTurn);
        //循环，直到isTurn == 0;
        turnTree();
    }

    //旋转树
    private void turnTree(int isTurn){
        //左旋
        if(isTurn == 1){
            assert this.right != null;
            int subLeftHeight = right.left==null?0:right.left.getHeight();
            int subRightHeight = right.right==null?0:right.right.getHeight();
            if(subLeftHeight > subRightHeight){
                //需要先右旋 右子树，将节点平衡到右子树的右子树
                right.turnTree(2);
            }
            BinaryTreeNode binaryTreeNode = new BinaryTreeNode();
            binaryTreeNode.value = this.value;
            binaryTreeNode.left = this.left;
            binaryTreeNode.right = this.right.left;
            this.value = this.right.value;
            this.right = this.right.right;
            this.left = binaryTreeNode;
        }
        //右旋
        if(isTurn == 2){
            assert this.left != null;
            int subLeftHeight = left.left==null?0:left.left.getHeight();
            int subRightHeight = left.right==null?0:left.right.getHeight();
            if(subLeftHeight < subRightHeight){
                //需要先右旋 右子树，将节点平衡到右子树的右子树
                left.turnTree(1);
            }
            BinaryTreeNode binaryTreeNode = new BinaryTreeNode();
            binaryTreeNode.value = this.value;
            binaryTreeNode.right = this.right;
            binaryTreeNode.left = this.left.right;
            this.value = this.left.value;
            this.left = this.left.left;
            this.right = binaryTreeNode;
        }
    }

}
