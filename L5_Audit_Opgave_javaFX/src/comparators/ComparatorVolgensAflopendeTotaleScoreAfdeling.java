package comparators;

import java.util.Comparator;

import model.Afdeling;

public class ComparatorVolgensAflopendeTotaleScoreAfdeling implements Comparator<Afdeling>{
	public int compare(Afdeling a1, Afdeling a2 ){
		return a2.getTotaleScore() - a1.getTotaleScore();
	}
	

}
