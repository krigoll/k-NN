import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Knn {

    private int k;
    private BufferedReader trainBuffer;
    private BufferedReader testBuffer;
    private List<Data> trainData;
    private List<Data> testData;
    private int vectorSize;

    public Knn (int k) {
        this.k = k;
        this.trainData = new ArrayList<>();
        this.testData = new ArrayList<>();
        try {
            input();
            readData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        doKnn();
    }

    private void input() throws FileNotFoundException {
        FileReader fr1 = new FileReader("C:\\Users\\ASUS\\Desktop\\NAI\\k-NN\\ProjectK-NN\\src\\data\\iris.data");
        trainBuffer = new BufferedReader(fr1);
        FileReader fr2 = new FileReader("C:\\Users\\ASUS\\Desktop\\NAI\\k-NN\\ProjectK-NN\\src\\data\\iris.test.data");
        testBuffer = new BufferedReader(fr2);
    }

    private void readData() throws IOException {

        while (trainBuffer.ready()) {
            String [] args = trainBuffer.readLine().split(",");
            double [] vector = new double[args.length-1];
            String label = args[args.length-1];

            for (int i = 0; i < args.length-1; i++) {
                vector[i] = Double.parseDouble(args[i]);
            }

            trainData.add(new Data(label,vector));
        }

        while (testBuffer.ready()) {
            String [] args = testBuffer.readLine().split(",");
            double [] vector = new double[args.length-1];
            String label = args[args.length-1];

            for (int i = 0; i < args.length-1; i++) {
                vector[i] = Double.parseDouble(args[i]);
            }

            testData.add(new Data(label,vector));
        }

        if (trainData.size() != 0) {
            vectorSize = trainData.get(0).getVector().length;
        }
    }

    private void doKnn() {
        int countTrue = 0;
        for (Data test : testData) {
            for (Data train : trainData) {
                double eq = test.calcEuq(train.getVector());
                String label = train.getLabel();
                test.addResult(label,eq);
            }
            if (test.kNNResult(k,true)) {
                countTrue++;
            }
        }

        System.out.println((double) (countTrue)/(double)(testData.size())*100+"%");
    }

    public void enterVector() {
        boolean isActive = true;
        Scanner scanner = new Scanner(System.in);
        Scanner scannerVector = new Scanner(System.in);
        int option = 0;
        testData.clear();
        while (isActive) {
            System.out.println("1. Wprowadz wektor");
            System.out.println("2. Sprawdz");
            option = scanner.nextInt();
            if (option == 2) {
                if (testData.size() == 0) {
                    System.out.println("Brak wektorow!");
                } else {
                    getResults();
                }
                isActive = false;
            } else if (option == 1) {
                System.out.println("Podaj wektor o wielkosci " + vectorSize + ". Oddziel pojednycze liczby spacja.");
                String [] vectorString = scannerVector.nextLine().split(" ");
                double [] vector = new double[vectorString.length];
                for (int i = 0; i < vector.length; i++) {
                    vector[i] = Double.parseDouble(vectorString[i]);
                }
                testData.add(new Data(null, vector));
                System.out.println("Wektor dodany!");
            } else {
                System.out.println("Zla opcja!");
            }
        }
    }

    private void getResults() {
        for (Data test : testData) {
            for (Data train : trainData) {
                double eq = test.calcEuq(train.getVector());
                String label = train.getLabel();
                test.addResult(label, eq);
            }
            test.kNNResult(k,false);
        }
    }

}
