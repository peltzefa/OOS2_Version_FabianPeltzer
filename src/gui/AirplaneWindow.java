package gui;

import messer.ADSBMessage;
import messer.ADSBPlaneHash;
import messer.Plane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * Created by Fabian on 05.12.2015.
 */
public class AirplaneWindow extends JFrame {

	public static DefaultListModel<ListElementPlane> planeListModel;
	public static String selectetPlaneICAO;
	public static Plane selectedPlane;
	public static JList planeList;
	public JLabel position;
	public JLabel velocity;
	public JLabel identification;
	private List<ListElementPlane> listObjects;

	public AirplaneWindow() {
		super("FlightControl");
		UIManager style = new UIManager();
		try {
			style.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		JTabbedPane tab = new JTabbedPane();

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();

		panel1.setName("Planes");
		panel2.setName("Position");
		panel3.setName("Velocity");
		panel4.setName("Identification");

		planeList = new JList();
		planeListModel = new DefaultListModel<ListElementPlane>();
		listObjects = new ArrayList<>();
		planeList.setModel(planeListModel);
		planeList.setCellRenderer(new PlaneListCellRenderer());
		planeList.setVisible(true);
		planeList.setBorder(new EmptyBorder(10, 10, 10, 10));
		planeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//TODO: DataListener


		position = new JLabel();
		velocity = new JLabel();
		identification = new JLabel();

		position.setText("No PLANE selected!");
		velocity.setText("No PLANE selected!");
		identification.setText("No PLANE selected!");

		panel2.add(position);
		panel3.add(velocity);
		panel4.add(identification);

		planeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListElementPlane elemPlane = (ListElementPlane) planeList.getSelectedValue();
				selectetPlaneICAO = elemPlane.getIcao();
				selectedPlane = ADSBPlaneHash.ADSBPlaneHashGet(selectetPlaneICAO);
				updateSelectedPlane();
			}
		});

		panel1.setLayout(new BorderLayout());
		panel1.add(new JScrollPane(planeList), BorderLayout.WEST);

		tab.add(panel1);
		tab.add(panel2);
		tab.add(panel3);
		tab.add(panel4);

		this.add(tab);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		startGUIUpdater();
		startPlaneListGUIUpdater();
	}

	public static void removePlane(String icao) {
		planeListModel.removeElement(icao);
	}

	public void updateSelectedPlane() {

			if (selectedPlane.getLastADSBPosition() != null) {
				position.setText("<html>" + selectedPlane.getLastADSBPosition().toString().replace("\n", "<br>") +
						"</html>");
			} else {
				position.setText("No message yet!");
			}

			if (selectedPlane.getLastADSBVelocity() != null) {
				velocity.setText("<html>" + selectedPlane.getLastADSBVelocity().toString().replace("\n", "<br>")
						+ "</html>");
			} else {
				velocity.setText("No message yet!");
			}

			if (selectedPlane.getLastADSBIdentification() != null) {
				identification.setText("<html>" + selectedPlane.getLastADSBIdentification().toString().replace
						("\n", "<br>") + "</html>");
			} else {
				identification.setText("No message yet!");
			}

	}

	public boolean planeListIsEmpty() {
		if (planeList.getModel().getSize() == 0) {
			return true;
		} else {
			return false;
		}
	}

	private void startGUIUpdater() {

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {

				boolean loop = true;

				while (loop) {
					try {
						updateSelectedPlane();
						System.out.println(">> UPDATED: " + selectedPlane.getIcao());
						Thread.sleep(1000);
					} catch (Exception e) {
						System.out.println("No UPDATES");
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}

				return null;
			}


		};

		worker.execute();

	}

	private void startPlaneListGUIUpdater() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {

				boolean loop = true;

				while (loop) {

					Date d = new Date();
					try {
						Iterator it = (Iterator) ADSBPlaneHash.ADSBMessagePlanes.keySet().iterator();
						while (it.hasNext()) {
							Plane p = ADSBPlaneHash.ADSBMessagePlanes.get(it.next());
							//System.out.println("Planehash: " + p.getIcao());
							long difference = p.getTimestamp().getTime() - (d.getTime() - 240000);

							ListElementPlane tmpPlane = null;

							for (ListElementPlane lep : listObjects) {
								if (lep.getIcao() == p.getIcao()) {
									tmpPlane = lep;
									//System.out.println("ListObjects: " + lep.getIcao());
								}
							}

							if (difference < 0) {
								planeList.clearSelection();
								planeListModel.removeElement(tmpPlane);
								listObjects.remove(tmpPlane);
								ADSBPlaneHash.ADSBMessagePlanes.remove(p.getIcao());
								//System.out.println("removed: " + tmpPlane.getIcao());
							} else if (difference > 60000 && !listObjects.contains(tmpPlane)) {
								ListElementPlane plane = new ListElementPlane(p.getIcao(), Color.BLACK);
								planeListModel.addElement(plane);
								listObjects.add(plane);
							} else if (difference > 60000 && listObjects.contains(tmpPlane) && tmpPlane.getColor() ==
									Color.RED) {
								planeListModel.removeElement(tmpPlane);
								tmpPlane.setColor(Color.black);
								planeListModel.addElement(tmpPlane);
							} else if (difference < 60000 && difference > 0 && listObjects.contains(tmpPlane)) {
								planeListModel.removeElement(tmpPlane);
								tmpPlane.setColor(Color.RED);
								planeListModel.addElement(tmpPlane);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("--------------");
					Thread.sleep(1000);
				}
				return null;
			}
		};

		worker.execute();
	}


}
