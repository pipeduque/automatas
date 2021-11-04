package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import GUI.Window.BackgroundPanel;
import control.ControlAutomaton;

public class intersectionButtonListener implements ActionListener {
	
	private ControlAutomaton controlAutomaton;
	private BackgroundPanel backgroundPanel;
	
	public intersectionButtonListener(ControlAutomaton controlAutomaton, BackgroundPanel backgroundPanel) {
		
		this.controlAutomaton = controlAutomaton;
		this.backgroundPanel = backgroundPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (controlAutomaton.intersection()) {
			
			controlAutomaton.drawAutomaton(2);
			
			String resourcePng = "./resources/PNG/automata02.png";
			
			backgroundPanel.setImage(resourcePng);
			backgroundPanel.updateUI();
			
		} else 
				JOptionPane.showMessageDialog(null, "Primero carga 2 automatas");
		
	}

}