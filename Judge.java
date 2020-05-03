import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.*;

public class Judge extends SpecialCharacter
{ 
    public void judgeRow(int row)
    {
        System.out.println("Judge looks at row " + row); //keep this line
        
        MastermindWorld.clearBalloons(row); //keep this line
        
        //part 1: complete the code below
        int howMany = 0;
        for(int i=0; i<3; i++){
            if(MastermindWorld.getObjectChar(i, row) == MastermindWorld.secret.charAt(i)) 
                howMany++;   
        }
        //System.out.println(howMany + " are correct");
        for(int p=0; p<howMany; p++){
            MastermindWorld.addObject(MastermindWorld.ORANGE_BALLOON, 4+p, row);
        }
        MastermindWorld.showScore();
    }   
    
    public void judgeLatestRow()
    {
        //part 1: complete the code below
        judgeRow(MastermindWorld.getLatestRow());
    }

    public void judgeAllRows()
    {
        //part 1: complete the code below
        for(int i=0; i<8; i++){
            if(MastermindWorld.isRowComplete(i)) judgeRow(i);
        }
    }
    
    public static int getNumberOfSpecificPegsInAnswer(char type, int row)
    {
        //part 2: complete the code below
        int counter = 0;
        for(int c=0; c<3; c++){
            if(type == MastermindWorld.getObjectChar(c, row)) counter++;
        }
        return counter; //you need to change this line}
   }

    public static int getNumberOfSpecificPegsInSecret(char type)
    {
        //part 2: complete the code below
        int counter = 0;
        for(int i=0; i<3; i++){
            if(type == MastermindWorld.secret.charAt(i)) counter++;
        }
        return counter; //you need to change this line
    }
    
    public static int getNumberOfOrangeBalloons(int row){
        int howMany = 0;
        for(int i=0; i<3; i++){
            if(MastermindWorld.getObjectChar(i, row) == MastermindWorld.secret.charAt(i)) 
                howMany++;   
        }
        return howMany;
    }
    
    /*
     * It returns the number of yellow balloons, assuming no orange balloons would be awarded. Read assignment description on how to make use of it.
     * It is used in part 2 only.
     */
    public static int getNumberOfYellowBalloons(int row)
    {
        return Math.min(getNumberOfSpecificPegsInSecret('g'), getNumberOfSpecificPegsInAnswer('g', row))
            + Math.min(getNumberOfSpecificPegsInSecret('s'), getNumberOfSpecificPegsInAnswer('s', row))
            + Math.min(getNumberOfSpecificPegsInSecret('a'), getNumberOfSpecificPegsInAnswer('a', row))
            + Math.min(getNumberOfSpecificPegsInSecret('p'), getNumberOfSpecificPegsInAnswer('p', row));
    }
}
