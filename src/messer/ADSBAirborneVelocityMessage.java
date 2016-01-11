package messer;

import messer.interfaces.ADSBAirborneVelocityMessageInterface;

/**
 * Created by Fabian on 09.11.2015.
 */
public class ADSBAirborneVelocityMessage extends ADSBMessage implements ADSBAirborneVelocityMessageInterface {

	private int subtype;
	private int intentChange;
	private int reservedA;
	private int navigationAccuracy;
	private int speed;
	private int heading;
	private int verticalRateSource;
	private int verticalSpeed;

	public ADSBAirborneVelocityMessage() {

	}

	public ADSBAirborneVelocityMessage(String timestamp, String icao, int type, int downlinkFormate, int capability,
									   int subtype, int intentChange, int reservedA, int navigationAccuracy, int
                                               speed, int heading, int verticalRateSource, int verticalSpeed) {
		super(timestamp, icao, type, downlinkFormate, capability);
		this.subtype = subtype;
		this.intentChange = intentChange;
		this.reservedA = reservedA;
		this.navigationAccuracy = navigationAccuracy;
		this.speed = speed;
		this.heading = heading;
		this.verticalRateSource = verticalRateSource;
		this.verticalSpeed = verticalSpeed;
	}

	@Override
	public int getSubtype() {
		return subtype;
	}

	@Override
	public int getIntentChange() {
		return intentChange;
	}

	@Override
	public int getReservedA() {
		return reservedA;
	}

	@Override
	public int getNavigationAccuracy() {
		return navigationAccuracy;
	}

	@Override
	public int getSpeed() {
		return speed;
	}

	@Override
	public int getHeading() {
		return heading;
	}

	@Override
	public int getVerticalRateSource() {
		return verticalRateSource;
	}

	@Override
	public int getVerticalSpeed() {
		return verticalSpeed;
	}

	@Override
	void print() {
		System.out.print(getIcao() + ": Airborne Velocity Message\nSubtype: " + getSubtype() + "\nIntent Change: " +
                getIntentChange() +
				"\nReserved A: " + getReservedA() + "\nNavigation Accuracy: " + getNavigationAccuracy() + "\nSpeed: "
                + getSpeed() +
				"\nHeading: " + getHeading() + "\nVertical Rate Source: " + getVerticalRateSource() + "\nVertical " +
                "Speed: " + getVerticalSpeed() + "\n\n");

	}

	public String toString() {
		String s = "Subtype: " + getSubtype() + "\nIntent Change: " +
                getIntentChange() + "\nReserved A: " + getReservedA() + "\nNavigation Accuracy: " +
				getNavigationAccuracy() + "\nSpeed: " + getSpeed() + "\nHeading: " + getHeading() + "\nVertical Rate " +
				"Source: " + getVerticalRateSource() + "\nVertical " + "Speed: " + getVerticalSpeed() + "\n";
		return s;
	}
}
