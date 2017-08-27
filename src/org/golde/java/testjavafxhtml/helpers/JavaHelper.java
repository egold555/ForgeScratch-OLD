package org.golde.java.testjavafxhtml.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
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
}
