package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.Window.BackgroundPanel;
import control.ControlAutomaton;

public class chooseFileButtonListener implements ActionListener {
	
	
	
	private ControlAutomaton controlAutomaton;
	private BackgroundPanel backgroundPanel;
	private int NroAutomaton;
	
	public chooseFileButtonListener(ControlAutomaton controlAutomaton, BackgroundPanel backgroundPanel, int NroAutomaton) {
		
		this.controlAutomaton = controlAutomaton;
		this.backgroundPanel = backgroundPanel;
		this.NroAutomaton = NroAutomaton;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		controlAutomaton.readAutomaton(controlAutomaton.chooseFileAutomaton(), NroAutomaton);
		controlAutomaton.drawAutomaton(NroAutomaton);
		
		String resourcePng = "./resources/PNG/automata0" + NroAutomaton + ".png";
		backgroundPanel.setImage(resourcePng);
		backgroundPanel.updateUI();
		
		
		
	}

}
