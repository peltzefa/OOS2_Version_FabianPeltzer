package messer;

import messer.interfaces.ADSBMessageFactoryInterface;
import messer.interfaces.ADSBMessageServerInterface;
import messer.interfaces.ADSBMessageServerObserverInterface;
import senser.ADSBSentence;

import java.util.Observable;

/**
 * Created by Fabian on 09.11.2015.
 */
public class ADSBMessageServer extends ADSBMessageServerInterface {

    private ADSBMessageFactoryInterface mFactory = new ADSBMessageFactory();

    @Override
    public void update(Observable o, Object arg) {
        ADSBSentence sentense = ADSBSentence.class.cast(arg);

        ADSBMessage message = mFactory.fromAdsbSentence(sentense);
        setChanged();
        notifyObservers(message);
    }

    @Override
    public void Constructor(String uri) {

    }

    @Override
    public ADSBMessageServerObserverInterface getObserver() {
        return ADSBMessageServerObserverInterface.class.cast(this);
    }
}
