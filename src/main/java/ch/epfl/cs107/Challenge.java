
package ch.epfl.cs107;

import ch.epfl.cs107.crypto.Decrypt;
import ch.epfl.cs107.stegano.TextSteganography;
import ch.epfl.cs107.utils.Text;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * <b>06</b> Provided for you to attempt the challenge in
 *
 * @author Mehdi ALAOUI (mehdi.alaoui@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public class Challenge {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Challenge(){}

    // ============================================================================================
    // ======================================== CHALLENGE =========================================
    // ============================================================================================
    public static void main(String[] args){
        challenge() ;
        String s = "BKDMEPHEZL GV V V T E Z W X B A F D L N T O L V Q L R W D O N S G I J P W J C W S O T V X F U I D Y F S H B K Q D P F B H U C C M Q T D G O K R Y F E S N B T B I C P D B R W N Q R E S E Z I U" ;
    }




    /**
     * Solves the challenge
     *
     * @return the flag in the format FLAG{********}
     */
    public static int getMax(int[] frequencies){
        int idx = 0 ;
        int max = 0 ;
        for(int i = 0 ; i < 26 ; ++i){
            if(frequencies[i]>max) {
                max = frequencies[i] ;
                idx = i ;
            }

        }
        return idx ;
    }

    public static int[] getFrequencies(String message){
        int[] frequencies = new int[26] ;
        for(char c : message.toCharArray()) frequencies[c - 'A'] += 1 ;
        return frequencies ;
    }
    public static String sortChars(String message){
        int[] frequencies = getFrequencies(message) ;
        String s = "" ;
        for(int i = 0 ; i < 26 ; ++i){
            int idx = getMax(frequencies) ;
            s += (char) (idx + 'A') ;
            frequencies[idx] = 0 ;
        }

        return s ;
    }

//    public static String decrypt(String message){
//        String key =
//    }
    public static String challenge(){
        String frequencyOrder = "ETAOINSHRDLCUMWFGYPBVKJXQZ";
        byte[] key = Text.toBytes(frequencyOrder) ;
        int[][] image1 = Helper.readImage("C:\\Users\\LEGION\\AppData\\Local\\Temp\\75ab30c0-82a8-4d60-8414-0a4adad2e2a4_crypto-stegano.zip.crypto-stegano.zip\\MP1-2025\\src\\main\\resources\\challenge\\image1.png")  ;


        String message1 = Text.toString(TextSteganography.revealText(image1)) ;
        String onlyEnUpp = message1.replaceAll("[^A-Z]", "");; ;
        System.out.println(onlyEnUpp);
        System.out.println(sortChars(onlyEnUpp));

//
        return message1 ;
    }
}
