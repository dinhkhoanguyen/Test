package Audio;

//import java.io.BufferedReader;
import java.io.IOException;

public class Test {
	public static void main(final String[] arg) throws IOException /*, InterruptedException */
	{
		AudioMaster.init();
		AudioMaster.setLisnerData();

		int buffer = AudioMaster.loadSound("Audio/Clubz123.wav");
		Source source = new Source();
		//source.setLooping(true);
		source.play(buffer);
		
		char c = ' ';
		while(c != 'q'){
			c = (char)System.in.read();
			
			if(c == 'p'){
				source.play(buffer);
			}
		}
		source.delete();
		AudioMaster.cleanUp();
	}
}
