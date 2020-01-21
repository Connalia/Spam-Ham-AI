import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class NaiveBayesWithTree {

	public static void main(String[] args) throws FileNotFoundException {
		
		long start1 = System.currentTimeMillis();
    	int i = 1;
    	//String startname = "part1/spm";
    	int numberOfSpam = 0;
    	int numberOfHam = 0;
    	
        File dir = new File("datasets");
        Map<String, Integer> wordCountHam = new TreeMap<String, Integer>();
        if(!dir.isDirectory()) throw new IllegalStateException("Not a directory");
        for(File file : dir.listFiles()) {
            if(!file.getName().startsWith("spm")) {
            	numberOfHam++;
            	Scanner input = new Scanner(file);
                while (input.hasNext()) {
                	
                    String next = input.next().toLowerCase();

                    	if (!wordCountHam.containsKey(next)) {
                        	wordCountHam.put(next, 1);
                        } else {
                        	wordCountHam.put(next, wordCountHam.get(next) + 1);
                        }
                    
                    
                    
                    
                }
                input.close();
            }
                
        }
        long start2 = System.currentTimeMillis();
        System.out.println("I read " + numberOfHam  + " ham emails");
        System.out.println("Time to read HamFiles:  " + (double)(start2 - start1) / 1000 + " sec.");
        
        Map<String, Integer> wordCountSpam = new TreeMap<String, Integer>();
        if(!dir.isDirectory()) throw new IllegalStateException("Not a directory");
        for(File file : dir.listFiles()) {
            if(file.getName().startsWith("spm")) {
            	numberOfSpam++;
            	Scanner input = new Scanner(file);
                while (input.hasNext()) {
                    String next = input.next().toLowerCase();

                    	if (!wordCountSpam.containsKey(next)) {
                        	wordCountSpam.put(next, 2);
                        } else {
                        	wordCountSpam.put(next, wordCountSpam.get(next) + 2);
                        }
                    
                    
                    
                }
                input.close();
            }
                
        }
        
        
        
        long start3 = System.currentTimeMillis();
        System.out.println("I read " + numberOfSpam  + " spam emails");
        System.out.println("Time to read SpamFiles:  " + (double)(start3 - start2) / 1000 + " sec.");
        
        // START OF TESTING
        
        int RealSpamCounter = 0;
		int RealHamCounter = 0;
		int errors = 0;
		//boolean flag = true;
	    File dir2 = new File("testing");
	    //File test = new File("test.txt");
	    //Map<String, Integer> wordCounts = new TreeMap<String, Integer>();
	    if(!dir2.isDirectory()) throw new IllegalStateException("Not a directory");
	    for(File file : dir2.listFiles()) {
	    	long start = System.currentTimeMillis();
	        if(file.getName().startsWith("spm")) {
	        	RealSpamCounter++;
	        }else {
	        	RealHamCounter++;
	        }
	        	Scanner input = new Scanner(file);
	        	
	        	double spamminessOfMessage = 1;
	        	double hamminessOfMessage = 1;
	        	
	        	Map<String,Integer> unique = new HashMap<String,Integer>();
	            
	        	while (input.hasNext()) {
	        		String next = input.next().toLowerCase();	
	        			if ( !unique.containsKey(next)) {
	        				
	        				unique.put(next, 1);
	        			
			            	double spamminessOfToken = 0;
			            	double cSpam = 0;
			            	double cHam = 0;
			                                
		                	//Poses fores periexetai se spam kai ham
			                if (wordCountSpam.containsKey(next)) {
			                	cSpam = wordCountSpam.get(next);
			                }
			                if (wordCountHam.containsKey(next)) {
			                	cHam = wordCountHam.get(next);
			                }
			               
			                //Pithanotita leksis na einai (spam leksi)	
		                	spamminessOfToken =  (cSpam+1)/(cSpam+cHam+2);
		                	//Pithanotita na einai spam to email me kanona pollaplasiasmou (Me basi tis lekseis pou exw metrisei mexri twra)
	                    	spamminessOfMessage = spamminessOfMessage*spamminessOfToken;
	                    	//Pithanotita na einai ham to email me kanona pollaplasiasmou (Me basi tis lekseis pou exw metrisei mexri twra)
	                    	hamminessOfMessage = hamminessOfMessage*(1 - spamminessOfToken);
	                    	
	                    	
	                    	//Oliki pithanotita na einai spam
	        	            spamminessOfMessage = spamminessOfMessage/(spamminessOfMessage+hamminessOfMessage);
	        	            //Oliki pithanotita na einai ham
	                    	hamminessOfMessage = hamminessOfMessage/(hamminessOfMessage+spamminessOfMessage);
		                    	
	        			}
        			}
	            

	            input.close();

	            //System.out.println(spamminessOfMessage);
	            if ( (double) (spamminessOfMessage - hamminessOfMessage) >= 0.5 ) {
	            	//METRAEI TA SPAMS
	            	errors++;
	            	//System.out.println("TO FILE: " + file.getName() + " EINAI SPAM!!!!!!!");
	            }
	            
	            
	            long end = System.currentTimeMillis();
	            //System.out.println("To file: " + file.getName() + " ekane "  + (double)(end - start) / 1000 + " sec.");
	    }
	    long finalend = System.currentTimeMillis();
	    //System.out.println("To tree ekane: " +  (double)(finalend - start3) / 1000 + " sec.");
	    System.out.println("Ta spam files einai: " + RealSpamCounter);
	    System.out.println("Ta ham files einai: " + RealHamCounter);
	    
	    if(errors == RealSpamCounter) System.out.println("SWSTA");
	    else System.out.println("EXASE " + (RealSpamCounter - errors));
	    
	    System.out.println("Pithanotita Epityxias: " +  ((double) errors/(double)RealSpamCounter)*100+"%");
	    
	}
}
