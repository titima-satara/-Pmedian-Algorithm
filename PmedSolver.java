
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Patchara
 */
public class PmedSolver extends JFrame {

    int N = 0;
    int E = 0;
    int p = 0;
    int wmax = 0;
    int[][] w = new int[N][N];

    //GeneticAlgorithm obj
    GeneticAlgorithm ga = new GeneticAlgorithm();
    //JFileChooser
    JFileChooser pathOpen = new JFileChooser();
    JFileChooser pathSave = new JFileChooser();
    //JFrame
    JFrame mainUI = new JFrame();
    JFrame configUI = new JFrame();
    JFrame newPUI = new JFrame();
    //JPanel
    JPanel boxSave = new JPanel();
    JPanel boxOpen = new JPanel();
    JPanel mainPanel = new JPanel();
    JPanel configPanel = new JPanel();
    JPanel optPanel = new JPanel();
    JPanel newPPanel = new JPanel();
    //JButton
    JButton configButton = new JButton();
    JButton solveButton = new JButton();
    JButton doneButton_cfg = new JButton();
    JButton doneButton_newp = new JButton();
    JButton newPButton = new JButton();
    //JMenu
    JMenu fileMenu = new JMenu();
    JMenu helpMenu = new JMenu();
    //JMenuItem
    JMenuItem open = new JMenuItem();
    JMenuItem save = new JMenuItem();
    JMenuItem saveResult = new JMenuItem();
    JMenuItem newFile = new JMenuItem();
    JMenuItem howTo = new JMenuItem();
    //JMenuBar
    JMenuBar menuBar = new JMenuBar();
    //JTextArea
    JTextArea textArea = new JTextArea();
    //JLabel
    JLabel headerLabel_cfg = new JLabel("Configuration");
    JLabel headerLabel_newp = new JLabel("Create New Problem");
    JLabel popLabel = new JLabel("Population : ");
    JLabel genLabel = new JLabel("Generation : ");
    JLabel mutationLabel = new JLabel("Mutation Rate : ");
    JLabel crossoverLabel = new JLabel("Crossover Rate : ");
    JLabel seedLabel = new JLabel("Seed : ");
    JLabel tournamentLabel = new JLabel("Tournament Size : ");
    JLabel nodeLabel = new JLabel("Node :");
    JLabel edgeLabel = new JLabel("Edge :");
    JLabel pLabel = new JLabel("p :");
    //JTextField
    JTextField popField = new JTextField("100");
    JTextField genField = new JTextField("100");
    JTextField mutationField = new JTextField("0.02");
    JTextField crossoverField = new JTextField("0.8");
    JTextField seedField = new JTextField("24");
    JTextField tournamentField = new JTextField("2");
    JTextField nodeField = new JTextField("");
    JTextField edgeField = new JTextField("");
    JTextField pField = new JTextField("");
    //JTable
    DefaultTableModel tableModel = new DefaultTableModel(5, 5);
    JTable weightTable = new JTable(tableModel);
    //JScrollPane
    JScrollPane scrPanel_mainW = new JScrollPane(textArea);
    JScrollPane scrPanel_newpW = new JScrollPane(weightTable);
    //Screen Size
    Dimension screenSizeMain = new Dimension(800, 600);
    Dimension screenSizeConfig = new Dimension(480, 500);
    Dimension screenSizeNewP = new Dimension(800, 600);
    //Font
    Font sanSerifFontHeader = new Font("SanSerif", Font.BOLD, 24);
    Font sanSerifFontBody = new Font("SanSerif", Font.PLAIN, 16);
    //Icon
    ImageIcon img = new ImageIcon("icon/Pmed_icon.png");

