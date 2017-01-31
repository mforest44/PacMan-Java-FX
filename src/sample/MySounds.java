package sample;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class MySounds {

	protected static final String AudioPlayer = null;
	
	public Clip PacBeginning  = loadClip("/Sounds/pacman_beginning.wav");
	public Clip PacChomp      = loadClip("/Sounds/pacman_chomp.wav");
	public Clip PacGhostEat   = loadClip("/Sounds/pacman_eatghost.wav");
	public Clip PacDeath      = loadClip("/Sounds/pacman_death.wav");

	public MySounds() {
		
	}

	public Clip loadClip(String filename)
	{
		Clip clip = null;
		
		try
		{
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(filename));
			clip = AudioSystem.getClip();
			clip.open( audioIn );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return(clip);
		
	}
	

	public void playClip( int index )
	{
		
		if (index == 1)
		   {
		   stopClip(1);
		   PacBeginning.start();
		   }
		else
			if (index == 2)
			   {
			   if (!PacChomp.isRunning())
			      {
			      stopClip(2);
			      PacChomp.start();
			      }
			   }
			else if(index == 3) {
				if (!PacGhostEat.isRunning()) {
					stopClip(3);
					PacGhostEat.start();
				}
				PacGhostEat.setFramePosition(0);
			}
			else if(index == 4) {
				if (!PacDeath.isRunning()) {
					PacDeath.start();
				}
				PacDeath.setFramePosition(0);
				
			}

	}
	
	public void stopClip( int index )
	{
		if (index == 1)
		   {
		   if (PacBeginning.isRunning() )
			   PacBeginning.stop();
		   PacBeginning.setFramePosition(0);
		   }
		else if (index == 2)
		   {
		   if (PacChomp.isRunning() )
			   PacChomp.stop();
		   PacChomp.setFramePosition(0);
		   }
	}
	
}
