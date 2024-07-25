import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;


public class Term implements Comparable<Term> {
    private String query; // the search key
    private long weight; // the weight given to the key
    private static final int less = -1;
    private static final int greater = 1;
    private static final int equal = 0;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        // corner case 1 - null query or negative weight
        if (query == null || weight < 0)
            throw new IllegalArgumentException("Query invalid/weight negative");
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeight();
    }

    private static class ReverseWeight implements Comparator<Term> {
        public int compare(Term a, Term b) {
            if (a.weight < b.weight) // if less than the second item, return 1
                return greater;
            else if (a.weight > b.weight) // if greater, return -1
                return less;
            else return equal;
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query
    public static Comparator<Term> byPrefixOrder(int r) {
        return new PrefixOrder(r);
    }

    private static class PrefixOrder implements Comparator<Term> {
        private int r; // length of the prefix

        // records number of characters to look at
        public PrefixOrder(int r) {
            // corner case 2 - check for negative r
            if (r < 0)
                throw new IllegalArgumentException("R cannot be negative!");
            this.r = r;
        }

        // compare two terms by first r characters
        public int compare(Term a, Term b) {
            String first = a.query;
            String second = b.query;

            // length of the shorter term
            int shortest = Math.min(first.length(), second.length());

            // checking if r > shortest
            // preventing charAt from being out of bounds
            int maxIndex = Math.min(r, shortest);

            // compare the r first letters of each word
            // if r > shortest, only compares up to the number
            // of letters in the shortest word
            for (int i = 0; i < maxIndex; i++) {
                int letterFirst = first.charAt(i);
                int letterSecond = second.charAt(i);

                if (letterFirst > letterSecond) {
                    return greater;
                }
                else if (letterFirst < letterSecond) {
                    return less;
                }
            }
            // if one of the words is shorter than r, then
            // we check which word is longer (will be counted
            // as greater than the shorter word)
            if (r > shortest) {
                if (first.length() > second.length())
                    return greater;
                else if (second.length() > first.length())
                    return less;
            }
            return equal;
        }
    }


    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return weight + "\t" + query;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term a = new Term("Cathy", 5);
        Term b = new Term("Cat", 5);
        Term ai = new Term("Cayla", 6);
        Term c = new Term("Yubi", 8);

        Term[] us = new Term[4];
        us[0] = a;
        us[1] = b;
        us[2] = c;
        us[3] = ai;

        Arrays.sort(us, Term.byPrefixOrder(1));
        StdOut.println("\nSorted by prefix when r = 1:");
        // should print out 5 first, and 8 last
        for (int i = 0; i < us.length; i++)
            System.out.println(us[i]);

        Arrays.sort(us, Term.byReverseWeightOrder());
        StdOut.println("\nSorted by weight:");
        // print out 8 first, and 5 last
        for (int j = 0; j < us.length; j++)
            System.out.println(us[j]);

        StdOut.println("Testing comparators");
        // should print 0
        StdOut.println(byReverseWeightOrder().compare(b, a));

        StdOut.println("Simple CompareTo's");
        // should be negative
        StdOut.println(b.compareTo(a));
        // should be negative
        StdOut.println(a.compareTo(c));

        // should print 0
        StdOut.println(Term.byPrefixOrder(6).compare(a, b));


        // testing random terms with random strings and weights
        Term p = new Term("GTG", 514482);
        Term q = new Term("ATCGGTG", 265838);
        // should be positive
        StdOut.println(p.compareTo(q));
    }
}
