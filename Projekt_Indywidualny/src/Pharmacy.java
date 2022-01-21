import java.util.ArrayList;

public class Pharmacy implements Firm {
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<Integer> vaccineAmount = new ArrayList<>();

    @Override
    public void addToList(String nazwa, int potrzebnaIlosc) {
        name.add(nazwa);
        vaccineAmount.add(potrzebnaIlosc);
    }

    @Override
    public String getName(int counter) {
        return name.get(counter);
    }

    @Override
    public int getMax(int counter) {
        return vaccineAmount.get(counter);
    }

    @Override
    public int getSize() {
        return vaccineAmount.size();
    }
}
