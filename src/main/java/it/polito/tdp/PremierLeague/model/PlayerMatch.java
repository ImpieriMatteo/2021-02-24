package it.polito.tdp.PremierLeague.model;

public class PlayerMatch {

	Integer playerID;
	String name;
	Double efficienza;
	Integer teamID;
	
	public PlayerMatch(Integer playerID, String name, Double efficienza, Integer teamID) {
		super();
		this.playerID = playerID;
		this.name = name;
		this.efficienza = efficienza;
		this.teamID = teamID;
	}
	
	public Integer getPlayerID() {
		return playerID;
	}
	public void setPlayerID(Integer playerID) {
		this.playerID = playerID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Double getEfficienza() {
		return efficienza;
	}

	public void setEfficienza(Double efficienza) {
		this.efficienza = efficienza;
	}

	public Integer getTeamID() {
		return teamID;
	}

	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((playerID == null) ? 0 : playerID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerMatch other = (PlayerMatch) obj;
		if (playerID == null) {
			if (other.playerID != null)
				return false;
		} else if (!playerID.equals(other.playerID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return playerID + " - " + name;
	}
	
	
}
