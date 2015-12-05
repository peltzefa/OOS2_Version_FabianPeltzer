package senser.interfaces;

import senser.ADSBSentence;

public interface ADSBSentenceFactoryInterface
{
	public ADSBSentence fromWebdisJson(String json);
}
