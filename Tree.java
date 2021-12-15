package Copy1;

class TreeNode {

    int item;
    TreeNode left;
    TreeNode right;

    public TreeNode(int key) {
        item = key;
        left = right = null;
    }
}

public class Tree {
    // root of Tree

    TreeNode root;

    Tree() {
        root = null;
    }

    void preorder(TreeNode node) {
        if (node == null) {
            return;
        }

        // traverse the root node
        System.out.print(node.item + "->");
        // traverse the left child
        preorder(node.left);
        // traverse the right child
        preorder(node.right);
    }

    public static void main(String[] args) {
        // create object of tree
        Tree tree = new Tree();

        // create nodes of the tree
        tree.root = new TreeNode(1);
        tree.root.left = new TreeNode(12);
        tree.root.right = new TreeNode(9);
        tree.root.left.left = new TreeNode(5);
        tree.root.left.right = new TreeNode(6);

        // preorder tree traversal
        System.out.println("\nPreorder traversal ");
        tree.preorder(tree.root);
        System.out.println("");
    }
}
