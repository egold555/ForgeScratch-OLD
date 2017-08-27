package org.golde.java.testjavafxhtml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.golde.java.testjavafxhtml.helpers.JavaHelper;
import org.golde.java.testjavafxhtml.helpers.PLog;

import netscape.javascript.JSObject;

/**
 * The class that JSObject comunicates too and calls functions to
 * @author Eric
 *
 */
public class JSFunctions {
	
	private Main main;
	private File forgeDir;
	private JSObject javaApp;
	
	public JSFunctions(Main main) {
		this.main = main;
		this.javaApp = main.window;
		this.forgeDir = main.forge_folder;
	}
	
	public String saveXML() {
		String XML = (String) javaApp.call("saveXML");
		XML = XML.substring(0, 42) + "<modName>" + main.MOD_NAME + "</modName>" + XML.substring(42);
		return XML;
	}

	public void load(String xml) {
		int startIndex = xml.indexOf("<modName>") + "<modName>".length();
		int endIndex = xml.indexOf("</modName>");
		main.MOD_NAME = xml.substring(startIndex, endIndex);
		javaApp.call("loadXML", xml);
	}
	
	public void run(String rawProjectName, String code) {
		String projectName = rawProjectName.replace(" ", "_");
		try {
		// 1. Search code for classes to get list of all blocks into a string list.
		List<String> blockNames = findBlockNames(code);
		
		// 2. Read Mod_Template into a string.
		String modTemplate = JavaHelper.readFile(new File(forgeDir, "forgescratch\\Mod_Template.java"));
		
		// 3. Replace stuff in that string.
		modTemplate = modTemplate.replace("Mod_Template", "Mod_" + projectName);
		modTemplate = modTemplate.replace("Mod Template", rawProjectName);
		
		modTemplate = modTemplate.replace("/*Variables*/", variables(blockNames));
		modTemplate = modTemplate.replace("/*Constructor calls*/", constructorCalls(blockNames));
		
		modTemplate = modTemplate.replace("/*Classes*/", fixCode(code));
		
		// 4. Write string to a new file.
		JavaHelper.writeFile(new File(forgeDir, "src\\main\\java\\org\\golde\\forge\\scratchforge\\mods\\" + "Mod_" + projectName + ".java"), modTemplate);
		
		
		}
		catch(Exception e) {
			PLog.error(e, "Failed to make mod!");
		}
		
		try {
			JavaHelper.runCMD(forgeDir, "\"C:\\Program Files\\Java\\jdk1.8.0_60/bin/java.exe\" -Xincgc -Xmx4G -Xms4G \"-Dorg.gradle.appname=gradlew\" -classpath \"gradle\\wrapper\\gradle-wrapper.jar\" org.gradle.wrapper.GradleWrapperMain runClient");
		}catch(Exception e) {
			PLog.error(e, "Failed to start forge!");
		}
	}
	
	private List<String> findBlockNames(String code) {
		List<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile("public class Mcblock_(.*?) extends BlockBase");
		
		Matcher matcher = pattern.matcher(code);
		while (matcher.find()) {
			result.add(matcher.group(1));
		}
		
		return result;
	}
	
	private String variables(List<String> blockNames) {
		String result = "";
		
		for (String blockName: blockNames) {
			result += "static " + className(blockName) + " " + variableName(blockName) + ";" + "\n";
		}
		
		return result;
	}

	
	private String constructorCalls(List<String> blockNames) {
		String result = "";
		
		for (String blockName: blockNames) {
			result += variableName(blockName) + " = new " + className(blockName) + "();" + "\n";
		}
		
		return result;
	}
	
	
	private String fixCode(String code) {
		code = code.replace("package delete_me;", "");
		code = code.replace("public class MyApp {", "");
		code = code.substring(0, code.length() - 4);
		return code;
	}
	
	private String variableName(String blockName)
	{
		return "mcblock_" + blockName;
	}
	
	private String className(String blockName)
	{
		return "Mcblock_" + blockName;
	}
	
	public void log(String msg) {
		PLog.info("[JS] " + msg);
	}
	
}
