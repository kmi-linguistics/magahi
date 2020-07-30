package corpusUtils;

import java.io.*;
import java.util.*;


public class SimpleFileReader{
	BufferedReader br2 = null;
	BufferedReader br1 =null;
	FileReader fr=null;
	static String TAB="\t";
	String line=null;
	String sourceData ="";
	String targetData ="";
	String sourceFile ="";
	String hKey;
	int hVal;
 	Map<Integer, String> ht1 = new HashMap<Integer, String>();
 	Map<String, String> ht = new HashMap<String, String>();
	String errMsg ="";
		
	public SimpleFileReader(String sFile){
		//ht=new Hashtable<Integer, String>();
		sourceFile=sFile;
	}
	
	public  Map<Integer, String> sFileReader(){
		try{
			    		
        	 br2 = new BufferedReader(new InputStreamReader (new FileInputStream (sourceFile), "UTF-8"));
    		int count=0;
    	    while((line = br2.readLine())!= null ){
				sourceData = "";
				targetData  = "";
				
				//sourceData = line.substring(0,line.indexOf(TAB)).trim();
				if (line.indexOf (TAB) > -1) {
					targetData = line.substring(line.indexOf(TAB),line.length()).trim();
					if(count>0){
						ht1.put(count, targetData);
					}
				}
				else {
					targetData = line;
					ht1.put (count+1, targetData);
				}

				 count++;
    		}
    
    		br2.close();
    	}
    	catch (Exception ex) {
			String errMsg = "error in reading file sFileReader... " + ex.toString() ;
			try {
				br2.close();
			}
			catch (Exception cl) {
			}
			System.out.println(errMsg);
			
		}
		return ht1;	
	}
		
	public  Map<String, String> sRFileReader(){
		try{
			    		
        	 br1 = new BufferedReader(new FileReader(sourceFile));
    		
    	    while((line = br1.readLine())!= null ){
				sourceData = "";
				targetData  = "";
				
				sourceData = line.substring(0,line.indexOf(TAB)).trim();
				targetData = line.substring(line.indexOf(TAB),line.length()).trim();	
				ht.put(targetData,sourceData);
				System.out.println(targetData+"	:		: "+sourceData);
    		}
    
    		br1.close();
    	}
    	catch (Exception ex) {
			String errMsg = "error in reading file sFileReader... " + ex.toString() ;
			try {
				br1.close();
			}
			catch (Exception cl) {
			}
			System.out.println(errMsg);	
		}
		return ht;	
	}
}	
