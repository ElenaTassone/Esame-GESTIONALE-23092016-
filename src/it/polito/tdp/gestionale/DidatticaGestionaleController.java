package it.polito.tdp.gestionale;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.gestionale.model.Frequenza;
import it.polito.tdp.gestionale.model.Model;
import it.polito.tdp.gestionale.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DidatticaGestionaleController {

	Model model ;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtMatricolaStudente;

	@FXML
	private TextArea txtResult;

	public void setModel(Model m){
		this.model = m ;
	}
	
	@FXML
	void doCorsiFrequentati(ActionEvent event) {
		Map<Integer, Frequenza> lista = model.getFrequenza();
		for(Frequenza f : lista.values()){
			txtResult.appendText(f.getFrequentanti()+" studenti frequentano"+f.getFrequentati()+"corsi. \n");
		}
		
		
	}
	
	@FXML
	void doVisualizzaCorsi(ActionEvent event) {
		model.getMinimoCorsi();
	}

	@FXML
	void initialize() {
		assert txtMatricolaStudente != null : "fx:id=\"txtMatricolaStudente\" was not injected: check your FXML file 'DidatticaGestionale.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DidatticaGestionale.fxml'.";

	}

}
