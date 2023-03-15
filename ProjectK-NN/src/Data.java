import java.util.*;

public class Data {

    private String label;
    private double [] vector;
    private List<Result> resultList;

    public Data (String label, double [] vector) {
        this.label = label;
        this.vector = vector;
        this.resultList = new ArrayList<>();
    }

    public double calcEuq(double [] vector) {
        double sum = 0;
        for (int i = 0; i < this.vector.length; i++) {
            sum+=Math.pow(vector[i]-this.vector[i],2);
        }
        return Math.sqrt(sum);
    }

    public void addResult(String label, double eq) {
        resultList.add(new Result(label,eq));
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public boolean kNNResult(int k) {
        Map<String,Integer> resultMap = new HashMap<>();
        resultList.sort(new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                return Double.compare(o1.getEuq(), o2.getEuq());
            }
        });

        for (int i = 0; i < k; i++) {
            if (!resultMap.containsKey(resultList.get(i).getLabel())) {
                resultMap.put(resultList.get(i).getLabel(), 1);
            } else {
                resultMap.replace(resultList.get(i).getLabel(),resultMap.get(resultList.get(i).getLabel())+1);
            }
        }

        int max = 0;
        String label = null;
        for (Map.Entry<String,Integer> entry : resultMap.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                label = entry.getKey();
            }
        }

        System.out.println("Najwiecej jest: " + max);
        System.out.println("Etykieta: " + label);
        System.out.println("Czy poprawna: " + this.label.equals(label));

        return this.label.equals(label);

    }

    public String getLabel() {
        return label;
    }

    public double[] getVector() {
        return vector;
    }
}
