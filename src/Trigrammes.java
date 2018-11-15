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

            case 2 :    trigramme.add(" " + word);
                        trigramme.add(" " + word);
                        break;

            default :   trigramme.add(" " + word.substring(0,1));
                        trigramme.add(word.substring(wordCharCount-2,wordCharCount-1) + " ");
                        for(int trigIterrator = 0; trigIterrator < (wordCharCount - 2); trigIterrator++)
                        {
                            trigramme.add(word.substring(trigIterrator, trigIterrator+2));
                        }
                        break;
        }

        return trigramme;
    }

}
