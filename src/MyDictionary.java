import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class MyDictionary
{
    HashMap<String, ArrayList<String>> dictionaryByTrig;
    Set<String> regularDictionary;
    double totalSearchDuration;

    public MyDictionary(String filePath)
    {
        regularDictionary = new HashSet<String>();
        dictionaryByTrig = new HashMap<String,ArrayList<String>>();
        totalSearchDuration = 0;

        File dictionaryFile = new File(filePath);
        Scanner dictionaryEntry = null;

        try
        {
            dictionaryEntry = new Scanner(new BufferedReader(new FileReader(new File(filePath))));
            while(dictionaryEntry.hasNextLine())
            {
                String currentDictionaryWord = dictionaryEntry.nextLine();

                regularDictionary.add(currentDictionaryWord);
                ArrayList<String> currentWordTrig = Trigrammes.findTrigrammes(currentDictionaryWord);
                for(String trig : currentWordTrig)
                {
                    if(dictionaryByTrig.containsKey(trig))
                    {
                        dictionaryByTrig.get(trig).add(currentDictionaryWord);
                    }
                    else
                    {
                        ArrayList<String> addedWord = new ArrayList<String>();
                        addedWord.add(currentDictionaryWord);
                        dictionaryByTrig.put(trig, addedWord);
                    }
                }
            }
        } catch (
                FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public boolean contains(String word)
    {
        return regularDictionary.contains(word);
    }

    public ArrayList<ArrayList<String>> findSimilarWords(String word)
    {
        ArrayList<String> wordTrigrammes = Trigrammes.findTrigrammes(word);

        double findSWStartTime = System.nanoTime();
        HashMap<String,Integer> wordsSharedTrigCount = new HashMap<String,Integer>();

        for(String wordTrig : wordTrigrammes)
        {
            ArrayList<String> dictionaryTrigEntry = dictionaryByTrig.get(wordTrig);
            if(dictionaryTrigEntry == null)
            {
                continue;
            }
            //System.out.println("t: " + dictionaryTrigEntry.size());
            for(String similarWord : dictionaryTrigEntry)
            {
                Integer currentWordCount = wordsSharedTrigCount.get(similarWord);
                wordsSharedTrigCount.put(similarWord, currentWordCount == null ? 1 : currentWordCount + 1);
            }
        }
        //System.out.println("Comptage de trig : " + (System.nanoTime()-findSWStartTime)/1000000000);

        //double reverseMapStartTime = System.nanoTime();
        ArrayList<ArrayList<String>> wordBySharedTrigCount = new ArrayList<ArrayList<String>>();

        for (int initIndex = 0 ; initIndex < 30; initIndex++)
        {
            ArrayList<String> initList = new ArrayList<String>();
            wordBySharedTrigCount.add(initList);
        }

        for(Map.Entry<String, Integer> e : wordsSharedTrigCount.entrySet())
        {
            //Integer currentWSTC = wordsSharedTrigCount.get(currentWord);
            //wordBySharedTrigCount.get(currentWSTC).add(currentWord);
            wordBySharedTrigCount.get(e.getValue()).add(e.getKey());
        }

        //System.out.println("Inversion HashMap : " + (System.nanoTime()-reverseMapStartTime)/1000000000);
        totalSearchDuration += (System.nanoTime() - findSWStartTime)/1000000000;
        return wordBySharedTrigCount;
    }

}
