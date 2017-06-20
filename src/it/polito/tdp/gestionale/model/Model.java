package it.polito.tdp.gestionale.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.gestionale.db.DidatticaDAO;

public class Model {
	
	private UndirectedGraph <Nodo, Iscrizione> grafo;
	private DidatticaDAO dao;
	private Map<Integer, Studente> studentiIdMap ;
	private Map<String, Corso> corsiIdMap ;
	private List<Iscrizione> iscrizioni ;
	private List<Studente> tuttiStudenti ;
	
	private List<Corso> best ;
	private List<Corso> parziale ;

	public Model(){
		this.grafo = new SimpleGraph<Nodo, Iscrizione> (Iscrizione.class);
		this.dao = new DidatticaDAO();
		this.studentiIdMap = new TreeMap <Integer, Studente> () ;
		this.corsiIdMap = new TreeMap <String, Corso> () ;
		this.iscrizioni = new ArrayList<Iscrizione> () ;
		this.tuttiStudenti  = new ArrayList<Studente> () ;
		this.parziale = new ArrayList<Corso > () ;
		this.best = new ArrayList<Corso > () ;
	}
	
	
//	public Map<Integer, Integer> getFrequenza() {
//	public List<Frequenza> getFrequenza() {
	public Map<Integer, Frequenza> getFrequenza(){
		this.creaGrafo();
		
//		List<Frequenza> result = new ArrayList<Frequenza> () ;
		
//		TreeMap <Integer, Integer> mappa = new TreeMap<Integer, Integer> () ;
		
		Map<Integer, Frequenza> map = new TreeMap<Integer, Frequenza> () ;
		
		for(int i = 0 ; i< corsiIdMap.size(); i++){
			map.put(i, new Frequenza(i));
		}
		for(Studente s : this.studentiIdMap.values()){
			map.get(s.getCorsi().size()).addFrequentanti();
			if(s.getCorsi().size()!=0)
				tuttiStudenti.add(s);
			
		}
		
		
		return map;
	}


	private void creaGrafo() {
		// forse meglio mettere direttamente dao....values
		studentiIdMap = dao.getAllStudenti() ;
		corsiIdMap = dao.getAllCorsi() ;
		Graphs.addAllVertices(grafo, studentiIdMap.values());
		Graphs.addAllVertices(grafo, corsiIdMap.values());
		
		iscrizioni = dao.getIscrizioni();
		for(Iscrizione i : iscrizioni){
			studentiIdMap.get(i.getStudente().getMatricola()).addCorsi(corsiIdMap.get(i.getCorso().getCodins()));
			corsiIdMap.get(i.getCorso().getCodins()).addIscritti(studentiIdMap.get(i.getStudente().getMatricola()));
//			i.getCorso().addIscritti(i.getStudente());
//			i.getStudente().addCorsi(i.getCorso());
			grafo.addEdge(corsiIdMap.get(i.getCorso().getCodins()), studentiIdMap.get(i.getStudente().getMatricola()), i) ;
			
		}
		
	}

	
	public static void main(String[] args) {
		
		Model m = new Model () ;
//		m.craGrafo();
		m.getFrequenza() ;
//		System.out.println(m.grafo);
		m.getMinimoCorsi();
	}


	public List<Corso> getMinimoCorsi() {
		this.recursive(best, parziale);
		return best ;
	
		
		
		
	}
	
	
	public void recursive(List<Corso> best, List<Corso> parziale){
		
		// condizione di terminazione:
			// tutti gli studenti sono avvisati: l'insieme di corsi contiene tutti gli studenti
//		if(parziale.containsAll(tuttiStudenti)){
			// se linsieme è più piccolo allora è migliore
		if(this.controlloStudenti(parziale)==true) {
			if(best.size()==0)
				best.addAll(parziale);
			if(best.size()>parziale.size()){
			best.clear();
			best.addAll(parziale);
			System.out.println(best.size());
			}
			
			return;
		}
		
		for(Corso c : corsiIdMap.values()){
			if(parziale.isEmpty()  || c.compareTo(parziale.get(parziale.size()-1)) > 0) {
				parziale.add(c) ;
//				System.out.println(c);
				this.recursive(best, parziale);
				parziale.remove(c) ;
			}
			
		}
		
	}

	public boolean controlloStudenti(List<Corso> parziale){
		List<Studente> result = new ArrayList<Studente> () ;
		for(Corso c : parziale){
			List<Studente> studenti = c.getIscritti();
			for(Studente s : studenti){
				if(!result.contains(s)){
					result.add(s);
				}
			}
		}
		if(result.containsAll(tuttiStudenti))
			return true;
		return false;
		
	}
	
}
