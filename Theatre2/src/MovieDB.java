import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDB {
	
	private Connection connection;
	
	private PreparedStatement ps;

	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public MovieDB(String url, String user, String password) {
		try {
			this.connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("exception");
		}
	}

	
	public void createTableMovie() {
		try {
			ps = connection.prepareStatement("create table if not exists Movie (id int not null, name varchar(45), duration int, primary key(id))");
			ps.execute(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void cleareTableMovie() {
		try {
			
			ps = connection
					.prepareStatement("DELETE from movie");
		
			
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	
	public Movie insertMovieInTable(int id,String name, int duration) {
		
		
		Movie movie = new Movie(name, new Time(duration/60, duration%60));
		try {
			
			ps = connection
					.prepareStatement("insert into movie(id,name, duration) values (?, ?, ?)");
		
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setInt(3, duration);
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movie;
	}
	
	public boolean DeleteMoviesInTableBy(int duration) {
		try {
			
			ps = connection
					.prepareStatement("DELETE from movie where duration >= ?");
		
			
			ps.setInt(1, duration);
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Movie> findAllMovies() {
		List<Movie> movies = new ArrayList<Movie>();
		ResultSet results = null;
		try {
			ps = connection.prepareStatement("select * from movie");
			
			results = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (results.next()) {
				Movie p;
				
				p = new Movie(results.getString("name"), new Time(results.getInt("duration")/60, results.getInt("duration")%60));
				//p.setId(results.getInt("id"));
				movies.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movies;
	}

	public List<Movie> findByLength(int duration) {
		List<Movie> movies = new ArrayList<Movie>();
		ResultSet results = null;

		if (duration < 0 ) {
			duration = Math.abs(duration);
		}
		

		try {
			ps = connection
					.prepareStatement("select * from movie where duration <= ? ");
			ps.setInt(1, duration);
			results = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (results.next()) {
				Movie p;
				p = new Movie(results.getString("name"), new Time(results.getInt("duration")/60, results.getInt("duration")%60));
				//p.setId(results.getInt("id"));
				movies.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movies;
	}

}
