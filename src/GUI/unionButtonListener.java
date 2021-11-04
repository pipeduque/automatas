package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.Window.BackgroundPanel;
import control.ControlAutomaton;

import javax.swing.JOptionPane;


public class unionButtonListener implements ActionListener {
	
	private ControlAutomaton controlAutomaton;
	private BackgroundPanel backgroundPanel;
	
	public unionButtonListener(ControlAutomaton controlAutomaton, BackgroundPanel backgroundPanel) {
		
		this.controlAutomaton = controlAutomaton;
		this.backgroundPanel = backgroundPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (controlAutomaton.union()) {
			
			controlAutomaton.drawAutomaton(2);
			
			String resourcePng = "./resources/PNG/automata02.png";
			
			backgroundPanel.setImage(resourcePng);
			backgroundPanel.updateUI();
			
		} else 
				JOptionPane.showMessageDialog(null, "Primero carga 2 automatas");
		
	}

}
