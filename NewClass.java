package Copy1;

import static java.lang.System.out;
import java.util.Vector;
import java.util.Scanner;
import java.io.IOException;

class NodeData {

    private long key;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public NodeData(long keyval) {
        this.key = keyval;
    }

    public String displayItem() {
        return ("[" + getKey() + "]");
    }
}

class Node {

    private static final int MAX_CHILDREN = 4;
    private static final int MAX_ITEMS_PER_NODE = MAX_CHILDREN - 1;
    private Node parent;
    private Node childArray[] = new Node[MAX_CHILDREN];
    private NodeData itemArray[] = new NodeData[MAX_ITEMS_PER_NODE];
    private int numItems;

    public int getNumItems() {
        return numItems;
    }

    public void increaseItemNum() {
        numItems++;
    }

    public void decreaseItemNum() {
        numItems--;
    }

    public Node getChild(int childNum) {
        return childArray[childNum];
    }

    public void setChild(int childNum, Node child) {
        childArray[childNum] = child;
    }

    public NodeData getItem(int index) {
        return itemArray[index];
    }

    public void setItem(int index, NodeData item) {
        itemArray[index] = item;
    }

    public boolean isItemNull(int index) {
        return (itemArray[index] == null) ? true : false;
    }
    //Useful methods to retrieve child, parent and check whether the node is a leaf

    public Node getParent() {
        return parent;
    }

    public boolean isLeaf() {
        return (getChild(0) == null) ? true : false;
    }

    public boolean isFull() {
        return (getNumItems() == MAX_ITEMS_PER_NODE) ? true : false;
    }

    public void connectChild(int childNum, Node child) {
        setChild(childNum, child);
        if (child != null) {
            child.parent = this;
        }
    }

    public Node disconnectChild(int childNum) {
        Node tempNode = getChild(childNum);
        setChild(childNum, null);
        return tempNode;
    }

    public int findItem(long key) {
        for (int x = 0; x < MAX_ITEMS_PER_NODE; x++) {
            if (isItemNull(x)) {
                break;
            } else if (getItem(x).getKey() == key) {
                return x;
            }
        }
        return -1;
    }

    public int insertItem(NodeData newItem) {
        increaseItemNum();
        for (int x = MAX_ITEMS_PER_NODE - 1; x >= 0; x--) {
            if (isItemNull(x)) {
                continue;
            } else {
                if (newItem.getKey() < getItem(x).getKey()) {
                    setItem(x + 1, getItem(x));
                } else {
                    setItem(x + 1, newItem);
                    return x + 1;
                }
            }
        }
        setItem(0, newItem);
        return 0;
    }

    public NodeData removeItem() {
        NodeData temp = getItem(getNumItems() - 1);
        setItem(getNumItems() - 1, null);
        decreaseItemNum();

        return temp;
    }

    public String displayNode() {
        String x = new String("");
        for (int node = 0; node < getNumItems(); node++) {
            x = x + getItem(node).displayItem();
        }

        return x;
    }
}

class Tree234 {

    private Node root;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Tree234() {
        this.root = new Node();
    }

    public int find(long key) {
        Node curNode = getRoot();
        int childNumber;

        while (true) {
            if ((childNumber = curNode.findItem(key)) != -1) {
                return childNumber;
            } else if (curNode.isLeaf()) {
                return -1;
            } else {
                curNode = getNextChild(curNode, key);
            }
        }
    }

    public void insert(long dValue) {
        Node curNode = getRoot();
        NodeData tempItem = new NodeData(dValue);

        while (true) {
            if (curNode.isFull()) {
                split(curNode);
                curNode = curNode.getParent();
                curNode = getNextChild(curNode, dValue);
            } else if (curNode.isLeaf()) {
                break;
            } else {
                curNode = getNextChild(curNode, dValue);
            }
        }
        curNode.insertItem(tempItem);
    }

