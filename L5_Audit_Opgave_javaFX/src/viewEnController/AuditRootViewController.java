package viewEnController;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import exceptions.ReedsAanwezigException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import model.Afdeling;
import model.AfdelingenLijst;
import model.JuryLedenLijst;
import model.JuryLid;
import model.Score;

import java.sql.*;

public class AuditRootViewController implements Observer {

	private AfdelingenLijst al;
	private JuryLedenLijst jl;
	private Stage stage;
	
//SQL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/AUDIT";
	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	
	public void setAfdelingenLijst(AfdelingenLijst al) {
		this.al = al;
	}

	public void setJuryLedenLijst(JuryLedenLijst jl) {
		this.jl = jl;
	}
	
	public void setStage(Stage stage){
		this.stage = stage;
	}	
	
	
	@FXML
	private TextArea tekstAreaJuryLeden;

	@FXML
	private TextArea tekstAreaAfdelingen;	

	@FXML
	void toevoegenAfdelingen(ActionEvent event) {
		//http://code.makery.ch/blog/javafx-dialogs-official/
		// inlezen van het afdelingsnummer
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(stage); //nodig om bij een gemaximaliseerde toepassing goed te verschijnen
		dialog.setTitle("Toevoegen");
		dialog.setHeaderText("Afdeling");
		dialog.setContentText("Nummer");
		dialog.setGraphic(null);
		int id = 0;
		String naam = null;
		try {
			id = Integer.parseInt(dialog.showAndWait().get());
			//controleren of reeds aanwezig
			if(al.zoekAfdelingOpIdNummer(id)>=0) throw new ReedsAanwezigException("ID bestaat al!"); 
			
			// inlezen van de afdelingsnaam
			//als id al ongeldig was, moet naam niet meer gevraagd worden.
			dialog = new TextInputDialog();
			dialog.initOwner(stage);
			dialog.setTitle("Toevoegen");
			dialog.setHeaderText("Afdeling");
			dialog.setContentText("Naam");
			dialog.setGraphic(null);
			
			try {
				naam = dialog.showAndWait().get();
				if (naam.isEmpty()) throw new Exception("U hebt geen naam opgegeven.");
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Waarschuwing");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		} 
		catch(NumberFormatException nfe){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fout");
			alert.setHeaderText("Ongeldig nummer");
			alert.setContentText("De afdeling zal niet worden toegevoegd!");
			alert.showAndWait();
		}
		catch(ReedsAanwezigException rae){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Waarschuwing");
			alert.setHeaderText(null);
			alert.setContentText(rae.getMessage());
			alert.showAndWait();
		}
		catch (Exception e) {
		}

		

		// toevoegen aan de lijst van afdelingen
		if (id > 0 && naam != null && !naam.isEmpty()) {
			Afdeling a = new Afdeling(id, naam);
			al.voegAfdelingToe(a);
		}
	}

	@FXML
	void toevoegenJuryLeden(ActionEvent event) {
		//http://code.makery.ch/blog/javafx-dialogs-official/
				// inlezen van juryid
				TextInputDialog dialog = new TextInputDialog();
				dialog.initOwner(stage); //nodig om bij een gemaximaliseerde toepassing goed te verschijnen
				dialog.setTitle("Toevoegen");
				dialog.setHeaderText("Jurylid");
				dialog.setContentText("Nummer");
				dialog.setGraphic(null);
				int id = 0;
				String naam=null;
				try {
					id = Integer.parseInt(dialog.showAndWait().get());
					//controleren of reeds aanwezig
					if(jl.zoekJuryLidOpIdNummer(id)>=0) throw new ReedsAanwezigException("ID bestaat al!"); 
					// inlezen van de jurylidnaam
					dialog = new TextInputDialog();
					dialog.initOwner(stage);
					dialog.setTitle("Toevoegen");
					dialog.setHeaderText("Jurylid");
					dialog.setContentText("Naam");
					dialog.setGraphic(null);
					try {
						naam = dialog.showAndWait().get();
						if (naam.isEmpty()) throw new Exception("U hebt geen naam opgegeven.");
					} 
					catch (Exception e) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Waarschuwing");
						alert.setHeaderText(null);
						alert.setContentText(e.getMessage());
						alert.showAndWait();
					}
				}
				catch(ReedsAanwezigException rae){
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Waarschuwing");
					alert.setHeaderText(null);
					alert.setContentText(rae.getMessage());
					alert.showAndWait();
				}
				catch(NumberFormatException nfe){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Fout");
					alert.setHeaderText("Ongeldig nummer");
					alert.setContentText("Het jurylid zal niet worden toegevoegd!");
					alert.showAndWait();
				}
				catch (Exception e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Fout");
					alert.setHeaderText("Ongekende fout opgetreden");
					alert.setContentText(null);
					alert.showAndWait();
				}

				// toevoegen aan de lijst van afdelingen
				if (id > 0 && naam != null && !naam.isEmpty()) {
					JuryLid j = new JuryLid(id, naam);
					jl.voegJuryLidToe(j);
				}
	}

	@FXML
	void toevoegenScores(ActionEvent event) {
		
		for (int i=0; i<al.getAantalAfdelingen(); i++){
			al.getAfdeling(i).getScorelijst().clear();
			for(JuryLid j: jl.getJuryLeden()){
				TextInputDialog dialog=new TextInputDialog();
				dialog.initOwner(stage);
				dialog.setTitle("Toevoegen");
				dialog.setHeaderText("Afdeling " + al.getAfdeling(i).getNaam());
				dialog.setContentText("Score van " + j.getNaam());
				dialog.setGraphic(null);
				int score=0;
				try{
					score=Integer.parseInt(dialog.showAndWait().get());
				}	
				catch(NumberFormatException nfe){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Fout");
					alert.setHeaderText("Ongeldige score");
					alert.setContentText("De score wordt nul!");
					alert.showAndWait();
					
				}
				catch(Exception e){	}
				
				Score s= new Score(score, j);
				al.getAfdeling(i).voegScoreToe(s);
				}
			al.refresh();
			}
		}
	

	@FXML
	void verwijderenAfdeling(ActionEvent event) {
		TextInputDialog dialog=new TextInputDialog();
		dialog.initOwner(stage);
		dialog.setTitle("Verwijderen");
		dialog.setHeaderText("Afdeling verwijderen");
		dialog.setContentText("Inschrijvingsnummer van te verwijderen afdeling: ");
		dialog.setGraphic(null);
		int id=0;
		try{
			id=Integer.parseInt(dialog.showAndWait().get());
		}
		catch(NumberFormatException nfe){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fout");
			alert.setHeaderText("Ongeldig nummer");
			alert.setContentText("De afdeling kan niet worden verwijderd!");
			alert.showAndWait();
		}
		catch(Exception e){}
		//er is een int ingegeven, maar bestaat de afdeling al?
		//als letter was ingegeven is id nu nul
			if(al.zoekAfdelingOpIdNummer(id)<0){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Waarschuwing");
				alert.setHeaderText(null);
				alert.setContentText("Afdeling bestaat niet");
				alert.showAndWait();
			}
			else {
				al.verwijderAfdeling(id);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Melding");
				alert.setHeaderText(null);
				alert.setContentText("Afdeling " +id+ " is verwijderd.");
				alert.showAndWait();
			}
	}
	

	@FXML
	void verwijderenJuryLid(ActionEvent event) {
		int idNummer=-1;
		TextInputDialog dialog=new TextInputDialog();
		dialog.initOwner(stage);
		dialog.setTitle("Verwijderen");
		dialog.setHeaderText("Jurylid verwijderen");
		dialog.setContentText("Inschrijvingsnummer van te verwijderen jurylid: ");
		dialog.setGraphic(null);
		try{
			idNummer=Integer.parseInt(dialog.showAndWait().get());
		}
		catch(NumberFormatException nfe){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fout");
			alert.setHeaderText("Ongeldig nummer");
			alert.setContentText("Het jurylid kan niet worden verwijderd!");
			alert.showAndWait();
		}
		catch(Exception e){}
		
		if (jl.zoekJuryLidOpIdNummer(idNummer)<0) { 
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Waarschuwing");
			alert.setHeaderText(null);
			alert.setContentText("Jurylid bestaat niet");
			alert.showAndWait();
		}
		else{
			jl.verwijderJuryLid(idNummer, al);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Melding");
			alert.setHeaderText(null);
			alert.setContentText("Jurylid " +idNummer+ " is verwijderd.");
			alert.showAndWait();
		}
	}

	@FXML
	void sorteerOpNaamAfdeling(ActionEvent event) {
		al.sorteerOpNaamAfdeling();
	}

	@FXML
	void sorteerOpIdNummerAfdeling(ActionEvent event) {
		al.sorteerOpIdNummerAfdeling();
	}

	@FXML
	void sorteerOpTotaleAflopendeScoreAfdeling(ActionEvent event) {
		al.sorteerOpTotaleAflopendeScoreAfdeling();
	}

	@FXML
	void afsluitenToepassing(ActionEvent event) {
		Alert confirmation = new Alert(AlertType.CONFIRMATION);
    	confirmation.setTitle("Afsluiten...");
    	confirmation.setHeaderText(null);
    	confirmation.setContentText("Audit-programma wordt afgesloten.");
    	Optional<ButtonType> result= confirmation.showAndWait();
    	if(result.get() == ButtonType.OK){
    		System.exit(0);
    	}
    	else {confirmation.close();}
	}

	@FXML
	void lezenGegevens(ActionEvent event) {
		Connection conn = null;
		Statement stmt = null;
		try{
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			
			//afdelingen toevoegen aan al
			String sql;
			sql = "SELECT id, naam FROM afdelingen ORDER BY id";
			ResultSet rs = stmt.executeQuery(sql);
			int id;
			String naam;
			Afdeling a;
			while (rs.next())
			 {
				id = rs.getInt("ID");
				naam = rs.getString("Naam");
				a=new Afdeling(id,naam);
				al.voegAfdelingToe(a);
			}
			//juryleden toevoegen aan jl
			sql = "SELECT id, naam FROM juryleden ORDER BY id";
			rs = stmt.executeQuery(sql);
			JuryLid j;
			while (rs.next())
			 {
				id = rs.getInt("ID");
				naam = rs.getString("Naam");
				j=new JuryLid(id,naam);
				jl.voegJuryLidToe(j);
			}
			
			//scores toevoegen
			sql = "SELECT idafdeling, idjurylid,waarde FROM scores ORDER BY idafdeling";
			rs = stmt.executeQuery(sql);
			int idafd,idjury,waarde;
			int indexafd,indexjury;
			Score s;
			for(Afdeling a1:al.getAfdelingen()){
				a1.getScorelijst().clear();
			}
			while(rs.next()){
				idafd=rs.getInt("idafdeling");
				indexafd=al.zoekAfdelingOpIdNummer(idafd);
				idjury=rs.getInt("idjurylid");
				indexjury=jl.zoekJuryLidOpIdNummer(idjury);
				waarde=rs.getInt("waarde");
				s=new Score(waarde,jl.getJuryLid(indexjury));
				al.getAfdeling(indexafd).voegScoreToe(s);
			}
			al.refresh();
			rs.close();
			stmt.close();
			conn.close();

			if (stmt != null)
				stmt.close();

			if (conn != null)
				conn.close();
		}
		catch(Exception e){}
	}

	@FXML
	void schrijvenGegevens(ActionEvent event) {
		Connection conn = null;
		Statement stmt2 = null;
		String sql;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		// oude tabellen droppen
				stmt2 = conn.createStatement();
				sql = "DROP TABLE IF EXISTS audit.afdelingen";
				stmt2.executeUpdate(sql);
				sql = "DROP TABLE IF EXISTS audit.scores";
				stmt2.executeUpdate(sql);
				sql = "DROP TABLE IF EXISTS audit.juryleden";
				stmt2.executeUpdate(sql);
		
		//nieuwe tabellen maken
				int id;
				String naam;
				//afdelingen
				sql="CREATE TABLE IF NOT EXISTS audit.afdelingen (ID INTEGER not NULL, Naam VARCHAR(35), PRIMARY KEY (`ID`))";
				stmt2.executeUpdate(sql);
				for(Afdeling a: al.getAfdelingen()){
					id=a.getId();
					naam = a.getNaam();
					sql = "INSERT INTO audit.afdelingen " +"VALUES (" + id + ", '" + naam + "')";
					stmt2.executeUpdate(sql);
				}
				
				//juryleden
				sql="CREATE TABLE IF NOT EXISTS audit.juryleden (ID INTEGER not NULL, Naam VARCHAR(35), PRIMARY KEY (`ID`))";
				stmt2.executeUpdate(sql);
				for(JuryLid j: jl.getJuryLeden()){
					id=j.getId();
					naam = j.getNaam();
					sql = "INSERT INTO audit.juryleden values (" + id + ", '" + naam + "')";
					stmt2.executeUpdate(sql);
				}
				
				//scores
				sql="CREATE TABLE IF NOT EXISTS audit.scores (idafdeling INTEGER not NULL, idjurylid INTEGER not NULL, waarde INTEGER NULL, PRIMARY KEY (`idafdeling`,`idjurylid`))";
				stmt2.executeUpdate(sql);
				int idafdeling, idjurylid, waarde;
				for (Afdeling a: al.getAfdelingen()){
					idafdeling=a.getId();
					for(Score s: a.getScorelijst()){
						idjurylid=s.getJury().getId();
						waarde=s.getWaarde();
						sql = "INSERT INTO audit.scores values (" + idafdeling + ", " + idjurylid +", " +waarde+")";
						stmt2.executeUpdate(sql);
					}
					
				}
				
				stmt2.close();
				conn.close();
		
	}
	catch(Exception e){}
		
	}


	@FXML
	void about(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(stage);
		alert.setTitle("About");
		alert.setHeaderText(null);
		alert.setContentText("Audit versie 1.0");
		alert.showAndWait();
	}
	
	private void tekenGrafiek(){
		//niet gevonden.
	}

	public void update(Observable arg0, Object arg1) { // Called from the Model
		tekstAreaAfdelingen.setText(al.toString());
		tekstAreaJuryLeden.setText(jl.toString());
		tekenGrafiek();	
	}

	@FXML
	void initialize() {
	}

}
