package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TestUtil {

    public static Collection<Object[]> permute(Object[]...arrays){
        List<Object[]> data = new ArrayList<Object[]>();
        Object[] tuple = new Object[arrays.length];
        permute(data, arrays, 0, tuple);

        return data;
    }

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