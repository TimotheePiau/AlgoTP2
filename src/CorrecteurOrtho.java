import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.lang.String;
import java.util.HashMap;
import java.util.Scanner;


public class CorrecteurOrtho
{
    MyDictionary dictionary;
    public CorrecteurOrtho(MyDictionary dictionary)
    {
        this.dictionary = dictionary;
    }

    public ArrayList<String> correction(String word)
    {
        //System.out.println(word);

        //double correctionStartTime =System.nanoTime();

        // Mot Juste?
        if(dictionary.contains(word))
        {
            return null;
        }
        //System.out.println("Test presence dico : " + (System.nanoTime()-correctionStartTime)/1000000000);

        // Recherche de Mot similaire
        ArrayList<ArrayList<String>> sharedTrigrammesCount = dictionary.findSimilarWords(word);
        //System.out.println("findSimilarWords : " + (System.nanoTime()-correctionStartTime)/1000000000);

        // Selection de N mot similaire
        ArrayList<String> reallySimilarWords = descendingSelection(sharedTrigrammesCount,100, 29);
        //System.out.println("Selection 100 mots : " + (System.nanoTime()-correctionStartTime)/1000000000);

        // Calcul des distances d'edition
        HashMap<Integer,ArrayList<String>> wordByEditDist = Levenstein.sortByEditDist(word, reallySimilarWords);
        //System.out.println("Leveinstein : " + (System.nanoTime()-correctionStartTime)/1000000000);

        // Selection des 5 mots les plus proches
        ArrayList<String> possibleCorrection = ascendingSelection(wordByEditDist, 5, 10);
        //System.out.println("Selection 5 mots : " + (System.nanoTime()-correctionStartTime)/1000000000);

        //System.out.println("Temps total : " + (System.nanoTime()-correctionStartTime)/1000000000);
        //System.out.println();

        return possibleCorrection;

    }

    public ArrayList<ArrayList<String>> correctionFromFile(String filePath)
    {


        File fileToFix = new File(filePath);
        Scanner fileEntry = null;

        ArrayList<String> listToFix = new ArrayList<String>();

        try
        {
            fileEntry = new Scanner(new BufferedReader(new FileReader(new File(filePath))));
            while(fileEntry.hasNextLine())
            {
                listToFix.add(fileEntry.nextLine());
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        ArrayList<ArrayList<String>> correctedWords = new ArrayList<ArrayList<String>>();

        for(String wordToFix : listToFix)
        {
            //double storageStartTime = System.nanoTime();
            ArrayList<String> correctedWord = new ArrayList<String>();

            correctedWord.add(wordToFix);
            correctedWord.addAll(correction(wordToFix));

            correctedWords.add(correctedWord);

            //System.out.println("Temps total mot : " + (System.nanoTime()-storageStartTime)/1000000000);
            //System.out.println();
        }

        return correctedWords;
    }

    public void correctorReader(ArrayList<ArrayList<String>> correctedFile)
    {
        for(ArrayList<String> correctedWord : correctedFile)
        {
            System.out.print(correctedWord.get(0) + " :");
            for(String possibleCorrection : correctedWord.subList(1,6))
            {
                System.out.print(" " + possibleCorrection + " |");
            }
            System.out.println();
        }
    }

    private ArrayList<String> descendingSelection(ArrayList<ArrayList<String>> listToReduce, int maxValueCount, int maxKeyValue)
    {
        ArrayList<String> selectedWord = new ArrayList<String>();
        int keyIterator = maxKeyValue;
        int selectedWordCount = 0;

        while((keyIterator>0) && (selectedWordCount < maxValueCount))
        {
            ArrayList<String> currentList = listToReduce.get(keyIterator);
            if(currentList.isEmpty())
            {
                keyIterator--;
                continue;
            }

            if((currentList.size() + selectedWordCount) <= maxValueCount)
            {
                selectedWord.addAll(currentList);
                selectedWordCount += currentList.size();
            }
            else
            {
                selectedWord.addAll(currentList.subList( 0, (maxValueCount - selectedWordCount)) );
                selectedWordCount = maxValueCount;
            }
            keyIterator--;
        }

        return selectedWord;
    }

    private ArrayList<String> ascendingSelection(HashMap<Integer,ArrayList<String>> mapToReduce, int maxValueCount, int maxKeyValue)
    {
        ArrayList<String> selectedWord = new ArrayList<String>();
        int keyIterator = 0;
        int selectedWordCount = 0;

        while((keyIterator < maxKeyValue) && (selectedWordCount < maxValueCount))
        {
            if(!mapToReduce.containsKey(keyIterator))
            {
                keyIterator++;
                continue;
            }
            if( (mapToReduce.get(keyIterator).size() + selectedWordCount) <= maxValueCount )
            {
                selectedWord.addAll(mapToReduce.get(keyIterator));
                selectedWordCount += mapToReduce.get(keyIterator).size();
            }
            else
            {
                selectedWord.addAll(mapToReduce.get(keyIterator).subList( 0, (maxValueCount - selectedWordCount)) );
                selectedWordCount = maxValueCount;
            }


            keyIterator++;
        }
        return selectedWord;
    }

//    // More quality versions (but more word)
//
//    private ArrayList<String> descendingQualitySelection(HashMap<Integer,ArrayList<String>> mapToReduce, int valueMaxCount, int maxKeyValue)
//    {
//        ArrayList<String> selectedWord = new ArrayList<String>();
//        int keyIterator = maxKeyValue;
//        int selectedWordCount = 0;
//
//        while((keyIterator>0) && (selectedWordCount < valueMaxCount))
//        {
//            if(!mapToReduce.containsKey(keyIterator))
//            {
//                keyIterator--;
//                continue;
//            }
//
//            selectedWord.addAll(mapToReduce.get(keyIterator));
//            selectedWordCount += mapToReduce.get(keyIterator).size();
//
//            keyIterator--;
//        }
//
//        return selectedWord;
//    }
//
//    private ArrayList<String> ascendingQualitySelection(HashMap<Integer,ArrayList<String>> mapToReduce, int valueMaxCount, int maxKeyValue)
//    {
//        ArrayList<String> selectedWord = new ArrayList<String>();
//        int keyIterator = 0;
//        int selectedWordCount = 0;
//
//        while((keyIterator < maxKeyValue) && (selectedWordCount < valueMaxCount))
//        {
//            if(!mapToReduce.containsKey(keyIterator))
//            {
//                keyIterator++;
//                continue;
//            }
//
//            selectedWord.addAll(mapToReduce.get(keyIterator));
//            selectedWordCount += mapToReduce.get(keyIterator).size();
//
//            keyIterator++;
//        }
//
//        return selectedWord;
//    }

}
