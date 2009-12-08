package groovy.util;

/**
 * Systematically generate permutations.
 *
 * Adapted from Java Code by Michael Gilleland (released with no restrictions) using an algorithm described here:
 * Kenneth H. Rosen, Discrete Mathematics and Its Applications, 2nd edition (NY: McGraw-Hill, 1991), pp. 282-284
 */
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PermutationGenerator<E> implements Iterator<List<E>> {
    private int[] a;
    private BigInteger numLeft;
    private BigInteger total;
    private List<E> items;

    /**
     * WARNING: Don't make n too large.
     * Recall that the number of permutations is n!
     * which can be very large, even when n is as small as 20 --
     * 20! = 2,432,902,008,176,640,000 and
     * 21! is too big to fit into a Java long, which is
     * why we use BigInteger instead.
     *
     * @param items the items to permute
     */
    public PermutationGenerator(Collection<E> items) {
        this.items = new ArrayList<E>(items);
        int n = items.size();
        if (n < 1) {
            throw new IllegalArgumentException("At least one item required");
        }
        a = new int[n];
        total = getFactorial(n);
        reset();
    }

    public void reset() {
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        numLeft = new BigInteger(total.toString());
    }

    public BigInteger getTotal() {
        return total;
    }

    public boolean hasNext() {
        return numLeft.compareTo(BigInteger.ZERO) == 1;
    }

    /**
     * Compute factorial (TODO: expose this)
     *
     * @param n the input integer
     * @return the factorial for n
     */
    private static BigInteger getFactorial(int n) {
        BigInteger fact = BigInteger.ONE;
        for (int i = n; i > 1; i--) {
            fact = fact.multiply(new BigInteger(Integer.toString(i)));
        }
        return fact;
    }

    /**
     * Generate next permutation (algorithm from Rosen p. 284)
     *
     * @return the items permuted
     */
    public List<E> next() {
        if (numLeft.equals(total)) {
            numLeft = numLeft.subtract(BigInteger.ONE);
            return items;
        }

        int temp;

        // Find largest index j with a[j] < a[j+1]
        int j = a.length - 2;
        while (a[j] > a[j + 1]) {
            j--;
        }

        // Find index k such that a[k] is smallest integer
        // greater than a[j] to the right of a[j]
        int k = a.length - 1;
        while (a[j] > a[k]) {
            k--;
        }

        // Interchange a[j] and a[k]
        temp = a[k];
        a[k] = a[j];
        a[j] = temp;

        // Put tail end of permutation after jth position in increasing order
        int r = a.length - 1;
        int s = j + 1;

        while (r > s) {
            temp = a[s];
            a[s] = a[r];
            a[r] = temp;
            r--;
            s++;
        }

        numLeft = numLeft.subtract(BigInteger.ONE);
        List<E> ans = new ArrayList<E>(a.length);
        for (int index : a) {
            ans.add(items.get(index));
        }
        return ans;
    }

    public void remove() {
        throw new UnsupportedOperationException("remove() not allowed for PermutationGenerator");
    }
}
