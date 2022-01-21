import java.util.ArrayList;

public class Manufacturer implements Firm {

    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<Integer> maxVaccineAmount = new ArrayList<>();

    @Override
    public void addToList(String nazwa, int maksymalna) {
        name.add(nazwa);
        maxVaccineAmount.add(maksymalna);
    }

    @Override
    public String getName(int counter) {
        return name.get(counter);
    }

    @Override
    public int getMax(int counter) {
        return maxVaccineAmount.get(counter);
    }

    @Override
    public int getSize() {
        return maxVaccineAmount.size();
    }
}
