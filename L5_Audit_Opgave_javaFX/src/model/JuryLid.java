package model;
public class JuryLid {

	private int id;
	private String naam;
	
	public JuryLid() {
		id = 0;
		naam = "onbekend";
	}

	public JuryLid(int id, String naam) {
		this.id = id;
		this.naam = naam;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public boolean checkIdNummer(int idNummer){
		return this.id == idNummer;
	}
	
	@Override
	public String toString() {
		return id + ". " + naam;
	}
	
	

}
