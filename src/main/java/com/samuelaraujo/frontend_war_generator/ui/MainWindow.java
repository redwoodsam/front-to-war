package com.samuelaraujo.frontend_war_generator.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.samuelaraujo.frontend_war_generator.dto.GenerationDTO;
import com.samuelaraujo.frontend_war_generator.dto.ProjectDetailsDTO;
import com.samuelaraujo.frontend_war_generator.service.MainService;
import com.samuelaraujo.frontend_war_generator.service.exception.GlobalExceptionHandler;
import com.samuelaraujo.frontend_war_generator.service.frontendlibrary.Environment;

public class MainWindow implements ActionListener {

	private JFrame frmGeradorDeWar;
	private JPanel logPane;
	
	// Text Fields
	private JTextField fldProjectFolder;
	private JTextField fldProjectName;
	private JTextField fldProjectDescription;
	private JComboBox<String> comboBoxEnvironment;
	private JTextArea logTextArea;
	
	// Buttons
	private JButton btnProjectFolder;
	private JButton btnGenerate;
	private JFileChooser projectChooser;
	
	private File selectedProjectFolder;
	private List<Environment> environments;
	private String projectLibrary;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		PrintStream outputPrintStream = new PrintStream(new CustomOutputStream(logTextArea));
		PrintStream errorPrintStream = new PrintStream(new CustomErrorStream(frmGeradorDeWar));
		System.setOut(outputPrintStream);
		System.setErr(outputPrintStream);
		GlobalExceptionHandler.registerExceptionHandler();
	}
	
	public void start() {
		frmGeradorDeWar.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGeradorDeWar = new JFrame();
		frmGeradorDeWar.setTitle("Gerador de WAR");
		frmGeradorDeWar.setResizable(false);
		frmGeradorDeWar.setBounds(100, 100, 450, 533);
		frmGeradorDeWar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGeradorDeWar.getContentPane().setLayout(null);
		frmGeradorDeWar.setLocationRelativeTo(null);
		
		projectChooser = new JFileChooser();
		projectChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		JPanel projectSelectionPane = new JPanel();
		projectSelectionPane.setBounds(27, 11, 379, 130);
		frmGeradorDeWar.getContentPane().add(projectSelectionPane);
		projectSelectionPane.setLayout(null);
		
		JLabel lblProjectFolder = new JLabel("Selecione a pasta do seu projeto: ");
		lblProjectFolder.setBounds(10, 5, 359, 14);
		lblProjectFolder.setHorizontalAlignment(SwingConstants.CENTER);
		projectSelectionPane.add(lblProjectFolder);
		
		JPanel projectPane = new JPanel();
		projectPane.setBounds(10, 30, 359, 37);
		projectSelectionPane.add(projectPane);
		projectPane.setLayout(null);
		
		fldProjectFolder = new JTextField();
		fldProjectFolder.setEditable(false);
		fldProjectFolder.setBounds(10, 5, 309, 25);
		projectPane.add(fldProjectFolder);
		fldProjectFolder.setColumns(10);
		
		btnProjectFolder = new JButton("...");
		btnProjectFolder.setToolTipText("Procurar");
		btnProjectFolder.setBounds(324, 5, 35, 25);
		btnProjectFolder.addActionListener(this);
		projectPane.add(btnProjectFolder);
		
		JPanel environmentPane = new JPanel();
		environmentPane.setBounds(10, 67, 359, 53);
		projectSelectionPane.add(environmentPane);
		environmentPane.setLayout(null);
		
		JLabel lblEnvironment = new JLabel("Selecione o ambiente:");
		lblEnvironment.setBounds(0, 9, 359, 14);
		lblEnvironment.setHorizontalAlignment(SwingConstants.CENTER);
		environmentPane.add(lblEnvironment);
		
		comboBoxEnvironment = new JComboBox<String>();
		comboBoxEnvironment.setEnabled(false);
		comboBoxEnvironment.setBounds(10, 31, 349, 22);
		environmentPane.add(comboBoxEnvironment);
		
		JPanel projectInfoPane = new JPanel();
		projectInfoPane.setBackground(SystemColor.menu);
		projectInfoPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		projectInfoPane.setBounds(27, 159, 379, 68);
		frmGeradorDeWar.getContentPane().add(projectInfoPane);
		projectInfoPane.setLayout(null);
		
		JLabel lblProjectName = new JLabel("Nome do projeto:");
		lblProjectName.setBounds(10, 6, 122, 25);
		projectInfoPane.add(lblProjectName);
		
		JLabel lblProjectDescription = new JLabel("Descrição:");
		lblProjectDescription.setBounds(10, 36, 122, 22);
		projectInfoPane.add(lblProjectDescription);
		
		fldProjectName = new JTextField();
		fldProjectName.setEditable(false);
		fldProjectName.setColumns(10);
		fldProjectName.setBounds(127, 6, 242, 25);
		projectInfoPane.add(fldProjectName);
		
		fldProjectDescription = new JTextField();
		fldProjectDescription.setEditable(false);
		fldProjectDescription.setColumns(10);
		fldProjectDescription.setBounds(127, 35, 242, 25);
		projectInfoPane.add(fldProjectDescription);
		
		btnGenerate = new JButton("Gerar");
		btnGenerate.setEnabled(false);
		btnGenerate.addActionListener(this);
		btnGenerate.setBounds(166, 234, 102, 39);
		frmGeradorDeWar.getContentPane().add(btnGenerate);
		
		logTextArea = new JTextArea(50, 10);
		logTextArea.setEditable(true);
		logTextArea.setForeground(Color.GREEN);
		logTextArea.setBackground(Color.BLACK);
		logTextArea.setLineWrap(true);
		
		logPane = new JPanel();
		logPane.setBackground(Color.BLACK);
		logPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		logPane.setBounds(10, 280, 414, 205);
		frmGeradorDeWar.getContentPane().add(logPane);
		
		JScrollPane logScrollPane = new JScrollPane(logTextArea);
		logScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		logScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		logScrollPane.setBorder(null);
		logScrollPane.setEnabled(true);
		logScrollPane.setPreferredSize(new Dimension(logPane.getWidth() - 30, logPane.getHeight()));
		
		logPane.add(logScrollPane);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnProjectFolder) {
			int returnValue = projectChooser.showOpenDialog(frmGeradorDeWar);
			
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				resetFields();
				selectedProjectFolder = projectChooser.getSelectedFile();
				
				String projectPath = selectedProjectFolder.getAbsolutePath();
				fldProjectFolder.setText(projectPath);
				
				ProjectDetailsDTO projectDetails = MainService.getProjectDetails(projectPath);
				environments = projectDetails.getEnvironments();
				projectLibrary = projectDetails.getLibrary().getName();
				
				fldProjectName.setText(projectDetails.getProjectName());
				fldProjectDescription.setText(projectDetails.getProjectName());
				
				fldProjectName.setEditable(true);
				fldProjectDescription.setEditable(true);
				
				environments.forEach(env -> comboBoxEnvironment.addItem(env.getName()));
				comboBoxEnvironment.setSelectedIndex(0);
				comboBoxEnvironment.setEnabled(true);
				
				logTextArea.append(String.format("-----------------------------------------------"
						+ "-------------------------------------------------\n%s %s - Versão: %s\n--------------------------------"
						+ "----------------------------------------------------------------", 
						new String("\t          "), projectDetails.getLibrary().getName(), projectDetails.getLibrary().getVersion()));
				
				logTextArea.setCaretPosition(0);
				
				btnGenerate.setEnabled(true);
			}
		}
		
		if(e.getSource() == btnGenerate) {
			String projectName = fldProjectName.getText();
			String projectDescription = fldProjectDescription.getText();
			String projectEnvironment = (String) comboBoxEnvironment.getSelectedItem();
			String projectPath = fldProjectFolder.getText();
			
			GenerationDTO generationDTO = new GenerationDTO(
					projectName, projectDescription, projectEnvironment, projectPath, projectLibrary);
			runMainService(generationDTO);
		}
		
	}

	private void runMainService(GenerationDTO generationDTO) {
		btnGenerate.setEnabled(false);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				MainService.run(generationDTO);
				btnGenerate.setEnabled(true);
				JOptionPane.showMessageDialog(frmGeradorDeWar, "Build concluído com sucesso!");
			}
		});
		thread.start();
	}

	private void resetFields() {
		logTextArea.setText("");
		btnGenerate.setEnabled(false);
		fldProjectFolder.setText("");
		fldProjectName.setText("");
		fldProjectDescription.setText("");
		fldProjectName.setEditable(false);
		fldProjectDescription.setEditable(false);
		comboBoxEnvironment.removeAllItems();
		comboBoxEnvironment.setEnabled(false);
	}
}
