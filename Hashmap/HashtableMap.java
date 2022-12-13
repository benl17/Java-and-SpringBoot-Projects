import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * HashtableMap class
 * @param <KeyType> allows for different types of Keys to be used in the hashtable
 * @param <ValueType> allows for different types of values ot be used in the hashtable
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>{
    protected int size;
    protected LinkedList<KeyValueNode<KeyType, ValueType>>[] hashTable;

    /**
     * Argument constructor assigns hashTable to a new linkedlist array with the capacity of capacity argument
     * @param capacity the initial capacity of the hash table
     */
    @SuppressWarnings("unchecked")
    public HashtableMap(int capacity) {
        this.hashTable = (LinkedList<KeyValueNode<KeyType, ValueType>>[])new LinkedList[capacity];
        this.size = 0;
    }

    /**
     * no argument constructor that assigns hashTable to a new linkedlist array with the capacity of 20 (default)
     */
    @SuppressWarnings("unchecked")
    public HashtableMap() {
        this.hashTable = (LinkedList<KeyValueNode<KeyType, ValueType>>[])new LinkedList[20]; //default size is 20
        this.size = 0;
    }

    /**
     * method that inserts a new key value pair into the hashTable. If the key is null or the key is already in the
     * array then it returns false, otherwise it obtains the hash index and inserts it into the linkedlist present
     * at the index
     * @param key key that is used to get the hash index and insert at correct position
     * @param value value of the key value pair
     * @return true if the key is not already present in the array, false if not or the key is null
     */
    @Override
    public boolean put(KeyType key, ValueType value) {
        if(key == null || containsKey(key)) {
            return false;
        }

        int hash = Math.abs(key.hashCode() % hashTable.length); //get hash index

        if(hashTable[hash] == null) {
            hashTable[hash] = new LinkedList<KeyValueNode<KeyType, ValueType>>();
            KeyValueNode currNode = new KeyValueNode(key, value);
            hashTable[hash].add(currNode);
            this.size++;
        } else {
            KeyValueNode currNode = new KeyValueNode(key, value);
            hashTable[hash].add(currNode);
            this.size++;
        }

        //checks if the current load factor is higher than 75%
        if(((float)size/(float)hashTable.length) >= 0.75) {
            rehashHelperMethod();
        }
        return true;
    }

    /**
     * helper method that will double the capacity of the hash table and rehash all the elements back into their place
     * when the load factor is greater than 75%. It is called from the put method
     */
    public void rehashHelperMethod() {
        LinkedList<KeyValueNode<KeyType, ValueType>>[] temp = new LinkedList[hashTable.length*2];
        for(int k = 0; k < hashTable.length; k++) {
            temp[k] = hashTable[k];
        }

        hashTable = new LinkedList[hashTable.length* 2];

        for(int w = 0; w < temp.length; w++) {
            hashTable[w] = temp[w];
        }

        for(int f = 0; f < hashTable.length; f++){
            if(hashTable[f] != null) {
                for(int g = 0; g < hashTable[f].size(); g++) {
                    put(hashTable[f].get(g).getKey(), hashTable[f].get(g).getVal());
                    hashTable[f].remove(hashTable[f].get(g));
                }
            }
        }
    }

    /**
     * the get method will return the element from the hash table associated with the key provided. returns null if
     * the method cannot find the key
     * @param key the key that will be used to find the value
     * @return the value associated with the key
     * @throws NoSuchElementException if the hash table does not contain the key
     */
    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        ValueType returnVal = null;
        if(!containsKey(key)) {
            throw new NoSuchElementException("Key is not present in the hash table");
        } else {
            int hash = Math.abs(key.hashCode() % hashTable.length); //get hash index
            for(int r = 0; r < hashTable[hash].size(); r++) {
                if(hashTable[hash].get(r).getKey().equals(key)) {
                    returnVal = (ValueType) hashTable[hash].get(r).getVal();
                }
            }
        }
        return returnVal;
    }

    /**
     * getter method for the size variable
     * @return the size of this hash table
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * containsKey method will check if the key is present in the hash table and return true if it is
     * @param key the key that is being checked for
     * @return true if the key is present in the hash table, false if not
     */
    @Override
    public boolean containsKey(KeyType key) {
        int hash = Math.abs(key.hashCode() % hashTable.length); //get hash index

        if(hashTable[hash] == null) {
            return false;
        } else {
            for(int t = 0; t < hashTable[hash].size(); t++) {
                if(key.equals(hashTable[hash].get(t).getKey())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * remove method will remove the key value pair associated with the key and return an instance of the value
     * @param key the key that will be used to look for the value
     * @return instance of the value associated with the key
     */
    @Override
    public ValueType remove(KeyType key) {
        if(!containsKey(key)) {
            return null;
        } else {
            int hash = Math.abs(key.hashCode() % hashTable.length); //get hash index
            for(int e = 0; e < hashTable[hash].size(); e++) {
                if(key.equals(hashTable[hash].get(e).getKey())) {
                    hashTable[hash].remove(e);
                    this.size--;
                    break;
                }
            }
        }
        return get(key);
    }

    /**
     * clears the entire hash table so no elements are in the array
     */
    @Override
    public void clear() {
        for(int i = 0; i < hashTable.length; i++) {
            if(hashTable[i] != null) {
                hashTable[i] = null;
            }
        }
        this.size = 0;
    }
}
