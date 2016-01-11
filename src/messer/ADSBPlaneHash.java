package messer;

import gui.AirplaneWindow;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Fabian on 05.12.2015.
 */
public class ADSBPlaneHash implements Observer {

    public static ConcurrentHashMap<String, Plane> ADSBMessagePlanes = new ConcurrentHashMap<String, Plane>();

    @Override
    public void update(Observable o, Object arg) {

        ADSBMessage message = ADSBMessage.class.cast(arg);
        if (message == null){
            System.out.println("Empty message!");
            return;
        }

        /*
        System.out.println("Time: " + message.getTimestamp());
        String[] times = message.getTimestamp().split("\\.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd.MM.yyyy, HH:mm:ss");
        Date date = new Date(Long.parseLong(times[0]));
        String time = dateFormat.format(date) + "." + times[1];
        System.out.println("Time: " + time);
        */

        if (ADSBMessagePlanes.containsKey(message.getIcao())) {

            Plane plane = ADSBMessagePlanes.get(message.getIcao());
            if (message instanceof ADSBAircraftIdentificationMessage) {
                ADSBAircraftIdentificationMessage messageSub = ADSBAircraftIdentificationMessage.class.cast(arg);
                plane.addNewADSBIdentifications(messageSub);
            } else if (message instanceof ADSBAirbornPositionMessage) {
                ADSBAirbornPositionMessage messageSub = ADSBAirbornPositionMessage.class.cast(arg);
                plane.addNewADSBPositions(messageSub);
            } else if (message instanceof ADSBAirborneVelocityMessage) {
                ADSBAirborneVelocityMessage messageSub = ADSBAirborneVelocityMessage.class.cast(arg);
                plane.addNewADSBVelocities(messageSub);
            } else {
                // nothing
            }
        } else {
            Plane plane = new Plane(message);
            addPlane(message.getIcao(), plane);
            System.out.println("Flugzeug in Hash: "+ message.getIcao());
            //AirplaneWindow.addPlane(message.getIcao());
        }

    }

    public void addPlane(String messageIcao, Plane p) {
        ADSBMessagePlanes.put(messageIcao, p);
    }

    public static Plane ADSBPlaneHashGet(String icao){
        return ADSBMessagePlanes.get(icao);
    }
}
