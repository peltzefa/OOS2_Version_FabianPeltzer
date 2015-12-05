package senser;
import senser.interfaces.ADSBSentenceFactoryInterface;

import java.util.regex.Pattern;


public class ADSBSentenceFactory implements ADSBSentenceFactoryInterface
{
	//define a regular express to filter only relevant messages:
	//{"subscribe":["message","ads.sentence","1379574427.9127481!ADS-B*8D40675258BDF05CDBFB59DA7D6F;\r\n"]}
	private static final String patAdsbJson = "\\d{10}\\.\\d{7}!ADS-B\\*.{28}";

	@Override
	public ADSBSentence fromWebdisJson(String json)
	{		
		if ( Pattern.matches ( patAdsbJson, json ) ) 
		{					
			//Get distinct values from the json string
			String timestamp = json.substring(0, 18);			//0-17(18)
			String dfca 	 = json.substring(25, 27);			//25-26(27)
			String icao 	 = json.substring(27, 33);			//27-32(33)
			String payload 	 = json.substring(33, 47);			//33-46(47)
			String parity 	 = json.substring(47, 53);			//47-52(53)
			
			return new ADSBSentence(timestamp, dfca, icao, payload, parity);
		}
		else
		{
			return null;
		}
	}
}
