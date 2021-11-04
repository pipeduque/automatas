package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;

import org.json.*;

/**
 * Clase donde se controla, administra y verifica el manejo de archivos
 *
 * @author pipe
 * @version 1.0
 */
public class FileControl {

	public FileControl() {

	}

	/**
	 * Abre el explorador de archivos para que el usuario pueda elegir uno y llama
	 * al metodo fileReader para leerlo
	 */
	public File chooseFile() {

		JFileChooser jfc = new JFileChooser();

		File currenDirectory = new File("./resources/automatasJSON");

		jfc.setCurrentDirectory(currenDirectory);
		jfc.showOpenDialog(jfc);

		File file = jfc.getSelectedFile();

		return file;
	}

	/**
	 * Lee el archivo que le entra por parametro, lo divide por lineas y mientras la
	 * linea no sea nula, se agregara la linea al string creado para el json
	 *
	 * 
	 * @param file Archivo que se pretende leer
	 * @throws java.io.IOException ERRORES CON EL MANEJO DEL ARCHIVO
	 * @throws FileException       FORMATOS QUE NO ESPERAMOS EN EL ARCHIVO
	 */
	public JSONObject fileReader(File file) throws IOException, FileException {

		BufferedReader buffer = new BufferedReader(
				new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "utf-8"));

		String line = "";
		String jsonString = "";

		while ((line = buffer.readLine()) != null) {

			jsonString += line;

		}

		JSONObject jsonObjet = new JSONObject(jsonString);

		buffer.close();

		return jsonObjet;
	}
}