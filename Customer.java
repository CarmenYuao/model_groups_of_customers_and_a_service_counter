
public class Customer {
	public int arrivaltime;
	public String id;
	public String arrival;
	public int waitime;
	public int leftime;
	public int T;
	
	
	public Customer(String id, String arrival,int T) {
		this.T=T;
		this.id=id;
		this.arrival=arrival;
		String[] time = arrival.split(":");
		int[] times = new int[3];
		for(int i=0;i<3;i++) {
			times[i]=Integer.parseInt(time[i]);
		}
		arrivaltime=times[0]*60*60+times[1]*60+times[2];
		if(arrivaltime>=32400 && arrivaltime<=61200) {
			this.waitime=0;
		}
		else if(arrivaltime<32400) {
			this.waitime=32400-arrivaltime;
		}
		else {
			waitime=0;
		}
		this.leftime= arrivaltime+waitime+T; 
		
	}
	
	public Customer(String id, String arrival,int earliest,int T) {
		this.id=id;
		this.arrival=arrival;
		String[] time = arrival.split(":");
		int[] times = new int[3];
		for(int i=0;i<3;i++) {
			times[i]=Integer.parseInt(time[i]);
		}
		if(times[0]<earliest) {
			times[0]=times[0]+12;
		}
		arrivaltime=times[0]*60*60+times[1]*60+times[2];
		
		
		
	}
	

}
