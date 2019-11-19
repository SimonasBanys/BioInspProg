import java.util.Vector;

public class AnnNode {

    private double[] weights;
    private double weightedSum = 0;
    private double out;


    public AnnNode(int s){
        this.weights = new double[s];
        for (int i = 0; i < s; i++){
            this.weights[i] = (((double) Math.round(Math.random() * 10000d)/10000d));
        }
    }

    public void calcWeightedSum(double[] inp){
        for (int i = 0; i < inp.length; i++){
            this.weightedSum += this.weights[i] * inp[i];
        }
    }

    public double[] getWeights(){
        return this.weights;
    }

    public double getWeightedSum(){
        return weightedSum;
    }

    public double getOut() {
        return out;
    }

    public void setOut(double out) {
        this.out = out;
    }
}
