package messer.interfaces;

/**
 * Created by Fabian on 09.11.2015.
 */
public interface ADSBAirborneVelocityMessageInterface {
    int getSubtype();
    int getIntentChange();
    int getReservedA();
    int getNavigationAccuracy();
    int getSpeed();
    int getHeading();
    int getVerticalRateSource();
    int getVerticalSpeed();
}
