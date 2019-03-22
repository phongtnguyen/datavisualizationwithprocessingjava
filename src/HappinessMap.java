/***************************************************************************************
 * 
 *	Data Mining Lab 4: World Happiness Map
 *	Phong Nguyen, Muoi Pham
 * 
 ***************************************************************************************/

import processing.core.PApplet;
import processing.core.PFont;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.providers.Google.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HappinessMap extends PApplet {
	
	UnfoldingMap map;
	List<Feature> country;
	List<Marker> marker;
	Map<String, String> countryId;
	PFont nameFont;
	String metrics = "happiness";
	String currentYear = "2015";
	
	Map<String, Float> happiness, gdp, health, freedom, corruption, generosity, currentMap;
	
	int xbase = 200;
	int ybase = 620;
	int colorMin = 0;
	int colorMax = 255;
	/*Main m;
	public HappinessMap(Main m) {
		this.m = m;
		setup();
	}*/

	/****************************************************************************
	 * 
	 * setup the world map and marker for each country
	 * 
	 ****************************************************************************/
	public void setup() {
		size(1000, 710, OPENGL);
		map = new UnfoldingMap(this, 0, 0, 1000f, 600f, new Microsoft.AerialProvider());
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		country = GeoJSONReader.loadData(this, "countries.geo.json");
		marker = MapUtils.createSimpleMarkers(country);
		map.addMarkers(marker);
		map.setTweening(true);
		
		nameFont = createFont("Arial Bold", 12);
		
		//map.setPanningRestriction(new Location(0, 0), 1);
		loadData("2015.csv");
		shade(happiness, 2.84f, 7.59f);
		currentMap = happiness;
	}
	
	/****************************************************************************
	 * 
	 * draw the map
	 * 
	 ****************************************************************************/
	public void draw() {
		background(10);
		map.draw();
		displayCountryName();
		addButton();
	}
	
	/****************************************************************************
	 * 
	 * mouseReleased() function
	 * 	detect if button for each metric is clicked on
	 * 	and change the shade accordingly
	 * 
	 ****************************************************************************/
	public void mouseReleased() {
		int x = xbase;
		int y = ybase;
		Float min, max;
		if (mouseX > x && mouseX < x+25 && mouseY > y && mouseY < y+25) {
			//System.out.println("Happiness");
			min = Collections.min(happiness.values());
			max = Collections.max(happiness.values());
			//System.out.println(min+" "+max);
			shade(happiness, min, max); //2.84f, 7.59f
			metrics = "happiness";
			currentMap = happiness;
		}
		
		x = x + 100;
		if (mouseX > x && mouseX < x+25 && mouseY > y && mouseY < y+25) {
			//System.out.println("GDP");
			min = Collections.min(gdp.values());
			max = Collections.max(gdp.values());
			//System.out.println(min+" "+max);
			shade(gdp, min, max);
			metrics = "gdp";
			currentMap = gdp;
		}
		
		x = x + 100;
		if (mouseX > x && mouseX < x+25 && mouseY > y && mouseY < y+25) {
			//System.out.println("Health");
			min = Collections.min(health.values());
			max = Collections.max(health.values());
			//System.out.println(min+" "+max);
			shade(health, min, max);
			metrics = "health";
			currentMap = health;
		}
		
		x = x + 100;
		if (mouseX > x && mouseX < x+25 && mouseY > y && mouseY < y+25) {
			//System.out.println("Freedom");
			min = Collections.min(freedom.values());
			max = Collections.max(freedom.values());
			//System.out.println(min+" "+max);
			shade(freedom, min, max);
			metrics = "freedom";
			currentMap = freedom;
		}
		
		x = x + 100;
		if (mouseX > x && mouseX < x+25 && mouseY > y && mouseY < y+25) {
			//System.out.println("Corruption");
			min = Collections.min(corruption.values());
			max = Collections.max(corruption.values());
			//System.out.println(min+" "+max);
			shade(corruption, min, max);
			metrics = "corruption";
			currentMap = corruption;
		}
		
		x = x + 100;
		if (mouseX > x && mouseX < x+25 && mouseY > y && mouseY < y+25) {
			//System.out.println("Generosity");
			min = Collections.min(generosity.values());
			max = Collections.max(generosity.values());
			//System.out.println(min+" "+max);
			shade(generosity, min, max);
			metrics = "generosity";
			currentMap = generosity;
		}
		
		x = xbase + 150;
		if (mouseX > x && mouseX < x+25 && mouseY > y+40 && mouseY < y+65) {
			//System.out.println("2015");]
			loadData("2015.csv");
			currentYear = "2015";
			min = Collections.min(currentMap.values());
			max = Collections.max(currentMap.values());
			shade(currentMap, min, max); //doesnt work
		}
		
		x = x + 100;
		if (mouseX > x && mouseX < x+25 && mouseY > y+40 && mouseY < y+65) {
			//System.out.println("2016");
			loadData("2016.csv");
			currentYear = "2016";
			min = Collections.min(currentMap.values());
			max = Collections.max(currentMap.values());
			shade(currentMap, min, max);
		}
		
		x = x + 100;
		if (mouseX > x && mouseX < x+25 && mouseY > y+40 && mouseY < y+65) {
			//System.out.println("2017");
			loadData("2017.csv");
			currentYear = "2017";
			min = Collections.min(currentMap.values());
			max = Collections.max(currentMap.values());
			shade(currentMap, min, max);
		}
	}
	
	/****************************************************************************
	 * 
	 * displayCountryName() function
	 * 	display the country name and score that the cursor is pointing to
	 * 	score is normalized on scale of 0 to 10
	 * 
	 ****************************************************************************/
	public void displayCountryName() { //change to mouseHover
		Marker hit = map.getFirstHitMarker(mouseX, mouseY);
		if (hit == null)
			return;
		String[] countryInfo = getCountryName(hit.getId());
		if (countryInfo[0] != null && !countryInfo[0].isEmpty() && !countryInfo[0].equals("ANTARCTICA")) { // && !countryInfo[1].equals("-1.0")
			fill(255);
			textAlign(CENTER);
			textFont(nameFont);
			float score = map(Float.parseFloat(countryInfo[1]), Float.parseFloat(countryInfo[2]), Float.parseFloat(countryInfo[3]), 0, 10);
			text(countryInfo[0]+": "+String.format("%.4f", score), mouseX, mouseY);
		}
	}
	
	/****************************************************************************
	 * 
	 * getCountryName() function
	 * 	get country name from a marker
	 * 
	 ****************************************************************************/
	private String[] getCountryName(String id) {
		String[] cInfo = new String[4];
		for (String cName : countryId.keySet()) {
			if (countryId.get(cName).equals(id)) {
				cInfo[0] = cName;
				getMetric(id, cInfo);
				break;
			}
		}
		return cInfo;
	}
	
	/****************************************************************************
	 * 
	 * getMetric() function
	 * 	get a country's score according to a selected metrics
	 * 
	 ****************************************************************************/
	private void getMetric(String id, String[] cInfo) {
		float val = -1;
		float min = 0;
		float max = 0;
		//System.out.println("getMetric: "+id);
		if (metrics.equals("happiness")) {
			if (happiness.containsKey(id)) {
				val = happiness.get(id);
				min = Collections.min(happiness.values());
				max = Collections.max(happiness.values());
			}
		}
		
		if (metrics.equals("gdp")) {
			if (gdp.containsKey(id)) {
				val = gdp.get(id);
				min = Collections.min(gdp.values());
				max = Collections.max(gdp.values());
			}
		}
		
		if (metrics.equals("health")) {
			if (health.containsKey(id)) {
				val = health.get(id);
				min = Collections.min(health.values());
				max = Collections.max(health.values());
			}
		}
		
		if (metrics.equals("freedom")) {
			if (freedom.containsKey(id)) {
				val = freedom.get(id);
				min = Collections.min(freedom.values());
				max = Collections.max(freedom.values());
			}
		}
		
		if (metrics.equals("corruption")) {
			if (corruption.containsKey(id)) {
				val = corruption.get(id);
				min = Collections.min(corruption.values());
				max = Collections.max(corruption.values());
			}
		}
		
		if (metrics.equals("generosity")) {
			if (generosity.containsKey(id)) {
				val = generosity.get(id);
				min = Collections.min(generosity.values());
				max = Collections.max(generosity.values());
			}
		}
		cInfo[1] = Float.toString(val);
		cInfo[2] = Float.toString(min);
		cInfo[3] = Float.toString(max);
	}
	
	/****************************************************************************
	 * 
	 * shade() function
	 * 	color countries based on their score 
	 * 
	 ****************************************************************************/
	public void shade(Map<String, Float> attr, float min, float max) {
		//System.out.println("Shade countries");
		for (Marker marker : marker) {
			String id = marker.getId();
			//System.out.println(id);
			
			if (attr.containsKey(id)) {
				float value = attr.get(id);
				//int colorLevel = (int) (value*value*2);
				int colorLevel = (int) map(value, min, max, colorMin, colorMax);
				//System.out.println(value+" "+colorLevel);
				marker.setColor(color(colorLevel, 50, 150)); //x 50 150
				
			}
			else {
				//System.out.println("Not found "+id);
				marker.setColor(color(150, 150, 150));
			}
		}
	}
	
	/****************************************************************************
	 * 
	 * loadData() function
	 * 	load scores of countries from a csv file
	 * 
	 ****************************************************************************/
	public void loadData(String file) {
		String[] entry = loadStrings(file); //in data folder
		happiness = new HashMap<String, Float>();
		gdp = new HashMap<String, Float>();
		health = new HashMap<String, Float>();
		freedom = new HashMap<String, Float>();
		corruption = new HashMap<String, Float>();
		generosity = new HashMap<String, Float>();
		countryId = getIdTable();
		//System.out.println("Entry: "+entry.length);
		
		for (String line : entry) {
			String[] attr = line.split(",");
			String country = countryId.get(attr[0].toUpperCase());
			if (country==null)
				System.out.println("no code: "+attr[0]);
			happiness.put(country, Float.parseFloat(attr[3]));
			gdp.put(country, Float.parseFloat(attr[5]));
			health.put(country, Float.parseFloat(attr[7]));
			freedom.put(country, Float.parseFloat(attr[8]));
			corruption.put(country, Float.parseFloat(attr[9]));
			generosity.put(country, Float.parseFloat(attr[10]));
		}
		
		//System.out.println(Arrays.asList(happiness));
	}
	
	/****************************************************************************
	 * 
	 * getIdTable() function
	 *  get the countries name and their corresponding trigram code
	 * 
	 ****************************************************************************/
	private Map<String, String> getIdTable() {
		Map<String, String> abbr = new HashMap<String, String>();

		try {
			BufferedReader br = new BufferedReader(new FileReader("abbr.txt")); //in data folder
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
	
	/****************************************************************************
	 * 
	 * addButton() function
	 * 
	 ****************************************************************************/
	private void addButton() {
		int x = xbase;
		int y = ybase;
		fill(240,20,70);
		rect(x, y, 25, 25);
		fill(255);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Happiness", x+27, y+12);
		
		x = x + 100;
		fill(50,20,240);
		rect(x, y, 25, 25);
		fill(255);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Economy", x+27, y+12);
		
		x = x + 100;
		fill(80,240,20);
		rect(x, y, 25, 25);
		fill(255);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Health", x+27, y+12);
		
		x = x + 100;
		fill(20,240,220);
		rect(x, y, 25, 25);
		fill(255);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Freedom", x+27, y+12);
		
		x = x + 100;
		fill(70);
		rect(x, y, 25, 25);
		fill(255);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Corruption", x+27, y+12);
		
		x = x + 100;
		fill(230,240,100);
		rect(x, y, 25, 25);
		fill(255);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Generosity", x+27, y+12);
		
		x = xbase + 150;
		fill(255);
		rect(x, y+40, 25, 25);
		fill(255);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("2015", x+27, y+52);
		
		x = x + 100;
		fill(255);
		rect(x, y+40, 25, 25);
		fill(255);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("2016", x+27, y+52);
		
		x = x + 100;
		fill(255);
		rect(x, y+40, 25, 25);
		fill(255);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("2017", x+27, y+52);
		
		textAlign(CENTER);
		fill(120,50,255);
		textFont(nameFont);
		text("Year "+currentYear, x+100, y+58);
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

0 Country,
1 Region,
2 Happiness Rank,
3 Happiness Score,
4 Lower Confidence Interval,
5 Upper Confidence Interval,
6 Economy (GDP per Capita),
7 Family,
8 Health (Life Expectancy),
9 Freedom,
10 Trust (Government Corruption),
11 Generosity,
12 Dystopia Residual

0 Country,
1 Happiness.Rank,
2 Happiness.Score,
3 Whisker.high,
4 Whisker.low,
5 Economy..GDP.per.Capita.,
6 Family,
7 Health..Life.Expectancy.,
8 Freedom,
9 Generosity,
10 Trust..Government.Corruption.,
11 Dystopia.Residual
*/