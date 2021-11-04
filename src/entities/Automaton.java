package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase encargada de llevar el control de la entidad Automata
 * @author pipe
 *
 */
public class Automaton {

	private ArrayList<String> statesList;
	private ArrayList<String> alphabet;
	private HashMap<String, Map<String,String>> transitionFunction;
	private String initialState;
	private ArrayList<String> finalStates;

	/**
	 * Constructor del automata
	 *
	 * @param jsonObjet del automata
	 */
	public Automaton() {

		this.statesList = new ArrayList<>();
		this.alphabet = new ArrayList<>();
		this.transitionFunction = new HashMap<>();
		this.finalStates= new ArrayList<>();
	}

	public ArrayList<String> getStatesList() {
		return statesList;
	}

	public void setStatesList(ArrayList<String> statesList) {
		this.statesList = statesList;
	}

	public ArrayList<String> getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(ArrayList<String> alphabet) {
		this.alphabet = alphabet;
	}

	public HashMap<String, Map<String, String>> getTransitionFunction() {
		return transitionFunction;
	}
	
	public void setTransitionFunction(HashMap<String, Map<String, String>> transitionFunction) {
		this.transitionFunction = transitionFunction;
	}

	public String getInitialState() {
		return initialState;
	}

	public void setInitialState(String initialState) {
		this.initialState = initialState;
	}

	public ArrayList<String> getFinalStates() {
		return finalStates;
	}

	public void setFinalStates(ArrayList<String> finalStates) {
		this.finalStates = finalStates;
	}

}
