package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TestUtil {

    /**
     * <p>
     * Takes a series of {@code Object} arrays and returns all the possible permutations of
     * the specified arrays, where each permutation tuple contains an element from each array.
     * For example, given the arrays [a, b, c], [d] and [e, f], the resulting output would be:
     * <p>
     * <pre>
     *      [[a, d, e],
     *       [a, d, f],
     *       [b, d, e],
     *       [b, d, f],
     *       [c, d, e],
     *       [c, d, f]]
     * </pre>
     * <p>
     * The index of each value in a permutation tuple depends on the order of the specified arrays.
     * For example the index of the second array passed as an argument to this method would have an
     * index of 1.
     * </p>
     * <p>
     * Note that duplicate tuples may occur in the output depending on the values in the specified arrays.
     * </p>
     *
     * @param arrays The arrays to permute.
     * @return A {@code Collection} containing all the permutation tuples.
     */
    public static Collection<Object[]> permute(Object[]...arrays){
        List<Object[]> data = new ArrayList<Object[]>();
        Object[] tuple = new Object[arrays.length];
        permute(data, arrays, 0, tuple);

        return data;
    }

    /**
     * A helper method for the {@code permute} method, which recursively builds the {@code Collection}
     * of permutation tuples.
     *
     * @param data The {@code Collection} of permutation tuples.
     * @param arrays The arrays passed into the {@code permute} method.
     * @param index The index of the current array in {@code arrays} argument.
     * @param tuple The current tuple.
     */
    private static void permute(Collection<Object[]> data, Object[][] arrays, int index, Object[] tuple){
        if(index == arrays.length){
            // the current permutation is complete, clone the tuple and add it to the collection
            data.add(Arrays.copyOf(tuple, tuple.length));
        }
        else{
            // add each value of the current array into the tuple and recursively build the rest of the tuple
            for(Object value : arrays[index]){
                tuple[index] = value;
                permute(data, arrays, index + 1, tuple);
            }
        }
    }

}