    public PmedSolver() {
        //Config
        super("P-Median Problem Solver");
        //JScrollPane
        scrPanel_mainW.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPanel_mainW.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrPanel_newpW.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrPanel_newpW.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //Set button 
        configButton.setText("Config");
        configButton.addActionListener((ActionEvent e) -> {
            configButtAction(e);
        });

        solveButton.setText("Solve");
        solveButton.addActionListener((ActionEvent e) -> {
            solveButtAction(e);
        });

        newPButton.setText("Create New Problem");
        newPButton.addActionListener((ActionEvent e) -> {
            newPButtonAction(e);
        });

        doneButton_cfg.setText("Done");
        doneButton_cfg.addActionListener((ActionEvent e) -> {
            doneButtAction_cfg(e);
        });

        doneButton_newp.setText("Done");
        doneButton_newp.addActionListener((ActionEvent e) -> {
            doneButtAction_newp(e);
        });

        //Set menu item value
        open.setText("Open File");
        open.addActionListener((ActionEvent e) -> {
            try {
                openButtAction(e);
            } catch (IOException ex) {
                Logger.getLogger(PmedSolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        save.setText("Save File");
        save.addActionListener((ActionEvent e) -> {
            try {
                saveButtAction(e);
            } catch (IOException ex) {
                Logger.getLogger(PmedSolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        saveResult.setText("Save Result");
        saveResult.addActionListener((ActionEvent e) -> {
            try {
                saveResultButtAction(e);
            } catch (IOException ex) {
                Logger.getLogger(PmedSolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        newFile.setText("New File");
        newFile.addActionListener((ActionEvent e) -> {
            newfileButtAction(e);
        });

        howTo.setText("How to use");
        howTo.addActionListener((ActionEvent e) -> {
            howtoButtAction(e);
        });

        //----------------------------------------------------------------------//
        //MAIN WINDOW
        //Set mainUI menu bar value
        fileMenu.setText("File");
        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(saveResult);
        fileMenu.add(newFile);
        helpMenu.setText("Help");
        helpMenu.add(howTo);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        //Set mainUI panel value
        textArea.setEditable(false);
        textArea.setFont(sanSerifFontBody);
        optPanel.setBackground(Color.LIGHT_GRAY);
        optPanel.setLayout(new FlowLayout());
        optPanel.add(configButton);
        optPanel.add(newPButton);
        optPanel.add(solveButton);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(BorderLayout.CENTER, scrPanel_mainW);
        mainPanel.add(BorderLayout.SOUTH, optPanel);

        //Set mainUI frame attribute
        mainUI.setTitle("P-Median Solver");
        mainUI.add(mainPanel);
        mainUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainUI.setIconImage(img.getImage());
        mainUI.setJMenuBar(menuBar);
        mainUI.setSize(screenSizeMain);
        mainUI.setLocationRelativeTo(null);
        mainUI.setResizable(true);
        mainUI.setVisible(true);

        //----------------------------------------------------------------------//
        //CONFIG WINDOW
        //Set configUI panel value
        configPanel.setLayout(null);

        //JLabel
        headerLabel_cfg.setFont(sanSerifFontHeader);
        headerLabel_cfg.setBounds(165, 10, 200, 40);
        configPanel.add(headerLabel_cfg);

        popLabel.setFont(sanSerifFontBody);
        popLabel.setBounds(75, 100, 100, 20);
        configPanel.add(popLabel);

        genLabel.setFont(sanSerifFontBody);
        genLabel.setBounds(75, 150, 100, 20);
        configPanel.add(genLabel);

        mutationLabel.setFont(sanSerifFontBody);
        mutationLabel.setBounds(75, 200, 120, 20);
        configPanel.add(mutationLabel);

        crossoverLabel.setFont(sanSerifFontBody);
        crossoverLabel.setBounds(75, 250, 125, 20);
        configPanel.add(crossoverLabel);

        seedLabel.setFont(sanSerifFontBody);
        seedLabel.setBounds(75, 300, 100, 20);
        configPanel.add(seedLabel);

        tournamentLabel.setFont(sanSerifFontBody);
        tournamentLabel.setBounds(75, 350, 150, 20);
        configPanel.add(tournamentLabel);

        //JTextField
        popField.setFont(sanSerifFontBody);
        popField.setMargin(new Insets(0, 5, 0, 0));
        popField.setBounds(255, 97, 150, 25);
        configPanel.add(popField);

        genField.setFont(sanSerifFontBody);
        genField.setMargin(new Insets(0, 5, 0, 0));
        genField.setBounds(255, 147, 150, 25);
        configPanel.add(genField);

        mutationField.setFont(sanSerifFontBody);
        mutationField.setMargin(new Insets(0, 5, 0, 0));
        mutationField.setBounds(255, 197, 150, 25);
        configPanel.add(mutationField);

        crossoverField.setFont(sanSerifFontBody);
        crossoverField.setMargin(new Insets(0, 5, 0, 0));
        crossoverField.setBounds(255, 247, 150, 25);
        configPanel.add(crossoverField);

        seedField.setFont(sanSerifFontBody);
        seedField.setMargin(new Insets(0, 5, 0, 0));
        seedField.setBounds(255, 297, 150, 25);
        configPanel.add(seedField);

        tournamentField.setFont(sanSerifFontBody);
        tournamentField.setMargin(new Insets(0, 5, 0, 0));
        tournamentField.setBounds(255, 347, 150, 25);
        configPanel.add(tournamentField);

        //JButton
        doneButton_cfg.setBounds(190, 410, 100, 30);
        configPanel.add(doneButton_cfg);

        //Set configUI frame attribute
        configUI.setTitle("Configulation");
        configUI.add(configPanel);
        configUI.setSize(screenSizeConfig);
        configUI.setLocationRelativeTo(null);
        configUI.setResizable(false);

        //----------------------------------------------------------------------//
        //NEW PROBLEM WINDOW
        //Set newPPanel panel value
        newPPanel.setLayout(null);

        //JLabel 
        headerLabel_newp.setFont(sanSerifFontHeader);
        headerLabel_newp.setBounds(280, 10, 235, 40);
        newPPanel.add(headerLabel_newp);

        nodeLabel.setFont(sanSerifFontBody);
        nodeLabel.setBounds(260, 70, 200, 20);
        newPPanel.add(nodeLabel);

        edgeLabel.setFont(sanSerifFontBody);
        edgeLabel.setBounds(260, 110, 200, 20);
        newPPanel.add(edgeLabel);

        pLabel.setFont(sanSerifFontBody);
        pLabel.setBounds(260, 150, 200, 20);
        newPPanel.add(pLabel);

        //JTextField
        nodeField.addActionListener((ActionEvent e) -> {
            int rowCol = Integer.parseInt(nodeField.getText());
            tableModel.setColumnCount(rowCol);
            tableModel.setRowCount(rowCol);
        });
        nodeField.setFont(sanSerifFontBody);
        nodeField.setMargin(new Insets(0, 5, 0, 0));
        nodeField.setBounds(380, 67, 150, 25);
        newPPanel.add(nodeField);

        edgeField.setFont(sanSerifFontBody);
        edgeField.setMargin(new Insets(0, 5, 0, 0));
        edgeField.setBounds(380, 107, 150, 25);
        newPPanel.add(edgeField);

        pField.setFont(sanSerifFontBody);
        pField.setMargin(new Insets(0, 5, 0, 0));
        pField.setBounds(380, 147, 150, 25);
        newPPanel.add(pField);

        //JTable
        scrPanel_newpW.setBounds(10, 192, 775, 300);
        weightTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        weightTable.setRowHeight(25);
        newPPanel.add(scrPanel_newpW);

        //JButton
        doneButton_newp.setBounds(345, 520, 100, 30);
        newPPanel.add(doneButton_newp);

        //Set newPUI frame attribute
        newPUI.setTitle("Create New Problem");
        newPUI.add(newPPanel);
        newPUI.setSize(screenSizeNewP);
        newPUI.setLocationRelativeTo(null);
        newPUI.setResizable(false);
    }

    //----------------------------------------------------------------------//
    //Main
    public static void main(String[] args) {
        PmedSolver ps = new PmedSolver();
    }

    //----------------------------------------------------------------------//
    //JButton in main window method
    void configButtAction(ActionEvent e) {
        configUI.setVisible(true);
    }

    void newPButtonAction(ActionEvent e) {
        newPUI.setVisible(true);
    }

    void solveButtAction(ActionEvent e) {
        if (N == 0 || E == 0 || p == 0 || w.length == 0) {
            JFrame alertUI = new JFrame();
            JPanel alertPanel = new JPanel();
            JLabel eMsg = new JLabel("No input.");
            //eMsg config
            eMsg.setFont(sanSerifFontBody);
            eMsg.setBounds(150, 100, 200, 20);
            //AlertPanel
            alertPanel.setLayout(new GridBagLayout());
            alertPanel.add(eMsg);
            //AlertUI
            alertUI.add(alertPanel);
            alertUI.setTitle("Error");
            alertUI.setLocationRelativeTo(null);
            alertUI.setSize(200, 100);
            alertUI.setResizable(false);
            alertUI.setVisible(true);
        } else {
            ga.lchrom = N;
            ga.E = E;
            ga.p = p;
            ga.w = w;
            Individual bestIndi = ga.solve();
            textArea.append("Optimal solution value = " + bestIndi.fitness + " \n");
            textArea.append("Open Location = ");
            for (int i = 0; i < p; i++) {
                textArea.append(String.valueOf(bestIndi.facilityNode[i] + 1) + " ");
            }
            textArea.append("\nAssign Node : \n");
            for (int j = 0; j < p; j++) {
                textArea.append("Location " + (bestIndi.facilityNode[j] + 1) + ": ");
                for (int k = 0; k < N; k++) {
                    textArea.append(String.valueOf(bestIndi.assignNode[bestIndi.facilityNode[j]][k]) + " ");
                }
                textArea.append("\n");
            }
            textArea.append("\n");
        }
    }

    //----------------------------------------------------------------------//
    //Done button method in config window
    void doneButtAction_cfg(ActionEvent e) {
        JFrame alertUI = new JFrame();
        JPanel alertPanel = new JPanel();
        JLabel eMsg = new JLabel();
        //eMsg config
        eMsg.setFont(sanSerifFontBody);
        //AlertPanel
        alertPanel.setLayout(new GridBagLayout());
        alertPanel.add(eMsg);
        //AlertUI
        alertUI.add(alertPanel);
        alertUI.setTitle("Error");
        alertUI.setLocationRelativeTo(null);
        alertUI.setResizable(false);
        //Check JTextField before set value to variable
        if (popField.getText().contains(".") || genField.getText().contains(".") || seedField.getText().contains(".") || tournamentField.getText().contains(".")) {
            eMsg.setText("The value of Population, Generation, Seed, Tournament Size must be an integer.");
            alertUI.setSize(600, 100);
            alertUI.setVisible(true);
        } else if (Integer.parseInt(popField.getText()) <= 0 || Integer.parseInt(genField.getText()) <= 0 || Double.parseDouble(mutationField.getText()) <= 0 || Double.parseDouble(crossoverField.getText()) <= 0 || Integer.parseInt(seedField.getText()) <= 0 || Integer.parseInt(tournamentField.getText()) <= 0) {
            eMsg.setText("The value must be greater than zero.");
            alertUI.setSize(300, 100);
            alertUI.setVisible(true);
        } else {
            //Get value from JTextArea
            int popSize = Integer.parseInt(popField.getText());
            int maxGen = Integer.parseInt(genField.getText());
            double mutationRate = Double.parseDouble(mutationField.getText());
            double crossoverRate = Double.parseDouble(crossoverField.getText());
            int seed = Integer.parseInt(seedField.getText());
            int tournamentSize = Integer.parseInt(tournamentField.getText());
            //Set value
            ga.setPopSize(popSize);
            ga.setGen(maxGen);
            ga.setMRate(mutationRate);
            ga.setCRate(crossoverRate);
            ga.setSeed(seed);
            ga.setTournamentSize(tournamentSize);
            //Close window
            configUI.dispose();
        }
    }

    //----------------------------------------------------------------------//
    //Done button method in new problem window
    void doneButtAction_newp(ActionEvent e) {
        JFrame alertUI = new JFrame();
        JPanel alertPanel = new JPanel();
        JLabel eMsg = new JLabel();
        //eMsg config
        eMsg.setFont(sanSerifFontBody);
        //AlertPanel
        alertPanel.setLayout(new GridBagLayout());
        alertPanel.add(eMsg);
        //AlertUI
        alertUI.add(alertPanel);
        alertUI.setTitle("Error");
        alertUI.setLocationRelativeTo(null);
        alertUI.setResizable(false);
        //Check JTextField before set N,E,p,w
        if (nodeField.getText().equals("") || edgeField.getText().equals("") || pField.getText().equals("")) {
            eMsg.setText("All fields must be filled in.");
            alertUI.setSize(300, 100);
            alertUI.setVisible(true);
        } else if (nodeField.getText().contains(".") || edgeField.getText().contains(".") || pField.getText().contains(".")) {
            eMsg.setText("The value of Node, Edge, p must be an integer.");
            alertUI.setSize(400, 100);
            alertUI.setVisible(true);
        } else if (Integer.parseInt(nodeField.getText()) <= 0 || Integer.parseInt(edgeField.getText()) <= 0 || Integer.parseInt(pField.getText()) <= 0) {
            eMsg.setText("The value must be greater than zero.");
            alertUI.setSize(300, 100);
            alertUI.setVisible(true);
        } else if (Integer.parseInt(pField.getText()) > Integer.parseInt(nodeField.getText())) {
            eMsg.setText("The p value must be less than the node value.");
            alertUI.setSize(400, 100);
            alertUI.setVisible(true);
        } else if (validCheck() == false) {
            eMsg.setText("Must fill in all fields in the table.");
            alertUI.setSize(300, 100);
            alertUI.setVisible(true);
        } else {
            //Get value from JTextArea
            int node = Integer.parseInt(nodeField.getText());
            int edge = Integer.parseInt(edgeField.getText());
            int pnode = Integer.parseInt(pField.getText());
            //Set value
            this.N = node;
            this.E = edge;
            this.p = pnode;
            this.w = new int[N][N];
            System.out.println(weightTable.getRowCount());
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    w[i][j] = Integer.parseInt(weightTable.getModel().getValueAt(i, j).toString());
                    System.out.println(w[i][j]);
                }
            }
            //Close window
            newPUI.dispose();
            drawInstance();
        }
    }

    //Check if weightTable is not filled up
    public boolean validCheck() {
        if (weightTable.getCellEditor() != null) {
            weightTable.getCellEditor().stopCellEditing();
        }
        for (int i = 0; i < weightTable.getRowCount(); i++) {
            for (int j = 0; j < weightTable.getColumnCount(); j++) {
                Object value = weightTable.getValueAt(i, j);
                if (value == null) {
                    return false;
                }
            }
        }
        return true;
    }

    //----------------------------------------------------------------------//
    //JMenuItem method
    void newfileButtAction(ActionEvent e) {
        textArea.setEditable(true);
        N = 0;
        E = 0;
        p = 0;
        wmax = 0;
        w = new int[N][N];
        textArea.selectAll();
        textArea.cut();
        textArea.setEditable(false);
    }

    void howtoButtAction(ActionEvent e) {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("documents/How to use.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    void saveButtAction(ActionEvent e) throws IOException {
        pathSave.setBounds(60, 120, 750, 450);
        boxSave.add(pathSave);

        int ret = pathSave.showDialog(null, "save");
        String path;

        if (ret == JFileChooser.APPROVE_OPTION) {
            File filePath = pathSave.getSelectedFile();
            path = filePath.getPath();
            writeInstance(path);
        }
    }

    void saveResultButtAction(ActionEvent e) throws IOException {
        pathSave.setBounds(60, 120, 750, 450);
        boxSave.add(pathSave);

        int ret = pathSave.showDialog(null, "save");
        String path;

        if (ret == JFileChooser.APPROVE_OPTION) {
            File filePath = pathSave.getSelectedFile();
            path = filePath.getPath();
            writeResult(path);
        }
    }

    void openButtAction(ActionEvent e) throws IOException {
        pathOpen.setBounds(60, 120, 750, 450);
        boxOpen.add(pathOpen);

        int ret = pathOpen.showDialog(null, "open");
        String path;

        if (ret == JFileChooser.APPROVE_OPTION) {
            File filePath = pathOpen.getSelectedFile();
            path = filePath.getPath();
            readInstance(path);
        }
    }

    void writeInstance(String path) throws IOException {
        try (PrintWriter output = new PrintWriter(path + ".in")) {
            output.println(N + " " + E + " " + p);
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    output.print(w[i][j] + " ");
                }
                output.println();
            }
            output.println();
        }
    }

    void writeResult(String path) throws IOException {
        if (textArea.getText().equals("")) {
            JFrame alertUI = new JFrame();
            JPanel alertPanel = new JPanel();
            JLabel eMsg = new JLabel();
            //eMsg config
            eMsg.setFont(sanSerifFontBody);
            eMsg.setText("No result to save.");
            //AlertPanel
            alertPanel.setLayout(new GridBagLayout());
            alertPanel.add(eMsg);
            //AlertUI
            alertUI.add(alertPanel);
            alertUI.setSize(300, 100);
            alertUI.setTitle("Error");
            alertUI.setLocationRelativeTo(null);
            alertUI.setResizable(false);
            alertUI.setVisible(true);
        } else {
            try (PrintWriter output = new PrintWriter(path + ".txt")) {
                output.println(textArea.getText());
            }
        }
    }

    void readInstance(String path) throws IOException {
        try (Scanner input = new Scanner(new File(path))) {
            N = input.nextInt();
            E = input.nextInt();
            p = input.nextInt();

            w = new int[N][N];
            wmax = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    w[i][j] = input.nextInt();
                    if (w[i][j] > wmax) {
                        wmax = w[i][j];
                    }
                }
            }
        }
        drawInstance();
    }

    void drawInstance() {
        String weightM = "Node = " + N + ", Edge = " + E + ", P = " + p + "\n";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                //weightM += w[i][j] + " ";
            }
            //weightM += "\n";
        }
        textArea.setText(weightM);
    }
}
