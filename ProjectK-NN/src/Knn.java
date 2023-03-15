import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Knn {

    private int k;
    private BufferedReader trainBuffer;
    private BufferedReader testBuffer;
    private List<Data> trainData;
    private List<Data> testData;

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
        //debug();
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
    }

    private void doKnn() {
        int countTrue = 0;
        for (Data test : testData) {
            for (Data train : trainData) {
                double eq = test.calcEuq(train.getVector());
                String label = train.getLabel();
                test.addResult(label,eq);
            }
            test.kNNResult(k);
            if (test.kNNResult(k)) {
                countTrue++;
            }
        }

        System.out.println((double) (countTrue)/(double)(testData.size())*100+"%");
    }

    private void debug() {
        for (Data data : trainData) {
            System.out.println(data.getLabel());
            for (int i = 0; i < data.getVector().length; i++) {
                System.out.print(data.getVector()[i]+ " ");
            }
            System.out.println();
        }
    }

}
