import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Dictionary
{
    HashMap<String, ArrayList<String>> dictionaryByTrig;
    Set<String> regularDictionary;

    public Dictionary(String filePath)
    {
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

//    // Old version
//    public boolean contains(String word, ArrayList<String> wordTrigrammes)
//    {
//        int minLength = 400000;                                     //Faire des stats
//
//        String minTrig = wordTrigrammes.get(0);
//        for(String wordTrigIter : wordTrigrammes)
//        {
//            if( dictionaryByTrig.containsKey(wordTrigIter) && ( dictionaryByTrig.get(wordTrigIter).size() < minLength ) )
//            {
//                minTrig = wordTrigIter;
//                minLength = dictionaryByTrig.get(wordTrigIter).size();
//            }
//        }
//
//        if( dictionaryByTrig.containsKey(minTrig) && (dictionaryByTrig.get(minTrig).contains(word)) )
//        {
//            return true;
//        }
//        return false;
//    }

}
