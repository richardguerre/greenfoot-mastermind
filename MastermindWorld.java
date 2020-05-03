import greenfoot.*;  
import java.util.Random;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class MastermindWorld extends GWorld
{
    //the dimensions of the game board
    public static final int WORLD_WIDTH = 7;
    public static final int WORLD_HEIGHT = 9;
    public static final int NUMBER_OF_PLAY_ROWS = WORLD_HEIGHT - 1;

    //the constants that represent different objects (or nothing) that present on the game board
    public static final char NOTHING = 'x';
    public static final char WALL = 'w';
    public static final char JUDGE = 'j';
    public static final char NICE_JUDGE = 'n';
    public static final char FRIEND_JUDGE = 'f';
    public static final char NAUGHTY_GUY = 'c';
    public static final char STRAWBERRY = 's';
    public static final char PEAR = 'p';
    public static final char APPLE = 'a';
    public static final char GRAPE = 'g';
    public static final char YELLOW_BALLOON = 'y';
    public static final char ORANGE_BALLOON = 'o';

    //the secret, which should be randomized by you at the beginning
    public static String secret = "";

    public static int highScore[] = {0, 0}; //you may use this in part 2, or you can ignore it and use your own variables

    static 
    {      
        GWorld.setWidth(WORLD_WIDTH);   
        GWorld.setHeight(WORLD_HEIGHT);
        GWorld.setCellSize(60);
    }

    public MastermindWorld() throws Exception
    {           
        initialize(); 
        generateSecret(); //you may comment out this line temporarily to keep the secret "pgs" for testing purpose
        showScore();
    }

    public static void initialize()
    {  
        //Initiliazation of the walls
        for(int r=0; r<WORLD_HEIGHT; r++){
            Wall newWall = new Wall();
            GWorld.addOneObject(newWall, 3, r);
        }
        for(int c=0; c<WORLD_HEIGHT; c++){
            Wall newWall = new Wall();
            GWorld.addOneObject(newWall, c, 8);
        }
        //intializations of the judges and naughty guy
        addObject(MastermindWorld.JUDGE, 0, 8);
        addObject(MastermindWorld.NICE_JUDGE, 1, 8);
        addObject(MastermindWorld.FRIEND_JUDGE, 2, 8);
        addObject(MastermindWorld.NAUGHTY_GUY, 4, 8);
    }

    public static void generateSecret(){
        char[] pegInitial = {'s', 'p', 'a', 'g'};
        int pegNum = 0;
        secret = "";
        for(int i=0; i<3; i++){
            pegNum = (int) (Math.random()*4); //Math.random gives values from 0 to 1, and (int) casts the double value into int
            char newPeg = pegInitial[pegNum];
            secret += newPeg;
        }
        System.out.println("The secret is "+secret);
    }

    public static void clearBalloons(int row)
    {  
        //part 1: complete the code below
        for(int i=0; i<3; i++) 
            removeBalloons(4+i, row);
    }

    public static void clearAllBalloons()
    {  
        //part 1: complete the code below
        for(int i=0; i<WORLD_HEIGHT-1; i++) 
            clearBalloons(i);
    }

    public static boolean hasSomePeg(int x, int y)
    {
        char c = MastermindWorld.getObjectChar(x, y);
        return  (c == MastermindWorld.STRAWBERRY) || (c == MastermindWorld.PEAR) || (c == MastermindWorld.APPLE) || (c == MastermindWorld.GRAPE);
    }

    public static boolean isRowComplete(int row)
    {
        boolean Allcomplete = false;
        boolean[] complete = {false, false, false};
        for(int i=0; i<3; i++){
            if(hasSomePeg(i,row)) complete[i] = true;
        }
        if(complete[0]&&complete[1]&&complete[2]) Allcomplete = true;
        return Allcomplete; 
    }

    public static int getLatestRow()
    {
        int max = -1;
        for(int i=0; i<8; i++){
            if(isRowComplete(i)&&i>max) max = i;
        }
        return max;
    }

    public static void saveGame() throws Exception
    {
        //part 2: complete the code below
        File savedFile = new File("save.txt");
        PrintWriter writer = new PrintWriter(savedFile);
        writer.print(MastermindWorld.secret);
        writer.print("\n");
        for(int c=0; c<7; c++){
            for(int r=0; r<9; r++){
                writer.print( MastermindWorld.getObjectChar(c, r) +" "+ c +" "+ r+"\n");
            }
        }
        writer.close();
    }

    public static void loadGame() throws Exception
    {
        //part 2: complete the code below
        removeAllNonWallObjects();
        File inFile = new File("save.txt");
        Scanner input = new Scanner(inFile);
        secret = input.next();
        System.out.println("The secret is " + secret);
        for(int i=0; i<64; i++){
            char pegChar = input.next().charAt(0);
            //System.out.println(pegChar + input.nextInt() + input.nextInt()); 
            MastermindWorld.addObject(pegChar, input.nextInt(), input.nextInt());
        }
        input.close();
        showScore();
    } 

    public static int calculateScore()
    {
        //part 2: complete the code below
        int numberOfYellowBalloons = 0;
        int numberOfOrangeBalloons = 0;
        int counter = 0;
        for(int i=0; i<9; i++){
            numberOfOrangeBalloons += Judge.getNumberOfOrangeBalloons(i);
            numberOfYellowBalloons += Judge.getNumberOfYellowBalloons(i);
            if(isRowComplete(i)) counter++;
        }
        //System.out.println(counter);
        if(counter != 0){
            int score = (numberOfOrangeBalloons*2 + numberOfYellowBalloons)*3360/counter;
            //System.out.println(score);
            return score;
        }
        else{ return 0; }
    }

    public static void updateHighscores() throws Exception
    {
        //part 2: complete the code below
        File inputFile = new File("highscore.txt");
        Scanner in = new Scanner(inputFile);
        
        highScore[0] = in.nextInt();
        highScore[1] = in.nextInt();
        in.close();
        
        System.out.printf("The highscores were: %5d and %5d%n", highScore[0], highScore[1]);
        
        int CurrentScore = calculateScore();
        if(calculateScore() >= highScore[0]){
            highScore[1] = highScore[0];
            highScore[0] = CurrentScore;
        }
        else if(calculateScore() >= highScore[1]){
            highScore[1] = CurrentScore;
        }
        
        PrintWriter out = new PrintWriter(inputFile);
        out.printf("%1d %1d%n", highScore[0], highScore[1]);
        out.close();
        System.out.printf("The new highscores are: %5d and %5d%n", highScore[0], highScore[1]);
    }
    

    /*
     * Parameters:
     * lowerBound - the (inclusive) lower bound of the random number
     * upperBound - the (inclusive) upper bound of the random number
     * 
     * Example:
     * getRandomNumber(1, 10) will return you a random number that is in range of [1, 10] (including 1 and 10).
     */
    public static int getRandomNumber(int lowerBound, int upperBound)
    {
        Random randomNumberGenerator = new Random();
        return lowerBound + randomNumberGenerator.nextInt(upperBound - lowerBound + 1);
    }

    /*
     * Parameters:
     * objectChar - a character that represents the object that you want to add; you are suggested to use the constants defined in this class, e.g., MastermindWorld.JUDGE
     * x - the x coordinate of the object to be placed (the top-left corner of the world is (0, 0)
     * y - the y coordinate
     * 
     * Example:
     * MastermindWorld.addObject(MastermindWorld.NICE_JUDGE, 2, 3); 
     * will create and place a NiceJudge object to the location (2,3) in the world
     */
    public static void addObject(char objectChar, int x, int y)
    {
        //create the object base on the objectChar
        Actor object = null;
        switch(objectChar)
        {
            case MastermindWorld.JUDGE: 
            object = new Judge();
            break;
            case MastermindWorld.NICE_JUDGE: 
            object = new NiceJudge();
            break;
            case MastermindWorld.FRIEND_JUDGE: 
            object = new FriendJudge();
            break;
            case MastermindWorld.NAUGHTY_GUY: 
            object = new NaughtyGuy();
            break;
            case MastermindWorld.STRAWBERRY: 
            object = new Strawberry();
            break;
            case MastermindWorld.PEAR: 
            object = new Pear();
            break;
            case MastermindWorld.APPLE: 
            object = new Apple();
            break;
            case MastermindWorld.GRAPE: 
            object = new Grape();
            break;
            case MastermindWorld.YELLOW_BALLOON: 
            object = new YellowBalloon();
            break;
            case MastermindWorld.ORANGE_BALLOON: 
            object = new OrangeBalloon();
            break;
            case MastermindWorld.WALL: 
            object = new Wall();
            break;
            default:
            return; //just exit the method if nothing should be added

        }   
        GWorld.addOneObject(object, x, y); //add the object to the world
    }

    /*
     * Parameters:
     * x - the x coordinate of the location that you want to check
     * y - the y coordinate
     * 
     * Example:
     * MastermindWorld.getObjectChar(2, 3) will return a character that represents the object that is located at the location (2,3) in the world.
     * If there is a Pear object there at location (2,3), then the character MastermindWorld.PEAR (which is defined as 'p') will be returned.
     * 
     * If there is nothing at the location, then the character MastermindWorld.NOTHING (which is defined as 'x') will be returned.
     * 
     * We assume that there is always at most one object at each location, except for the case of a Judge/NaughtyGuy sitting on a Wall. 
     * For such case, only the Judge/NaughtyGuy will be returned as the result.
     */
    public static char getObjectChar(int x, int y)
    {
        //return the character that represents the object at (x, y)
        if(GWorld.getOneObjectAt(x, y, "NiceJudge") != null) return MastermindWorld.NICE_JUDGE;
        else if(GWorld.getOneObjectAt(x, y, "FriendJudge") != null) return MastermindWorld.FRIEND_JUDGE;
        else if(GWorld.getOneObjectAt(x, y, "Judge") != null) return MastermindWorld.JUDGE;
        else if(GWorld.getOneObjectAt(x, y, "NaughtyGuy") != null) return MastermindWorld.NAUGHTY_GUY;
        else if(GWorld.getOneObjectAt(x, y, "Strawberry") != null) return MastermindWorld.STRAWBERRY;
        else if(GWorld.getOneObjectAt(x, y, "Pear") != null) return MastermindWorld.PEAR;
        else if(GWorld.getOneObjectAt(x, y, "Apple") != null) return MastermindWorld.APPLE;
        else if(GWorld.getOneObjectAt(x, y, "Grape") != null) return MastermindWorld.GRAPE;
        else if(GWorld.getOneObjectAt(x, y, "YellowBalloon") != null) return MastermindWorld.YELLOW_BALLOON;
        else if(GWorld.getOneObjectAt(x, y, "OrangeBalloon") != null) return MastermindWorld.ORANGE_BALLOON;
        else if(GWorld.getOneObjectAt(x, y, "Wall") != null) return MastermindWorld.WALL;
        else return MastermindWorld.NOTHING;
    }

    /*
     * Parameters:
     * x - the x coordinate
     * y - the y coordinate
     * 
     * Example:
     * Calling removeBaloons(2, 3) will remove any balloon at the position (2,3). 
     * It is safe to call the method even if the specified position has nothing in it - it just does nothing in that case.
     * 
     * It is able to remove mutliple balloons that exist at the same position at once.
     */
    public static void removeBalloons(int x, int y)
    {
        removeObjectsFromWorld(getAllObjectsAt(x, y, "Balloon"));
    }

    /*
     * Example:
     * Calling removeAllObjects() will remove all objects in the world. It should be used at the beginning of the initialize method.
     */
    private static void removeAllObjects()
    {   
        removeAllNonWallObjects();
        GWorld.removeObjectsFromWorld(GWorld.getAllObjects("Wall"));
    }

    /*
     * Example:
     * Calling removeAllNonWallObjects() will remove all objects that are not walls in the world. It should be used at the beginning of the loadGame method.
     */
    private static void removeAllNonWallObjects()
    {   
        GWorld.removeObjectsFromWorld(GWorld.getAllObjects("Peg"));
        GWorld.removeObjectsFromWorld(GWorld.getAllObjects("SpecialCharacter"));
        GWorld.removeObjectsFromWorld(GWorld.getAllObjects("Balloon"));
    }

    /*
     * It puts a text "Score: x" near the NaughtyGuy at the bottom, where x is the score returned by your calculateScore() method.
     */
    public static void showScore()
    {
        int score = calculateScore();

        GWorld.removeObjectsFromWorld(GWorld.getAllObjects("Score"));
        Score s = new Score(score);
        GWorld.addOneObject(s, WORLD_WIDTH-2, WORLD_HEIGHT-1);
    }
}
