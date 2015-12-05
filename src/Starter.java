import client.Client;
import messer.ADSBMessageDisplay;
import messer.ADSBMessageServer;
import senser.Senser;

public class Starter
{

	public static void main(String[] args)
	{
		String urlString = "http://flugmon-it.hs-esslingen.de/subscribe/ads.sentence";
		Senser server = new Senser(urlString);
		ADSBMessageServer mServer = new ADSBMessageServer();
		ADSBMessageDisplay mDisplay = new ADSBMessageDisplay();
		mServer.addObserver(mDisplay);
		
		Client client = new Client();
		server.addObserver(client);
		server.addObserver(mServer);

		client.start();
		new Thread(server).start();
	}
}
