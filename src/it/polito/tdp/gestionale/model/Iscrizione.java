package it.polito.tdp.gestionale.model;

public class Iscrizione {

	private Studente studente;
	private Corso corso ;
	
	public Iscrizione(Studente studente, Corso corso) {
		super();
		this.studente = studente;
		this.corso = corso;
	}

	public Studente getStudente() {
		return studente;
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}

	public Corso getCorso() {
		return corso;
	}

	public void setCorso(Corso corso) {
		this.corso = corso;
	}
	
	
}
