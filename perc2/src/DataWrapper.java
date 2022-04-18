import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;

public final class DataWrapper {
    //private final String species;
    private int speciesInt;
    private  double[] values;
    private HashMap<String, Integer> vals;
    private String species;

    public DataWrapper(HashMap<String, Integer> data, String predict, String predict2, int total) {
        this.values = new double[26];
        this.species = predict2;
        vals = data;
        for (int i = 'A'; i <= 'Z'; i++) {
            char c = (char)i;
            String c1 = String.valueOf(c);
            if(!data.containsKey(c1)){
                this.values[i-'A'] = 0;
            }else{
                Double m = (double)data.get(c1);
                Double m1 = (double)total;
                this.values[i-'A'] = m/m1;
            }
        }
        if(predict2.equals(predict)){
            this.speciesInt = 1;
        }else{
            this.speciesInt = 0;
        }
    }

    public String getSpecies() {
        return species;
    }

    @Override
    public String toString() {
        return "DataWrapper{" +
                "species='" + speciesInt + '\'' +
                ", values=" + Arrays.toString(values) +
                '}';
    }

    public HashMap<String, Integer> getVals() {
        return vals;
    }

    public double[] getValues() {
        return values.clone();
    }

    public int getSpeciesInt() {
        return speciesInt;
    }
}