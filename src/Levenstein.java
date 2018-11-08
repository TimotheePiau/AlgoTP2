import java.util.ArrayList;
import java.util.HashMap;

public class Levenstein
{
    public static int distanceLevenshtein(String mot1, String mot2)
    {
        int word1CharCount = mot1.length()+1;
        int word2CharCount = mot2.length()+1;

        int decomposedDistanceMatrix[][] = new int[word1CharCount][word2CharCount];


        for(int lineIndex = 0; lineIndex < word1CharCount; lineIndex++)
        {
            decomposedDistanceMatrix[lineIndex][0] = lineIndex;
        }

        for(int rowIndex = 1; rowIndex < word2CharCount; rowIndex++)
        {
            decomposedDistanceMatrix[0][rowIndex] = rowIndex;
        }

        for(int lineIndex = 1; lineIndex < word1CharCount; lineIndex++)
        {
            for (int rowIndex = 1; rowIndex < word2CharCount; rowIndex++)
            {
                decomposedDistanceMatrix[lineIndex][rowIndex] = -2;
            }
        }


        for(int lineIndex = 1; lineIndex < word1CharCount; lineIndex++)
        {
            for (int rowIndex = 1; rowIndex < word2CharCount; rowIndex++)
            {
                String sousmot1 = subwordConstruct(mot1, lineIndex);
                String sousmot2 = subwordConstruct(mot2, rowIndex);
                decomposedDistanceMatrix[lineIndex][rowIndex] = subdistLevenshtein(sousmot1, sousmot2, decomposedDistanceMatrix);
            }
        }

        //printMatrix(decomposedDistanceMatrix);
        return decomposedDistanceMatrix[word1CharCount-1][word2CharCount-1];
    }

    // Substring!!!
    public static String subwordConstruct(String mot, int subLength)
    {
        String sousmot = "";
        for(int wordIndex = 0; wordIndex < subLength; wordIndex++)
        {
            sousmot += mot.charAt(wordIndex);
        }
        return sousmot;
    }

    public static int subdistLevenshtein(String mot1, String mot2, int[][] decomposedDistanceMatrix)
    {
        int word1CharCount = mot1.length();
        int word2CharCount = mot2.length();
        if(mot1.charAt(mot1.length()-1) == mot2.charAt(mot2.length()-1))
        {
            return decomposedDistanceMatrix[word1CharCount-1][word2CharCount-1];
        }
        else
        {
            int actualMin = decomposedDistanceMatrix[word1CharCount-1][word2CharCount-1];
            if(actualMin > decomposedDistanceMatrix[word1CharCount][word2CharCount-1])
            {
                actualMin = decomposedDistanceMatrix[word1CharCount][word2CharCount-1];
            }
            else if(actualMin > decomposedDistanceMatrix[word1CharCount-1][word2CharCount])
            {
                actualMin = decomposedDistanceMatrix[word1CharCount-1][word2CharCount];
            }
            return actualMin+1;
        }
    }

    public static HashMap<Integer,ArrayList<String>> sortByEditDist(String word, ArrayList<String> reallySimilarWords)
    {
        HashMap<Integer,ArrayList<String>> wordByEditDist = new HashMap<Integer,ArrayList<String>>();
        for(String testedWord : reallySimilarWords)
        {
            int editDist = distanceLevenshtein(word, testedWord);
            if(wordByEditDist.containsKey(editDist))
            {
                wordByEditDist.get(editDist).add(testedWord);
            }
            else
            {
                ArrayList<String> addedWord = new ArrayList<String>();
                addedWord.add(testedWord);
                wordByEditDist.put(editDist, addedWord);
            }
        }
        return wordByEditDist;
    }

    public void printMatrix(int[][] matrix)
    {
        for(int lineIndex = 0; lineIndex < matrix.length; lineIndex++)
        {
            for(int rowIndex = 0; rowIndex < matrix[0].length; rowIndex++)
            {
                System.out.print(matrix[lineIndex][rowIndex] + " ");
            }
            System.out.println();
        }
    }
}
