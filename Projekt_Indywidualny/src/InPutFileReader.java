import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InPutFileReader {

    Manufacturer manufacturer = new Manufacturer();
    Pharmacy pharmacy = new Pharmacy();
    Contracts contracts = new Contracts();
    public ArrayList<Integer> manufacturerContractID = new ArrayList<>();
    public ArrayList<Integer> pharmacyContractID = new ArrayList<>();


    public void readFile(String path) {
        if(path == null){
            throw new IllegalArgumentException("Input path cannot be null.");
        }

        try {
            File inFile = new File(path);
            FileReader reader = new FileReader(inFile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            int hashCounter = 0;
            int lineCounter = 0;
            int manufacturerCounter = 0;
            int pharmacyCounter = 0;
            int indexMan = 0;
            int indexPhar = 0;
            ArrayList<Integer> howManyManu  = new ArrayList<>();
            ArrayList<Integer> howManyPhar  = new ArrayList<>();
            while((line = bufferedReader.readLine()) != null) {

                if(line.contains("#")){
                    hashCounter++;
                    lineCounter++;
                }else if((line.contains("#") != true) && hashCounter == 1){
                    lineCounter++;
                    manufacturerCounter++;
                    String[] words = line.split(" \\| ");
                    if(words.length != 3){
                        finish(1,lineCounter);
                    }else if(lineCounter == 251){
                        finish(3, lineCounter);
                    }else {
                        howManyManu.add(checkIfInteger(words[0], lineCounter));
                        if ((manufacturerCounter-1) == howManyManu.get(manufacturerCounter - 1)) {
                            manufacturer.addToList(words[1], checkIfInteger(words[2], lineCounter));
                        }else{
                            finish(2, lineCounter);
                        }
                    }
                }else if((line.contains("#") != true) && hashCounter == 2){
                    lineCounter++;
                    pharmacyCounter++;
                    String[] words = line.split(" \\| ");
                    if(words.length != 3){
                        finish(1,lineCounter);
                    }else if(lineCounter == 502){
                        finish(3, lineCounter);
                    }
                    else{
                        howManyPhar.add(checkIfInteger(words[0], lineCounter));
                        if ((pharmacyCounter-1) == howManyPhar.get(pharmacyCounter - 1)) {
                            pharmacy.addToList(words[1], checkIfInteger(words[2], lineCounter));
                        }else{
                            finish(2, lineCounter);
                        }
                    }
                }else if((line.contains("#") != true) && hashCounter == 3){
                    lineCounter++;
                    if(indexPhar != pharmacyCounter){
                        indexPhar++;
                    }else {
                        indexMan++;
                        indexPhar = 1;
                    }

                    String[] words = line.split(" \\| ");
                    if(words.length != 4){
                        finish(1,lineCounter);
                    }else {
                        if(checkIfInteger(words[0], lineCounter) == howManyManu.get(indexMan) && checkIfInteger(words[1], lineCounter) ==
                                howManyPhar.get(indexPhar -1 )){
                            contracts.addToList(checkIfDouble(words[2], lineCounter), checkIfDouble(words[3], lineCounter));
                            manufacturerContractID.add(checkIfInteger(words[0], lineCounter));
                            pharmacyContractID.add(checkIfInteger(words[1], lineCounter));
                            addConnections(checkIfInteger(words[0], lineCounter),checkIfInteger(words[1], lineCounter));
                        }else{
                            finish(2, lineCounter);
                        }
                    }
                }else if( hashCounter ==0){
                    finish(2, 1);
                }
            }
        }catch (IOException e){
            throw new IllegalArgumentException("Input path cannot be null.");
        }
    }

    private void addConnections(int checkIfInteger, int checkIfInteger1) {
        String str = manufacturer.getName(checkIfInteger) + " -> " + pharmacy.getName(checkIfInteger1);
        contracts.addToStringList(str);
    }


    private void finish(int x, int lineCounter){
        if(x == 1){
            System.out.println("Za malo danych w " + lineCounter + " linii.");
            System.exit(lineCounter);
        }else if(x==2){
            System.out.println("Bledne dane w " + lineCounter + " linii.");
            System.exit(lineCounter);
        }else{
            System.out.println("Za dużo danych w pliku!");
            System.exit(lineCounter);
        }
    }

    private int checkIfInteger(String word, int lineCounter){
        int cyfra = 0;
        try{
            cyfra = Integer.parseInt(word);
        }catch(Exception e){
            finish(2, lineCounter);
        }
        return cyfra;
    }

    private double checkIfDouble(String word, int lineCounter){
        double cyfra = 0;
        try{
            cyfra = Double.parseDouble(word);
        }catch(Exception e){
            finish(2, lineCounter);
        }
        return cyfra;
    }

}
