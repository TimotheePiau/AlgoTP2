import java.util.*;

public class Trigrammes
{

    public static ArrayList<String> findTrigrammes(String word)
    {
        int wordCharCount = word.length();
        ArrayList<String> trigramme = new ArrayList<String>();

        switch (wordCharCount)
        {
            case 0 :
            case 1 :    trigramme.add("1");
                        break;

            case 2 :    trigramme.add(word);
                        break;

            default :   trigramme.add(word.substring(0,1));
                        trigramme.add(word.substring(wordCharCount-2,wordCharCount-1));
                        for(int trigIterrator = 0; trigIterrator < (wordCharCount - 2); trigIterrator++)
                        {
                            trigramme.add(word.substring(trigIterrator, trigIterrator+2));
                        }
                        break;
        }

        return trigramme;
    }

    public static HashMap<Integer,ArrayList<String>> findSimilarWords(ArrayList<String> wordTrigrammes, Dictionary dictionary)
    {
        HashMap<String,Integer> wordsSharedTrigCount = new HashMap<String,Integer>();

        for(String wordTrig : wordTrigrammes)
        {
            if(dictionary.dictionaryByTrig.containsKey(wordTrig))
            {
                for(String similarWord : dictionary.dictionaryByTrig.get(wordTrig))
                {
                    if(wordsSharedTrigCount.containsKey(similarWord))
                    {
                        wordsSharedTrigCount.put(similarWord, wordsSharedTrigCount.get(similarWord)+1);
                    }
                    else
                    {
                        wordsSharedTrigCount.put(similarWord, 1);
                    }
                }
            }
        }

        HashMap<Integer,ArrayList<String>> wordBySharedTrigCount = new HashMap<Integer,ArrayList<String>>();

        Set wordSet = wordsSharedTrigCount.entrySet();
        Iterator wordIterator = wordSet.iterator();

        while(wordIterator.hasNext())
        {
            String currentWord = (String)wordIterator.next();
            if (wordBySharedTrigCount.containsKey(wordsSharedTrigCount.get(currentWord)))
            {
                wordBySharedTrigCount.get(wordsSharedTrigCount.get(currentWord)).add((String)currentWord);
            }
            else
            {
                ArrayList<String> addedWord = new ArrayList<String>();
                addedWord.add(currentWord);
                wordBySharedTrigCount.put(wordsSharedTrigCount.get(currentWord),addedWord);
            }
        }

        return wordBySharedTrigCount;
    }
}
