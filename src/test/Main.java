package test;

import GUI.Window;
import control.ControlAutomaton;

/**
 * Clase main para ejecutar el proyecto automatas v1 
 * @author pipe
 *
 */
public class Main {

	/**
	 * Metodo main
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		ControlAutomaton controlAutomaton = new ControlAutomaton();
		Window window = new Window(controlAutomaton);
		window.setVisible(true);
		
	}
}
