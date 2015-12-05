package messer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Fabian on 09.11.2015.
 */
public class ADSBMessageDisplay implements Observer {

    @Override
    public void update(Observable o, Object arg) {

        ADSBMessage message = ADSBMessage.class.cast(arg);

        if (message == null) {
            return;
        } else if (arg instanceof ADSBAirbornPositionMessage){
            ADSBAirbornPositionMessage messageSub = ADSBAirbornPositionMessage.class.cast(arg);
            messageSub.print();
        } else if (arg instanceof ADSBAirborneVelocityMessage){
            ADSBAirborneVelocityMessage messageSub = ADSBAirborneVelocityMessage.class.cast(arg);
            messageSub.print();
        } else if (arg instanceof ADSBAircraftIdentificationMessage){
            ADSBAircraftIdentificationMessage messageSub = ADSBAircraftIdentificationMessage.class.cast(arg);
            messageSub.print();
        }

    }
}
