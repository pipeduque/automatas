package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import entities.Automaton;

/**
 * Clase encarga de controlar las opereaciones que se desean hacer con los
 * automatas
 * 
 * @author pipe
 *
 */
public class ControlAutomaton {

	private File fileJSON;
	private JSONObject automatonObj;
	private ArrayList<Automaton> automatonList;

	/**
	 * Constructor del control de automatas
	 */
	public ControlAutomaton() {

		this.automatonList = new ArrayList<Automaton>();
	}

	/**
	 * Metodo encargado de llamar a la clase FileControl para que nos de el archivo
	 * JSON del automata
	 * 
	 * @return
	 */
	public JSONObject chooseFileAutomaton() {

		FileControl fileControl = new FileControl();
		fileJSON = fileControl.chooseFile();

		try {
			automatonObj = fileControl.fileReader(fileJSON);

		} catch (IOException ex) {
			Logger.getLogger(FileControl.class.getName()).log(Level.SEVERE, null, "NO ES POSIBLE ABRIR EL ARCHIVO");
		} catch (FileException fe) {
			Logger.getLogger(FileControl.class.getName()).log(Level.SEVERE, null, fe);
		}
		return automatonObj;
	}

	/**
	 * Metodo encargado de crear el automata segun el archivo JSONObject y lo asigna
	 * a la lista de automatas
	 * 
	 * @param automatonObj
	 * @param NroAutomaton
	 */
	public void readAutomaton(JSONObject automatonObj, int NroAutomaton) {

		JSONControl jsonControl = new JSONControl();
		jsonControl.readAutomatonJSON(automatonObj);

		Automaton automaton = new Automaton();

		automaton.setStatesList(jsonControl.getStatesList());
		automaton.setAlphabet(jsonControl.getAlphabet());
		automaton.setTransitionFunction(jsonControl.getTransitionFunction());
		automaton.setInitialState(jsonControl.getInitialState());
		automaton.setFinalStates(jsonControl.getFinalStates());

		completarAutomaton(automaton);

		automatonList.add(NroAutomaton, automaton);

	}

	public void completarAutomaton(Automaton automaton) {

		HashMap<String, Map<String, String>> destiny = new HashMap<>();

		if (automaton.getStatesList().contains("E")) {
			automaton.getStatesList().add("E");
		}

		for (int i = 0; i < automaton.getStatesList().size(); i++) {

			Map<String, String> dest = new HashMap<>();
			for (int j = 0; j < automaton.getAlphabet().size(); j++) {

				dest.put(automaton.getAlphabet().get(j), "E");

			}

			destiny.put(automaton.getStatesList().get(i), dest);
		}


		for (HashMap.Entry<String, Map<String, String>> entryAut : automaton.getTransitionFunction().entrySet()) {

			Map<String, String> dest = new HashMap<>();
			for (HashMap.Entry<String, Map<String, String>> entry2 : destiny.entrySet()) {

				for (Map.Entry<String, String> entry3 : entry2.getValue().entrySet()) {

					if (entryAut.getKey().equals(entry2.getKey())
							&& !(entryAut.getValue().containsKey(entry3.getKey()))) {

						entryAut.getValue().put(entry3.getKey(), "E");

					}
				}
			}
		}
	}

	/**
	 * Metodo encargado de crear el archivo dot con el contenido
	 * 
	 * @param route   Ruta donde se desea crear el archivo
	 * @param contens Contenido a escribir el en archivo dot
	 */
	public void createFile(String route, String contens) {

		FileWriter fw = null;
		PrintWriter pw = null;

		try {
			fw = new FileWriter(route);
			pw = new PrintWriter(fw);
			pw.write(contens);
			pw.close();
			fw.close();
		} catch (Exception e) {

		} finally {
			if (pw != null)
				pw.close();
		}
	}

	/**
	 * Metodo encargado de escribir el contenido para el archivo dot del automata
	 * que se desea dibujar
	 * 
	 * @param automaton automata que se desea escribir en archivo dot
	 * @return el texto del automata para el dot
	 * @throws IOException
	 */
	public String writeDot(Automaton automaton) throws IOException {

		String automatonText = "digraph { \n";

		automatonText += "rankdir=LR; \n";
		automatonText += "fake [style=invisible] \n";

		for (int i = 0; i < automaton.getStatesList().size(); i++) {

			if (automaton.getStatesList().get(i).equals(automaton.getInitialState())) {
				automatonText += "fake -> " + automaton.getStatesList().get(i) + " [style=bold] \n";
				automatonText += automaton.getStatesList().get(i) + " [root=true] \n";

			} else if (automaton.getFinalStates().contains(automaton.getStatesList().get(i))) {
				automatonText += automaton.getStatesList().get(i) + " [shape=doublecircle] \n";
			} else
				automatonText += automaton.getStatesList().get(i) + " \n";
		}

		for (HashMap.Entry<String, Map<String, String>> entry : automaton.getTransitionFunction().entrySet()) {

			for (Map.Entry<String, String> entry2 : entry.getValue().entrySet()) {

				automatonText += entry.getKey() + " -> " + entry2.getValue() + " [ label = " + entry2.getKey()
						+ " ];\n";

			}

		}
		automatonText += "}";

		// System.out.println(automatonText);
		return automatonText;
	}

