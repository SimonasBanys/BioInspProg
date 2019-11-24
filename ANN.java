import java.util.ArrayList;
import java.util.Arrays;

public class ANN {

    private double[] inputs; // initial inputs into the ANN
    private int layers; // amount of layers in ANN
    private int[] nodes; // amount of nodes in each specific layer
    private int maxWeights = 0; // total amount weights through all nodes
    private ArrayList<AnnNode[]> layout = new ArrayList<>(); // an array list determining the layout of ANN

// --------------------  Activation functions ---------------

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

// --------------------- End of Activation functions ------------


    // Constructor for ANN
    public ANN(int n, int l, double[] inp){
        this.layers = l;                                                                                                // store the amount of layers
        this.inputs = inp;                                                                                              // store inputs per layer
        nodes = new int[l];
        int size = (int) Math.round((Math.random()*(n-1)))+1;                                                           // define the amount of nodes for initial layer
        AnnNode[] lrs = new AnnNode[size];                                                                              // array of nodes for each layer
        for (int i = 0; i < size; i++){                                                                                 // creation of nodes for the initial layer
            lrs[i] = new AnnNode(inp.length);                                                                           // creation of node
            lrs[i].calcWeightedSum(inp);                                                                                // calculation of weighted input sum for the particular node
        }
        nodes[0] = lrs.length;                                                                                          // store the amount of nodes in initial layer
        maxWeights += lrs.length*inp.length;                                                                            // the amount of weights in total required for the weight update
        this.layout.add(lrs);                                                                                           // adds the layer of nodes to the ANN layout
        for (int i = 1; i < l-1; i++){
            size = (int) Math.round((Math.random()* (n-1)))+1;
            lrs = new AnnNode[size];
            for (int j = 0; j < size; j++){
                lrs[j] = new AnnNode(this.layout.get(i-1).length);
            }
            nodes[i] = lrs.length;
            maxWeights += lrs.length*lrs[0].getWeights().length;
            this.layout.add(lrs);
        }
        lrs = new AnnNode[1];                                                                                           // generates a single node in the final layer
        lrs[0] = new AnnNode(this.layout.get(l-1).length);                                                              // makes sure that the node has the right amount of connections to the previous layer
        nodes[l-1] = lrs.length;
        maxWeights += lrs[0].getWeights().length;
        this.layout.add(lrs);                                                                                           // adds the final layer to the layout
    }

    public void calculate(int x){
        double[] next = new double[this.layout.get(0).length];
        double[] temp;
        for (int i = 0; i < next.length; i++){                                                                          // calculates activation function output for the initial layer
            switch (x){
                case 0:
                    next[i] = this.nullFunc(this.layout.get(0)[i].getWeightedSum());
                    this.layout.get(0)[i].setOut(next[i]);
                    break;
                case 1:
                    next[i] = this.hypTan(this.layout.get(0)[i].getWeightedSum());
                    this.layout.get(0)[i].setOut(next[i]);
                    break;
                case 2:
                    next[i] = this.cos(this.layout.get(0)[i].getWeightedSum());
                    this.layout.get(0)[i].setOut(next[i]);
                    break;
                case 3:
                    next[i] = sigmoid(this.layout.get(0)[i].getWeightedSum());
                    this.layout.get(0)[i].setOut(next[i]);
                    break;
                case 4:
                    next[i] = gauss(this.layout.get(0)[i].getWeightedSum());
                    this.layout.get(0)[i].setOut(next[i]);
                    break;
                default :
                    break;
            }
        }
        for (int i = 1; i < layers; i++){
            temp = Arrays.copyOf(next, next.length);
            next = new double[this.layout.get(i).length];
            for (int j = 0; j < next.length; j++){
                this.layout.get(i)[j].calcWeightedSum(temp);
                switch (x){
                    case 0:
                        next[j] = this.nullFunc(this.layout.get(i)[j].getWeightedSum());
                        this.layout.get(i)[j].setOut(next[j]);
                        break;
                    case 1:
                        next[j] = this.hypTan(this.layout.get(i)[j].getWeightedSum());
                        this.layout.get(i)[j].setOut(next[j]);
                        break;
                    case 2:
                        next[j] = this.cos(this.layout.get(i)[j].getWeightedSum());
                        this.layout.get(i)[j].setOut(next[j]);
                        break;
                    case 3:
                        next[j] = sigmoid(this.layout.get(i)[j].getWeightedSum());
                        this.layout.get(i)[j].setOut(next[j]);
                        break;
                    case 4:
                        next[j] = gauss(this.layout.get(i)[j].getWeightedSum());
                        this.layout.get(i)[j].setOut(next[j]);
                        break;
                    default :
                        break;
                }
            }
        }
    }

    public int getLayers() {
        return this.layers;
    }

    public double[] getInputs() {
        return inputs;
    }

    public void setWeights(double[] w) throws Exception {
        if (w.length != this.maxWeights){                                                                               // checks if the size of new weight array for the ANN matches the size of current ANN weight array
            throw new Exception("new weight array size doesnt match existing one");                                     // throw exception if it doesnt match
        } else {                                                                                                        // if matches perform the weigth update
            int x = 0;                                                                                                  // index of the new weight array
            int workLayer = 0;                                                                                          // layer of nodes to be worked on
            double[] newWeights;                                                                                        // array for new weights
            for (int i = 0; i < this.layout.get(workLayer).length; i++){                                                // loop to work through the layer
                newWeights = new double[this.layout.get(workLayer)[i].getWeights().length];                             // new weights array for each node is set to the size of
                for (int j = 0; j < newWeights.length; j++){                                                            // set up of the new weights array
                    newWeights[j] = w[x];
                    x++;                                                                                                // increment the index
                }
                this.layout.get(workLayer)[i].setWeights(newWeights);                                                   // set new weights for the node
                if (i == this.layout.get(workLayer).length-1){                                                          // check if it was the last node in the layer, if yes, move to new layer and start from the new node
                    i =0;
                    workLayer++;
                }
            }
        }
    }

    public int getMaxWeights(){
        return maxWeights;
    }

    public int[] getNodes(){
        return nodes;
    }

    public void updateInputs(double[] inp){                                                                             // method for updating the input array
        this.inputs = inp;
        for (int i = 0; i < this.layout.get(0).length; i++){
            this.layout.get(0)[i].calcWeightedSum(this.inputs);
        }
    }
}
