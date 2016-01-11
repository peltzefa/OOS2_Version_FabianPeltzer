package messer;

import gui.AirplaneWindow;

import java.util.Date;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Fabian on 05.12.2015.
 */
public class Plane{

	private String Icao;
	private Date timestamp;
	private LinkedList<ADSBAircraftIdentificationMessage> ADSBIdentificationmessages;
	private LinkedList<ADSBAirbornPositionMessage> ADSBPositions;
	private LinkedList<ADSBAirborneVelocityMessage> ADSBVelocities;

	public Plane(ADSBMessage message) {
		this.Icao = message.getIcao();
		this.ADSBIdentificationmessages = new LinkedList<ADSBAircraftIdentificationMessage>();
		this.ADSBPositions = new LinkedList<ADSBAirbornPositionMessage>();
		this.ADSBVelocities = new LinkedList<ADSBAirborneVelocityMessage>();

		date();

		if (message instanceof ADSBAircraftIdentificationMessage) {
			ADSBAircraftIdentificationMessage messageSub = ADSBAircraftIdentificationMessage.class.cast(message);
			addNewADSBIdentifications(messageSub);
		} else if (message instanceof ADSBAirbornPositionMessage) {
			ADSBAirbornPositionMessage messageSub = ADSBAirbornPositionMessage.class.cast(message);
			addNewADSBPositions(messageSub);
		} else if (message instanceof ADSBAirborneVelocityMessage) {
			ADSBAirborneVelocityMessage messageSub = ADSBAirborneVelocityMessage.class.cast(message);
			addNewADSBVelocities(messageSub);
		} else {
			// nothing
		}
	}

	public void addNewADSBIdentifications(ADSBAircraftIdentificationMessage message) {
		this.ADSBIdentificationmessages.add(message);
		date();
	}

	public void addNewADSBPositions(ADSBAirbornPositionMessage message) {
		this.ADSBPositions.add(message);
		date();
	}

	public void addNewADSBVelocities(ADSBAirborneVelocityMessage message) {
		this.ADSBVelocities.add(message);
		date();
	}

	private void date() {
		Date d = new Date();
		this.timestamp = d;
	}

	public ADSBAircraftIdentificationMessage getLastADSBIdentification() {
		if (ADSBIdentificationmessages.isEmpty()) {
			return null;
		} else {
			return this.ADSBIdentificationmessages.getLast();
		}
	}

	public ADSBAirbornPositionMessage getLastADSBPosition() {
		if (ADSBPositions.isEmpty()) {
			return null;
		} else {
			return this.ADSBPositions.getLast();
		}
	}

	public ADSBAirborneVelocityMessage getLastADSBVelocity() {
		if (ADSBVelocities.isEmpty()) {
			return null;
		} else {
			return this.ADSBVelocities.getLast();
		}
	}

	public String getIcao() {
		return Icao;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
