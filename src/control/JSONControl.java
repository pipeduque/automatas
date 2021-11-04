package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Clase encargada del manejo con los archivos JSON
 * 
 * @author pipe
 *
 */
public class JSONControl {


	private ArrayList<String> statesList;
	private ArrayList<String> alphabet;
	private HashMap<String, Map<String, String>> transitionFunction;
	private String initialState;
	private ArrayList<String> finalStates;

	/**
	 * Constructor del controlador de los archivos JSON
	 */
	public JSONControl() {

		this.statesList = new ArrayList<>();
		this.alphabet = new ArrayList<>();
		this.transitionFunction = new HashMap<>();
		this.finalStates = new ArrayList<>();
		
	}

	/**
	 * Metodo encargado de crear el JSONObject del automata
	 * 
	 * @param statesList
	 * @param alphabet
	 * @param transitionFunction
	 * @param initialState
	 * @param finalStates
	 * @return Retorna el JSONOBject del automata
	 */
	public JSONObject writeAutomatonJSON() {
		
		JSONObject jsonAutomatonObject = new JSONObject();
		
		JSONArray jsonStatesList = new JSONArray();
		JSONArray jsonAlphabetList = new JSONArray();
		JSONArray jsonTransitionFunction = new JSONArray();
		JSONArray jsonFinalStates = new JSONArray();
		
		for (int i = 0; i < statesList.size(); i++)
			jsonStatesList.put(statesList.get(i));

		for (int i = 0; i < alphabet.size(); i++)
			jsonAlphabetList.put(alphabet.get(i));

		for (HashMap.Entry<String, Map<String, String>> entry : transitionFunction.entrySet()) {

			JSONObject jsonState = new JSONObject();
			JSONArray jsonDestinyStatesList = new JSONArray();

			jsonState.put("state", entry.getKey());

			for (Map.Entry<String, String> entry2 : entry.getValue().entrySet()) {

				JSONObject jsonDestinyState = new JSONObject();

				jsonDestinyState.put("symbol", entry2.getKey());
				jsonDestinyState.put("destinationState", entry2.getValue());

				jsonDestinyStatesList.put(jsonDestinyState);
			}

			jsonState.put("destinyStates", jsonDestinyStatesList);

			jsonTransitionFunction.put(jsonState);
		}

		for (int i = 0; i < finalStates.size(); i++)
			jsonFinalStates.put(finalStates.get(i));

		jsonAutomatonObject.put("statesList", jsonStatesList);
		jsonAutomatonObject.put("alphabet", jsonAlphabetList);
		jsonAutomatonObject.put("transitionFunction", jsonTransitionFunction);
		jsonAutomatonObject.put("initialState", initialState);
		jsonAutomatonObject.put("finalStates", jsonFinalStates);
		
		readAutomatonJSON(jsonAutomatonObject);
		
		return jsonAutomatonObject;
	}

	/**
	 * Encargado de crear el automata a partir de un JSONOBject
	 * 
	 * @param automaton
	 */
	public void readAutomatonJSON(JSONObject jsonObject) {
		
		
		
		// LLena la lista de estados
		for (int i = 0; i < jsonObject.getJSONArray("statesList").length(); i++)
			statesList.add(jsonObject.getJSONArray("statesList").getString(i));

		// LLena la lista del alfabeto
		for (int i = 0; i < jsonObject.getJSONArray("alphabet").length(); i++)
			alphabet.add(jsonObject.getJSONArray("alphabet").getString(i));

		// Llena la funcion de transiccion
		for (int i = 0; i < jsonObject.getJSONArray("transitionFunction").length(); i++) {

			String state = jsonObject.getJSONArray("transitionFunction").getJSONObject(i).getString("state");

			int destinyStatesLength = jsonObject.getJSONArray("transitionFunction").getJSONObject(i)
					.getJSONArray("destinyStates").length();

			Map<String, String> destinyStates = new HashMap<>();

			for (int j = 0; j < destinyStatesLength; j++) {

				String symbol = jsonObject.getJSONArray("transitionFunction").getJSONObject(i)
						.getJSONArray("destinyStates").getJSONObject(j).getString("symbol");
				String destinationState = jsonObject.getJSONArray("transitionFunction").getJSONObject(i)
						.getJSONArray("destinyStates").getJSONObject(j).getString("destinationState");

				destinyStates.put(symbol, destinationState);
			}

			transitionFunction.put(state, destinyStates);
		}

		// Asigna el estado inicial
		initialState = jsonObject.getString("initialState");

		// Asigna los estados finales
		for (int i = 0; i < jsonObject.getJSONArray("finalStates").length(); i++)
			finalStates.add(jsonObject.getJSONArray("finalStates").getString(i));
	}
	
	public ArrayList<String> getStatesList() {
		return statesList;
	}

	public ArrayList<String> getAlphabet() {
		return alphabet;
	}

	public HashMap<String, Map<String, String>> getTransitionFunction() {
		return transitionFunction;
	}

	public String getInitialState() {
		return initialState;
	}
	
	public ArrayList<String> getFinalStates() {
		return finalStates;
	}

	public void setStatesList(ArrayList<String> statesList) {
		this.statesList = statesList;
	}

	public void setAlphabet(ArrayList<String> alphabet) {
		this.alphabet = alphabet;
	}

	public void setTransitionFunction(HashMap<String, Map<String, String>> transitionFunction) {
		this.transitionFunction = transitionFunction;
	}

	public void setInitialState(String initialState) {
		this.initialState = initialState;
	}

	public void setFinalStates(ArrayList<String> finalStates) {
		this.finalStates = finalStates;
	}
	
	
}
