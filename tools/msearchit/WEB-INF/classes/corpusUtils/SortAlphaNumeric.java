package corpusUtils;

import java.util.*;
/**
 * 
 */

/**
 * @author ritesh
 * This class return a sorted List of the file names which end in an integer preceeded by an underscore sign(_)
 */
public class SortAlphaNumeric implements Comparator<String> {
	@Override
	public int compare (String o1, String o2) {
		Integer o1Number = Integer.parseInt(o1.substring (o1.lastIndexOf("_")+1, o1.lastIndexOf(".")));
		Integer o2Number = Integer.parseInt(o2.substring (o2.lastIndexOf("_")+1, o2.lastIndexOf(".")));
		return o1Number.compareTo(o2Number);
	}
}
