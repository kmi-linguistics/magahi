/**
 * 
 */
package corpusUtils;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 * @author ritesh
 *
 */
public class recordMaintainer {
	
	static String TAB="\t";
	
	public void writeEditRecord (String originalFile, String editedFile, long originalFileSize, long editedFileSize) {
		try {
			String recordFilePath = originalFile.substring(0, originalFile.indexOf("original-corpus"))+"editing_record.xls";
			String currentLocation = editedFile.substring(editedFile.lastIndexOf("/", editedFile.lastIndexOf("/")-1)+1, editedFile.lastIndexOf("/"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter (new FileOutputStream (recordFilePath, true), "UTF-8"));
			String dateTime = getCurrentDateTime();			
			bw.write(dateTime+TAB+originalFile+TAB+convertFileSize(originalFileSize)+TAB+editedFile+TAB+convertFileSize(editedFileSize)+TAB+currentLocation);
			bw.newLine();
			bw.close();
		}
		catch (Exception e) {
			System.out.println ("recordMaintainer.writeEditRecord error: " + e);
		}
		return;
	}
	
	private String convertFileSize (long originalSize) {
		if(originalSize >= 1024) 
			return originalSize/1024 + " kb";
		else
			return originalSize + " bytes";
	}
	
	public String getCurrentDateTime () {
		String currentDateTime = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		currentDateTime = dateFormat.format(date);
		return currentDateTime;
	}
}
