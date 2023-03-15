public class Data {

    private String label;
    private double [] vector;

    public Data (String label, double [] vector) {
        this.label = label;
        this.vector = vector;
    }

    public String getLabel() {
        return label;
    }

    public double[] getVector() {
        return vector;
    }
}
