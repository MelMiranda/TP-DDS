package domain;

import java.util.Date;

import service.AvailabilityService;

public class CGPService {

	private String serviceName;
	private RangeOfAtention rangeOfAtention;

	public CGPService(String serviceName, RangeOfAtention rangeOfAtention) {
		super();
		this.serviceName = serviceName;
		this.rangeOfAtention = rangeOfAtention;
	}

	public RangeOfAtention getRangeOfAtention() {
		return rangeOfAtention;
	}

	public void setRangeOfAtention(RangeOfAtention rangeOfAtention) {
		this.rangeOfAtention = rangeOfAtention;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public boolean isAvailable(Date date, AvailabilityService availabilityService){
		boolean available=availabilityService.isAvailability(this.getRangeOfAtention());
		return available;		
	}

}
