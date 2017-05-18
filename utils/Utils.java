package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//This class is just something like a helper
public class Utils {

	public static String loadFileAsString(String path){
		
		//StringBuilder allows you to add chars to the String easily
		StringBuilder builder = new StringBuilder(); 
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null){ //As long as we have not reach the end of the file
				builder.append(line + "\n");			
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	//Just for parsing a number
	public static int parseInt(String number){
		try{
			return Integer.parseInt(number);
		} catch(NumberFormatException e){
			e.fillInStackTrace();
			return 0;
		}
	}
	
}
