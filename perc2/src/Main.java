import javax.sound.midi.Soundbank;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.*;

public class Main {
    public static String NORWEGIAN = "Norwegian";
    public static String SWEDISH = "Swedish";
    public static String FINNISH = "Finnish";

    public static HashMap<String, Integer> determinationValues = new HashMap<>();

    private static DataWrapper readData(String line2) {
        int total = 0;
        HashMap<String, Integer> hs = new HashMap<>();
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("input.txt"), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int j = 0; j < lines.size(); j++) {
            String line = lines.get(j).toUpperCase();
            for (int k = 0; k < line.length(); k++) {
                char res = line.charAt(k);
                if (res >= 'A' && res <= 'Z') {
                    total++;
                    String letter = String.valueOf(line.charAt(k));
                    if (hs.containsKey(letter)) {
                        int res2 = hs.get(letter);
                        hs.put(letter, res2 + 1);
                    } else {
                        hs.put(letter, 1);
                    }
                }
            }
        }
        return new DataWrapper(hs, "", "", total);
    }

    private static List<DataWrapper> loadDataFromFiles(List<String> path, String predict) throws IOException {
        List<DataWrapper> trainData = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            String[] parDir = path.get(i).split("\\\\");
            String ParDir = parDir[parDir.length-1];
            File dir = new File(path.get(i));
            File[] directoryListing = dir.listFiles();
            for (File child : directoryListing){
                int total = 0;
                HashMap<String, Integer> hs = new HashMap<>();
                List<String> lines = Files.readAllLines(child.toPath(), Charset.defaultCharset());
                for (int j = 0; j < lines.size() ; j++) {
                    String line = lines.get(j).toUpperCase();
                    for (int k = 0; k < line.length(); k++) {
                        char res = line.charAt(k);
                        if(res>='A' && res <= 'Z'){
                            total++;
                            String letter = String.valueOf(line.charAt(k));
                            if(hs.containsKey(letter)){
                                int res2  =hs.get(letter);
                                hs.put(letter, res2+1);
                            }else{
                                hs.put(letter, 1);
                            }
                        }
                    }
                }
                DataWrapper data = new DataWrapper(hs, predict, ParDir, total);
                trainData.add(data);
            }
        }
        return trainData;
    }

    public static void showCharacteristics(List<Perc2> perceptrons, int size, List<DataWrapper> dataWrapperList){
        double totalcor =0;
        HashMap<String, Integer> values = new HashMap<>();
        HashMap<Perc2, Integer> val2 = new HashMap<>();
        int count = 0;
        for (int i = 0; i < perceptrons.size(); i++) {
            values.put(perceptrons.get(i).getSpecies(), count);
            val2.put(perceptrons.get(i), 0);
            count++;
        }
        int[][] cMatrix = new int[perceptrons.size()][perceptrons.size()];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < perceptrons.size(); j++) {
                double a2 = perceptrons.get(j).predict(dataWrapperList.get(i).getValues());
                if(a2 == 1 && dataWrapperList.get(i).getSpecies().equals(perceptrons.get(j).getSpecies())){
                    totalcor++;
                    cMatrix[j][values.get(dataWrapperList.get(i).getSpecies())] = cMatrix[j][values.get(dataWrapperList.get(i).getSpecies())]+1;
                    val2.put(perceptrons.get(j), val2.get(perceptrons.get(j))+1);
                }
                if(a2 == 1 &&!( dataWrapperList.get(i).getSpecies().equals(perceptrons.get(j).getSpecies()))){
                    cMatrix[j][values.get(dataWrapperList.get(i).getSpecies())] = cMatrix[j][values.get(dataWrapperList.get(i).getSpecies())]+1;
                    val2.put(perceptrons.get(j), val2.get(perceptrons.get(j))+1);
                }
            }
        }
        //confusion matrix
        for (int i = 0; i < cMatrix.length; i++) {
            System.out.print(perceptrons.get(i).getSpecies().substring(0, 3) + " ");
            for (int j = 0; j < cMatrix[i].length; j++) {
                System.out.print(String.format("%.2f", (double)cMatrix[i][j]/val2.get(perceptrons.get(i)))+" ");
            }
            System.out.println();
        }
        for (int i = 0; i < perceptrons.size(); i++) {
            System.out.print("  ");
            System.out.print(" " + perceptrons.get(i).getSpecies().substring(0, 3));
        }
        System.out.println();
        //Recall precision f measure
        double f = 0;
        double truepos=0;
        double falsepos=0;
        double falseneg = 0;
        for (int i = 0; i < perceptrons.size(); i++) {
            for (int j = 0; j < cMatrix[i].length; j++) {
                if(i == j){
                    truepos = cMatrix[i][j];
                }else{
                    falseneg = falseneg+cMatrix[i][j];
                }
            }
            for (int j = 0; j < cMatrix.length; j++) {
                if(i == j){
                    truepos = cMatrix[j][i];
                }else{
                    falsepos = falsepos+cMatrix[j][i];
                }
            }
            double P = truepos/(truepos+falsepos);
            double R = truepos/(truepos+falseneg);
            double F = 2*P*R/(P+R);
            System.out.println(perceptrons.get(i).getSpecies() + " -> " + "RECALL : " + R +" PRECISION : " + P + " F MEASURE : " + F);
        }
        //accuracy
        System.out.println((totalcor/dataWrapperList.size())*100 +"%");
    }



    public static void main(String[] args) {
        HashMap<String, Integer> hs = new HashMap<>();
        hs.put("A", 1);
        System.out.println(hs.get("B"));
        List<String> paths = new ArrayList<>();
        List<String> pathsT = new ArrayList<>();
        String path1 = "C:\\Users\\Dima\\IdeaProjects\\PerceptronLanguage\\perc2\\Data\\Finnish";
        String path2 = "C:\\Users\\Dima\\IdeaProjects\\PerceptronLanguage\\perc2\\Data\\Norwegian";
        String path3 = "C:\\Users\\Dima\\IdeaProjects\\PerceptronLanguage\\perc2\\Data\\Swedish";
        String pathT = "C:\\Users\\Dima\\IdeaProjects\\PerceptronLanguage\\perc2\\Data_test\\Finnish";
        String pathT1 = "C:\\Users\\Dima\\IdeaProjects\\PerceptronLanguage\\perc2\\Data_test\\Norwegian";
        String pathT2 = "C:\\Users\\Dima\\IdeaProjects\\PerceptronLanguage\\perc2\\Data_test\\Swedish";
        paths.add(path1);
        paths.add(path2);
        paths.add(path3);
        pathsT.add(pathT);
        pathsT.add(pathT1);
        pathsT.add(pathT2);
        List<DataWrapper> data = null;
        List<DataWrapper> datatest = null;
        List<DataWrapper> data2 = null;
        List<DataWrapper> datatest2 = null;
        List<DataWrapper> data3 = null;
        List<DataWrapper> datatest3 = null;
        try {
            data = loadDataFromFiles(paths, FINNISH);
            datatest = loadDataFromFiles(pathsT, FINNISH);
            data2 = loadDataFromFiles(paths, NORWEGIAN);
            datatest2 = loadDataFromFiles(pathsT, NORWEGIAN);
            data3 = loadDataFromFiles(paths, SWEDISH);
            datatest3= loadDataFromFiles(pathsT, SWEDISH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Perc2> perc2s = new ArrayList<>();
        Perc2 p2 = new Perc2(data, datatest, 0.1 , 100, 0.1, FINNISH);
        p2.learn();
        perc2s.add(p2);
        Perc2 p3 = new Perc2(data2, datatest2, 0.1 , 100, 0.1, NORWEGIAN);
        p3.learn();
        perc2s.add(p3);
        Perc2 p4 = new Perc2(data3, datatest3, 0.1 , 100, 0.1, SWEDISH);
        p4.learn();
        perc2s.add(p4);
        showCharacteristics(perc2s, datatest.size(),datatest);
        Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    System.out.println("Provide a date for the test: ");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("stop"))
                        break;
                    DataWrapper dt = null;
                    if (input.equals("start"))
                        dt = readData(input);
                    //make file for tests
                    double res1 = p2.predict(dt.getValues());
                    double res2 = p3.predict(dt.getValues());
                    double res3 = p4.predict(dt.getValues());
                    if(res1 == 1.0){
                        System.out.println("Can be Finnish");
                    }else {
                        System.out.println("Can NOT be Finnish");
                    }if(res2 == 1){
                        System.out.println("Can be Norwegian");
                    }else {
                        System.out.println("Can NOT be Norwegian");
                    }
                    if(res3 == 1){
                        System.out.println("Can be Swedish");
                    }else {
                        System.out.println("Can NOT be Swedish");
                    }
                }catch (Exception e ){
                    System.out.println("ERR");
                }
            }
    }
}
