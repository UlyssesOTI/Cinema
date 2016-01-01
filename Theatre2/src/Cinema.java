import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;


public class Cinema {
	
	private static Scanner s = new Scanner(System.in);

	Map<Integer,Movie> movieMap;
	private Map<Days, Schedule> WeekSchedule;
	private Time open;
	private Time close;
	private MovieDB mdb;
	
	public Cinema(Time open, Time close) {
		super();
		this.WeekSchedule = new TreeMap<Days, Schedule>();
		this.movieMap =  new TreeMap<Integer,Movie>();
		this.open = open;
		this.close = close;
		this.mdb = new MovieDB("jdbc:mysql://localhost:3306/cinema", "root","");
	}
	
	public void printMovieMap(){
		if(movieMap.isEmpty()){
			System.out.println("\nMovie Map is Empty!");
			return;
		}
			
		for (Entry<Integer,Movie> iterable_element : movieMap.entrySet()) {
			System.out.println(iterable_element);
		}
	}
	
	public static int inputInt(){
		int i=0;
		boolean wrong = true;
		do {
			try {
				i=s.nextInt();
				wrong = false;
			} catch (Exception e) {
				System.out.println("You entered wrong value! Enter integer value!\n");
				System.out.println("Input again:");
				s.nextLine();	
				wrong = true;
			}
			
		} while (wrong);
		
		return i;
	}
	
