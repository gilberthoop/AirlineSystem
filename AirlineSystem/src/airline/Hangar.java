package airline;

import java.util.ArrayList;
import java.util.List;

import database.DataBaseInterface;

public class Hangar implements java.io.Serializable{

	private List<Plane> planes = new ArrayList<Plane>();
	/*
	public void loadPlanesFromDB(){
		/**
		 * build the list of planes
		 */
		
		/*
		if(planes.size() == 0){
			Plane plane1 = new Plane("xxy", "fastPlane" , 10, 10, 30, 0, 200);
			Plane plane2 = new Plane("xxy", "slowPlane" , 50, 10, 50, 1, 100);
			Plane plane3 = new Plane("xxy", "coolPlane" , 30, 30, 30, 2, 150);
			
			addPlane(plane1);
			addPlane(plane2);
			addPlane(plane3);
		}
		
		
	
	}
*/
	/*
	private void savePlanesToDB(){
		//TODO save all the planes to the data base
	}
	*/
	public List<Plane>getAllPlanes(){
		return planes;
	}
	/*
	public List<Plane> getPlanes(int MinSeats){
		//TODO get all planes that match the min seat requirment
		return planes;
	}
	*/
	private boolean planeConflict(Plane plane){
		
		for(int i=0;i<planes.size();i++){
			if(planes.get(i).equals(plane))
					return true;
		}
		
		return false;
	}
	
	public boolean addPlane(Plane plane){
		//
		if(!planeConflict(plane)){
			planes.add(plane);
			return true;
		}
		return false;
		
		 
	}
	public int size(){
		return planes.size();
	}
	public Plane getPlane(int idx){
		return planes.get(idx);
	}
	
}
