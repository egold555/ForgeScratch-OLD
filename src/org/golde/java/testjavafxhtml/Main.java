package org.golde.java.testjavafxhtml;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.golde.java.testjavafxhtml.helpers.JavaHelper;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * Main program class
 * @author Eric
 *
 */
public class Main implements ActionListener{

	//File for JSObject window to communicate functions to
	public JSFunctions jsFunctions; 
	
	//Config manager. 
	//Makes a properties file and simple saving and loading settings
	public Config config = new Config();

	//Forge directory
	public File forge_folder = new File("forge");

	//Main JFame
	private static JFrame frame = new JFrame("Forge Scratch"); 

	//Menu bar for JFrame
	private JMenuBar menuBar = new JMenuBar(); 

	//Every thing to put under the "File" button in the menu bar
	private JMenuItem file_newItem = new JMenuItem("New");
	private JMenuItem file_openItem = new JMenuItem("Open");
	private JMenuItem file_saveItem = new JMenuItem("Save");
	private JMenuItem file_saveAsItem = new JMenuItem("Save As");
	private JMenuItem file_exitItem = new JMenuItem("Exit");

	//Every thing to put under the "Mod Options" button in the menu bar
	private JMenuItem modOptions_textures = new JMenuItem("Textures");
	private JMenuItem modOptions_enabledMods = new JMenuItem("Enabled Mods");
	private JMenuItem modOptions_exportMod = new JMenuItem("Export Mod");

	//Every thing to put under the "Program Options" button in the menu bar
	private JMenuItem programOptions_programArgs = new JMenuItem("Java Arguments");

	//Every thing to put under the "Help" button in the menu bar
	private JMenuItem help_about = new JMenuItem("About");

	//File name for wither opening or closing. Set by "Open" or "Save As"
	private String filename = null;  

	//Simple final arguments for the file extension so I do not miss type it.
	private final String FILE_EXTENTION = "blockmod";
	private final String FILE_EXTENTION_DESCRIPTION = "Mod Save File";

	//The magical thing that communicates with the javascript portion
	public JSObject window; 

	//Default mod name, gets overwritten on new project creation
	public String MOD_NAME = "If you see this, something bad happened"; 



	public static void main(String[] args) {
		//Run things after everything, also non static :)
		SwingUtilities.invokeLater(new Main()::initAndShowGUI); 
	}

