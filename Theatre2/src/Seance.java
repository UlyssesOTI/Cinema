public class Seance implements Comparable<Seance>{
	
	private static int spacesid=0;
	
	private static IdNumerator idNumerator = new IdNumerator();
	
	private int id;
	private Movie movie;
	private Time startTime;
	private Time endTime;
	private boolean free = false;

	
	

	public Seance(Movie movie, Time startTime){
		super();
		this.id = idNumerator.index();
		spacesid = spaceChange(spacesid,((Integer)this.id).toString());
		this.movie  = movie;
		this.startTime = (startTime==null)? new Time(0, 0, Days.MON): startTime;
		setEndTime();
		this.free = false;
	}
	
	private int spaceChange(int i, String str){
		return i<=str.length() ?  str.length() : i;
	}
	
	private String spaceAdd(String str, int l){
		String sp = "";
		for (int j = 0; j < l-str.length(); j++) {
			sp+=" ";
		}
		return sp;
	}
	
	private void setEndTime(){
		int hours = 0;
		int mins = 0;
		Days day = this.startTime.getDay();
		if(this.startTime.getMin()+this.movie.getDuration().getMin()>59){
			hours = 1;
			mins = this.startTime.getMin()+this.movie.getDuration().getMin()-60;
		}else{
			hours = 0;
			mins = this.startTime.getMin()+this.movie.getDuration().getMin();
		}
		
		if(this.startTime.getHour()+this.movie.getDuration().getHour()+hours>23){
			hours+=this.startTime.getHour()+this.movie.getDuration().getHour()-24;
			if(day.ordinal()==6){
				day = Days.MON;
			}else{
				day =  Days.values()[day.ordinal()+1];
			}
		}else{
			hours+=this.startTime.getHour()+this.movie.getDuration().getHour();
		}
		this.endTime = new Time(hours,mins , day);
	}
	
	public Movie getMovie() {
		return movie;
	}
	
	public int getId() {
		return this.id;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
		setEndTime();
	}

	public Time getEndTime() {
		return endTime;
	}

	@Override
	public int compareTo(Seance arg0) {
		if(this.startTime.getDay().ordinal()-arg0.startTime.getDay().ordinal()!=0){
			return this.startTime.getDay().ordinal()-arg0.startTime.getDay().ordinal();
		}
		if(this.startTime.getHour()-arg0.startTime.getHour()!=0){
			return this.startTime.getHour()-arg0.startTime.getHour();
		}
		if(this.startTime.getMin()-arg0.startTime.getMin()!=0){
			return this.startTime.getMin()-arg0.startTime.getMin();
		}
		return 0;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((movie == null) ? 0 : movie.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		Seance other = (Seance) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (movie == null) {
			if (other.movie != null)
				return false;
		} else if (!movie.equals(other.movie))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Seance (id = "+this.id+spaceAdd(((Integer)this.id).toString(),spacesid)+") [Movie " + movie + " Starts : " + startTime + ", Ends : " + endTime + "]";
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		if(!this.free){
			idNumerator.freeIndex(this.id);
		}
		
		super.finalize();
	}
}
