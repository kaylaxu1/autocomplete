import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BinarySearchDeluxe {
    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (key == null)
            throw new IllegalArgumentException("Search key cannot be null!");

        for (int k = 0; k < a.length; k++) {
            if (a[k] == null)
                throw new IllegalArgumentException("Items in array cannot be null!");
        }

        int low = 0; // lower bound
        int hi = a.length - 1; // upper bound

        // get the start index of target number
        int firstIndex = -1;
        while (low < hi) {
            int mid = (hi - low) / 2 + low;
            
            if (comparator.compare(a[mid], key) < 0) {
                low = mid + 1; // search in right half
            }
            else if (comparator.compare(a[mid], key) > 0)
                hi = mid - 1; // search in left half
            else {
                // record matching index but keep checking in left half
                firstIndex = mid;
                hi = mid - 1;
            }
        }
        return firstIndex;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (key == null)
            throw new IllegalArgumentException("Search key cannot be null!");

        for (int k = 0; k < a.length; k++) {
            if (a[k] == null)
                throw new IllegalArgumentException("Items in array cannot be null!");
        }
        int low = 0; // lower bound
        int hi = a.length - 1; // upper bound

        int lastIndex = -1;
        while (low <= hi) {
            int mid = (hi - low) / 2 + low;
            if (comparator.compare(a[mid], key) > 0) {
                low = mid + 1; // check right half
            }
            else if (comparator.compare(a[mid], key) < 0)
                hi = mid - 1; // check left half
            else {
                // record matching index but keep checking in right half
                lastIndex = mid;
                low = mid + 1;
            }
        }
        return lastIndex;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }
        StdOut.println(lastIndexOf(terms, new Term("M", 0), Term.byPrefixOrder(1)));
        StdOut.println(firstIndexOf(terms, new Term("M", 0), Term.byPrefixOrder(1)));
    }
}
