package messer;

import messer.interfaces.ADSBAirbornPositionMessageInterface;

/**
 * Created by Fabian on 09.11.2015.
 */
public class ADSBAirbornPositionMessage extends ADSBMessage implements ADSBAirbornPositionMessageInterface {

    private int surveillanceStatus;
    private int nicSupplement;
    private int altitude;
    private int timeFlag;
    private int cprFormate;
    private int cprLongitude;
    private int cprLatitude;

    public ADSBAirbornPositionMessage() {

    }

    public ADSBAirbornPositionMessage(int surveillanceStatus, int nicSupplement, int altitude, int timeFlag, int cprFormate, int cprLongitude, int cprLatitude) {
        this.surveillanceStatus = surveillanceStatus;
        this.nicSupplement = nicSupplement;
        this.altitude = altitude;
        this.timeFlag = timeFlag;
        this.cprFormate = cprFormate;
        this.cprLongitude = cprLongitude;
        this.cprLatitude = cprLatitude;
    }

    @Override
    public int getSurveillanceStatus() {
        return surveillanceStatus;
    }

    @Override
    public int getNicSupplement() {
        return nicSupplement;
    }

    @Override
    public int getAltitude() {
        return altitude;
    }

    @Override
    public int getTimeFlag() {
        return timeFlag;
    }

    @Override
    public int getCprFormat() {
        return cprFormate;
    }

    @Override
    public int getCprLogitude() {
        return cprLongitude;
    }

    @Override
    public int getCprLatitude() {
        return cprLatitude;
    }

    void print(){
        System.out.print(getIcao() + ": ADSB Position Message\nSurveillance Status: "+ getSurveillanceStatus() + "\nNic Supplement: " +
        getNicSupplement() + "\nAltitude: " + getAltitude() + "\nTimeFlag: " + getTimeFlag() + "\nCPR Formate: " + getCprFormat() +
        "\nCPR Longitude: " + getCprLogitude() + "\nCPR Latitude: " + getCprLatitude() + "\n\n");
    }
}
