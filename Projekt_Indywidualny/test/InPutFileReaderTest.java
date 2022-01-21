import org.junit.Test;
import static org.junit.Assert.*;



public class InPutFileReaderTest {

    @Test(expected = IllegalArgumentException.class)
    public void should_reciveIllegalArgumentException_when_pathIsNull(){
        //given
        InPutFileReader inPutFileReader = new InPutFileReader();
        String path = null;
        // when
        inPutFileReader.readFile(path);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_giveIllegalArgumentException_when_pathIsWrong(){
        //given
        InPutFileReader inPutFileReader = new InPutFileReader();
        String path = "src/resorses/daneKtorychNieMa.txt";
        // when
        inPutFileReader.readFile(path);

        // then
        assert false;
    }

    @Test
    public void should_giveManufacturerValue(){
        //given
        InPutFileReader inPutFileReader = new InPutFileReader();
        String path = "src/resorses/dane1.txt";
        // when
        inPutFileReader.readFile(path);
        int result = inPutFileReader.manufacturer.getMax(0);

        // then
        int expected = 900;
        assertEquals(expected,result);
    }

    @Test
    public void should_givePharmacyValue(){
        //given
        InPutFileReader inPutFileReader = new InPutFileReader();
        String path = "src/resorses/dane1.txt";
        // when
        inPutFileReader.readFile(path);
        int result = inPutFileReader.pharmacy.getMax(0);

        // then
        int expected = 450;
        assertEquals(expected,result);
    }

    @Test
    public void should_giveContractAmount(){
        //given
        InPutFileReader inPutFileReader = new InPutFileReader();
        String path = "src/resorses/dane1.txt";
        // when
        inPutFileReader.readFile(path);
        double result = inPutFileReader.contracts.getAmount(0);

        // then
        double expected = 800;
        assertEquals(expected,result,0);
    }

    @Test
    public void should_giveTabWithPharmacName(){
        //given
        InPutFileReader inPutFileReader = new InPutFileReader();
        String path = "src/resorses/dane1.txt";
        // when
        inPutFileReader.readFile(path);
        String[] result = new String[3];
        for(int i =0; i<3; i++){
            result[i] = inPutFileReader.pharmacy.getName(i);
        }


        // then
        String[] expected = new String[] {"CentMedEko Centrala", "CentMedEko 24h", "CentMedEko Nowogrodzka"};
        assertArrayEquals(expected, result);
    }

    @Test
    public void should_giveTabWithPharmacyVaccineAmount(){
        //given
        InPutFileReader inPutFileReader = new InPutFileReader();
        String path = "src/resorses/dane1.txt";
        // when
        inPutFileReader.readFile(path);
        Integer[] result = new Integer[3];
        for(int i =0; i<3; i++){
            result[i] = inPutFileReader.pharmacy.getMax(i);
        }


        // then
        Integer[] expected = new Integer[] {450, 690, 1200};
        assertArrayEquals(expected, result);
    }

    @Test
    public void should_giveTabWithManufacturerName(){
        //given
        InPutFileReader inPutFileReader = new InPutFileReader();
        String path = "src/resorses/dane1.txt";
        // when
        inPutFileReader.readFile(path);
        String[] result = new String[3];
        for(int i =0; i<3; i++){
            result[i] = inPutFileReader.manufacturer.getName(i);
        }


        // then
        String[] expected = new String[] {"BioTech 2.0", "Eko Polska 2020", "Post-Covid Sp. z o.o."};
        assertArrayEquals(expected, result);
    }

    @Test
    public void should_giveTabWithManufacturerVaccineAmount(){
        //given
        InPutFileReader inPutFileReader = new InPutFileReader();
        String path = "src/resorses/dane1.txt";
        // when
        inPutFileReader.readFile(path);
        Integer[] result = new Integer[3];
        for(int i =0; i<3; i++){
            result[i] = inPutFileReader.manufacturer.getMax(i);
        }


        // then
        Integer[] expected = new Integer[] {900, 1300, 1100};
        assertArrayEquals(expected, result);
    }

}
