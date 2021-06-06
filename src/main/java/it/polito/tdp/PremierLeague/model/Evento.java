package it.polito.tdp.PremierLeague.model;

public class Evento implements Comparable<Evento>{
	
	public enum EventType {
		GOAL,
		ESPULSIONE,
		INFORTUNIO
	};
	
	private Integer eventID;
	private EventType eventType;
	private Integer teamID;
	
	public Evento(Integer eventID) {
		this.eventID = eventID;
	}

	public Integer getEventID() {
		return eventID;
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
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
		result = prime * result + ((eventID == null) ? 0 : eventID.hashCode());
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
		Evento other = (Evento) obj;
		if (eventID == null) {
			if (other.eventID != null)
				return false;
		} else if (!eventID.equals(other.eventID))
			return false;
		return true;
	}

	@Override
	public int compareTo(Evento other) {
		return this.eventID.compareTo(other.eventID);
	}

}
