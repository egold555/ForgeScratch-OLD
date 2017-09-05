package org.golde.java.scratchforge.helpers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

/**
 * A class to do functions that Java should have built in
 * @author Eric
 *
 */
public class JavaHelper {

	//Opens up a cmd prompt and executes commands. 
	public static void runCMD(File dir, String cmd) throws IOException {
		Runtime.getRuntime().exec("cmd.exe /c cd \"" + dir.getAbsolutePath() + "\" & start \"Console\" cmd.exe /c \"" + cmd + "\"");
	}


	//Write file to system
	public static void writeFile(File file, String text) throws FileNotFoundException, UnsupportedEncodingException {
		Formatter out = new Formatter(file, "UTF-8");  // might fail
		out.format("%s", text);
		out.close();
	}

	//Read file from system
	public static String readFile(File file) throws FileNotFoundException, UnsupportedEncodingException {
		Scanner in = new Scanner(file, "UTF-8");
		String result = "";
		while (in.hasNext()) {
			result = result + in.nextLine() + "\n";
		}
		in.close();
		return result;
	}

	static List<File> files;
	//List all files in folder. This works for nested files
	public static List<File> listFilesForFolder(final File folder) {
		files = new ArrayList<File>();
		File[] listOfFiles = folder.listFiles();
		if(listOfFiles == null) {
			PLog.error("Directory " + folder.getAbsolutePath() + " is missing!!! Returning a blank list so that we do not crash!");
			return files;
		}
		for (final File fileEntry : listOfFiles) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				files.add(fileEntry);
			}
		}
		return files;
	}

	public static boolean isStringEmpty(String s) {

		if(s == null) {return true;}
		if(s.equalsIgnoreCase("")) {return true;}
		if(s.equalsIgnoreCase(" ")) {return true;}

		return false;
	}
	
	public static void openFileWithDefaultProgram(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (Exception e) {
			PLog.error(e, "Failed to open file!");
		}
	}
	
	public static String base64Encode(String toEncode) {
		return Base64.getEncoder().encodeToString(toEncode.getBytes());
	}
	
	public static String base64Decode(String toDecode) {
		return new String(Base64.getDecoder().decode(toDecode));
	}
	
	public static String makeJavaId(String name) {
		String result = "";
		for (int i = 0; i < name.length(); ++i) {
			char c = name.charAt(i);
			if (isJavaId(c)) {
				result = result + c;
			}
			else {
				result = result + "_";
			}
		}

		return result;
	}

	public static boolean isJavaId(char c) {
		if (c >= 'A' && c <= 'Z')
			return true;
		else if (c >= 'a' && c <= 'z') 
			return true;
		else if (c >= '0' && c <= '9') 
			return true;

		return false;
	}
}
