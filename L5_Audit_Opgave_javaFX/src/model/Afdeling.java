package model;
import java.util.ArrayList;
import java.util.List;


public class Afdeling{
	
	private int id;
	private String naam;
	private List<Score> scoreLijst;
	
	public Afdeling() {
		id = 0;
		naam = null;
		scoreLijst = new ArrayList<Score>();
	}
	
	public Afdeling(int idNummer, String naam) {
		this.id = idNummer;
		this.naam = naam;
		scoreLijst = new ArrayList<Score>(); 
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int inschrijvingsNummer) {
		this.id = inschrijvingsNummer;
	}

	public List<Score> getScorelijst() {
		return scoreLijst;
	}

	public void setScoreLijst(List<Score> scorelijst) {
		this.scoreLijst = scorelijst;
	}

	public void voegScoreToe(Score s){
		scoreLijst.add(s);
	}
	
	public boolean checkId(int idNummer){
		return this.id == idNummer;
	}
	
	public boolean checkNaam(String naam){
		return this.naam.compareTo(naam) == 0;
	}
	
	public int getTotaleScore(){
		int totaal = 0;
		for(Score s:scoreLijst){	// for each score s uit de scoreLijst ...
			totaal += s.getWaarde();
		}
		return totaal;
	}
	
	@Override
	public String toString() {
		StringBuffer hulp = new StringBuffer(id + ". " + naam+ "\t");
		for (Score s:scoreLijst)
			hulp.append(s.toString());
		
		return hulp.toString() + System.getProperty("line.separator");
	}
}
