package corpusUtils;

import java.io.*;


 public class UserValidator{
	
	public String user="";
	public String fileName="";
	public 	boolean check=false;
	String errMsg ="";
	
	BufferedReader fr = null;
	
	public UserValidator(String userPass,String userFile){
		user=userPass;
		fileName=userFile;				
	}
	
	public boolean checkUser(){
	
		String line=null;
		errMsg = "";
		
		try {
			
			fr = new BufferedReader(new InputStreamReader( new FileInputStream(fileName), "utf-8") );			

			while ((line = fr.readLine() ) != null ) {
				//System.out.println("Line : "+line);
				if(user.equals(line.trim())){
					check=true;
					break;
				}
        	}
		}

		catch (Exception ex) {
			errMsg = "error in reading file... " + ex.toString() ;
			try {
				fr.close();
			}
			catch (Exception e) {
			}
			System.out.println(errMsg);	
		}
		
		return check;
		
	}
	

}