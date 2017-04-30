package comparators;

import java.util.Comparator;

import model.Afdeling;

public class ComparatorVolgensNaamAfdeling implements Comparator<Afdeling>{
	public int compare(Afdeling a1, Afdeling a2 ){
		return a1.getNaam().compareToIgnoreCase(a2.getNaam());
	}
	

}
