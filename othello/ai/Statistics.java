package othello.ai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Statistics {
    private static Statistics instance = new Statistics();
    private BufferedWriter bfr;
    private static final File file = new File("statistics.txt");
    private Statistics() {
        try {
            bfr= new BufferedWriter(new FileWriter(file,true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Statistics getInstance() {
        return instance;
    }
    public boolean addDataPoint(String player, String opp, int nodes, int time, int score, String heuristicData) {
        try {
            bfr.append("\n");
            bfr.append(player+" "+opp+" "+nodes+" "+time+" "+score+" "+heuristicData);
            bfr.close();
            bfr.flush();
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}
