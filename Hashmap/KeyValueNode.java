// --== CS400 Project One File Header ==--
// Name: <Ben Lynch>
// CSL Username: <lynch>
// Email: <bplynch3@wisc.edu>
// Lecture #: <004 @4:00pm>
// Notes to Grader: <None>

import com.sun.jdi.Value;

/**
 * Helper class that handles creating and accessing values associated with the hash table linked list
 * @param <KeyType>
 * @param <ValueType>
 */
public class KeyValueNode<KeyType, ValueType> {
    ValueType val;
    KeyType key;

    /**
     * constructor for nodes in the linked list in the hash table
     * @param key key that will allow for access to value
     * @param value value associated with key
     */
    public KeyValueNode(KeyType key, ValueType value){
        this.val = value;
        this.key = key;
    }

    /**
     * returns the value
     * @return the value
     */
    public ValueType getVal() {
        return this.val;
    }

    /**
     * returns key
     * @return the key
     */
    public KeyType getKey() {
        return this.key;
    }
}
