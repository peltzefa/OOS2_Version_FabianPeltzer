package senser;
import java.util.Observable;

public class Senser extends Observable implements Runnable
{
	StreamingWebClient client;

	public Senser(String uri)
	{
		client = new StreamingWebClient(uri, 512);
	}

	private String getSentence()
	{
		//Define an regular expression to read in only relevant sentences
		String filter = "\\d{10}\\.\\d{7}!ADS-B\\*.{28}";
		// to find . or * you must use \\. or \\*;

		return client.readChunk(filter);
	}
	
	public void run()
	{
		ADSBSentence sentence;
		
		//Create factory and display object
		ADSBSentenceFactory factory = new ADSBSentenceFactory();
		ADSBSentenceDisplay display = new ADSBSentenceDisplay();
		
		while (true)
		{
			sentence = factory.fromWebdisJson(getSentence());
			
			if (sentence != null)
			{
				//Display the sentence
				display.display(sentence);
				
				//Notify all observers
				setChanged();
				notifyObservers(sentence);
			}			
		}		
	}
}
