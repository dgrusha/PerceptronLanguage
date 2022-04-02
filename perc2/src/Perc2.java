import java.util.ArrayList;
import java.util.List;

public class Perc2 {
    double theta=0;
    double alpha = 0;
    private List<DataWrapper> train;
    private List<DataWrapper> test;
    private double[] weights = new double[4];
    double rate;
    int numOfIter;

    public Perc2(List<DataWrapper> train, List<DataWrapper> test, double rate, int numOfIter, double alpha) {
        this.train = train;
        this.test = test;
        this.rate = rate;
        this.numOfIter = numOfIter;
        this.alpha = alpha;
    }

    //learn without alpha
    public void learn(){
        for (int i = 0; i < numOfIter; i++) {
            //int Toterr = 0;
            for(DataWrapper dw : train){
                double writtenRes = countNet(dw.getValues());
                double error = dw.getSpeciesInt() - writtenRes;
                //Toterr += error
                for (int j = 0; j < dw.getValues().length; j++) {
                    //* alpha
                    weights[j] += dw.getValues()[j]*error;
                }
                //theta += alpha*error;
                theta += error;
            }
        }
    }

    public void predict(){
        double res=0;
        int totalCor = 0;
        for (int i = 0; i < test.size(); i++) {
            res = countNet(test.get(i).getValues());
            System.out.println(res + " // " +test.get(i).getSpeciesInt() + " // " + test.get(i).getSpecies() );
            if(res == test.get(i).getSpeciesInt()){
                totalCor++;
            }
        }
        System.out.println(totalCor + " of " + test.size() + " is correct");
    }

    //learn with alpha
    public void learnA(){
        for (int i = 0; i < numOfIter; i++) {
            double totalErr = 0;
            for(DataWrapper dw : train){
                double writtenRes = countNetA(dw.getValues());
                double error = (dw.getSpeciesInt() - writtenRes);
                totalErr += Math.abs(error);
                weights[0] += alpha*error;

                for (int j = 0; j < dw.getValues().length-1; j++) {
                    weights[j+1] += alpha*error*dw.getValues()[j];
                }
            }
        }
    }

    private double countNet(double[] data){
        double result = 0;
        for (int i = 0; i < data.length; i++) {
            result += weights[i]*data[i];
        }
        if (result > 0) {
            return 1;
        }
        else {
            return 0;
        }
    }



    //USING ALPHA
    public void predictA(){
        double res=0;
        int totalCor = 0;
        for (int i = 0; i < test.size(); i++) {
            res = countNetA(test.get(i).getValues());
            System.out.println(res + " // " +test.get(i).getSpeciesInt() + " // " + test.get(i).getSpecies() );
            if(res == test.get(i).getSpeciesInt()){
                totalCor++;
            }
        }
        System.out.println(totalCor + " of " + test.size() + " is correct");
    }

    //prediction with activation formula
    public double countNetA(double[] data){
        double a = weights[0];
        for (int i = 0; i < weights.length-1; i++) {
            a += weights[i+1]*data[i];
        }
        if (a > 0) {
            return 1;
        }
        else {
            return 0;
        }
    }

    //prediction without activation formula
    public double predict(double[] data){
        return countNet(data);
    }

    public double getTheta() {
        return theta;
    }

    public double[] getWeights() {
        return weights;
    }


}
