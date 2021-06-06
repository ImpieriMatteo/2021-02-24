package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.model.Evento.EventType;

public class Simulatore {
	
	private PriorityQueue<Evento> queue;
	private Model model;
	
	private Integer Neventi;
	private PlayerMatch playerBest;
	private TeamSimulazione team1;
	private TeamSimulazione team2;
	
	private List<Evento> eventi;
	
	public void init(Integer N, Model model) {
		this.queue = new PriorityQueue<>();
		for(int i=1; i<=N; i++)
			this.queue.add(new Evento(i));
		
		this.model = model;
		
		this.Neventi = N;
		this.playerBest = this.model.getGiocatoreMigliore();
		Match m = this.model.getMatchGrafo();
		this.team1 = new TeamSimulazione(m.getTeamHomeID());
		this.team2 = new TeamSimulazione(m.getTeamAwayID());
		
		this.eventi = new ArrayList<>();
	}
	
	public List<Evento> simula() {
		
		while(!this.queue.isEmpty()) {
			Evento e = this.queue.poll();
			
			Integer probabilita = (int)(Math.random()*100);
			
			//GOAL (50%)
			if(probabilita<50) {
				e.setEventType(EventType.GOAL);
				
				if(this.team1.getGiocatoriInCampo()>this.team2.getGiocatoriInCampo()) 
					e.setTeamID(this.team1.getTeamID());
				
				else if(this.team1.getGiocatoriInCampo()<this.team2.getGiocatoriInCampo()) 
					e.setTeamID(this.team2.getTeamID());
		
				else 
					e.setTeamID(this.playerBest.getTeamID());
				
				this.eventi.add(e);
			}
			//ESPULSIONE (30%)
			else if(probabilita>=50 && probabilita<80) {
				e.setEventType(EventType.ESPULSIONE);
				
				Integer prob = (int)(Math.random()*100);
				
				if(prob<60) {
					e.setTeamID(this.playerBest.getTeamID());
					
					if(this.team1.getTeamID()==this.playerBest.getTeamID())
						this.team1.espulsione();
					else
						this.team2.espulsione();
				}
					
				else {
					if(this.team1.getTeamID()==this.playerBest.getTeamID()) {
						this.team2.espulsione();
						e.setTeamID(this.team2.getTeamID());
					}
					else {
						this.team1.espulsione();
						e.setTeamID(this.team1.getTeamID());
					}
				}
				
				this.eventi.add(e);
			}
			//INOFORTUNIO (20%)
			else {
				e.setEventType(EventType.INFORTUNIO);
				
				Integer prob = (int)(Math.random()*100);
				
				// Aggiungo 2 eventi
				if(prob<50) {
					this.Neventi++;
					this.queue.add(new Evento(this.Neventi));
					this.Neventi++;
					this.queue.add(new Evento(this.Neventi));
				}
				// Aggiungo 3 eventi
				else {
					this.Neventi++;
					this.queue.add(new Evento(this.Neventi));
					this.Neventi++;
					this.queue.add(new Evento(this.Neventi));
					this.Neventi++;
					this.queue.add(new Evento(this.Neventi));
				}
			}
		}
		
		return this.eventi;
	}

}
