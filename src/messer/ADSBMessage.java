package messer;

import messer.interfaces.ADSBMessageInterface;

/**
 * Created by Fabian on 09.11.2015.
 */
public class ADSBMessage implements ADSBMessageInterface {
    private String icao;
    private int type;
    private int downlinkFormate;
    private int capability;
    private int payload;

    public ADSBMessage (String icao, int type, int downlinkFormate, int capability, int payload){
        this.icao = icao;
        this.type = type;
        this.downlinkFormate = downlinkFormate;
        this.capability = capability;
        this.payload = payload;
    }

    public ADSBMessage () {

    }

    @Override
    public String getTimestamp() {
        return null;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getIcao() {
        return icao;
    }

    @Override
    public int getDownlinkFormat() {
        return downlinkFormate;
    }

    @Override
    public int getCapability() {
        return capability;
    }

    public void setIcao(String icao){
        this.icao = icao;
    }

    void print(){
        /*System.out.print("--ADSB Message--\nTimestamp: " + getTimestamp() +
                "\nType: " + getType() +
                "\nIcao: " + getIcao() +
                "\nDownlink Formate: "+ getDownlinkFormat() +
                "\nCapability: " + getCapability() + "\n");*/
    }
}
