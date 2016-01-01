import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Movie implements Comparable<Movie>{
	
	static String[] filmNames = {
			"The Shawshank Redemption",
			"The Godfather"	,
			"The Godfather: Part II",
			"The Dark Knight",
			"Pulp Fiction",
			"12 Angry Men",
			"Schindler's List",
			"Il buono, il brutto, il cattivo",
			"The Lord of the Rings: II",
			"Fight Club",
			"The Lord of the Rings: I",
			"Star Wars: Episode V ",
			"Forrest Gump",
			"Inception",
			"Flew Over the Cuckoo\'s Nest",
			"Se7en"};

	private static int spacesTitle=0;
	private static int spacesid=0;
	
	private static IdNumerator idNumerator = new IdNumerator();
	
	private int id;
	private String title;
	private Time duration;
	private boolean free = false;
	
	
	public static Map<Integer,Movie>  generateMovies(){
		Map<Integer,Movie> movieMap = new TreeMap<Integer,Movie>();	
		Time duration;
		Movie movie;
		Random r = new Random();
		for (int i = 0; i < filmNames.length; i++) {
			duration = new Time(r.nextInt(3)+1, r.nextInt(30)/10*10+30);
			movie = new Movie(filmNames[i], duration);
			movieMap.put((Integer)movie.getId(), movie);
		}
		return movieMap;
	}
	
	public Movie(String title, Time duration){
		super();
		this.id = idNumerator.index();
		spacesid = spaceChange(spacesid,((Integer)this.id).toString());
		this.title = title;
		spacesTitle = spaceChange(spacesTitle,this.title);
		this.duration = duration;	
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
	
	public int getId(){
		return this.id;
	} 
	
	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	} 

	public void setDuration(Time duration){
		this.duration = duration;
	}

	public Time getDuration(){
		return this.duration;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Movie other = (Movie) obj;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString(){
		return "(id="+spaceAdd(((Integer)this.id).toString(),spacesid)+this.id+") \""+this.title+"\""+ spaceAdd(this.title,spacesTitle) + " (Duration "+this.duration+ ")";
	}

	@Override
	public int compareTo(Movie o) {
		
		return this.id-o.id;
	}
	
	@Override
	protected void finalize() throws Throwable {
		if(!this.free){
			idNumerator.freeIndex(this.id);
		}
		
		super.finalize();
	}


}