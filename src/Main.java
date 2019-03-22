/*import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import processing.core.PApplet;
import javax.swing.*;

public class Main extends JFrame implements ActionListener{
	HappinessMap map;
	JButton happinessB, gdpB, healthB, freedomB, corruptionB, generosityB, y2015B, y2016B, y2017B;
	Map<String, Float> happiness, gdp, health, freedom, corruption, generosity;
	Map<String, Float> currentMap;
	Map<String, String> countryId;
 	public Main() {
		super("World Happiness Map");
        map = new HappinessMap(this);
        map.init();
		makeGUI();
		addListener();
		countryId = getIdTable();
		map.loadData("2015.csv");
		float min = Collections.min(happiness.values());
		float max = Collections.max(happiness.values());
		map.shade(happiness, min, max);
	}
	
	public static void main(String args[]) {
		Main m = new Main();
		m.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource()==happinessB) {
			System.out.println("button");
		}
		else if (ae.getSource()==gdpB) {
			System.out.println("button");
		}
		else if (ae.getSource()==healthB) {
			System.out.println("button");
		}
		else if (ae.getSource()==freedomB) {
			System.out.println("button");
		}
		else if (ae.getSource()==corruptionB) {
			System.out.println("button");
		}
		else if (ae.getSource()==generosityB) {
			System.out.println("button");
		}
		else if (ae.getSource()==y2015B) {
			System.out.println("button");
		}
		else if (ae.getSource()==y2016B) {
			System.out.println("button");
		}
		else if (ae.getSource()==y2017B) {
			System.out.println("button");
		}
	}
	
	private Map<String, String> getIdTable() {
		Map<String, String> abbr = new HashMap<String, String>();

		try {
			BufferedReader br = new BufferedReader(new FileReader("data/abbr.txt")); //in data folder
			String line;
			while ( (line = br.readLine()) != null ) {
				//System.out.println(line);
				String[] tok = line.split("\\t"); //regex symbol
				abbr.put(tok[0].toUpperCase(), tok[2]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(abbr.size()+": "+Arrays.asList(abbr));
		return abbr;
	}
	
	private void makeGUI() {
		setSize(1000, 720);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(map, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel bP1 = new JPanel(new FlowLayout());
        happinessB = new JButton("Happiness");      
        bP1.add(happinessB);
        gdpB = new JButton("Economy");
        bP1.add(gdpB);
        healthB = new JButton("Health");
        bP1.add(healthB);
        freedomB = new JButton("Freedom");
        bP1.add(freedomB);
        corruptionB = new JButton("Corruption");
        bP1.add(corruptionB);
        generosityB = new JButton("Generosity");
        bP1.add(generosityB);
        buttonPanel.add(bP1, BorderLayout.NORTH);
        JPanel bP2 = new JPanel(new FlowLayout());
        y2015B = new JButton("2015");
        bP2.add(y2015B);
        y2016B = new JButton("2016");
        bP2.add(y2016B);
        y2017B = new JButton("2017");
        bP2.add(y2017B);
        buttonPanel.add(bP2, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void addListener() {
		happinessB.addActionListener(this);
        gdpB.addActionListener(this);
        healthB.addActionListener(this);
        freedomB.addActionListener(this);
        corruptionB.addActionListener(this);
        generosityB.addActionListener(this);
        y2015B.addActionListener(this);
        y2016B.addActionListener(this);
        y2017B.addActionListener(this);
	}
}

/*
0 Country
1 Region
2 Happiness Rank
3 Happiness Score
4 Standard Error
5 Economy (GDP per Capita)
6 Family
7 Health (Life Expectancy)
8 Freedom
9 Trust (Government Corruption)
10 Generosity
11 Dystopia Residual
*/