import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class IdNumerator {
	
	private int lastid;
	private Set<Integer> freeIndexes;
	//private Iterator<Integer> iterator;
	
	public IdNumerator() {
		super();
		this.lastid = 0;
		this.freeIndexes = new CopyOnWriteArraySet<Integer>();
	}
		
	public int index(){
		Integer id;
		if (this.freeIndexes.isEmpty()){
			id=this.lastid;
			this.lastid++;
		}else{
			Iterator<Integer> iterator;
			iterator = this.freeIndexes.iterator();
			id = iterator.next();
			freeIndexes.remove(id);			
		}
//		for (Integer integer : freeIndexes) {
//			System.out.print(integer+" ");
//		}
		return id;
	}
	
	public void freeIndex(int id){
		freeIndexes.add((Integer)id);
	}
	
	@Override
	public String toString() {
		return "IdNumerator [lastid=" + lastid + ", freeIndexes=" + freeIndexes + " ]";
	}
	
	

}
