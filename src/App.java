import java.util.ArrayList;

public class App
{
    public static void main(String[] args)
    {

        double startTime = System.nanoTime();

        MyDictionary dictionary = new MyDictionary("ressources/dico.txt");

        double dicoCreationDuration = (System.nanoTime()-startTime)/1000000000;
        System.out.println("Creation du dictionaire : " + dicoCreationDuration + "s");


        CorrecteurOrtho myCorrector = new CorrecteurOrtho(dictionary);

        startTime = System.nanoTime();
        ArrayList<ArrayList<String>> correctedFile = myCorrector.correctionFromFile("ressources/fautes.txt");
        double fileCorrectionDuration = (System.nanoTime()-startTime)/1000000000;
        System.out.println("Temps passé dans findSimilarWords : " + dictionary.totalSearchDuration + "s");
        System.out.println("Correction du fichier : " + fileCorrectionDuration + "s");


        //myCorrector.correctorReader(correctedFile);
    }
}
