import java.util.ArrayList;

public class App
{
    public static void main(String[] args)
    {
        double exeTime = System.nanoTime();
        ArrayList<String> trigrammes = Trigrammes.findTrigrammes("algorithmique");

        System.out.println((System.nanoTime()-exeTime)/1000000000);
    }
}
