import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class NaughtyGuy extends SpecialCharacter
{
    public void cheat()
    {
		//part 2: complete the code below
		MastermindWorld.addObject(MastermindWorld.secret.charAt(getX()), getX(), getY());
		setLocation(4, 8);
   }
    
    public void reveal()
    {
		//part 2: complete the code below
		for(int i=0; i<3; i++){
		    if((i != getX())&&(!MastermindWorld.hasSomePeg(i, getY()))){
		    MastermindWorld.addObject(MastermindWorld.secret.charAt(i), i, getY());
		  }
		}
		setLocation(4, 8);
    }
}
