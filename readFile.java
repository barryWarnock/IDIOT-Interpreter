import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
public class readFile extends Fileopen{

    public static String fileScan(File selectedFile) throws IOException{
    	Scanner scan = null;
    	 
    	
    	//scans file
		try {
			scan = new Scanner(selectedFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scan.hasNextLine()) {
            String line = scan.nextLine();
             return line; 
        }
		scan.close(); 
    	
		return null;
    	
    	
    }

	
	
	
	

}
