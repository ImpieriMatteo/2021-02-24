package it.polito.tdp.PremierLeague.model;

public class TeamSimulazione {
	
	private Integer teamID;
	private Integer giocatoriInCampo;
	
	public TeamSimulazione(Integer teamID) {
		this.teamID = teamID;
		this.giocatoriInCampo = 11;
	}

	public Integer getTeamID() {
		return teamID;
	}

	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}

	public Integer getGiocatoriInCampo() {
		return giocatoriInCampo;
	}

	public void espulsione() {
		this.giocatoriInCampo--;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamID == null) ? 0 : teamID.hashCode());
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
		TeamSimulazione other = (TeamSimulazione) obj;
		if (teamID == null) {
			if (other.teamID != null)
				return false;
		} else if (!teamID.equals(other.teamID))
			return false;
		return true;
	}

}

