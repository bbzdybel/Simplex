import java.util.ArrayList;

public class Contracts {

    private ArrayList<Double> amount = new ArrayList<>();
    private ArrayList<Double> cost = new ArrayList<>();
    private ArrayList<String> connections = new ArrayList<>();

    public void addToList(double ilosc, double cena){
        amount.add(ilosc);
        cost.add(cena);
    }

    public void addToStringList(String connection){
        connections.add(connection);
    }

    public double getAmount(int counter) {
        return amount.get(counter);
    }

    public double getCost(int counter) {
        return cost.get(counter);
    }

    public int getSize() {
        return amount.size();
    }

    public String getstringConnection(int connection){
        return connections.get(connection);
    }
}
