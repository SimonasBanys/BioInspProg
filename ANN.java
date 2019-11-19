import java.util.Arrays;
import java.util.LinkedList;

public class ANN {

    private double[] inputs;
    private int layers;
    private LinkedList<AnnNode[]> layer = new LinkedList<AnnNode[]>();

    private double sigmoid(double x){
        double out;
        out = 1/(1+Math.exp(-x));
        return out;
    }

    private double nullFunc(double x){
        return x*0.0;
    }

    private double hypTan(double x){
        return Math.tanh(x);
    }

    private double cos(double x){
        return Math.cos(x);
    }

    private double gauss(double x){
        return Math.exp(0-(x*x/2));
    }

    public ANN(int n, int l, double[] inp){
        this.layers = l;
        this.inputs = inp;
        int size = (int) Math.round((Math.random()* (n-1)))+1;
        AnnNode[] lrs = new AnnNode[size];
        for (int i = 0; i < size; i++){
            lrs[i] = new AnnNode(inp.length);
            lrs[i].calcWeightedSum(inp);
        }
        this.layer.add(lrs);
        for (int i = 1; i < l; i++){
            size = (int) Math.round((Math.random()* (n-1)))+1;
            lrs = new AnnNode[size];
            for (int j = 0; j < size; j++){
                lrs[j] = new AnnNode(this.layer.getLast().length);
            }
            layer.add(lrs);
        }
    }

    public void calculate(int x){
        double[] next = new double[layer.getFirst().length];
        double[] temp;
        for (int i = 0; i < next.length; i++){
            switch (x){
                case 0:
                    next[i] = this.nullFunc(this.layer.getFirst()[i].getWeightedSum());
                    this.layer.getFirst()[i].setOut(next[i]);
                    break;
                case 1:
                    next[i] = this.hypTan(this.layer.getFirst()[i].getWeightedSum());
                    this.layer.getFirst()[i].setOut(next[i]);
                    break;
                case 2:
                    next[i] = this.cos(this.layer.getFirst()[i].getWeightedSum());
                    this.layer.getFirst()[i].setOut(next[i]);
                    break;
                case 3:
                    next[i] = sigmoid(this.layer.getFirst()[i].getWeightedSum());
                    this.layer.getFirst()[i].setOut(next[i]);
                    break;
                case 4:
                    next[i] = gauss(this.layer.getFirst()[i].getWeightedSum());
                    this.layer.getFirst()[i].setOut(next[i]);
                    break;
                default :
                    break;
            }
            System.out.print(next[i] + " ");
        }
        System.out.println();

        for (int i = 1; i < layers; i++){
            temp = Arrays.copyOf(next, next.length);
            next = new double[this.layer.get(i).length];
            for (int j = 0; j < next.length; j++){
                layer.get(i)[j].calcWeightedSum(temp);
                switch (x){
                    case 0:
                        next[j] = this.nullFunc(this.layer.get(i)[j].getWeightedSum());
                        this.layer.get(i)[j].setOut(next[j]);
                        break;
                    case 1:
                        next[j] = this.hypTan(this.layer.get(i)[j].getWeightedSum());
                        this.layer.get(i)[j].setOut(next[j]);
                        break;
                    case 2:
                        next[j] = this.cos(this.layer.get(i)[j].getWeightedSum());
                        this.layer.get(i)[j].setOut(next[j]);
                        break;
                    case 3:
                        next[j] = sigmoid(this.layer.get(i)[j].getWeightedSum());
                        this.layer.get(i)[j].setOut(next[j]);
                        break;
                    case 4:
                        next[j] = gauss(this.layer.get(i)[j].getWeightedSum());
                        this.layer.get(i)[j].setOut(next[j]);
                        break;
                    default :
                        break;
                }
                System.out.print(next[j] + " ");
            }
            System.out.println();
        }
        System.out.println();

    }

    public int getLayers() {
        return this.layers;
    }

    public double[] getInputs() {
        return inputs;
    }
}
