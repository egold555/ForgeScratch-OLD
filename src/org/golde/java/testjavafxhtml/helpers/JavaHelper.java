package org.golde.java.testjavafxhtml.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
	public static void writeFile(File file, String text) throws FileNotFoundException {
		Formatter out = new Formatter(file);  // might fail
		out.format("%s", text);
		out.close();
	}

	//Read file from system
	public static String readFile(File file) throws FileNotFoundException {
		Scanner in = new Scanner(file);
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
		for (final File fileEntry : folder.listFiles()) {
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
}
