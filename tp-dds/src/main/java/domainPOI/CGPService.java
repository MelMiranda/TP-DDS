package domainPOI;

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

}