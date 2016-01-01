
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;

public class Schedule {
	
	private Set<Seance> seanceSet;

	public Schedule() {
		super();
		this.seanceSet = new TreeSet<Seance>();
	}

	public Set<Seance> getSeanceSet() {
		return seanceSet;
	}
	
	class Struct{
		int start;
		int end;
		
		public Struct(int start,int end) {
			this.start = start;				
			this.end = end;
		}
	}
	
	public int freeTimeSpace(Time open, Time close){
		
		int space=0;
		
		List<Struct> list = new ArrayList<Struct>();
		int absbefore = 0;
		int absOpen;
		
		
		if(open.compareTo(close)!=0){
			
			for (int i = 0; i < 7; i++) {
				if(close.compareTo(open)>0){					
					absOpen = open.getHour()*60+open.getMin()+i*24*60;									
					list.add(new Struct(absbefore,absOpen));					
					absbefore =  close.getHour()*60+close.getMin()+open.getMin()+i*24*60;
												
				}else{
					absOpen = open.getHour()*60+open.getMin()+i*24*60;									
					list.add(new Struct(absbefore,absOpen));					
					absbefore =  close.getHour()*60+close.getMin()+open.getMin()+(i+1)*24*60;
				}				
			}
		}
		
		
		
		
		int start;
		int end;
		for (Seance seance : seanceSet) {			
			start = seance.getStartTime().getHour()*60+seance.getStartTime().getMin()+seance.getStartTime().getDay().ordinal()*24*60;
			end = seance.getEndTime().getHour()*60+seance.getEndTime().getMin()+seance.getEndTime().getDay().ordinal()*24*60;
			for (int i = 0; i < list.size()-1; i++) {
				if(list.get(i).end < start && list.get(i+1).start>end){
					list.add(i+1,new Struct(start,end));
					break;
				}
			}
						
		}
		
//		for (Struct struct : list) {
//			System.out.println(struct.start+" - "+struct.end);
//		}
		
		space = 0;
		for (int i = 0; i < list.size()-1; i++) {
			if(space<list.get(i+1).start-list.get(i).end){
				space = list.get(i+1).start-list.get(i).end;
			}
		}
		
		if (list.get(list.size()-1).start<6*24*60 && space<6*24*60-list.get(list.size()-1).start){
			space = 6*24*60-list.get(list.size()-1).start;
		}
		
				
		return space;
		
	}
	
	public boolean IsFreeTime(Map<Integer,Movie>  movieMap, Time open, Time close){
	
		int minDurring = 20000;
		for (Entry<Integer, Movie> entry : movieMap.entrySet()) {
			if(entry.getValue().getDuration().getHour()*60+entry.getValue().getDuration().getMin()<minDurring){
				minDurring = entry.getValue().getDuration().getHour()*60+entry.getValue().getDuration().getMin();
			}
		}
		if(minDurring*2>=freeTimeSpace(open, close)){
			return false;
		}
		return true;
	}
		
	public boolean isPlacedBetween(Time start, Time end,Time open,Time close){
//		int absOpen;
//		int absClose;
//		int absStart;
//		int absEnd; 
//		if(close.compareTo(open)==0){
//			return true;
//		}	
//		if(start.getDay().ordinal()==6 && end.getDay().ordinal()==0){
//			absStart = start.getHour()*60+start.getMin()+start.getDay().ordinal()*24*60;
//			absEnd = end.getHour()*60+end.getMin()+7*24*60;
//		}else{
//			absStart = start.getHour()*60+start.getMin()+start.getDay().ordinal()*24*60;
//			absEnd = end.getHour()*60+end.getMin()+end.getDay().ordinal()*24*60;
//		}
//			
//		
//		if(close.compareTo(open)>0){
//			absOpen = open.getHour()*60+this.open.getMin()+start.getDay().ordinal()*24*60;
//			absClose =  this.close.getHour()*60+this.close.getMin()+start.getDay().ordinal()*24*60;			
//		}else{
//			absOpen = this.open.getHour()*60+this.open.getMin()+start.getDay().ordinal()*24*60;
//			absClose =  this.close.getHour()*60+this.close.getMin()+(start.getDay().ordinal()+1)*24*60;
//		}
//		
//		if(absStart>absOpen && absStart<absClose && absEnd>absOpen && absEnd<absClose){
//			return true;
//		}
//			
		return false;
	}
	
	public boolean addSeance(Seance seance){
		
		for (Seance seancei : seanceSet) {
			Time seanceiEnd = seancei.getEndTime();
			Time seanceEnd = seance.getEndTime();
			if(seancei.getStartTime().getDay().ordinal()==6 && seancei.getEndTime().getDay().ordinal()==0){
				seanceiEnd = new Time(23, 59, Days.SUN);
			}
			if(seance.getStartTime().getDay().ordinal()==6 && seance.getEndTime().getDay().ordinal()==0){
				seanceEnd = new Time(23, 59, Days.SUN);
			}
			if((seance.getStartTime().compareTo(seancei.getStartTime())>=0 && seance.getStartTime().compareTo(seanceiEnd)<=0) || 
					(seanceEnd.compareTo(seancei.getStartTime())>=0 && seanceEnd.compareTo(seanceiEnd)<=0) || 
					(seance.getStartTime().compareTo(seancei.getStartTime())<=0 && seanceEnd.compareTo(seanceiEnd)>=0)){
	
				return false;
			}
		}
		this.seanceSet.add(seance);
		return true;
	}
	
	public void removeSeance(Seance seance){
		this.seanceSet.remove(seance);
	}

	@Override
	public String toString() {
		String str = "";
		if (seanceSet.isEmpty()){
			str = "\nShedule is empty!\n";
		}			
		else{
			str = "Shedule:\n";
		}
		int i = 1;
		Time startSecond = null;
		Time endFirst = null;
		for (Seance seance : seanceSet) {
			startSecond = seance.getStartTime();
			
			str +="\n"+i+++" "+ seance.toString();
			if(endFirst!=null){
				str +=" "+startSecond.compareTo(endFirst)+"\n";
			}
			else{
				str +="\n";
			}
			endFirst =seance.getEndTime();
		}
		return str;
	}
	 
}
