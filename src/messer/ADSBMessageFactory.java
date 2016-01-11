package messer;

import messer.interfaces.ADSBMessageFactoryInterface;
import senser.ADSBSentence;

/**
 * Created by Fabian on 09.11.2015.
 */
public class ADSBMessageFactory implements ADSBMessageFactoryInterface {

	private char[] ascii6 = {'@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', ' ', '!', '"', '#', '$', '%', '&',
			'\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';',
			'<', '=', '>', '?'};

	@Override
	public ADSBMessage fromAdsbSentence(ADSBSentence sentence) {

		String timestamp, icao;
		int type, downlinkFormate, capability, payload;
		timestamp = sentence.getTimestamp();
		icao = sentence.getIcao();
		type = 0;
		capability = 0;
		downlinkFormate = 0;

		String payloadString = getBinStringfromHex(sentence.getPayload());
		int payloadType = getIntFromBinString(payloadString, 0, 4);

		if (payloadType >= 1 && payloadType <= 4) {

			//Aircraft Identification and Category Message

			int payloadSubtype = getIntFromBinString(payloadString, 5, 7);
			String identificationCharacter;

			identificationCharacter = Character.toString(ascii6[getIntFromBinString(payloadString, 8, 13)]) +
					Character.toString(ascii6[getIntFromBinString(payloadString, 14, 19)]) +
					Character.toString(ascii6[getIntFromBinString(payloadString, 20, 25)]) +
					Character.toString(ascii6[getIntFromBinString(payloadString, 26, 31)]) +
					Character.toString(ascii6[getIntFromBinString(payloadString, 32, 37)]) +
					Character.toString(ascii6[getIntFromBinString(payloadString, 38, 43)]) +
					Character.toString(ascii6[getIntFromBinString(payloadString, 44, 49)]) +
					Character.toString(ascii6[getIntFromBinString(payloadString, 50, 55)]);

			ADSBMessage message = new ADSBAircraftIdentificationMessage(timestamp, icao, type, downlinkFormate,
					capability, payloadSubtype, identificationCharacter);
			return message;

		} else if (payloadType >= 5 && payloadType <= 8) {
			//Surface Position Message
		} else if ((payloadType >= 9 && payloadType <= 18) || (payloadType >= 20 && payloadType <= 22)) {

			//Airborne Position Message
			int surveillanceStatus = getIntFromBinString(payloadString, 5, 6);
			int nicSupplement = getIntFromBinString(payloadString, 7, 7);
			int qBit = getIntFromBinString(payloadString, 15, 15);   //0 = 100ft steps, 1 = 25ft steps

			String altitudeString = payloadString.substring(8, 15) + payloadString.substring(16, 20);
			int altitudeCalculationInt = Integer.parseInt(altitudeString, 2);

			int factor = (qBit == 1) ? 25 : 100;
			int altitude = altitudeCalculationInt * factor - 1000;

			int timeFlag = getIntFromBinString(payloadString, 20, 20);
			int cprFormate = getIntFromBinString(payloadString, 21, 21);    //0 = even, 1 = odd
			int cprLongitude = getIntFromBinString(payloadString, 22, 38);
			int cprLatitude = getIntFromBinString(payloadString, 39, 55);

			//TODO: Longitude / Latitude berechnen (Formel suchen & einsetzten) ???

			ADSBMessage message = new ADSBAirbornPositionMessage(timestamp, icao, type, downlinkFormate, capability,
					surveillanceStatus, nicSupplement, altitude, timeFlag, cprFormate, cprLongitude, cprLatitude);
			return message;

		} else if (payloadType == 19) {

			//Airborne Velocity Message
			int payloadSubtype = getIntFromBinString(payloadString, 5, 7);

			if (payloadSubtype >= 1 && payloadSubtype <= 4) {
				int intentChange = getIntFromBinString(payloadString, 8, 8);
				int reservedA = getIntFromBinString(payloadString, 9, 9);
				int navigationAccuracy = getIntFromBinString(payloadString, 10, 12);

				int eastWestDirection, eastWestVelocity, northSouthDirection, northSouthVelocity, headingStatusBit,
						headingPreVal, airspeedType, airspeed, speed, heading;

				if (payloadSubtype == 1 || payloadSubtype == 2) {
					eastWestDirection = getIntFromBinString(payloadString, 13, 13); //0 = east, 1 = west
					eastWestVelocity = getIntFromBinString(payloadString, 14, 23);
					//System.out.println("eastwestvelo: " + eastWestVelocity);
					northSouthDirection = getIntFromBinString(payloadString, 24, 24); //0 = north, 1 = south
					northSouthVelocity = getIntFromBinString(payloadString, 25, 34);
					//System.out.println("northsouthvelo: " + northSouthVelocity);

					if (eastWestDirection == 1) eastWestVelocity = eastWestVelocity * -1;
					if (northSouthDirection == 1) northSouthVelocity = northSouthVelocity * -1;

					//TODO: Output Formel überprüfen -> 444?
					double eastWestVelocity2 = eastWestVelocity * eastWestVelocity;
					double nortSouthVelocity2 = northSouthVelocity * northSouthVelocity;
					//System.out.println("eastwestvelo2: " + eastWestVelocity2);
					//System.out.println("northsouthvelo2: " + nortSouthVelocity2);
					double sumVelocities = eastWestVelocity2 + nortSouthVelocity2;
					//System.out.println("sumvelo: " + sumVelocities);
					speed = (int) Math.sqrt(sumVelocities);
					//System.out.println("speed: " + speed);

					double eastWestDIVNorthSouth = eastWestVelocity / northSouthVelocity;
					//System.out.println("eastWestDIVNorthSouth: " + eastWestDIVNorthSouth);
					double atanEWNS = Math.atan(eastWestDIVNorthSouth);
					//System.out.println("atanEWNS: " + atanEWNS);
					double truePI = 360 / (2 * Math.PI);
					//System.out.println("truePI: " + truePI);

					double headingDouble = atanEWNS * truePI;
					//System.out.println("headingDouble: " + headingDouble);

					heading = (int) headingDouble;
					if (heading < 0) heading += 360;

				} else {
					headingStatusBit = getIntFromBinString(payloadString, 13, 13); //0 = NOT available, 1 = available
					headingPreVal = getIntFromBinString(payloadString, 14, 23);
					airspeedType = getIntFromBinString(payloadString, 24, 24);//0 = indicated Airspeed, 1 = true
					// Airspeed
					airspeed = getIntFromBinString(payloadString, 25, 34);

					//TODO: Werte berechnen
					speed = 0;
					heading = 0;
				}

				int verticalRateSource = getIntFromBinString(payloadString, 35, 35);
				int verticalRateSign = getIntFromBinString(payloadString, 36, 36);
				int verticalRatePre = getIntFromBinString(payloadString, 37, 45);

				int verticalSpeed = (verticalRatePre - 1) * 64;
				//System.out.println("verticalSpeed: " + verticalSpeed);
				if (verticalRateSign == 1) verticalSpeed = verticalSpeed * -1;
				//System.out.println("verticalSpeedafter: " + verticalSpeed);

				ADSBMessage message = new ADSBAirborneVelocityMessage(timestamp, icao, type, downlinkFormate,
						capability, payloadSubtype, intentChange, reservedA, navigationAccuracy, speed, heading,
						verticalRateSource, verticalSpeed);
				return message;
			}
		}
		return null;
	}

	String getBinStringfromHex(String hex) {

		long binLong = Long.parseLong(hex, 16);
		String binString = Long.toBinaryString(binLong);

		while (binString.length() < 56) {
			binString = "0" + binString;
		}
		return binString;
	}

	int getIntFromBinString(String binString, int from, int to) {
		return Integer.parseInt(binString.substring(from, (to + 1)), 2);
	}
}
