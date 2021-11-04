package GUI;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import control.ControlAutomaton;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;

	private BackgroundPanel backgroundPanel1, backgroundPanel2, backgroundPanel3;

	private ControlAutomaton controlAutomaton;

	public Window(ControlAutomaton controlAutomaton) {

		this.controlAutomaton = controlAutomaton;
		
		setTitle("Automatas v1");
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setResizable(false);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		placeAutomaton1();
		placeAutomaton2();
		placeAutomatonResult();
		
		placeComponentsPanelRigth();
		
	}

	private void placeComponentsPanelRigth() {

		JLabel loadAutomatonLabel = new JLabel("Automatas ACME");
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0.1;
		add(loadAutomatonLabel, gbc);
		gbc.weightx = 0.0;
	
	}

	private void placeAutomaton1() {

		JLabel automatonLabel = new JLabel("Automata 1");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		add(automatonLabel, gbc);

		backgroundPanel1 = new BackgroundPanel("./resources/background/backgroundAutomaton.jpg");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.weightx = 1;
		add(backgroundPanel1, gbc);
		gbc.weighty = 0.0;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		
		JButton chooseFileButton1 = new JButton("Elegir");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		backgroundPanel1.add(chooseFileButton1, gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		ActionListener chooseFileButtonListener1 = new chooseFileButtonListener(controlAutomaton, backgroundPanel1, 0);
		chooseFileButton1.addActionListener(chooseFileButtonListener1);
		
		JButton LCButton = new JButton("LC/L");
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		backgroundPanel1.add(LCButton, gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		ActionListener LCButtonListener = new LCButtonListener(controlAutomaton, backgroundPanel1, 0);
		LCButton.addActionListener(LCButtonListener);
		
		JButton LRButton = new JButton("LR/L");
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		backgroundPanel1.add(LRButton, gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		ActionListener LRButtonListener = new LRButtonListener(controlAutomaton, backgroundPanel1, 0);
		LRButton.addActionListener(LRButtonListener);
		
	}

	private void placeAutomaton2() {

		JLabel automatonLabel = new JLabel("Automata 2");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		add(automatonLabel, gbc);

		backgroundPanel2 = new BackgroundPanel("./resources/background/backgroundAutomaton.jpg");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.weightx = 1;
		add(backgroundPanel2, gbc);
		gbc.weighty = 0.0;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		
		JButton chooseFileButton2 = new JButton("Elegir");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		backgroundPanel2.add(chooseFileButton2, gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		ActionListener chooseFileButtonListener2 = new chooseFileButtonListener(controlAutomaton, backgroundPanel2, 1);
		chooseFileButton2.addActionListener(chooseFileButtonListener2);
		
		JButton LCButton = new JButton("LC/L");
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		backgroundPanel2.add(LCButton, gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		ActionListener LCButtonListener = new LCButtonListener(controlAutomaton, backgroundPanel2, 1);
		LCButton.addActionListener(LCButtonListener);
		
		JButton LRButton = new JButton("LR/L");
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		backgroundPanel2.add(LRButton, gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		ActionListener LRButtonListener = new LRButtonListener(controlAutomaton, backgroundPanel2, 1);
		LRButton.addActionListener(LRButtonListener);
		
	}
	
	private void placeAutomatonResult() {

		JLabel automatonLabel = new JLabel("Resultados");
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		
		add(automatonLabel, gbc);

		backgroundPanel3 = new BackgroundPanel("./resources/background/backgroundAutomaton.jpg");
		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.weightx = 1;
		add(backgroundPanel3, gbc);
		gbc.weighty = 0.0;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		
		JButton unionButton = new JButton("Unión");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		backgroundPanel3.add(unionButton, gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		ActionListener unionButtonListener = new unionButtonListener(controlAutomaton, backgroundPanel3);
		unionButton.addActionListener(unionButtonListener);
		
		JButton intersectionButton = new JButton("Intersección");
		gbc.gridx = 4;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		backgroundPanel3.add(intersectionButton, gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		ActionListener intersectionButtonListener = new intersectionButtonListener(controlAutomaton, backgroundPanel3);
		intersectionButton.addActionListener(intersectionButtonListener);
	}

	class BackgroundPanel extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String image;
		
		public BackgroundPanel(String image) {
			this.image = image;
		}
		
		@Override
		public void paint(Graphics g) {
			
			BufferedImage imageBackground = null;
			try {
				imageBackground = ImageIO.read(new File(image));
			} catch (Exception e) {
				
			}
		
			g.drawImage(imageBackground, 0, 0, getWidth(), getHeight(), this);
			
			setOpaque(false);
			
			super.paint(g);
			
			SwingUtilities.updateComponentTreeUI(this);
			this.validateTree();
		}
		
		public void setImage(String image) {
			this.image = image;
		}
	}
}
