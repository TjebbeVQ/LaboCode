package main;
import java.util.Scanner;

import model.Afdeling;
import model.AfdelingenLijst;
import model.JuryLedenLijst;
import model.JuryLid;
import model.Score;

public class AuditMainNietGrafisch {

	static Scanner sc = new Scanner(System.in); 
	static AfdelingenLijst al = new AfdelingenLijst();
	static JuryLedenLijst jl = new JuryLedenLijst();

	public static void drukMenu() {
		System.out.println("Hoofdmenu" + System.getProperty("line.separator"));
		System.out.println("\t  1. Toevoegen afdeling");
		System.out.println("\t  2. Toevoegen jurylid");
		System.out.println("\t  3. Toevoegen alle scores");
		System.out.println("\t  4. Tonen afdelingenLijst");
		System.out.println("\t  5. Tonen juryleden");
		System.out.println("\t  6. Verwijderen afdeling");
		System.out.println("\t  7. Ordenen afdelingenlijst alfabetisch op naam");
		System.out.println();
		System.out.println("\t  8. Ordenen afdelingenlijst op inschrijvingsnummer (GEACTIVEERD)");
		System.out.println("\t  9. Ordenen afdelingenlijst op behaalde totale score (GEACTIVEERD)");
		System.out.println("\t 10. Zoek afdeling op naam en toon (GEACTIVEERD)");
		System.out.println("\t 11. Toon beste afdeling (GEACTIVEERD)");
		System.out.println("\t 12. Verwijder JuryLid en al zijn gegeven scores (GEACTIVEERD)");
		System.out.println();		
		System.out.println("\t 0. Stoppen");
		System.out.println();
		System.out.print("Keuze: ");
	}
	
	public static void wisScherm(){
		for (int i=0; i<100; i++) System.out.println();
	}
	
	public static void waitUntilKeypressed() {
		System.out.print("Druk <enter> om verder te gaan: ");
		sc.nextLine();
	}

	public static void voegAfdelingToe() {
		int idNummer;
		String naam;
		System.out.print("Idnummer: ");
		try {
			idNummer = Integer.parseInt(sc.nextLine());
			System.out.print("Naam: ");
			naam = sc.nextLine();
			Afdeling a = new Afdeling(idNummer, naam);
			al.voegAfdelingToe(a);
		} catch (NumberFormatException e){ System.out.println("Ongeldige waarde!");
		}
	}
	
	public static void verwijderAfdeling(){
		int idNummer;
		System.out.print("Idnummer: ");
		idNummer = Integer.parseInt(sc.nextLine());
		if (al.zoekAfdelingOpIdNummer(idNummer)<0) 
			System.out.println(idNummer + " bestaat niet");
		else{
			al.verwijderAfdeling(idNummer);
			System.out.println("deelnemer " + idNummer + " is verwijderd");
		}
	}

	// verwijder jurylid en al zijn gegeven scores
	public static void verwijderJuryLid(){
		int idNummer;
		System.out.print("Id-nummer: ");
		idNummer = Integer.parseInt(sc.nextLine());
		if (jl.zoekJuryLidOpIdNummer(idNummer)<0) 
			System.out.println(idNummer + " bestaat niet");
		else{
			jl.verwijderJuryLid(idNummer, al);
			System.out.println("jurylid " + idNummer + " is verwijderd");
		}
	}	
	
	public static void toonAfdeling(){
		String naam = null;
		System.out.print("Naam: ");
		naam = sc.nextLine();
		int index = al.zoekAfdelingOpNaam(naam);
		if (index<0) 
			System.out.println(naam + " bestaat niet");
		else{
			System.out.println(al.getAfdeling(index));
		}
	}
	
	public static void toonBesteAfdeling(){
		AfdelingenLijst w; //winnaars hulplijst 
		w = al.zoekWinnaars();
		System.out.println("De beste afdeling(en): ");
		System.out.println(w.toString());
	}
	
	public static void voegJuryLidToe() {
		int id;
		String naam;
		System.out.print("Id: ");
		id = Integer.parseInt(sc.nextLine());
		System.out.print("Naam: ");
		naam = sc.nextLine();
		JuryLid j = new JuryLid(id, naam);
		jl.voegJuryLidToe(j);
	}
	
	public static void voegAlleScoresToe(){
		// scores resetten: alle vroeger ingegeven scores verwijderen
		for (Afdeling d:al.getAfdelingen())
			d.getScorelijst().clear();
		// nu toevoegen van alle scores
		int waarde;
		for (int i=0; i<al.getAantalAfdelingen(); i++){
			Afdeling d = al.getAfdeling(i);
			System.out.println("score voor " + d.getNaam() + ": ");
			for (int j=0; j<jl.getAantalJuryLeden(); j++){		
				JuryLid lid = jl.getJuryLid(j);
				System.out.print("\t" + lid.getNaam() + ": ");
				waarde = Integer.parseInt(sc.nextLine());
				Score s = new Score(waarde, lid);
				d.voegScoreToe(s);
			}
		}
	}
	
	public static void main(String[] args) {
		int keuze;
		do {
			wisScherm();
			drukMenu();
			try {
				keuze = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) { keuze = -1;};
			switch (keuze){
				case 0: break;	
				case 1: voegAfdelingToe(); break;
				case 2: voegJuryLidToe(); break;
				case 3: voegAlleScoresToe(); break;
				case 4: wisScherm(); System.out.println(al.toString()); waitUntilKeypressed(); break;
				case 5: wisScherm(); System.out.println(jl.toString()); waitUntilKeypressed(); break;
				case 6: verwijderAfdeling(); waitUntilKeypressed(); break;
				case 7: al.sorteerOpNaamAfdeling(); System.out.println("Ok"); waitUntilKeypressed(); break;
				
				case 8: al.sorteerOpIdNummerAfdeling(); System.out.println("Ok"); waitUntilKeypressed(); break;
				case 9: al.sorteerOpTotaleAflopendeScoreAfdeling(); System.out.println("Ok"); waitUntilKeypressed(); break;
				case 10: toonAfdeling();waitUntilKeypressed(); break;
				case 11: toonBesteAfdeling();waitUntilKeypressed(); break;
				case 12: verwijderJuryLid();waitUntilKeypressed(); break;
				default: System.out.println("Ongeldige keuze!");waitUntilKeypressed();break;
			}
		} while (keuze != 0);
		System.out.println("Good bye");
	}
}
