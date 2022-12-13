import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always maintained.
     */
    protected static class Node<T> {
        public int blackHeight;
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild;
        public Node<T> rightChild;

        public Node(T data) {
            this.blackHeight = 0;
            this.data = data;
        }

        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     *
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException     when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain
     *                                  equal data references
     */
    @Override
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if (data == null) throw new NullPointerException("This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if (root == null) {
            root = newNode;
            size++;
            this.root.blackHeight = 1;
            return true;
        } // add first node to an empty tree
        else {
            boolean returnValue = insertHelper(newNode, root); // recursively insert into subtree
            if (returnValue) size++;
            else throw new IllegalArgumentException(
                    "This RedBlackTree already contains that value.");
            this.root.blackHeight = 1;
            return returnValue;
        }
    }

    /**
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     *
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the
     *                newNode should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if (compare == 0) return false;

            // store newNode within left subtree of subtree
        else if (compare < 0) {
            if (subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else {
            if (subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.rightChild);
        }
    }

    /**
     * helper method that swaps the colors of parent, grandparent and uncle of the redNode provided. This method is
     * called from the enforceRBTreePropertiesAfterInsert() method.
     * @param redNode node provided that gives access to the parent, grandparent and uncle nodes
     */
    public void colorSwap(Node<T> redNode) {
        if(redNode.parent.isLeftChild()) {
            //checks parent and grandparent color to know what color to switch parent and grandparent color too
            if(redNode.parent.blackHeight == 0 && redNode.parent.parent.blackHeight == 0) {
                redNode.parent.blackHeight = 1;
                redNode.parent.parent.blackHeight = 1;
                redNode.parent.parent.rightChild.blackHeight = 1;
                enforceRBTreePropertiesAfterInsert(redNode.parent.parent);//calls enforce method again to check for other violations
            }
            //checks parent and grandparent color to know what color to switch parent and grandparent color too
            else if(redNode.parent.blackHeight == 0 && redNode.parent.parent.blackHeight == 1) {
                redNode.parent.blackHeight = 1;
                redNode.parent.parent.blackHeight = 0;
                redNode.parent.parent.rightChild.blackHeight = 1;
                enforceRBTreePropertiesAfterInsert(redNode.parent.parent);//calls enforce method again to check for other violations
            }
            //checks parent and grandparent color to know what color to switch parent and grandparent color too
            else if(redNode.parent.blackHeight == 1 && redNode.parent.parent.blackHeight == 0) {
                redNode.parent.blackHeight = 0;
                redNode.parent.parent.blackHeight = 1;
                redNode.parent.parent.rightChild.blackHeight = 1;
                enforceRBTreePropertiesAfterInsert(redNode.parent.parent); //calls enforce method again to check for other violations
            }
            //if all other if statements fail, then we know that both parent and grandparent are black
            else {
                redNode.parent.blackHeight = 0;
                redNode.parent.parent.blackHeight = 0;
                redNode.parent.parent.rightChild.blackHeight = 1;
                enforceRBTreePropertiesAfterInsert(redNode.parent.parent);//calls enforce method again to check for other violations
            }
        } else {
            //checks parent and grandparent color to know what color to switch parent and grandparent color too
            if(redNode.parent.blackHeight == 0 && redNode.parent.parent.blackHeight == 0) {
                redNode.parent.blackHeight = 1;
                redNode.parent.parent.blackHeight = 1;
                redNode.parent.parent.leftChild.blackHeight = 1;
                enforceRBTreePropertiesAfterInsert(redNode.parent.parent);//calls enforce method again to check for other violations
            }
            //checks parent and grandparent color to know what color to switch parent and grandparent color too
            else if(redNode.parent.blackHeight == 0 && redNode.parent.parent.blackHeight == 1) {
                redNode.parent.blackHeight = 1;
                redNode.parent.parent.blackHeight = 0;
                redNode.parent.parent.leftChild.blackHeight = 1;
                enforceRBTreePropertiesAfterInsert(redNode.parent.parent);//calls enforce method again to check for other violations
            }
            //checks parent and grandparent color to know what color to switch parent and grandparent color too
            else if(redNode.parent.blackHeight == 1 && redNode.parent.parent.blackHeight == 0) {
                redNode.parent.blackHeight = 0;
                redNode.parent.parent.blackHeight = 1;
                redNode.parent.parent.leftChild.blackHeight = 1;
                enforceRBTreePropertiesAfterInsert(redNode.parent.parent);//calls enforce method again to check for other violations
            } else {
                //if all other if statements fail, then we know that both parent and grandparent are black
                redNode.parent.blackHeight = 0;
                redNode.parent.parent.blackHeight = 0;
                redNode.parent.parent.leftChild.blackHeight = 1;
                enforceRBTreePropertiesAfterInsert(redNode.parent.parent);//calls enforce method again to check for other violations
            }
        }
    }

    /**
     * Recursive method that takes a reference to a red node to resolve any red-black tree violations. If the parent
     * node violates a red-black tree property then the method is called on the redNode parameter parent.
     * @param redNode newly added node that will be checked for all possible cases
     */
    protected void enforceRBTreePropertiesAfterInsert(Node<T> redNode) {
        if(redNode.parent == null || redNode.parent.blackHeight == 1 ) { //if node is root or parent is black then we are done
            return;
        }

        //case 1 uncle is red
        if(redNode.parent.isLeftChild()){ //parent is a left child
            if(redNode.parent.parent != null && redNode.parent.parent.rightChild != null) {
                if (redNode.parent.parent.rightChild.blackHeight == 0) {//checks if the uncle is red
                    colorSwap(redNode); //calls colorSwap() method to swap colors of parent, grandparent, and uncle
                    return;
                }
            }
        } else if(!redNode.parent.isLeftChild()) { //parent is a right child
            if(redNode.parent.parent != null && redNode.parent.parent.leftChild != null) {
                if (redNode.parent.parent.leftChild.blackHeight == 0) {//checks if uncle is red
                    colorSwap(redNode); //calls colorSwap() method to swap colors of parent, grandparent, and uncle
                    return;
                }
            }
        }
        //cases 2 and 3
        if(redNode.parent.parent != null && redNode.parent.isLeftChild()) { //parent is a left child and grandparent not null
            if(redNode.isLeftChild()) { //case 2
                rotate(redNode.parent, redNode.parent.parent);
                redNode.parent.blackHeight = 1; //swap parent color
                redNode.parent.rightChild.blackHeight = 0; //swap grandparent color color
            } else { // case 3
                rotate(redNode, redNode.parent);//rotate redNode to be the new parent of redNode's previous parent
                rotate(redNode, redNode.parent);//rotate redNode to be in the position of its previous grandparent
                redNode.blackHeight = 1;//now that redNode is in the right position, change color to black
                redNode.rightChild.blackHeight = 0;
            }
        } else if(redNode.parent.parent != null && !redNode.parent.isLeftChild()) { //parent is a right child and grandparent not null
            if(!redNode.isLeftChild()) { // case 2
                rotate(redNode.parent, redNode.parent.parent);
                redNode.parent.blackHeight = 1; //swap parent color
                redNode.parent.leftChild.blackHeight = 0;//swap uncle grandparent color
            } else { //case 3
                rotate(redNode, redNode.parent);//rotate redNode to be the new parent of redNode's previous parent
                rotate(redNode, redNode.parent);//rotate redNode to be in the position of its previous grandparent
                redNode.blackHeight = 1; //now that redNode is in the right position, change color to black
                redNode.rightChild.blackHeight = 0;
            }
        }
    }

    /**
     * This method tests the color swap method, case 1, to make sure the colors are correct
     */
    @Test
    public void testColorSwap() {
        RedBlackTree<Integer> colorSwapTest = new RedBlackTree<>();
        colorSwapTest.insert(2);
        colorSwapTest.insert(1);
        colorSwapTest.insert(3);
        colorSwapTest.insert(4);
        //checks that the nodes have the correct red/black color
        Assertions.assertEquals(1, colorSwapTest.root.blackHeight);
        Assertions.assertEquals(1, colorSwapTest.root.rightChild.blackHeight);
        Assertions.assertEquals(1, colorSwapTest.root.leftChild.blackHeight);
        Assertions.assertEquals(0, colorSwapTest.root.rightChild.rightChild.blackHeight);
    }

    /**
     * tests the validity of case two where a tree has a red node violation and the uncle of the node is black and the
     * node is on the opposite side of the uncle
     */
    @Test
    public void testRotations1() {
        RedBlackTree<Integer> rotationOne = new RedBlackTree<>();
        rotationOne.insert(20);
        rotationOne.insert(15);
        rotationOne.insert(25);
        rotationOne.root.rightChild.blackHeight = 1;
        rotationOne.insert(12);
        //checks if the nodes have correct red/black color
        Assertions.assertEquals(1, rotationOne.root.blackHeight);
        Assertions.assertEquals(0, rotationOne.root.rightChild.blackHeight);
        Assertions.assertEquals(0, rotationOne.root.leftChild.blackHeight);
        Assertions.assertEquals(1, rotationOne.root.rightChild.rightChild.blackHeight);
        //checks if the nodes are in the correct position
        Assertions.assertEquals(15, rotationOne.root.data);
        Assertions.assertEquals(20, rotationOne.root.rightChild.data);
        Assertions.assertEquals(12, rotationOne.root.leftChild.data);
        Assertions.assertEquals(25, rotationOne.root.rightChild.rightChild.data);
    }

    /**
     * tests the validity of another larger RBT where the tree has a red node violation with the uncle of the node being
     * black and the node is on the opposite side as the uncle
     */
    @Test
    public void testRotations2() {
        RedBlackTree<Integer> rotationTwo = new RedBlackTree<>();
        rotationTwo.insert(1);
        rotationTwo.insert(2);
        rotationTwo.insert(3);
        rotationTwo.insert(4);
        rotationTwo.insert(5);
        rotationTwo.insert(6);
        rotationTwo.insert(7);
        rotationTwo.insert(8);
        rotationTwo.insert(9);
        //checks if the nodes have the correct red/black color
        Assertions.assertEquals(1, rotationTwo.root.blackHeight);
        Assertions.assertEquals(0, rotationTwo.root.leftChild.blackHeight);
        Assertions.assertEquals(0, rotationTwo.root.rightChild.blackHeight);
        Assertions.assertEquals(1, rotationTwo.root.leftChild.leftChild.blackHeight);
        Assertions.assertEquals(1, rotationTwo.root.leftChild.rightChild.blackHeight);
        Assertions.assertEquals(1, rotationTwo.root.rightChild.rightChild.blackHeight);
        Assertions.assertEquals(1, rotationTwo.root.rightChild.leftChild.blackHeight);
        Assertions.assertEquals(0, rotationTwo.root.rightChild.rightChild.leftChild.blackHeight);
        Assertions.assertEquals(0, rotationTwo.root.rightChild.rightChild.rightChild.blackHeight);
        //checks if the nodes are in the correct position
        Assertions.assertEquals(4, rotationTwo.root.data);
        Assertions.assertEquals(2, rotationTwo.root.leftChild.data);
        Assertions.assertEquals(6, rotationTwo.root.rightChild.data);
        Assertions.assertEquals(1, rotationTwo.root.leftChild.leftChild.data);
        Assertions.assertEquals(3, rotationTwo.root.leftChild.rightChild.data);
        Assertions.assertEquals(5, rotationTwo.root.rightChild.leftChild.data);
        Assertions.assertEquals(8, rotationTwo.root.rightChild.rightChild.data);
        Assertions.assertEquals(7, rotationTwo.root.rightChild.rightChild.leftChild.data);
        Assertions.assertEquals(9, rotationTwo.root.rightChild.rightChild.rightChild.data);
    }

    /**
     * tests the validity of enforceRBTreePropertiesAfterInsert() case 3 to ensure that when a node is on the same
     * side as the uncle (triangle), that the rotation is right
     */
    @Test
    public void testCase3() {
        RedBlackTree<Integer> triangleTest = new RedBlackTree<>();
        triangleTest.insert(10);
        triangleTest.insert(15);
        triangleTest.insert(8);
        triangleTest.root.rightChild.blackHeight = 1;
        triangleTest.insert(9);
        //check if nodes have the correct red/black color
        Assertions.assertEquals(1, triangleTest.root.blackHeight);
        Assertions.assertEquals(0, triangleTest.root.leftChild.blackHeight);
        Assertions.assertEquals(0, triangleTest.root.rightChild.blackHeight);
        Assertions.assertEquals(1, triangleTest.root.rightChild.rightChild.blackHeight);
        //check if nodes are in the correct position
        Assertions.assertEquals(9, triangleTest.root.data);
        Assertions.assertEquals(8, triangleTest.root.leftChild.data);
        Assertions.assertEquals(10, triangleTest.root.rightChild.data);
        Assertions.assertEquals(15, triangleTest.root.rightChild.rightChild.data);
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     *
     * @param child  is the node being rotated from child to parent position
     *               (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *               (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *                                  node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        if (parent.leftChild != child && parent.rightChild != child) {
            throw new IllegalArgumentException("rotation not needed");
        }
        if (parent.rightChild == child) { //left rotation
            boolean preLeftChild = parent.isLeftChild();//parent may change positions, save a boolean before any changes
            Node<T> saveTemp = parent.parent; //saves child node grandparent before any changes
            parent.rightChild = child.leftChild;
            if (child.leftChild != null) {//checks if child node's right child is null
                child.leftChild.parent = parent;//if not null then child's right child new parent is parent node
            }
            child.leftChild = parent; //assigns parent as the left child of child node
            parent.parent = child; //important-assigns child node as the new parent of parent node
            if (parent == root) {//checks if the parent is the root
                root = child;//assigns child node, that recently switched with parent, as the root of the tree
            } else {
                child.parent = saveTemp;
                if (!preLeftChild) { //boolean from before that checked if parent node was a left child
                    saveTemp.rightChild = child;
                } else {
                    saveTemp.leftChild = child;
                }
            }
        }

        if (parent.leftChild == child) { //right rotation
            boolean preLeftChild = parent.isLeftChild();//parent may change positions, save a boolean before any changes
            Node<T> saveTemp = parent.parent; //saves child node grandparent before any changes
            parent.leftChild = child.rightChild;
            if (child.rightChild != null) {//checks if child node's right child is null
                child.rightChild.parent = parent;//if not null then child's right child new parent is parent node
            }
            child.rightChild = parent;//assigns parent as the right child of the child node
            parent.parent = child;//important-assigns child node as the new parent of parent node
            if (parent == root) { //checks if parent was the root
                root = child; //assigns child node, that recently switched with parent, as the root of the tree
            } else {
                child.parent = saveTemp;
                if (!preLeftChild) {//boolean from before that checked if parent node was a left child
                    saveTemp.rightChild = child;
                } else {
                    saveTemp.leftChild = child;
                }
            }
        }
    }

    /**
     * Get the size of the tree (its number of nodes).
     *
     * @return the number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     *
     * @return true of this.size() return 0, false if this.size() > 0
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     *
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    @Override
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if (data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *data*.
     *
     * @param data    the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsHelper(T data, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }

    /**
     * Returns an iterator over the values in in-order (sorted) order.
     *
     * @return iterator object that traverses the tree in in-order sequence
     */
    @Override
    public Iterator<T> iterator() {
        // use an anonymous class here that implements the Iterator interface
        // we create a new on-off object of this class everytime the iterator
        // method is called
        return new Iterator<T>() {
            // a stack and current reference store the progress of the traversal
            // so that we can return one value at a time with the Iterator
            Stack<Node<T>> stack = null;
            Node<T> current = root;

            /**
             * The next method is called for each value in the traversal sequence.
             * It returns one value at a time.
             * @return next value in the sequence of the traversal
             * @throws NoSuchElementException if there is no more elements in the sequence
             */
            public T next() {
                // if stack == null, we need to initialize the stack and current element
                if (stack == null) {
                    stack = new Stack<Node<T>>();
                    current = root;
                }
                // go left as far as possible in the sub tree we are in un8til we hit a null
                // leaf (current is null), pushing all the nodes we fund on our way onto the
                // stack to process later
                while (current != null) {
                    stack.push(current);
                    current = current.leftChild;
                }
                // as long as the stack is not empty, we haven't finished the traversal yet;
                // take the next element from the stack and return it, then start to step down
                // its right subtree (set its right sub tree to current)
                if (!stack.isEmpty()) {
                    Node<T> processedNode = stack.pop();
                    current = processedNode.rightChild;
                    return processedNode.data;
                } else {
                    // if the stack is empty, we are done with our traversal
                    throw new NoSuchElementException("There are no more elements in the tree");
                }

            }

            /**
             * Returns a boolean that indicates if the iterator has more elements (true),
             * or if the traversal has finished (false)
             * @return boolean indicating whether there are more elements / steps for the traversal
             */
            public boolean hasNext() {
                // return true if we either still have a current reference, or the stack
                // is not empty yet
                return !(current == null && (stack == null || stack.isEmpty()));
            }

        };
    }

    /**
     * This method performs an inorder traversal of the tree. The string
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * Note that this RedBlackTree class implementation of toString generates an
     * inorder traversal. The toString of the Node class above
     * produces a level order traversal of the nodes / values of the tree.
     *
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    public String toInOrderString() {
        // use the inorder Iterator that we get by calling the iterator method above
        // to generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        Iterator<T> treeNodeIterator = this.iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (treeNodeIterator.hasNext())
            sb.append(treeNodeIterator.next());
        while (treeNodeIterator.hasNext()) {
            T data = treeNodeIterator.next();
            sb.append(", ");
            sb.append(data.toString());
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the tree rooted
     * at the current node. The string representations of each data value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * Note that the Node's implementation of toString generates a level
     * order traversal. The toString of the RedBlackTree class below
     * produces an inorder traversal of the nodes / values of the tree.
     * This method will be helpful as a helper for the debugging and testing
     * of your rotation implementation.
     *
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        String output = "[ ";
        LinkedList<Node<T>> q = new LinkedList<>();
        q.add(this.root);
        while (!q.isEmpty()) {
            Node<T> next = q.removeFirst();
            if (next.leftChild != null) q.add(next.leftChild);
            if (next.rightChild != null) q.add(next.rightChild);
            output += next.data.toString();
            if (!q.isEmpty()) output += ", ";
        }
        return output + " ]";
    }

    @Override
    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }

    /**
     * Main method to run tests. Comment out the lines for each test
     * to run them.
     * @param args
     */
    public static void main(String[] args) {

    }
}