	/**
	 * Metodo de encargado de dibujar el automata en un png
	 * 
	 * @param NroAutomaton automata que se desea realizar
	 */
	public void drawAutomaton(int NroAutomaton) {
		try {

			createFile("./resources/DOT/automata0" + NroAutomaton + ".dot", writeDot(automatonList.get(NroAutomaton)));

			ProcessBuilder pbuilder;

			/*
			 * Realiza la construccion del comando en la linea de comandos esto es: dot
			 * -Tpng -o archivo.png archivo.dot
			 */
			pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", "./resources/PNG/automata0" + NroAutomaton + ".png",
					"./resources/DOT/automata0" + NroAutomaton + ".dot");
			pbuilder.redirectErrorStream(true);
			// Ejecuta el proceso
			pbuilder.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que realiza la inteccecion o union de los automatas segun la bandera
	 * que entra por parametro
	 * 
	 * @param flag Verdadero para realizar union, false para realizar interseccion
	 * @return
	 */
	public boolean unionOrIntersection(Boolean flag) {

		if (automatonList.size() == 3)
			automatonList.remove(2);

		// Si la lista de automatas es menor a 2 significa que solo hay 1 automata y no
		// se puede realizar la union o interseccion, retornamos falso, de lo contrario
		// procedemos con la operacion y retornamos verdadero
		if (automatonList.size() >= 2) {

			// Quintupla para el nuevo automata resultado de la union o inteseccion
			ArrayList<String> statesList = new ArrayList<>();
			ArrayList<String> alphabet = new ArrayList<>();
			HashMap<String, Map<String, String>> transitionFunction;
			String initialState;
			ArrayList<String> finalStates = new ArrayList<>();

			// Variables para la manipulacion de los automatas 1 y 2
			Automaton aut1 = automatonList.get(0);
			Automaton aut2 = automatonList.get(1);

			// Variables para la manipulacion de los estados en la funcion de transiccion
			HashMap<String, Map<String, String>> transitionFunctionAut1, transitionFunctionAut2;

			transitionFunction = new HashMap<>();
			transitionFunctionAut1 = aut1.getTransitionFunction();
			transitionFunctionAut2 = aut2.getTransitionFunction();
			String stateAut1;
			String stateAut2;

			// Lista de estados del automata 1 y 2 y sus tamaños
			ArrayList<String> statesListAut1 = aut1.getStatesList();
			ArrayList<String> statesListAut2 = aut2.getStatesList();
			int sizeStatesListAut1 = statesListAut1.size();
			int sizeStatesListAut2 = statesListAut2.size();

			// Lista de estados finales del automata 1 y 2
			ArrayList<String> finalStatesAut1 = aut1.getFinalStates();
			ArrayList<String> finalStatesAut2 = aut2.getFinalStates();

			// Recorremos la lista de estados del automata 1
			for (int i = 0; i < sizeStatesListAut1; i++) {

				// Asignamos el estado 1 del automata 1 a la variable
				stateAut1 = statesListAut1.get(i);

				// Recorremos la lista de estados del automata 2
				for (int j = 0; j < sizeStatesListAut2; j++) {

					// Asignamos el estado 2 del automata 2 a la variable
					stateAut2 = statesListAut2.get(j);

					// Si flag es verdadero se realiza la union de los automatas, falso se realiza
					// la inteseccion de los automatas
					if (flag) {

						// Si algunos de los estados de los automatas es estado final, se agrega a la
						// lista de estados finales del nuevo automata resultado de la UNION el estado
						// del aut1 mas el estado del aut 2 como uno solo
						if (finalStatesAut1.contains(stateAut1) || finalStatesAut2.contains(stateAut2))
							finalStates.add(stateAut1 + stateAut2);
					} else {

						// Si ambos estados de los automatas son estados finales, se agrega a la
						// lista de estados finales del nuevo automata resultado de la INTERSECCION el
						// estado del aut1 mas el estado del aut 2 como uno solo
						if (finalStatesAut1.contains(stateAut1) && finalStatesAut2.contains(stateAut2))
							finalStates.add(stateAut1 + stateAut2);
					}

					// agregamos a la lista de estados del nuevo automata resultado de la union o
					// interseccion el estado del aut1 mas el estado del aut2 como uno solo
					statesList.add(stateAut1 + stateAut2);
				}
			}

			// Si flag es verdadero se realizara la union de ambos automatas asi que el
			// alfabeto estara conformado por todos los simbolos de ambos automatas sin
			// repetirse
			//
			// Si flag es falso se realizara la interseccion de ambos automatas asi que el
			// alfabeto estara conformado por los simbolos en comun de ambos automatas

			if (flag) {
				// Agregamos a la lista del alfabeto del nuevo automata el alfabeto del
				// automata 1
				alphabet.addAll(aut1.getAlphabet());

				// Recorremos la lista del alfabeto del automata 2
				for (int i = 0; i < aut2.getAlphabet().size(); i++) {

					// Si flag es verdadero se colocan todos los simbolos del alfabeto del automata
					// 2 que no esten repetidos en el alfabeto compuesto por el automata 1
					if (!alphabet.contains(aut2.getAlphabet().get(i)))
						alphabet.add(aut2.getAlphabet().get(i));

				}
			} else {

				// Recorremos los alfabetos del automata 1 y 2
				for (int i = 0; i < aut1.getAlphabet().size(); i++) {
					for (int j = 0; j < aut2.getAlphabet().size(); j++) {

						// Verificamos si un estado pertenece a los alfabetos de ambos automatas, si es
						// asi, lo agregamos al alfabeto del nuevo automata resultante de la inteseccion
						if (!alphabet.contains(aut2.getAlphabet().get(i)))
							alphabet.add(aut2.getAlphabet().get(i));
					}
				}
			}

			// Recorremos la funcion de transiccion del automata 1
			for (HashMap.Entry<String, Map<String, String>> entryAut1 : transitionFunctionAut1.entrySet()) {

				// Recorremos la funcion de transiccion del automata 2
				for (HashMap.Entry<String, Map<String, String>> entryAut2 : transitionFunctionAut2.entrySet()) {

					// Asignamos el nuevo estado conformado por estado del aut 1 mas el estado del
					// aut 2
					String state = entryAut1.getKey() + entryAut2.getKey();

					// Preparamos el Map para asignar la lista de estados destino al estado actual
					// (state), compuesta por el simbolo (key) y estado destino (value) con ese
					// simbolo
					Map<String, String> destinyStates = new HashMap<>();

					// Recorremos la lista de estados destino del automata 1
					for (Map.Entry<String, String> entry2Aut1 : entryAut1.getValue().entrySet()) {

						// Recorremos la lista de estados destino del automata 2
						for (Map.Entry<String, String> entry2Aut2 : entryAut2.getValue().entrySet()) {

							// Si nos encontramos en el mismo simbolo procedemos a llenar nuestro nuevo
							// objeto para la funcion de transiccion resultado de la union o interseccion de
							// ambos automatas, compuesto por el estado actual (state), simbolo y estado
							// destino (estado destino del aut 1 mas el estado destino del aut 2 como uno
							// solo) con ese simbolo
							if (entry2Aut1.getKey().equals(entry2Aut2.getKey())) {

								String symbol = entry2Aut1.getKey();
								String destinationState = entry2Aut1.getValue() + entry2Aut2.getValue();
								destinyStates.put(symbol, destinationState);

								transitionFunction.put(state, destinyStates);
							}
						}
					}
				}
			}

			// Asignamos el estado inicial del automata resultado de la union o interseccion
			// como la suma del estado inicial del aut 1 mas el estado inicial del aut 2
			initialState = automatonList.get(0).getInitialState() + automatonList.get(1).getInitialState();

			Automaton automaton = new Automaton();

			automaton.setStatesList(statesList);
			automaton.setAlphabet(alphabet);
			automaton.setTransitionFunction(transitionFunction);
			automaton.setInitialState(initialState);
			automaton.setFinalStates(finalStates);

			automatonList.add(2, automaton);

			// Creamos el automata y lo asignamos en la posicion 2 de la lista de automatas

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metodo encargado de elegir si se realizara la union a los automatas
	 * 
	 * @return
	 */
	public Boolean union() {

		return unionOrIntersection(true);
	}

	/**
	 * Metodo encargado de elegir si se realizara la interseccion a los automatas
	 * 
	 * @return
	 */
	public Boolean intersection() {

		return unionOrIntersection(false);
	}

	/**
	 * Metodo encargado de hacer el complemento del automata
	 * 
	 * @param NroAutomaton automata al que se le desea hacer el complemento
	 * @return
	 */
	public Boolean complement(int NroAutomaton) {

		// Si no se ha elegido el automata el tamaño de la lista sera menor al automata
		// que se pide realizar complemento (NroAutomaton) y retornamos false, de lo
		// contrario procedemos y retornamos verdadero
		if (automatonList.size() > NroAutomaton) {
			// Asignamos a la variable automata el automata al que se le desea realizar el
			// complemento
			Automaton automaton = automatonList.get(NroAutomaton);

			// Lista para guardar los nuevo estados finales
			ArrayList<String> finalStates = new ArrayList<>();

			// Asignamos el estado inicial al automata en cuestion que se le realiza el
			// complemento como el estado final
			automaton.setInitialState(automaton.getFinalStates().get(0));

			// Recorremos la lista de estados
			for (int i = 0; i < automaton.getStatesList().size(); i++) {

				// Si el estado actual no es un estado inicial, lo hacemos estado final
				if (!automaton.getStatesList().get(i).equals(automaton.getInitialState()))
					finalStates.add(automaton.getStatesList().get(i));

			}

			// Asignamos los estados finales al automata es cuestion que se le realiza el
			// complemento
			automaton.setFinalStates(finalStates);

			return true;
		} else
			return false;

	}

	/**
	 * Metodo encargado de hacer el reverso del automata
	 * 
	 * @param NroAutomaton automata al que se le desea hacer el reverso
	 * @return
	 */
	public Boolean reverse(int NroAutomaton) {

		// Si no se ha elegido el automata el tamaño de la lista sera menor al automata
		// que se pide realizar reverso (NroAutomaton) y retornamos false, de lo
		// contrario procedemos y retornamos verdadero
		if (automatonList.size() > NroAutomaton) {
			// Asignamos a la variable automata el automata al que se le desea realizar el
			// reverso
			Automaton automaton = automatonList.get(NroAutomaton);

			// Lista para guardar los nuevo estados finales
			ArrayList<String> finalStates = new ArrayList<>();

			// Variables para la manipulacion de los estados en la funcion de transiccion
			HashMap<String, Map<String, String>> transitionFunctionAut1, transitionFunction;
			transitionFunction = new HashMap<>();
			transitionFunctionAut1 = automaton.getTransitionFunction();

			// Guardamos el estado inicial del automata en cuestion
			String inicialState = automaton.getInitialState();

			// Asignamos el estado inicial al automata en cuestion que se le realiza el
			// reverso como el estado final
			automaton.setInitialState(automaton.getFinalStates().get(0));

			// Asignamos a la lista de estados finales el estado inicial guardado en
			// inicialState
			finalStates.add(inicialState);
			automaton.setFinalStates(finalStates);

			// Recorremos la funcion de transicion del automata en cuestion
			for (HashMap.Entry<String, Map<String, String>> entryAut : transitionFunctionAut1.entrySet()) {

				// Recorremos la lista de estados destino del automata
				for (Map.Entry<String, String> entry2Aut : entryAut.getValue().entrySet()) {

					// Preparamos el Map para asignar la lista de estados destino al estado actual
					// (state = entru2Aut.value), compuesta por el simbolo (entry2Aut.key) y estado
					// destino (entryAut.State) con ese simbolo
					Map<String, String> destinyStates = new HashMap<>();

					// Asignamos el estado como el destinationState
					String state = entry2Aut.getValue();

					// Asignamos el simbolo
					String symbol = entry2Aut.getKey();

					// Asignamos el estado destino como el State
					String destinationState = entryAut.getKey();

					// Si no estamos yendo de un estado al mismo estado le agregamos, porque el
					// reverso de eso seria quedarse en el mismo estado
					if (!state.contains(destinationState)) {

						// Agregamos los nuevos valores para la funcion de transiccion del automata en
						// cuestion
						destinyStates.put(symbol, destinationState);
						transitionFunction.put(state, destinyStates);

					}
				}
			}

			Map<String, String> destiny = new HashMap<>();
			destiny.clear();

			for (int i = 0; i < automaton.getStatesList().size(); i++) {

				if (!transitionFunction.containsKey(automaton.getStatesList().get(i))) {

					transitionFunction.put(automaton.getStatesList().get(i), destiny);
				}
			}

			automaton.setTransitionFunction(transitionFunction);

			for (HashMap.Entry<String, Map<String, String>> entryAut : automaton.getTransitionFunction().entrySet()) {

				for (int i = 0; i < automaton.getAlphabet().size(); i++) {

					if (!entryAut.getValue().containsKey(automaton.getAlphabet().get(i))) {

						entryAut.getValue().put(automaton.getAlphabet().get(i), entryAut.getKey());

					}
				}

			}

			return true;
		} else
			return false;
	}
}
