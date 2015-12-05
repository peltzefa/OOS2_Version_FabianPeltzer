package messer.interfaces;

import messer.interfaces.ADSBMessageServerObserverInterface;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Fabian on 09.11.2015.
 */
public abstract class ADSBMessageServerInterface extends Observable implements Observer{

    public void Constructor(String uri){

    }

    public ADSBMessageServerObserverInterface getObserver(){
        return null;
    }
}
