package controller.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import domain.Address;
import domain.Coordinate;
import domain.RangeOfAtention;
import internalService.PoiService;
import poi.Bank;
import poi.BusStation;
import poi.CGP;
import poi.CGPService;
import poi.Poi;
import users.Admin;
import users.Terminal;

@Controller
public class AdminController {

	private Admin admin;

	public AdminController() {
		admin = new Admin();
	}
	
	
	@RequestMapping(value = ("/terminal-add"), method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity addTerminal(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "lat", required = true) double lat,
			@RequestParam(value = "lon", required = true) double lon) {

		boolean state = admin.addTerminal(new Terminal(name,new Coordinate(lat,lon)));
		return new ResponseEntity(state, HttpStatus.OK);
	}
	
	@RequestMapping(value = ("/show-terminal"), method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity showTerminals() {

		List<String> terminals= new ArrayList<String>();
		for (Terminal currentTerminal: admin.getPoiService().getTerminales()){
			terminals.add(currentTerminal.getNombre());
		}
		return new ResponseEntity(terminals, HttpStatus.OK);
	}

	@RequestMapping(value = ("/terminal-remove"), method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity removeTerminal(
			@RequestParam(value = "name", required = true) String name) {

		boolean state = admin.removeTerminal(name);
		return new ResponseEntity(state, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = ("/reportByDate"), method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity reportByDate() {

		Map<String, Integer> reportsByDate= PoiService.getReportService().getReportesTotalesPorFecha();
		return new ResponseEntity(reportsByDate, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = ("/reportByTerminal"), method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity reportByTerminal(
			@RequestParam(value = "name", required = true) String name) {

		Map<String, Integer> reportsByDate= PoiService.getReportService().getParcialesPorTerminal(name);
		return new ResponseEntity(reportsByDate, HttpStatus.OK);
	}
	
	@RequestMapping(value = ("/all-reports"), method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity allReports() {
		Map<String, Integer> reportsByDate= PoiService.getReportService().getReportesTotalesTodasLasTerminales();
		return new ResponseEntity(reportsByDate, HttpStatus.OK);
	}
	
	
	
	
	
	


	@RequestMapping(value = ("/poi-remove"), method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity removePoi(
			@RequestParam(value = "name", required = true) String name) {

		boolean state = admin.removePoi(name);
		return new ResponseEntity(state, HttpStatus.OK);
	}

	@RequestMapping(value = ("/poi-modify"), method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity modifyPoi(
			@RequestParam(value = "name", required = true) String name) {
		Poi poi = null; // TODO Agregar muchos parametros y filtrar tipo por
						// type
		boolean state = admin.modifyPoi(poi, name);
		return new ResponseEntity(state, HttpStatus.OK);
	}

	
	
	@RequestMapping(value = ("/poi-addBank"), method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity addPoiBank(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "mainStreet", required = true) String mainStreet,
			@RequestParam(value = "lat", required = true) double lat,
			@RequestParam(value = "lon", required = true) double lon){

		Poi poi = new Bank(name,new Address(mainStreet), new Coordinate(lat,lon));

		admin.addPoi(poi);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = ("/poi-addBusStation"), method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity addPoiBusStation(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "mainStreet", required = true) String mainStreet,
			@RequestParam(value = "lat", required = true) double lat,
			@RequestParam(value = "lon", required = true) double lon,
			@RequestParam(value = "busNumber", required = true) String busNumber){
		// TODO Agregar muchos parametros y filtrar tipo por type

		Poi poi=new BusStation(name,new Address(mainStreet), new Coordinate(lat,lon),busNumber);
		admin.addPoi(poi);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = ("/poi-addCGP"), method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity addPoiCGP(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "mainStreet", required = true) String mainStreet,
			@RequestParam(value = "lat", required = true) double lat,
			@RequestParam(value = "lon", required = true) double lon,
			@RequestParam(value = "communeRadius", required = true) double communeRadius){
		// TODO Agregar muchos parametros y filtrar tipo por type

		Poi poi= new CGP(name,new Address(mainStreet), new Coordinate(lat,lon),communeRadius,new ArrayList<CGPService>());
		admin.addPoi(poi);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value=("/poi-addCGPServiceInTo"), method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity addPoiCGPService(
			@RequestParam(value = "nameOfCGP", required = true) String nameOfCGP,
			@RequestParam (value="serviceName", required= true) String serviceName,
			@RequestParam(value = "daysOfAttention", required = true) List<Integer> daysOfAttention){
		
		
		for(Integer index :daysOfAttention){
			System.out.println(index);
		}
		CGPService cgpService= new CGPService(serviceName,new RangeOfAtention(null,daysOfAttention));
		boolean status= admin.addCGPServiceToCGP(nameOfCGP,cgpService);
		return new ResponseEntity(status,HttpStatus.OK);
		
	}
	
	public ResponseEntity addScheduleToCGPService(
			@RequestParam(value = "nameOfCGP", required = true) String nameOfCGP,
			@RequestParam (value="serviceName", required= true) String serviceName,
			@RequestParam(value = "hourMax", required = true) String hourMax,
			@RequestParam(value="hourMin", required = true) String hourMin){
		
		boolean status =admin.addScheduleToCGPService(nameOfCGP, serviceName, hourMax, hourMin);
		return new ResponseEntity(status,HttpStatus.OK);
		
	}
	
	
	
	
}