	static public void pressAnyKey() {
		System.out.println("\n   DONE!\n Press any key to continue.");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static String inputStr(){
		
		
		String str="";
		boolean wrong = false;			
		do {
			try {
				str=s.next();				
			} catch (Exception e) {
				System.out.println("You entered wrong value! Enter String value!\n");
				System.out.println("Input again:");
				s.nextLine();	
				wrong = true;
			}
			
		} while (wrong);
	
		return str;
	}
	
	public void addOneNewSeance() {
		System.out.println("Input day:");
		String day = inputStr();
		int i;
		int h;
		int m;
		Seance[] seances;
		do {
			System.out.println("\nInput id of Movie to add:");
			i =inputInt();
			Movie movie = movieMap.get(i);			
			if(movie==null){
				System.out.println("There is no movie with if = "+i);
				i = 1;
				continue;
			}
			System.out.println("\nInput Hour:");
			h =inputInt();
			System.out.println("\nInput Minute:");
			m =inputInt();
			Days d = Days.valueOf(day);
			Seance seance = new Seance(movie, new Time(h, m,d));
			addSeance(seance, day);
			i = 0;
		} while (i==1);
		
	}
	
	public void addNewSeances() {
		System.out.println("Input day:");
		String day = inputStr();
		int i;
		int h;
		int m;
		Seance[] seances;
		do {
			System.out.println("\nInput id of Movie to add:");
			i =inputInt();
			Movie movie = movieMap.get(i);			
			if(movie==null){
				System.out.println("There is no movie with if = "+i);
				continue;
			}
			System.out.println("\nInput Hour:");
			h =inputInt();
			System.out.println("\nInput Minute:");
			m =inputInt();
			Seance seance = new Seance(movie, new Time(h, m,Days.valueOf(day)));
			addSeance(seance, day);
			System.out.println("Done!\n");
			System.out.println("Input other one?");
			System.out.println("1 - yes");
			System.out.println("0 - no");
			i =inputInt();
		} while (i==1);
		
	}
	
	public int addSeances(String day, Seance... seances) {
		Schedule schedule = WeekSchedule.get(Days.valueOf(day));
		int q = 0;
		for (Seance seance2 : seances) {
			if(!schedule.addSeance(seance2)){
				System.out.println("Seance "+seance2+" CAN NOT be added to Schedule!");
			}
			else{
				q++;
			}
		}
		return q;
	}
	
	
	public boolean addSeance (Seance seance, String day){
		Schedule schedule = WeekSchedule.get(Days.valueOf(day));
		if(schedule==null){
			schedule = new Schedule();
			WeekSchedule.put(Days.valueOf(day), schedule);
		}
		if(!schedule.addSeance(seance)){
			System.out.println("Seance "+seance+" CAN NOT be added to Schedule!");
		}
		return true;
	}
	
	public void removeAllMovie(){
		System.out.println("\nInput id of Movie to remove:");
		int i =inputInt();
		Movie movie = movieMap.get(i);
		if(movie==null){
			System.out.println("There is no movie with if = "+i);
			return;
		}
		removeMovie(movie);
	}
	
	public boolean removeMovie(Movie movie){
		for (Entry<Days, Schedule> entry : WeekSchedule.entrySet()){
			Iterator<Seance> iterator = entry.getValue().getSeanceSet().iterator();
			while (iterator.hasNext()) {
				if(iterator.next().getMovie().compareTo(movie) == 0){
					iterator.remove();
				}				
			}
			
		}
		return true;
	}
	
	public boolean removeSeance(){
		System.out.println("Input day:");
		String day = inputStr();
		System.out.println("\nInput id of Seance to add:");
		int	i =inputInt();
		
		
		
		Schedule schedule = WeekSchedule.get(Days.valueOf(day));
		Iterator<Seance> iterator = schedule.getSeanceSet().iterator();
		while (iterator.hasNext()) {
			if(iterator.next().getId()==i){
				iterator.remove();
				return true;
			}				
		}
				
		return false;
	}
	
	public boolean isPlacedBetween(Time start, Time end){
		int absOpen;
		int absClose;
		int absStart;
		int absEnd; 
		if(this.close.compareTo(this.open)==0){
			return true;
		}	
		if(start.getDay().ordinal()==6 && end.getDay().ordinal()==0){
			absStart = start.getHour()*60+start.getMin()+start.getDay().ordinal()*24*60;
			absEnd = end.getHour()*60+end.getMin()+7*24*60;
		}else{
			absStart = start.getHour()*60+start.getMin()+start.getDay().ordinal()*24*60;
			absEnd = end.getHour()*60+end.getMin()+end.getDay().ordinal()*24*60;
		}
			
		
		if(this.close.compareTo(this.open)>0){
			absOpen = this.open.getHour()*60+this.open.getMin()+start.getDay().ordinal()*24*60;
			absClose =  this.close.getHour()*60+this.close.getMin()+start.getDay().ordinal()*24*60;			
		}else{
			absOpen = this.open.getHour()*60+this.open.getMin()+start.getDay().ordinal()*24*60;
			absClose =  this.close.getHour()*60+this.close.getMin()+(start.getDay().ordinal()+1)*24*60;
		}
		
		if(absStart>absOpen && absStart<absClose && absEnd>absOpen && absEnd<absClose){
			return true;
		}
			
		return false;
	}
	public int generateRandomMovies(){
		this.movieMap = Movie.generateMovies();
		return  this.movieMap.size();
	}
	
	public boolean generateRandomWeekSchedules(){
		this.WeekSchedule = new TreeMap<Days, Schedule>();
		this.movieMap =  new TreeMap<Integer,Movie>();
		this.movieMap = Movie.generateMovies();
		Schedule schedule = new Schedule();
		Random r = new Random();
		Time start;
		Movie movie;
		Seance seance;
		
		while(schedule.IsFreeTime(movieMap,this.open,this.close)){
		//for (int i = 0; i < 30; i++) {
			
			
			
			do {
					
				movie = movieMap.get(r.nextInt(Movie.filmNames.length));
				seance = new Seance(movie, null);
				start = new Time(r.nextInt(24), r.nextInt(60)/10*10, Days.values()[r.nextInt(7)]);
				seance.setStartTime(start);
				if (!isPlacedBetween(seance.getStartTime(), seance.getEndTime())) {
					//System.out.println("a");
					break;
				}
				
			} while ( !schedule.addSeance(seance));	
			//System.out.println("maxSpace= "+schedule.freeTimeSpace(open, close));
			//System.out.println(schedule);
		}
		
		//System.out.println(schedule);
		
		Days day = null;
		Schedule newschedule = new Schedule();
		for (Seance seances : schedule.getSeanceSet()) {
			if(seances.getStartTime().getDay() != day ){
				if(day!=null){
					this.WeekSchedule.put(day, newschedule);
				}
				newschedule = new Schedule();				
				day = seances.getStartTime().getDay();				
			}			
			newschedule.addSeance(seances);			
		}
		this.WeekSchedule.put(day, newschedule);
						
		return true;
	}
	
	public boolean addNewMovie() {
		System.out.println("Input movie name:");
		String name = inputStr();
		System.out.println("Input abs length:");
		int length = inputInt();
		Movie movie = new Movie(name, new Time(length/60, length%60));
		movieMap.put(movie.getId(), movie);
		return true;
	}

	
	
	
	@Override
	public String toString() {
		String str = "";
		if (WeekSchedule.isEmpty()){
			str = "\nWeek Shedule is empty!\n";
		}			
		else{
			str = "\nWeek Shedule:\n";
		}
		for (Entry<Days, Schedule> entry : WeekSchedule.entrySet()) {
			str += "\n "+entry.getKey()+" "+entry.getValue().toString()+"\n";
		}
		return str;
	}
	
	
	//====================================================================
	
	
	public void createTableCinema(){
		this.mdb.createTableMovie();
	}
	
	public void insertAllMovies(){
		for (Movie movie : movieMap.values()) {
			this.mdb.insertMovieInTable(movie.getId(),movie.getTitle(), movie.getDuration().getHour()*60+movie.getDuration().getMin());
		}
		
	}
	
	public void clearTable(){
			
		mdb.cleareTableMovie();
		
	}
	
	public void DeleteMovies(){
		System.out.println("Input length:");		
		mdb.DeleteMoviesInTableBy(inputInt());
		
	}
	
	public void readAllMovies(){
		List<Movie> movies = new ArrayList<Movie>();
		movies = mdb.findAllMovies();
		movieMap.clear();
		for (Movie movie : movies) {
			movieMap.put(movie.getId(), movie);
		}
		
	}
	
	public void readMoviesByLength(){
		List<Movie> movies = new ArrayList<Movie>();
		System.out.println("Input length:");		
		movies = mdb.findByLength(inputInt());
		movieMap.clear();
		for (Movie movie : movies) {
			movieMap.put(movie.getId(), movie);
		}
		
	}
	
	
	
}
