package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import airline.Scheduler;



public class DataBaseInterface {
	
	private static final String FILE_NAME = "data/scheduler.ser";
	
	public static Scheduler LoadScheduler(){
		
		File file = new File(FILE_NAME);
		if(!file.exists()) {
		    try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return new Scheduler();
		}else{ 
		
			Scheduler s = null;
		      try
		      {
		         FileInputStream fileIn = new FileInputStream(FILE_NAME);
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         s = (Scheduler) in.readObject();
		         in.close();
		         fileIn.close();
		      }catch(IOException i)
		      {
		         i.printStackTrace();
		         
		      }catch(ClassNotFoundException c)
		      {
		         System.out.println("Scheduler class not found");
		         c.printStackTrace();
		         
		      }
			
			
			
			return s;
		}
		
	}
	
	
	public static void SaveScheduler(Scheduler scheduler){
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(FILE_NAME);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(scheduler);
	         out.close();
	         fileOut.close();
	         System.out.println("Serialized data is saved in data/schehuler.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	   }

}
