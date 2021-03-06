package messer;

import messer.interfaces.ADSBAircraftIdentificationMessageInterface;

/**
 * Created by Fabian on 09.11.2015.
 */
public class ADSBAircraftIdentificationMessage extends ADSBMessage implements ADSBAircraftIdentificationMessageInterface {

    private int emitterCategory;
    private String aircraftId;

    public ADSBAircraftIdentificationMessage(String timestamp, String icao, int type, int downlinkFormate, int capability, int emitterCategory, String aircraftId){
        super(timestamp, icao, type, downlinkFormate, capability);
        this.emitterCategory = emitterCategory;
        this.aircraftId = aircraftId;
    }

    @Override
    public int getEmitterCategory() {
        return emitterCategory;
    }

    @Override
    public String getAircraftId() {
        return aircraftId;
    }

    void print(){
        System.out.print(getIcao() + ": ADSB Aircraft Identification Message\nEmitter Category: " +
                getEmitterCategory()+"\nAircraft ID: " + getAircraftId()+ "\n\n");
    }

    public String toString(){
        String s = "Emitter Category: " + getEmitterCategory()+"\nAircraft ID: " + getAircraftId()+ "\n";
        return s;
    }
}
