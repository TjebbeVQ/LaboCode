package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import comparators.ComparatorVolgensAflopendeTotaleScoreAfdeling;
import comparators.ComparatorVolgensIdNummerAfdeling;
import comparators.ComparatorVolgensNaamAfdeling;

public class AfdelingenLijst extends Observable {

	private List<Afdeling> afdelingen;

	public AfdelingenLijst() {
		afdelingen = new ArrayList<Afdeling>();
	}
	
	public List<Afdeling> getAfdelingen() {
		return afdelingen;
	}

	public void setAfdelingen(List<Afdeling> afdelingen) {
		this.afdelingen = afdelingen;
		setChanged();
		notifyObservers();
	}

	public Afdeling getAfdeling(int index) {
		return afdelingen.get(index);
	}

	public int getAantalAfdelingen() {
		return afdelingen.size();
	}
	
	public void voegAfdelingToe(Afdeling d) {
		afdelingen.add(d);
		setChanged();
		notifyObservers();
	}

	public int zoekAfdelingOpIdNummer(int inschrijvingsNummer) {
		int aantal = afdelingen.size();
		boolean gevonden = false;
		int i = 0;
		while (i < aantal && !gevonden) {
			Afdeling a = afdelingen.get(i);
			if (a.checkId(inschrijvingsNummer))
				gevonden = true;
			else
				i++;
		}
		if (gevonden)
			return i;
		else
			return -1;
	}

	public int zoekAfdelingOpNaam(String naam) {
		int aantal = afdelingen.size();
		boolean gevonden = false;
		int i = 0;
		while (i < aantal && !gevonden) {
			Afdeling a = afdelingen.get(i);
			if (a.checkNaam(naam))
				gevonden = true;
			else
				i++;
		}
		if (gevonden)
			return i;
		else
			return -1;
	}


	public AfdelingenLijst zoekWinnaars() {
		AfdelingenLijst winnaars = new AfdelingenLijst();;
		// if (afdelingen.size() != 0)
		int max = 0;
		
		for (Afdeling a : afdelingen) {
			if (a.getTotaleScore() > max) {
				max = a.getTotaleScore();
			}
		}
		
		for (Afdeling a : afdelingen) {
			if (a.getTotaleScore() == max) {
				winnaars.voegAfdelingToe(a);
			}
		}
		return winnaars;
		// }
	}
	
	public void verwijderAfdeling(int inschrijvingsNummer) {
		int index = this.zoekAfdelingOpIdNummer(inschrijvingsNummer);
		if (index >= 0)
			afdelingen.remove(index);
		setChanged();
		notifyObservers();
	}

	public void sorteerOpNaamAfdeling() {
		Collections.sort(afdelingen, new ComparatorVolgensNaamAfdeling());
		setChanged();
		notifyObservers();
	}

	public void sorteerOpTotaleAflopendeScoreAfdeling() {
		Collections.sort(afdelingen, new ComparatorVolgensAflopendeTotaleScoreAfdeling());
		setChanged();
		notifyObservers();
	}

	public void sorteerOpIdNummerAfdeling() {
		Collections.sort(afdelingen, new ComparatorVolgensIdNummerAfdeling());
		setChanged();
		notifyObservers();
	}

	@Override
	public String toString() {
		StringBuffer hulp = new StringBuffer();
		// int aantal = afdelingen.size();
		// for(int i=0; i<aantal; i++){
		// afdeling a = afdelingen.get(i);
		// hulp.append(a.toString());
		// }
		for (Afdeling a : afdelingen)
			hulp.append(a.toString());
		return hulp.toString();
	}
	
	public void refresh(){
		setChanged();
		notifyObservers();
	}

}