	//Start creation of everything
	void initAndShowGUI() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JFXPanel fxPanel = new JFXPanel(){

			private static final long serialVersionUID = 5346914097448163759L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(640, 480);
			}
		};

		frame.add(fxPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		//Make the menu button "File" and add elements to it
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(file_newItem);
		fileMenu.add(file_openItem);
		fileMenu.add(file_saveItem);
		fileMenu.add(file_saveAsItem);
		fileMenu.add(file_exitItem);
		file_newItem.addActionListener(this);
		file_openItem.addActionListener(this);
		file_saveItem.addActionListener(this);
		file_saveAsItem.addActionListener(this);
		file_exitItem.addActionListener(this);
		menuBar.add(fileMenu);

		//Make the menu button "Mod Options" and add elements to it
		JMenu modOptionsMenu = new JMenu("Mod Options");
		modOptionsMenu.add(modOptions_textures);
		modOptionsMenu.add(modOptions_enabledMods);
		modOptionsMenu.add(modOptions_exportMod);
		modOptions_textures.addActionListener(this);
		modOptions_textures.setEnabled(false);
		modOptions_enabledMods.addActionListener(this);
		modOptions_exportMod.addActionListener(this);
		modOptions_exportMod.setEnabled(false);
		menuBar.add(modOptionsMenu);

		//Make the menu button "Program Options" and add elements to it
		JMenu programOptionsMenu = new JMenu("Program Options");
		programOptionsMenu.add(programOptions_programArgs);
		programOptions_programArgs.addActionListener(this);
		programOptions_programArgs.setEnabled(false);
		menuBar.add(programOptionsMenu);

		//Make the menu button "Help" and add elements to it
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(help_about);
		help_about.addActionListener(this);
		help_about.setEnabled(false);
		menuBar.add(helpMenu);

		frame.setJMenuBar(menuBar);

		Platform.runLater(() -> {
			fxPanel.setScene(createScene());
		});

		startupDialog();
	}

	//Setup the web scene and JSObject
	Scene createScene() {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		WebView  webView = new WebView();
		webView.setContextMenuEnabled(false);
		WebEngine webEngine = webView.getEngine();
		File f = new File("html\\index.html");
		webEngine.getLoadWorker().stateProperty().addListener(
				new ChangeListener<Worker.State>() {
					public void changed(ObservableValue<? extends Worker.State> ov, Worker.State oldState, Worker.State newState) {
						if (newState == Worker.State.SUCCEEDED) {
							// web page is loaded.
							window = (JSObject) webEngine.executeScript("window");
							jsFunctions = new JSFunctions(Main.this);
							window.setMember("java_app", jsFunctions);

						}
					}
				});


		webEngine.load(f.toURI().toString());
		root.getChildren().add(webView);
		return scene;
	}


	// Handle menu events
	@Override
	public void actionPerformed(ActionEvent e) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Object source = e.getSource();
				//File
				if (source == file_newItem) {
					//new mod
					createMod(false);
				}
				else if (source == file_openItem) {
					loadFile(false);
				}
				else if (source == file_saveItem) {
					saveFile(filename);
				}
				else if (source == file_saveAsItem) {
					saveFile(null);
				}
				else if (source == file_exitItem) {
					System.exit(0);
				}
				
				//Mod Options
				else if(source == modOptions_enabledMods) {
					jsFunctions.showEnabledMods(frame);
				}
				

			}
		});

	}

	// Prompt user to enter filename and load file.  Allow user to cancel.
	// If file is not found, pop up an error and leave screen contents
	// and filename unchanged.
	private void loadFile(boolean isStarting) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.addChoosableFileFilter(new FileNameExtensionFilter(FILE_EXTENTION_DESCRIPTION, FILE_EXTENTION));
		fc.setAcceptAllFileFilterUsed(false);
		fc.setMultiSelectionEnabled(false);
		String name = null;
		if (fc.showOpenDialog(null) != JFileChooser.CANCEL_OPTION) {
			name = fc.getSelectedFile().getAbsolutePath();
		}
		else {
			if(isStarting) {
				startupDialog();
			}
			return;  // user cancelled

		}
		try {
			String XML = JavaHelper.readFile(new File(name));

			jsFunctions.load(XML);
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(frame, "File not found: " + name, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Save named file.  If name is null, prompt user and assign to filename.
	// Allow user to cancel, leaving filename null.  Tell user if save is
	// successful.
	private void saveFile(String name) {
		if (name == null) {  // get filename from user
			JFileChooser fc = new JFileChooser();
			fc.setDialogType(JFileChooser.SAVE_DIALOG);
			fc.addChoosableFileFilter(new FileNameExtensionFilter(FILE_EXTENTION_DESCRIPTION, FILE_EXTENTION));
			fc.setAcceptAllFileFilterUsed(false);
			if (fc.showSaveDialog(null) != JFileChooser.CANCEL_OPTION) {
				name = fc.getSelectedFile().getAbsolutePath();
			}
		}
		if (name != null) {  // else user cancelled
			try {

				if(!name.endsWith("." + FILE_EXTENTION)) { //make sure users dont mess file extenton up
					name += "." + FILE_EXTENTION;
				}

				File file = new File(name);

				if (file.exists()) {
					int response = JOptionPane.showConfirmDialog(null, "Do you want to replace the existing file?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response != JOptionPane.YES_OPTION) {
						return;
					} 
				}


				JavaHelper.writeFile(file, jsFunctions.saveXML());
				filename = name;

				JOptionPane.showMessageDialog(frame, "Saved to " + filename, "Save File", JOptionPane.PLAIN_MESSAGE);
			}
			catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(frame, "Cannot write to file: " + name, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/*private void showProgramArgsMenu() {
        JTextField field1 = new JTextField("-Xincgc -Xmx4G -Xms4G");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Arguments:"));
        panel.add(field1);
        int result = JOptionPane.showConfirmDialog(null, panel, "Program Argument Options", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

        }
	}*/

	//Main dialog on startup
	public void startupDialog() {
		JRadioButton newProject = new JRadioButton("New");
		JRadioButton openProject = new JRadioButton("Open");
		
		newProject.setSelected(true);

		ButtonGroup bttnGroup = new ButtonGroup();
		bttnGroup.add(newProject);
		bttnGroup.add(openProject);

		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(newProject);
		panel.add(openProject);

		int result; 
		do  {
			result = JOptionPane.showConfirmDialog(frame, panel, "What would you like to do?", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		}
		while(result == JOptionPane.CLOSED_OPTION); 

		if(newProject.isSelected()) {
			createMod(true);
		}else {
			loadFile(true);
		}
	}

	//Prompt screen to make a mod
	private void createMod(boolean isStarting) {
		JTextField field1 = new JTextField("");
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("Mod Name: "));
		panel.add(field1);
		int result = JOptionPane.showConfirmDialog(frame, panel, "New Mod", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION && !JavaHelper.isStringEmpty(field1.getText())) {
			MOD_NAME = field1.getText();
		}else {
			if(isStarting) {
				startupDialog();
			}
			return;
		}
	}

	

	//Simple error dialog
	public static void showQuickErrorDialog(IOException e) {
		final JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Sans-Serif", Font.PLAIN, 10));
		textArea.setEditable(false);
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		textArea.setText(writer.toString());

		JScrollPane scrollPane = new JScrollPane(textArea);		
		scrollPane.setPreferredSize(new Dimension(350, 150));

		JOptionPane.showMessageDialog(frame, scrollPane, "An Error Has Occurred", JOptionPane.ERROR_MESSAGE);
	}

}
