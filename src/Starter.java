import client.Client;
import gui.AirplaneWindow;
import messer.ADSBMessageDisplay;
import messer.ADSBMessageServer;
import messer.ADSBPlaneHash;
import senser.Senser;

import javax.swing.*;

public class Starter {

	public static void main(String[] args) {
		String urlString = "http://flugmon-it.hs-esslingen.de/subscribe/ads.sentence";
		Senser server = new Senser(urlString);
		ADSBMessageServer mServer = new ADSBMessageServer();
		ADSBMessageDisplay mDisplay = new ADSBMessageDisplay();
		ADSBPlaneHash pHash = new ADSBPlaneHash();
		mServer.addObserver(mDisplay);
		mServer.addObserver(pHash);

		Client client = new Client();
		server.addObserver(client);
		server.addObserver(mServer);

		client.start();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AirplaneWindow();
			}
		});

		new Thread(server).start();
	}
}
