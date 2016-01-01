import java.io.IOException;
import java.util.Map;
import java.util.Random;

import javax.print.attribute.standard.SheetCollate;
import javax.rmi.ssl.SslRMIClientSocketFactory;

public class Main {
	
	

	public static void main(String[] args) {
//
		Cinema cinema = new Cinema(new Time(8, 00), new Time(23, 00));
//		System.out.println(cinema.isPlacedBetween(new Time(22, 20, Days.SUN), new Time(01, 29, Days.MON)));
//			
////			
			int i=0;
			
			while(true){			
				System.out.println("\nOperations with a ZooClub:");
				System.out.println("1 - Add new Seances");
				System.out.println("2 - Add One new Seance");
				System.out.println("3 - Remove Movie from all seances");
				System.out.println("4 - Remove Seances");
				System.out.println("5 - Print all Movies");			
				System.out.println("6 - Print all Schedules");
				System.out.println("7 - Add random Movies");			
				System.out.println("8 - Add random Schedules");
				System.out.println("9 - Add new Movie");
				
				System.out.println("10 - Create Table Seance");
				System.out.println("11 - Isert all Movies to Table");
				System.out.println("12 - Read Movies from table");
				System.out.println("13 - Read Movies from table by length diapazon");
				System.out.println("14 - Delete Movies from table by length diapazon");
				
				System.out.println("0 - exit");
				System.out.print("Input Operation:");		
					
				do {
					i = Cinema.inputInt();
					if(!(i>=0 && i<=14)){
						System.out.println("\nYou entered wrong digit!\n\t Try again!\n");
					}else{
						break;
					}
				} while (true);
					
				
				switch(i){
			
				case 1:  cinema.addNewSeances();
					Cinema.pressAnyKey();
					break;
				case 2: cinema.addOneNewSeance();
					Cinema.pressAnyKey();
					break;
				case 3: cinema.removeAllMovie(); 
					Cinema.pressAnyKey();
					break;
				case 4:  cinema.removeSeance();
					Cinema.pressAnyKey();
					break;
				case 5: 
					cinema.printMovieMap();
					Cinema.pressAnyKey();
					break;
				case 6: 
					System.out.println(cinema.toString());
					Cinema.pressAnyKey();
					break;
				case 7: 
					cinema.generateRandomMovies();
					Cinema.pressAnyKey();
					break;
				case 8: 
					cinema.generateRandomWeekSchedules();
					Cinema.pressAnyKey();
					break;
				case 9: 
					cinema.addNewMovie();
					Cinema.pressAnyKey();
					break;
					
				case 10: 
					cinema.createTableCinema();
					Cinema.pressAnyKey();
					break;
					
				case 11: cinema.clearTable();
					cinema.insertAllMovies();
					Cinema.pressAnyKey();
					break;
					
				case 12: 
					cinema.readAllMovies();
					Cinema.pressAnyKey();
					break;
				case 13: 
					cinema.readMoviesByLength();
					Cinema.pressAnyKey();
					break;
					
				case 14: 
					cinema.DeleteMovies();
					Cinema.pressAnyKey();
					break;
					
					
					
				case 0:
					System.out.print("\nEND!");
					return;
				default:
					System.out.print("\nEND!");
					return;	
				}	
			}	
	
	}
	

}
