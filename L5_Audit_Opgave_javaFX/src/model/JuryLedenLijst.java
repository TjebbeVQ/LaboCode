package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class JuryLedenLijst extends Observable{

	private List<JuryLid> juryLeden;
	
	public JuryLedenLijst(){
		juryLeden = new ArrayList<JuryLid>();
	}
	
	public List<JuryLid> getJuryLeden() {
		return juryLeden;
	}
	
	public int getAantalJuryLeden(){
		return juryLeden.size();
	}
	
	public JuryLid getJuryLid(int index){
		return juryLeden.get(index);
	}
	
	public void voegJuryLidToe(JuryLid lid){
		juryLeden.add(lid);
		setChanged();
		notifyObservers();
	}
	
	public int zoekJuryLidOpIdNummer(int idNummer) {
		int aantal = juryLeden.size();
		boolean gevonden = false;
		int i = 0;
		while (i < aantal && !gevonden) {
			JuryLid lid = juryLeden.get(i);
			if (lid.checkIdNummer(idNummer))
				gevonden = true;
			else
				i++;
		}
		if (gevonden)
			return i;
		else
			return -1;
	}
	
	public void verwijderJuryLid(int idNummer, AfdelingenLijst al) {
		int index = this.zoekJuryLidOpIdNummer(idNummer);
		if (index >= 0){
			JuryLid lid = juryLeden.get(index);
			//vooraf verwijderen van de scores van betreffend jurylid
			//int aantal, i;
			//List<Score> sl;
		//	Score s;
			for (Afdeling a: al.getAfdelingen()){
				/*sl = a.getScorelijst();
				aantal = sl.size();
				for(i=0; i<aantal; i++){
					s = sl.get(i);
					if(s.getJury() == lid){
						sl.remove(i);
						aantal--;
					}
				}*/
				
				
				 for(int i=0; i<a.getScorelijst().size();i++){
				  		if(a.getScorelijst().get(i).getJury()==lid) {
				  			a.getScorelijst().remove(i);
				  			}
				  }
 
				//DIT MAG NIET:
				//for (Score s: a.getScorelijst()){
					//if (s.getJury() == lid); 
						//a.getScorelijst().remove(index);
						//geeft ConcurrentModificationException, nl. een element verwijderen
						//heeft invloed op de iterator die de for each gebruikt
				//}
			}
			//verwijderen van het juryLid
			juryLeden.remove(index);
			setChanged();
			notifyObservers();
		}
	}
	
	public String toString() {
		StringBuffer hulp = new StringBuffer();
		for (JuryLid lid:juryLeden)
			hulp.append(lid.toString() + System.getProperty("line.separator"));
		return hulp.toString();
	}
}
