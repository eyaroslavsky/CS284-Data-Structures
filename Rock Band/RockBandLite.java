
  package assign3;
  import cos126.StdDraw;
  import cos126.StdAudio;

  public class RockBandLite {
      public static void main(String[] args) {

          // create two guitar strings, for concert A and C
          double CONCERT_A = 440.0;
          double CONCERT_C = CONCERT_A * Math.pow(2, 3.0/12.0);  
          GuitarString stringA = new GuitarString(CONCERT_A);
          GuitarString stringC = new GuitarString(CONCERT_C);
	  /* PianoString stringS = new PianoString(CONCERT_A);
	  PianoString stringD = new PianoString(CONCERT_C);
	  DrumString stringZ = new DrumString(CONCERT_A);
	  DrumString stringX = new DrumString(CONCERT_C);*/

          while (true) {
              // check if the user has typed a key; if so, process it   
              if (StdDraw.hasNextKeyTyped()) {
                  char key = StdDraw.nextKeyTyped();
                  if      (key == 'a') { stringA.pluck(); }
                  else if (key == 'c') { stringC.pluck(); }
		  /*  else if (key == 's') { stringS.pluck(); }
		  else if (key == 'd') { stringD.pluck(); }
		  else if (key == 'z') { stringZ.pluck(); }
		  else if (key == 'x') { stringX.pluck(); }*/
              }

              // compute the superposition of samples
              double sample = stringA.sample() + stringC.sample();
	      /* double sample2 = stringS.sample() + stringD.sample();
		 double sample3 = stringZ.sample() + stringX.sample();*/
  
              // send the result to the sound card
              StdAudio.play(sample);
	      /* StdAudio.play(sample2);
		 StdAudio.play(sample3);*/
  
              // advance the simulation of each guitar string by one step   
              stringA.tic();
              stringC.tic();
	      /* stringS.tic();
	      stringD.tic();
	      stringZ.tic();
	      stringX.tic();*/
          }
       }
  }
