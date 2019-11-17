public class ANN {

    private double[][][] weights;
    private double[] inputs;
    private int nodes;
    private int layers;

    public double sigmoid(double x){
        double out;
        out = 1/(1+Math.exp(-x));
        return out;
    }

    public double nullFunc(double x){
        return 0;
    }

    public double hypTan(double x){
        return Math.tanh(x);
    }

    public double cos(double x){
        return Math.cos(x);
    }

    public double gauss(double x){
        return Math.exp(0-(x*x/2));
    }

    public ANN(int n, int l, double[] inp){
        this.layers = l;
        this.nodes = n;
        if (inp.length != this.nodes){
            throw new RuntimeException("input vector is not of appropriate size");
        }
        weights = new double[nodes][inp.length][layers];
        for (int i = 0; i < nodes; i++){
            for (int j = 0; j < inp.length; j++){
                for (int k = 0; k < layers; k++){
                    weights[i][j][k] = (double) Math.round(Math.random() * 10000d)/10000d;
                }
            }
        }
    }


    public double[][][] getWeights() {
        return weights;
    }

    public int getNodes(){
        return nodes;
    }

    public int getLayers() {
        return layers;
    }


}
