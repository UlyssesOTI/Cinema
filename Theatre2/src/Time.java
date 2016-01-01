public class Time implements Comparable<Time>{
	
	private int min;
	private int hour;
	private Days day;

	public Time(int hour,int min,Days day){
		super();
		this.hour = (hour>=0 && hour<=23)? hour : 0;	
		this.min = (min>=0 && min<=59)? min : 0;
		this.day = day;
	}
	
	public Time(int hour,int min){
		super();
		this.hour = (hour>=0 && hour<=23)? hour : 0;	
		this.min = (min>=0 && min<=59)? min : 0;
		this.day = Days.MON;
	}

	public void setMin(int min){
		this.min = (min>=0 && min<=59)? min : 0;
	}

	public int getMin(){
		return this.min;
	}

	public void setHour(int hour){
		this.hour = (hour>=0 && hour<=23)? hour : 0;
	}

	public int getHour(){
		return this.hour;
	}
	
	public Days getDay() {
		return day;
	}

	public void setDay(Days day) {
		this.day = day;
	}
	
	public int[] difference(Time o) {
		int i = this.compareTo(o);
		int[] arr = {0,0,0};
		arr[0] = i/(24*60);
		arr[1] = (i-arr[0]*24*60)/60;
		arr[2] = (i-arr[0]*24*60-arr[1]*60);
		return arr;
	}
	
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + hour;
		result = prime * result + min;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		if (day != other.day)
			return false;
		if (hour != other.hour)
			return false;
		if (min != other.min)
			return false;
		return true;
	}
	
	
	@Override
	public int compareTo(Time o) {
//		if (this.day == Days.SUN && o.day == Days.MON){
//			return (6*24*60+this.hour*60+this.min)-(7*24*60+o.hour*60+o.min);
//		}
//		if(this.day == Days.MON && o.day == Days.SUN){
//			return (7*24*60+this.hour*60+this.min)-(6*24*60+o.hour*60+o.min);
//		}
		return (this.day.ordinal()*24*60+this.hour*60+this.min)-(o.day.ordinal()*24*60+o.hour*60+o.min);
	}

	@Override
	public String toString(){
		return ""+((this.day == null)? "" : this.day+" ") +
				((((Integer)(this.hour)).toString().length()==1)? "0"+this.hour : this.hour)+":"+
				((((Integer)(this.min)).toString().length()==1)? "0"+this.min : this.min);
	}

}