    public void split(Node oldNodeForSplit) {
        NodeData bItem;
        NodeData cItem;
        Node parent;
        Node child2;
        Node child3;
        int itemIndex;
        cItem = oldNodeForSplit.removeItem();
        bItem = oldNodeForSplit.removeItem();
        child2 = oldNodeForSplit.disconnectChild(2);
        child3 = oldNodeForSplit.disconnectChild(3);
        Node newNodeForSplit = new Node();
        if (oldNodeForSplit == getRoot()) {
            setRoot(new Node());
            parent = getRoot();
            root.connectChild(0, oldNodeForSplit);
        } else {
            parent = oldNodeForSplit.getParent();
        }
        itemIndex = parent.insertItem(bItem);
        for (int itemN = parent.getNumItems() - 1; itemN > itemIndex; itemN--) {
            Node temp = parent.disconnectChild(itemN);
            parent.connectChild(itemN + 1, temp);
        }
        parent.connectChild(itemIndex + 1, newNodeForSplit);
        newNodeForSplit.insertItem(cItem);
        newNodeForSplit.connectChild(0, child2);
        newNodeForSplit.connectChild(1, child3);
    }

    public Node getNextChild(Node theNode, long theValue) {
        int k;
        for (k = 0; k < theNode.getNumItems(); k++) {
            if (theValue < theNode.getItem(k).getKey()) {
                return theNode.getChild(k);
            }
        }
        return theNode.getChild(k);
    }

    public void printTree() {
        Vector<String> treeV = new Vector<String>();
        treeInfoForPrinting(getRoot(), 0, 0, treeV);
        for (int x = 0; x < treeV.size(); x++) {
            out.println("Level " + x + ": " + treeV.get(x));
        }
    }

    private void treeInfoForPrinting(Node thisNode, int level, int childN, Vector<String> treeV) {
        try {
            treeV.set(level, treeV.get(level) + ", Child" + childN + " = " + thisNode.displayNode());
        } catch (Exception e) {
            treeV.insertElementAt("Child" + childN + " = " + thisNode.displayNode(), level);
        }
        for (int x = 0; x < thisNode.getNumItems() + 1; x++) {
            Node nextNode = thisNode.getChild(x);

            if (nextNode != null) {
                treeInfoForPrinting(nextNode, level + 1, x, treeV);
            } else {
                return;
            }
        }
    }
}

class Tree234Test {

    public static void main(String[] args) throws IOException {
        boolean cycleController = true;
        Tree234 theTree = new Tree234();
        while (cycleController) {
            switch (getInput("[SsIiFfEe]", "\n(S)how, (I)nsert, (F)ind or (E)xit").charAt(0)) {
                case 's':
                case 'S':

                    out.println("\nTypical 2-3-4 tree with 4 children structure: \n");
                    out.println("                                               ***");
                    out.println("        ***                      ***                     ***                      ***");
                    out.println("*** *** *** ***     *** *** *** ***     *** *** *** ***     *** *** *** ***");

                    out.println("\nThe tree contains: ");
                    theTree.printTree();
                    break;

                case 'i':
                case 'I':
                    theTree.insert(Integer.parseInt(getInput("[0-9]+", "Value to insert: ")));
                    break;

                case 'f':
                case 'F':
                    if (theTree.find(Integer.parseInt(getInput("[0-9]+", "Value to find: "))) != -1) {
                        out.println("Value is in the tree");
                    } else {
                        out.println("Value is not in the tree");
                    }
                    break;

                case 'e':
                case 'E':
                    cycleController = false;
                    break;

                default:
                    out.print("Invalid entry\n");
            }
        }
    }

    public static String getInput(String filter, String message) throws IOException {
        Scanner sc = new Scanner(System.in);
        out.println(message);

        while (!sc.hasNext(filter)) {
            out.println("Wrong choice");
            sc.next();
        }
        return sc.next();
    }
}
