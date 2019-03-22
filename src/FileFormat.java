import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/***************************************************************************************************
 * 
 * Format 2016 and 2017 data according to 2015 data
 *
 ***************************************************************************************************/
public class FileFormat {
	public static void main(String args[]) {
		try {
			BufferedReader br2016 = new BufferedReader(new FileReader("data/2016o.csv"));
			BufferedReader br2017 = new BufferedReader(new FileReader("data/2017o.csv"));
			BufferedWriter bw2016 = new BufferedWriter(new FileWriter("data/2016.csv"));
			BufferedWriter bw2017 = new BufferedWriter(new FileWriter("data/2017.csv"));
			
			String s;
			
			while ((s=br2016.readLine()) != null) {
				String[] line = s.split(",");
				bw2016.write(line[0]+","+line[1]+","+line[2]+","+line[3]+","+line[4]+","+line[6]+","+line[7]+","+line[8]+","+line[9]+","+line[10]+","+line[11]+","+line[12]+"\n");
			}
			bw2016.flush();
			
			while ((s=br2017.readLine()) != null) {
				String[] line = s.split(",");
				bw2017.write(line[0]+",Earth,"+line[1]+","+line[2]+","+line[3]+","+line[5]+","+line[6]+","+line[7]+","+line[8]+","+line[10]+","+line[9]+","+line[11]+"\n");
			}
			bw2017.flush();
			
			br2016.close();
			br2017.close();
			bw2016.close();
			bw2017.close();
		} catch (IOException io) {
			System.out.println("IO Exception");
		}
	}
}