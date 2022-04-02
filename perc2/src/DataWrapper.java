import java.util.Arrays;

public final class DataWrapper {
    private final String species;
    private int speciesInt;
    private final double[] values;

    public DataWrapper(String data, String predict) {
        String[] temp = data
                .replaceAll(" +","")
                .replaceAll(",", "\\.")
                .split("\t");

        this.species = temp[temp.length-1];
        if(temp[temp.length-1].equals(predict)){
            this.speciesInt = 1;
        }else{
            this.speciesInt = 0;
        }

        this.values = new double[temp.length-1];
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = Double.parseDouble(temp[i]);
        }
    }

    public DataWrapper(String data) {
        String[] temp = data
                .replaceAll(" +","")
                .replaceAll(",", "\\.")
                .split("\t");

        this.species = temp[temp.length-1];
        this.values = new double[temp.length-1];
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = Double.parseDouble(temp[i]);
        }
    }

    public DataWrapper(String species, double[] values) {
        this.species = species;
        this.values = values;
    }

    public String getSpecies() {
        return species;
    }

    @Override
    public String toString() {
        return "DataWrapper{" +
                "species='" + species + '\'' +
                ", values=" + Arrays.toString(values) +
                '}';
    }

    public double[] getValues() {
        return values.clone();
    }

    public int getSpeciesInt() {
        return speciesInt;
    }
}