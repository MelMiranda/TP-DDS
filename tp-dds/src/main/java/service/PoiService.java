package service;

import java.util.ArrayList;
import java.util.List;

import domainPOI.Coordinate;
import domainPOI.Poi;

public class PoiService {
	private PoiDatabaseManager databaseManager;
	
	
	
	public Poi searchPoi(String poiName){
		return databaseManager.searchPoi(poiName);
		
		
	}
	
	public List<String> obtainPoiInformation(){
		return new ArrayList<String>();
	}
	
	private void saveSearch(String poiName){
		
	}

	public String poiType(Poi poi){
		return poi.getType();
	}
	
	public Coordinate poiCoordinate(Poi poi){
		return poi.getCordinate();
	}
	public boolean areNear(String poiName1, String poiName2){
		Poi poi1 =searchPoi(poiName1);
		Poi poi2=searchPoi(poiName2);
		return poi1.isNearby(poi2.getCordinate());
	}
	
	public boolean isEnable(String poiName){
		Poi poi= searchPoi(poiName);
		return poi.isEnable();
	}
}