// --== CS400 Project One File Header ==--
// Name: <Ben Lynch>
// CSL Username: <lynch>
// Email: <bplynch3@wisc.edu>
// Lecture #: <004 @4:00pm>
// Notes to Grader: <None>
import java.util.NoSuchElementException;

public interface MapADT <KeyType, ValueType> {
        public boolean put(KeyType key, ValueType value);

        public ValueType get(KeyType key) throws NoSuchElementException;

        public int size();

        public boolean containsKey(KeyType key);

        public ValueType remove(KeyType key);

        public void clear();
}
