import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static HashMap<String, Integer> determinationValues = new HashMap<>();

    private static List<DataWrapper> loadDataFromFile(String path, String predict) throws IOException {
        List<DataWrapper> trainData = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get(path));

        DataWrapper data = new DataWrapper(lines.get(0), predict);
        trainData.add(data);
        int valuesSize = data.getValues().length;
        for (int i = 1; i < lines.size(); i++) {
            data = new DataWrapper(lines.get(i), predict);
            if (data.getValues().length != valuesSize) {
                System.out.println(lines.get(i)
                        + " - The number of attributes does not match the trainSet number of attributes!");
            } else {
                trainData.add(data);
            }
        }

        return trainData;
    }

    private static List<DataWrapper> loadDataFromFile(String path) throws IOException {
        List<DataWrapper> trainData = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get(path));

        DataWrapper data = new DataWrapper(lines.get(0));
        trainData.add(data);
        int valuesSize = data.getValues().length;
        for (int i = 1; i < lines.size(); i++) {
            data = new DataWrapper(lines.get(i));
            if (data.getValues().length != valuesSize) {
                System.out.println(lines.get(i)
                        + " - The number of attributes does not match the trainSet number of attributes!");
            } else {
                trainData.add(data);
            }
        }

        return trainData;
    }

    public static void main(String[] args) {
        List<DataWrapper> train = null;
        List<DataWrapper> test = null;
        List<DataWrapper> train2 = null;
        List<DataWrapper> test2 = null;
        List<DataWrapper> train3 = null;
        List<DataWrapper> test3 = null;
        List<DataWrapper> trainMain = null;
        try {
            trainMain = loadDataFromFile("iris_test.txt");
            train = loadDataFromFile("iris_training.txt", "Iris-setosa");
            test = loadDataFromFile("iris_test.txt", "Iris-setosa");
            train2 = loadDataFromFile("iris_training.txt", "Iris-versicolor");
            test2 = loadDataFromFile("iris_test.txt", "Iris-versicolor");
            train3 = loadDataFromFile("iris_training.txt", "Iris-virginica");
            test3 = loadDataFromFile("iris_test.txt", "Iris-virginica");

        } catch (IOException e) {
            e.printStackTrace();
        }
        Perc2 p2 = new Perc2(train, test, 0.1 , 100, 0.1);
        p2.learn();
        Perc2 p3 = new Perc2(train2, test2, 0.1 , 100, 0.1);
        p3.learn();
        Perc2 p4 = new Perc2(train3, test3, 0.1 , 100, 0.1);
        p4.learn();
        double totCor = 0;
        for (int i = 0; i < test.size(); i++) {
            double a1=p2.predict(test.get(i).getValues());
            double a2=p3.predict(test.get(i).getValues());
            double a3=p4.predict(test.get(i).getValues());
            if(a1 == 1 && test.get(i).getSpecies().equals("Iris-setosa")){
                totCor++;
            }
            else if(a2 == 1 && test.get(i).getSpecies().equals("Iris-versicolor")){
                totCor++;
            }else if(a3 == 1 && test.get(i).getSpecies().equals("Iris-virginica")){
                totCor++;
            }
            System.out.println("Setosa " + a1 + " // " + "Versi " + a2 + " // " + "Virgin" + a3 + " //actual" + test.get(i).getSpecies());
        }
        System.out.println("% correct " + (totCor/test.size())*100);
        double[] w = p2.getWeights();

        while(true){
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    System.out.println("Provide a date for the test: ");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("stop"))
                        break;
                    String[] temp = input.split(" ");
                    double[] values = new double[4];
                    for (int i = 0; i < temp.length; i++) {
                        values[i] = Double.parseDouble(temp[i]);
                    }
                    double res2 = p2.predict(values);
                    double res3 = p3.predict(values);
                    double res4 = p4.predict(values);
                    if(res2 == 1.0){
                        System.out.println("Can be Iris-setosa");
                    }else {
                        System.out.println("Can NOT be Iris-setosa");
                    }if(res3 == 1){
                        System.out.println("Can be Iris-versicolor");
                    }else {
                        System.out.println("Can NOT be Iris-versicolor");
                    }
                    if(res4 == 1){
                        System.out.println("Can be Iris-virginica");
                    }else {
                        System.out.println("Can NOT be Iris-virginica");
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            }
        }
    }
}
