import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Evan on 8/5/2014.
 */
public class CardDeck {

    //This is just to prove that git is working
    private int topCard;
    private int deckSize;
    private int[] deck;
    ArrayList<Integer> unusedCards = new ArrayList<Integer>();
    String folderName;
    String filePathSlash;


    public CardDeck(int numberOfCards, String folderLocation)
    {
        setFilePathSlash();
        folderName = folderLocation;
        topCard = 0;
        deckSize = numberOfCards;
        deck = new int[deckSize];
        unusedCards.ensureCapacity(deckSize);
        loadDeck();
        randomizeDeck();
    }

    //This method generates a new deck array
    public void randomizeDeck()
    {
        int randomNumber = 0;
        Arrays.fill(deck, 0);
        for(int i = 0; i < deckSize; i++)
        {
            randomNumber = randomInt();
            if(checkDeckForDupes(randomNumber) == false)
            {
                deck[i] = randomNumber;
            } else {
                i--;
            }
        }
        topCard = 0;
        if(unusedCardsContains(deck[topCard]))
            topCardIncrement();
    }


    //This method is used when randomly assigning array values to make sure the loop has no duplicates
    private boolean checkDeckForDupes(int testNumber)
    {
        for(int i = 0; i < deckSize; i++)
        {
            if(deck[i] == testNumber)
                return true;
        }
        return false;
    }


    //This method is used to return the file path of the "top" card of the deck
    public String displayTopCard()
    {

        File[] folder = new File(folderName + "/").listFiles();
        for(File file : folder)
        {
            if (file.getName().contains(Integer.toString(deck[topCard]))) {
                return new String(folderName + filePathSlash + file.getName());
            }
        }
        return "No File Found";
    }

    public void removeFromDeck(int addedCard)
    {
        unusedCards.add(addedCard);
    }

    public void addToDeck(int removedCard)
    {
        unusedCards.set(getIndex(removedCard), null);
    }

//    public boolean isNotAllowed(int value)
//    {
//        if(unusedCards.contains(value))
//            return true;
//        else
//            return false;
//    }

    //This method returns the path of the image at the number passed to it.
    public String getImagePath(int requestedImage)
    {
        File[] folder = new File(folderName + filePathSlash).listFiles();
        for(File file : folder)
        {
            if (file.getName().contains(Integer.toString(requestedImage))) {
                return new String(folderName + filePathSlash + file.getName());
            }
        }
        return "No File Found";
    }

    public void topCardIncrement()
    {
        if(topCard == (deckSize - 1))
        {
            topCard = 0;
        } else {
            topCard++;
        }

        if(unusedCardsContains(deck[topCard]))
            topCardIncrement();
    }

    public void topCardDecrement()
    {
        System.out.println("topCardIncrement");
        if(topCard == 0)
        {
            topCard = (deckSize - 1);
        } else {
            topCard--;
        }

        if(unusedCardsContains(deck[topCard]))
            topCardDecrement();
    }

    public int getIndex(int value)
    {
        for(int i = 0; i < unusedCards.size(); i++)
        {
            if(value == unusedCards.get(i))
            {
                return i;
            }
        }
        return 0;
    }


    public int getDeckSize()
    {
        return deckSize;
    }

    public String getFolderName()
    {
        return folderName;
    }

    public String getFilePathSlash()
    {
        return filePathSlash;
    }

    public boolean unusedCardsContains(int testedValue)
    {
        if(unusedCards.contains(testedValue))
            return true;
        else
            return false;

    }

    public void saveDeck()
    {
        File file = new File(folderName + ".txt");
        try
        {
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < unusedCards.size(); i++)
            {
                if(unusedCards.get(i) != null)
                {
                    output.write(unusedCards.get(i).toString());
                    System.out.println("print statement");
                    output.write("\n");
                }
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDeck()
    {
        File file = new File(folderName + ".txt");
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String inputString;
            while((inputString = input.readLine()) != null)
            {
                unusedCards.add(Integer.parseInt(inputString));
            }
            input.close();
        } catch (IOException e) {

        }

    }

    private int randomInt()
    {
        int min = 1;
        int max = deckSize;
        Random rand = new Random();

        int randomNum = rand.nextInt((max-min) + 1) + min;

        return randomNum;
    }

    //This sets the direction of slashes for file paths
    private void setFilePathSlash()
    {
        String OSName = System.getProperty("os.name");
        if(OSName.contains("Mac"))
            filePathSlash = "/";
        else
            filePathSlash = "\\";
    }

}
