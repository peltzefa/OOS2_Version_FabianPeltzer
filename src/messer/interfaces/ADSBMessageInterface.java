package messer.interfaces;

/**
 * Created by Fabian on 09.11.2015.
 */
public interface ADSBMessageInterface {
    String getTimestamp();
    int getType();
    String getIcao();
    int getDownlinkFormat();
    int getCapability();
}
