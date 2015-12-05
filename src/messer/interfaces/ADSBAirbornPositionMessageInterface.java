package messer.interfaces;

/**
 * Created by Fabian on 09.11.2015.
 */
public interface ADSBAirbornPositionMessageInterface {
    int getSurveillanceStatus();
    int getNicSupplement();
    int getAltitude();
    int getTimeFlag();
    int getCprFormat();
    int getCprLogitude();
    int getCprLatitude();
}