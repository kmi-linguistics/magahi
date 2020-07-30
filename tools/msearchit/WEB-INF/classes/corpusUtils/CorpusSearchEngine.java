/**
 * 
 */
package corpusUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ritesh
 *
 */

public class CorpusSearchEngine {
	String fileName = "";
	public CorpusSearchEngine(String readFile) {
		fileName=readFile.substring(readFile.lastIndexOf("/") + 1, readFile.lastIndexOf("."));
	}
	
	public Map<String, String> searchTheText (String searchText, int startIndex, Map<Integer, String> dataMap, boolean regex) {
		String key = "", value = "";
		Map<String, String> resultsMap = new LinkedHashMap<String, String>();
		if (startIndex < dataMap.size()) {
			for (int i = startIndex; i < dataMap.size(); i++) {
				if (dataMap.containsKey(i)) {
					String currentText = dataMap.get(i).trim();
					if (regex) {
						Pattern pt = Pattern.compile(searchText);
						Matcher m = pt.matcher(currentText);
						if (m.find()) {
							searchText = m.group();

							key = fileName + "\t" + i;
							value = currentText.substring(0, currentText.indexOf(searchText)) + "<font color=\"navy\"><b>" + searchText + "</b></font>" +
									currentText.substring(currentText.indexOf(searchText) + searchText.length());
							resultsMap.put(key, value);
						}
					}
					else {
						if (currentText.contains(searchText)) {
							key = fileName + "\t" + i;
							value = currentText.substring(0, currentText.indexOf(searchText)) + "<font color=\"navy\"><b>" + searchText + "</b></font>" +
									currentText.substring(currentText.indexOf(searchText) + searchText.length());
							resultsMap.put(key, value);
						}
					}
				}
			}
		}
		return resultsMap;
		
	}
}
