package model;
public class Score {
	
	private int waarde;
	private JuryLid jury;
	
	public Score() {
		waarde = 0;
		jury = null;
	}
	
	public Score(int waarde, JuryLid jury) {
		this.waarde = waarde;
		this.jury = jury;
	}

	public int getWaarde() {
		return waarde;
	}

	public void setWaarde(int waarde) {
		this.waarde = waarde;
	}

	public JuryLid getJury() {
		return jury;
	}

	public void setJury(JuryLid jury) {
		this.jury = jury;
	}

	@Override
	public String toString() {
		return System.getProperty("line.separator") + "\t"+ waarde + "\t\t("+ jury.toString() + ")";
	}
	
	
}
