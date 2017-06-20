package it.polito.tdp.gestionale.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.gestionale.model.Corso;
import it.polito.tdp.gestionale.model.Iscrizione;
import it.polito.tdp.gestionale.model.Studente;

public class DidatticaDAO {

	private Map<Integer, Studente> studentiIdMap ;
	private Map<String, Corso> corsiIdMap ;
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String codins) {

		final String sql = "SELECT * FROM corso where codins=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Corso corso = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),
						rs.getInt("pd"));
				return corso;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Data una matricola ottengo lo studente.
	 */
	public Studente getStudente(int matricola) {

		final String sql = "SELECT * FROM studente where matricola=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Studente studente = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"),
						rs.getString("cds"));
				return studente;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	

	// tutti gli studenti
	public Map<Integer, Studente> getAllStudenti() {
		
		studentiIdMap = new TreeMap<Integer, Studente> () ;
		
		final String sql = "SELECT * FROM studente ";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Studente studente = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"),
						rs.getString("cds"));
				studentiIdMap.put(studente.getMatricola(), studente) ;
				
			}

			return studentiIdMap;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	// tutti i corsi
	public Map<String, Corso> getAllCorsi() {
		
		corsiIdMap = new TreeMap<String, Corso> () ;
		

		final String sql = "SELECT * FROM corso ";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Corso corso = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),
						rs.getInt("pd"));
				corsiIdMap.put(corso.getCodins(), corso);
			}

			return corsiIdMap;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Iscrizione> getIscrizioni(){
		
		List<Iscrizione> result = new ArrayList<Iscrizione> () ;
		final String sql = "SELECT * FROM iscrizione ";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Corso corso = corsiIdMap.get(rs.getString("codins"));
				Studente studente = studentiIdMap.get(rs.getInt("matricola"));
				result.add(new Iscrizione(studente, corso ));
			}

			return result;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	// Test main
	public static void main(String[] args) {
		
		DidatticaDAO dd = new DidatticaDAO();
//		System.out.println(dd.getCorso("01JEFPG"));
//		System.out.println(dd.getStudente(134806));
		System.out.println(dd.getAllCorsi());
		System.out.println(dd.getAllStudenti());
		System.out.println(dd.getIscrizioni()) ;
	}

}
