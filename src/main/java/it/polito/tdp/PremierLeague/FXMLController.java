
/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Evento;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Simulatore;
import it.polito.tdp.PremierLeague.model.Evento.EventType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	private Simulatore simulatore;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnGiocatoreMigliore"
    private Button btnGiocatoreMigliore; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMatch"
    private ComboBox<Match> cmbMatch; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Match m = this.cmbMatch.getValue();
    	if(m==null) {
    		this.txtResult.appendText("Devi prima scegliere un match!");
    		return;
    	}
    	
    	String result = this.model.creaGrafo(m);
    	this.txtResult.appendText(result);
    	
    	this.btnGiocatoreMigliore.setDisable(false);
    	this.btnSimula.setDisable(false);
    }

    @FXML
    void doGiocatoreMigliore(ActionEvent event) {    	
    	this.txtResult.clear();
    	
    	Match m = this.cmbMatch.getValue();
    	if(!m.equals(this.model.getMatchGrafo())) {
    		this.txtResult.appendText("Se cambi match, prima di cercare il giocatore migliore, devi creare il grafo!");
    		return;
    	}
    	
    	this.txtResult.appendText("Giocatore migliore:\n");
    	this.txtResult.appendText(this.model.getDatiGiocatoreMigliore());
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	this.simulatore = new Simulatore();
    	Match match = this.cmbMatch.getValue();
    	if(!match.equals(this.model.getMatchGrafo())) {
    		this.txtResult.appendText("Se cambi match, prima di simulare gli N eventi, devi creare il grafo!");
    		return;
    	}
    	
    	Integer N;
    	try {
    		N = Integer.parseInt(this.txtN.getText());
    	}
    	catch(NumberFormatException e) {
    		this.txtResult.appendText("Numero di eventi inserito non correttamente!");
    		return;
    	}

    	this.simulatore.init(N, model);
    	List<Evento> eventi = this.simulatore.simula();
    	
    	Integer GOALHome = 0;
    	Integer GOALAway = 0;
    	Integer ESPULSIONIHome = 0;
    	Integer ESPULSIONIAway = 0;
    	for(Evento e : eventi) {
    		if(e.getTeamID().equals(match.getTeamHomeID()) && e.getEventType().equals(EventType.GOAL))
    			GOALHome++;
    		else if(e.getTeamID().equals(match.getTeamAwayID()) && e.getEventType().equals(EventType.GOAL))
    			GOALAway++;
    		
    		if(e.getTeamID().equals(match.getTeamHomeID()) && e.getEventType().equals(EventType.ESPULSIONE))
    			ESPULSIONIHome++;
    		else if(e.getTeamID().equals(match.getTeamAwayID()) && e.getEventType().equals(EventType.ESPULSIONE))
    			ESPULSIONIAway++;
    	}
    	
    	this.txtResult.appendText("Risultato della simulazione sulla partita selezionata:\n");
    	this.txtResult.appendText(String.format("%s %d - %d %s\n\n", match.getTeamHomeNAME(), GOALHome, GOALAway, match.getTeamAwayNAME()));
    	this.txtResult.appendText(String.format("ESPULSIONI => home: %d away: %d", ESPULSIONIHome, ESPULSIONIAway));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGiocatoreMigliore != null : "fx:id=\"btnGiocatoreMigliore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMatch != null : "fx:id=\"cmbMatch\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.cmbMatch.getItems().addAll(this.model.getAllMatch());
    }
}
