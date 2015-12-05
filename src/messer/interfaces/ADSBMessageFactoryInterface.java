package messer.interfaces;

import messer.ADSBMessage;
import senser.ADSBSentence;

/**
 * Created by Fabian on 09.11.2015.
 */
public interface ADSBMessageFactoryInterface {
    ADSBMessage fromAdsbSentence(ADSBSentence sentence);
}
