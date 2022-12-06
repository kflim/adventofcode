public class BinaryTreeNode {

    private Integer leftValue;
    private Integer rightValue;
    private BinaryTreeNode leftChild;
    private BinaryTreeNode rightChild;
    private BinaryTreeNode parent;

    public BinaryTreeNode() {
        this.leftValue = null;
        this.rightValue = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    public Integer getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(Integer leftValue, boolean isAdding) {
        if (isAdding) {
            this.leftValue += leftValue;
        } else {
            this.leftValue = leftValue;
        }
    }

    public Integer getRightValue() {
        return rightValue;
    }

    public void setRightValue(Integer rightValue, boolean isAdding) {
        if (isAdding) {
            this.rightValue += rightValue;
        } else {
            this.rightValue = rightValue;
        }
    }

    public BinaryTreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BinaryTreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public BinaryTreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(BinaryTreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public BinaryTreeNode getParent() {
        return parent;
    }

    public void setParent(BinaryTreeNode parent) {
        this.parent = parent;
    }

    public int getMagnitude() {
        if (leftChild != null && rightChild != null) {
            return 3 * leftChild.getMagnitude() + 2 * rightChild.getMagnitude();
        } else if (leftChild != null) {
            return 3 * leftChild.getMagnitude() + 2 * rightValue;
        } else if (rightChild != null) {
            return 3 * leftValue + 2 * rightChild.getMagnitude();
        } else {
            return 3 * leftValue + 2 * rightValue;
        }
    }

    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    @Override
    public String toString() {
        // Printing is done inorder
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if(leftChild != null) {
            sb.append(leftChild);
        } else {
            sb.append(leftValue);
        }
        sb.append(",");
        if(rightChild != null) {
            sb.append(rightChild);
        } else {
            sb.append(rightValue);
        }
        sb.append("]");
        return sb.toString();
    }
}
