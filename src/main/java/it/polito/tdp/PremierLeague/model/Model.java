package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private Match matchGrafo = null;
	
	private SimpleDirectedWeightedGraph<PlayerMatch, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;

	public Model() {
		this.dao = new PremierLeagueDAO();
	}
	
	public String creaGrafo(Match m) {
		this.matchGrafo = m;
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		List<PlayerMatch> playerMatch = new ArrayList<>();
		
		playerMatch = this.dao.getPlayerPerMatch(m);
		
		Graphs.addAllVertices(this.grafo, playerMatch);
		
		for(PlayerMatch pm1 : playerMatch) {
			for(PlayerMatch pm2 : playerMatch) {
				Double diffEffic = pm1.getEfficienza()-pm2.getEfficienza();
				
				if(!pm1.equals(pm2) && pm1.getTeamID()!=pm2.getTeamID() && diffEffic >= 0 && !this.grafo.containsEdge(pm1, pm2) && !this.grafo.containsEdge(pm2, pm1)) 
					Graphs.addEdgeWithVertices(this.grafo, pm1, pm2, diffEffic);
			}
		}
		
		return String.format("Grafo creato con %d vertici e %d archi", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
	}
	
	public String getDatiGiocatoreMigliore() {
		PlayerMatch playerBest = null;
		Double delta = 0.0;
		
		for(PlayerMatch pm : this.grafo.vertexSet()) {
			Double deltaAttuale = 0.0;
			
			for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(pm))
				deltaAttuale += this.grafo.getEdgeWeight(e);
			
			for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(pm))
				deltaAttuale -= this.grafo.getEdgeWeight(e);
			
			if(deltaAttuale>delta) {
				delta = deltaAttuale;
				playerBest = pm;
			}
		}
		
		return String.format("%d - %s, delta efficienza = %.3f", playerBest.getPlayerID(), playerBest.getName(), delta);
	}
	
	public PlayerMatch getGiocatoreMigliore() {
		PlayerMatch playerBest = null;
		Double delta = 0.0;
		
		for(PlayerMatch pm : this.grafo.vertexSet()) {
			Double deltaAttuale = 0.0;
			
			for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(pm))
				deltaAttuale += this.grafo.getEdgeWeight(e);
			
			for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(pm))
				deltaAttuale -= this.grafo.getEdgeWeight(e);
			
			if(deltaAttuale>delta) {
				delta = deltaAttuale;
				playerBest = pm;
			}
		}
		
		return playerBest;
	}

	
	public List<Match> getAllMatch(){
		List<Match> result = new ArrayList<>(this.dao.listAllMatches());
		Collections.sort(result);
		return result;
	}
	
	public Match getMatchGrafo() {
		return this.matchGrafo;
	}
	
	public Set<PlayerMatch> getPlayerMatch() {
		return this.grafo.vertexSet();
	}
}
