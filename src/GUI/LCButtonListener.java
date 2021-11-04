package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import GUI.Window.BackgroundPanel;
import control.ControlAutomaton;

public class LCButtonListener implements ActionListener {

	private ControlAutomaton controlAutomaton;
	private BackgroundPanel backgroundPanel;
	private int NroAutomaton;

	public LCButtonListener(ControlAutomaton controlAutomaton, BackgroundPanel backgroundPanel, int NroAutomaton) {

		this.controlAutomaton = controlAutomaton;
		this.backgroundPanel = backgroundPanel;
		this.NroAutomaton = NroAutomaton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (controlAutomaton.complement(NroAutomaton)) {

			controlAutomaton.drawAutomaton(NroAutomaton);

			String resourcePng = "./resources/PNG/automata0" + NroAutomaton + ".png";

			backgroundPanel.setImage(resourcePng);
			backgroundPanel.updateUI();

		} else {
			
			int nro = NroAutomaton + 1;
			JOptionPane.showMessageDialog(null, "Elige primero el automata " + nro);
		}

	}

}