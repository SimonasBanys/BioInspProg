public class main {

    public static void main(String[] args){
        double[] in = {4, 7, 9, 6, 2};
        ANN a = new ANN(5, 5, in);
        for (int i = 0; i < 6; i++){
            a.calculate(i);
        }
    }
}
