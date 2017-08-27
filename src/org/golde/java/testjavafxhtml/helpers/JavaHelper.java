package org.golde.java.testjavafxhtml.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;

public class JavaHelper {
	
	public static void runCMD(File dir, String cmd) throws IOException {
		Runtime.getRuntime().exec("cmd.exe /c cd \"" + dir.getAbsolutePath() + "\" & start \"Console\" cmd.exe /c \"" + cmd + "\"");
	}
	
	public static void writeFile(File file, String text) throws FileNotFoundException {
		Formatter out = new Formatter(file);  // might fail
		out.format("%s", text);
		out.close();
	}
	
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
