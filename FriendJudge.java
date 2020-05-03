import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class FriendJudge extends Judge
{
    public void judgeRow(int row)
    {
        System.out.println("FriendJudge looks at row " + row); //keep this line
        
        MastermindWorld.clearBalloons(row); //keep this line
        
        //part 2: complete the code below
        int yellow = getNumberOfYellowBalloons(row);
        int orange = getNumberOfOrangeBalloons(row);
        yellow -= orange;
        
        for(int i=0; i<3; i++){
            if(MastermindWorld.getObjectChar(i, row) == MastermindWorld.secret.charAt(i)){
                MastermindWorld.addObject(MastermindWorld.ORANGE_BALLOON, i+4, row);
                yellow--;
            }
            else if(yellow>0){
                MastermindWorld.addObject(MastermindWorld.YELLOW_BALLOON, i+4, row);
                yellow--;
            }
        }
        

    }
}
