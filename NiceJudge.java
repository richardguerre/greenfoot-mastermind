import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class NiceJudge extends Judge
{
    public void judgeRow(int row)
    {
        System.out.println("NiceJudge looks at row " + row); //keep this line
        
        MastermindWorld.clearBalloons(row); //keep this line
        
        //part 2: complete the code below
        int yellow = getNumberOfYellowBalloons(row);
        int orange = getNumberOfOrangeBalloons(row);
        yellow -= orange;
        
        for(int p=0; p<orange; p++){
            MastermindWorld.addObject(MastermindWorld.ORANGE_BALLOON, 4+p, row);
        }
        for(int p=0; p<yellow; p++){
            MastermindWorld.addObject(MastermindWorld.YELLOW_BALLOON, 4+orange+p, row);
        }
    }
}
