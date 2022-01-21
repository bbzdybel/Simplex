import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Algorytm{
    InPutFileReader inPutFileReader = new InPutFileReader();
    private int contractsSize;
    private int manufacturerSize;
    private int pharmacySize;
    private int m = 1000000;
    private int symplexX;
    private int symplexY;
    private double[][] symplexTab;


    public void run(String path){
        inPutFileReader.readFile(path);
        contractsSize = inPutFileReader.contracts.getSize();
        manufacturerSize = inPutFileReader.manufacturer.getSize();
        pharmacySize = inPutFileReader.pharmacy.getSize();
        symplexX = (2*contractsSize + manufacturerSize + pharmacySize + 3);
        symplexY = (contractsSize + manufacturerSize + pharmacySize + 3);
        symplexTab = new double[symplexY][symplexX];
        createMatrix();
    }

    public void createMatrix(){
        for (int i = 0; i < contractsSize; i++) {
            symplexTab[0][i+2] = inPutFileReader.contracts.getCost(i);
        }
        for (int i = 0; i < symplexX-3; i++) {
            symplexTab[1][i+2] = i+1;
        }
        for (int i = 0; i < symplexY-3; i++) {
            symplexTab[i+2][1] = contractsSize+i+1;
        }
        for (int i = symplexX-pharmacySize-1; i < symplexX-1; i++) {
            symplexTab[0][i] = m;
        }
        for (int i = symplexY-pharmacySize-1; i < symplexY-1; i++) {
            symplexTab[i][0] = m;
        }
        for (int i = 0; i < symplexY-3; i++) {
            if(i<contractsSize) {
                symplexTab[i + 2][symplexX - 1] = inPutFileReader.contracts.getAmount(i);
            }else if(i >= contractsSize && i < contractsSize + manufacturerSize){
                symplexTab[i + 2][symplexX - 1] = inPutFileReader.manufacturer.getMax(i-contractsSize);
            }else{
                symplexTab[i + 2][symplexX - 1] = inPutFileReader.pharmacy.getMax(i-contractsSize-manufacturerSize);
            }
        }
        fillTheCenterOfSymplexTab();
        checkMatrix();
    }

    private void fillTheCenterOfSymplexTab() {
        for (int i = 2; i < 2 + contractsSize; i++) {
            symplexTab[i][i] = 1;
        }
        for(int i =2+contractsSize; i < symplexX - 1; i++){
            symplexTab[i-contractsSize][i] = 1;
        }

        for (int i = 2; i < 2+ contractsSize; i++) {
            symplexTab[2+ contractsSize+inPutFileReader.manufacturerContractID.get(i-2)][i] = 1;
            symplexTab[2 + contractsSize +manufacturerSize+ inPutFileReader.pharmacyContractID.get(i-2)][i] = 1;
        }
    }

    private void checkMatrix() {
        double[] tmpVec = new double[symplexY-3];
        double[] wyniki = new double[symplexX-2];
        int positiveValues = 0;
        for (int i = 2; i < symplexY-1; i++) {
            tmpVec[i-2] = symplexTab[i][0];
        }
        for (int i = 0; i < symplexY-3; i++) {
            for (int j = 0; j < symplexX-2; j++) {
                wyniki[j] += tmpVec[i]*symplexTab[i+2][j+2];
            }
        }
        for (int i = 0; i < symplexX -2; i++) {
            wyniki[i] = wyniki[i] - symplexTab[0][i+2];
            if(wyniki[i] <= 0){
                positiveValues++;
            }
        }
        if(positiveValues == symplexX-3){
            writeInFile(wyniki[wyniki.length-1]);
        }else{
            createNextStage(wyniki);
        }
    }

    private void createNextStage(double[] wyniki) {
        int nrKolumny = 0;
        int nrWiersza = 0;
        double kolXWier = 0;
        double [] valuesKol = new double[symplexY-3];
        double [] valuesWier = new double[symplexX-2];
        double tmp = Double.MIN_VALUE;
        for (int i = 0; i < wyniki.length -1; i++) {
            if(wyniki[i] > 0 && wyniki[i] > tmp){
                tmp = wyniki[i];
                nrKolumny = i +2;

            }
        }
        tmp = Double.MAX_VALUE;
        for (int i = 2; i < symplexY-1 ; i++) {
            if(symplexTab[i][symplexX-1] > 0 && symplexTab[i][nrKolumny] == 1 && symplexTab[i][symplexX-1] < tmp){
                tmp = symplexTab[i][symplexX-1];
                nrWiersza = i;
            }
        }
        kolXWier = symplexTab[nrWiersza][nrKolumny];
        for (int i = 0; i < valuesKol.length; i++) {
            valuesKol[i] = symplexTab[i+2][nrKolumny];
        }
        for (int i = 0; i < valuesWier.length; i++) {
            valuesWier[i] = symplexTab[nrWiersza][i+2];
        }

        symplexTab[nrWiersza][0] = symplexTab[0][nrKolumny];
        symplexTab[nrWiersza][1] = symplexTab[1][nrKolumny];
        for (int i = 2; i < contractsSize+ pharmacySize+ manufacturerSize+2; i++) {
            for (int j = 2; j < contractsSize+ contractsSize+pharmacySize+ manufacturerSize +2; j++) {
                symplexTab[i][j] = symplexTab[i][j] - ((valuesKol[i-2]*valuesWier[j-2])/kolXWier);
            }
        }
        for (int i = 2; i < symplexY-1; i++) {
            if(symplexTab[1][nrKolumny] == symplexTab[i][1]){
                symplexTab[i][nrKolumny] = 1;
            }else{
                symplexTab[i][nrKolumny] = 0;
            }
        }
        for (int i = 2; i < symplexY-1; i++) {
            symplexTab[i][symplexX-1] = symplexTab[i][symplexX-1] - ((valuesKol[i-2]*valuesWier[symplexX-3])/kolXWier);
        }

        for (int i = 2; i < symplexX ; i++) {
            symplexTab[nrWiersza][i] = (valuesWier[i-2]/kolXWier);
        }
        checkMatrix();
    }

    private void writeInFile(double overallCost) {

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("directoryWithOutPutFile/outPutFile.txt");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        try {
            for (int i = 2; i < symplexY-1; i++) {
                if(symplexTab[i][1]>= 1 && symplexTab[i][1]<=contractsSize){
                    bufferedWriter.write(inPutFileReader.contracts.getstringConnection((int)symplexTab[i][1] - 1) +
                            " [Koszt = "  + symplexTab[i][symplexX-1]+ " * " + symplexTab[i][0] + " zł]");
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.write("Całkowite koszta= " + overallCost);
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }



}
