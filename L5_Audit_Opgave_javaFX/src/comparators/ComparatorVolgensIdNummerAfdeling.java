package comparators;

import java.util.Comparator;

import model.Afdeling;


public class ComparatorVolgensIdNummerAfdeling implements Comparator<Afdeling>{
	public int compare(Afdeling a1, Afdeling a2 ){
		return a1.getId() - a2.getId();
	}
	

}
