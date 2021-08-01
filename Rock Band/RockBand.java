/*
 * RockBand.java
 *
 */

package assign3;
import cos126.StdDraw;
import cos126.StdAudio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class RockBand {


    static String guitarBassKeyboard ="`1234567890-=qwertyuiop[]\\asdfghjkl;'";
    static String pianoKeyboard = "~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"";
    static String drumKeyboard = "ZXCVBNM<>?zxcvbnm,.";
  
    public static void main(String[] args) {
	Guitar guitar = new Guitar(37);
	Piano piano = new Piano(37);
	Bass bass = new Bass(37);
	Drum drum = new Drum(19);
	Instrument[] instruments = {guitar,piano,bass,drum};

	boolean lowMode = false;
	int index;

	if (args != null && args.length == 2 && args[0].equals("-play_from_file")) {
	    File txt = new File(args[1]);
	    Scanner in = null;

	    try {
		in = new Scanner(txt);
	    }
	    catch(Exception e) {
		e.printStackTrace();
		System.exit(0);
	    }
	    
	    int speed = 100;
	    // int skip = 0;

	    in.useDelimiter(" |\\n");
	    
	    while (in.hasNext()) {
		String input = in.next();
		index = -1;

		if (input.equals("/")) {
		    for(int x = 0; x < speed; x++){
			double sum = guitar.ringNotes() + piano.ringNotes() + drum.ringNotes() + bass.ringNotes();
			StdAudio.play(sum);
		    }
		}
		int skip = 0;

		for (int y = 0; y < input.length(); y++) {
		    char char1 = input.charAt(y);
		    if (y < input.length()-1) {
			char char2 = input.charAt(y+1);
			if (char1 == '#' && char2 == '#') {
			    boolean finish = false;
			    for (int z = y+2; z < input.length(); z++) {
				if (input.charAt(z) < 48 || input.charAt(z) > 57) {
				    finish = true;
				    speed = Integer.parseInt(input.substring(y+2));
				}
				if (finish == false) {
				    skip = input.length() - y + 1;
				}
			    }
			}
			else if (char1 == '@' && char2 == '@') {
			    System.out.println(input.substring(y+2));
			    skip = input.length();
			}
			else if (char1 == 'L' && char2 == 'L') {
			    lowMode = true;
			    skip = 2;
			}
		    }

		    if (char1 == '/') {
			lowMode = false;
			skip = 1;
		    }

		    if (skip == 0) {
			if (guitarBassKeyboard.indexOf(char1) > -1) {
			    index = guitarBassKeyboard.indexOf(char1);
			    if (!lowMode) { guitar.playNote(index); }
			    else { bass.playNote(index); }
			}
			else if (pianoKeyboard.indexOf(char1) > -1) {
			    index = pianoKeyboard.indexOf(char1);
			    piano.playNote(index);
			}
			else if (drumKeyboard.indexOf(char1) > -1) {
			    index = drumKeyboard.indexOf(char1);
			    drum.playNote(index);
			}
		    }
		    else {
			skip--;
		    }
		    for (int a = 0; a < speed; a++) {
			double sum = guitar.ringNotes() + piano.ringNotes() + drum.ringNotes() + bass.ringNotes();
			StdAudio.play(sum);
		    }
		}
	    }
	}
	else {
	    while (true) {
		if (StdDraw.hasNextKeyTyped()) {
		    char key = StdDraw.nextKeyTyped();
		    if (key == '\n' && !lowMode) { lowMode = true; }
		    else if (key == '\n' && lowMode) { lowMode = false; }
		  
		    if (guitarBassKeyboard.indexOf(key) > -1 && !lowMode) {
			int guitarIndex = guitarBassKeyboard.indexOf(key);
			instruments[0].playNote(guitarIndex);
		    }
		    else if (pianoKeyboard.indexOf(key) > -1) {
			int pianoIndex = pianoKeyboard.indexOf(key);
			instruments[1].playNote(pianoIndex);		  }
		    else if (drumKeyboard.indexOf(key) > -1) {
			int drumIndex = drumKeyboard.indexOf(key);
			instruments[3].playNote(drumIndex);
		    }
		    else if (guitarBassKeyboard.indexOf(key) > -1 && lowMode) {
			int bassIndex = guitarBassKeyboard.indexOf(key);
			instruments[2].playNote(bassIndex);
		    }
		}
		double sum = guitar.ringNotes() + piano.ringNotes() + drum.ringNotes() + bass.ringNotes();
		StdAudio.play(sum);
	    }
	}
    }
}
