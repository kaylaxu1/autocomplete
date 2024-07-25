import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Autocomplete {
    private Term[] terms; // the given array of terms to search in
    private int firstIndex; // index of first match
    private int lastIndex; // index of last match

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException("input terms array cannot be null");
        }
        // check for null items in the array
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null)
                throw new IllegalArgumentException("Null item in array!");
        }

        // create a defensive copy of the terms array.
        this.terms = Arrays.copyOf(terms, terms.length);
        Arrays.sort(this.terms);

        firstIndex = -1;
        lastIndex = -1;
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("Null prefix found");

        int r = prefix.length();

        Term key = new Term(prefix, 0); // 0 is an arbitrary weight
        firstIndex = BinarySearchDeluxe.firstIndexOf(terms, key, Term.byPrefixOrder(r));
        lastIndex = BinarySearchDeluxe.lastIndexOf(terms, key, Term.byPrefixOrder(r));

        Term[] matches = new Term[numberOfMatches(prefix)];

        int index = 0;
        if (firstIndex == -1 && lastIndex == -1) return matches; // no matches
        else if (firstIndex == lastIndex) {
            matches[0] = terms[firstIndex];
        }
        else {
            for (int i = firstIndex; i <= lastIndex; i++) {
                matches[index++] = terms[i];
            }
        }
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("Null prefix found");

        if (firstIndex == -1 || lastIndex == -1) return 0;
        return lastIndex - firstIndex + 1;
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

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}
