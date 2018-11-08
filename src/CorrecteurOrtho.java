import java.util.ArrayList;
import java.lang.String;
import java.util.HashMap;


public class CorrecteurOrtho
{
    Dictionary dictionary;
    public CorrecteurOrtho(Dictionary dictionary)
    {
        this.dictionary = dictionary;
    }

    public ArrayList<String> correction(String word)
    {
        ArrayList<String> wordTrigrammes = Trigrammes.findTrigrammes(word);

        // Mot Juste?
        if(dictionary.contains(word))
        {
            return null;
        }

        // Recherche de Mot similaire
        HashMap<Integer,ArrayList<String>> sharedTrigrammesCount = Trigrammes.findSimilarWords(wordTrigrammes, dictionary);

        // Selection de N mot similaire
        ArrayList<String> reallySimilarWords = descendingSelection(sharedTrigrammesCount,100, 30);

        // Calcul des distances d'edition
        HashMap<Integer,ArrayList<String>> wordByEditDist = Levenstein.sortByEditDist(word, reallySimilarWords);

        // Selection des 5 mots les plus proches
        ArrayList<String> possibleCorrection = ascendingSelection(wordByEditDist, 5, 10);

        return possibleCorrection;

    }

    private ArrayList<String> descendingSelection(HashMap<Integer,ArrayList<String>> mapToReduce, int valueMaxCount, int maxKeyValue)
    {
        ArrayList<String> selectedWord = new ArrayList<String>();
        int keyIterator = maxKeyValue;
        int selectedWordCount = 0;

        while((keyIterator>0) && (selectedWordCount < valueMaxCount))
        {
            if(!mapToReduce.containsKey(keyIterator))
            {
                keyIterator--;
                continue;
            }

            selectedWord.addAll(mapToReduce.get(keyIterator));
            selectedWordCount += mapToReduce.get(keyIterator).size();

            keyIterator--;
        }

        return selectedWord;
    }

    private ArrayList<String> ascendingSelection(HashMap<Integer,ArrayList<String>> mapToReduce, int valueMaxCount, int maxKeyValue)
    {
        ArrayList<String> selectedWord = new ArrayList<String>();
        int keyIterator = 0;
        int selectedWordCount = 0;

        while((keyIterator < maxKeyValue) && (selectedWordCount < valueMaxCount))
        {
            if(!mapToReduce.containsKey(keyIterator))
            {
                keyIterator++;
                continue;
            }

            selectedWord.addAll(mapToReduce.get(keyIterator));
            selectedWordCount += mapToReduce.get(keyIterator).size();

            keyIterator++;
        }

        return selectedWord;
    }

}
