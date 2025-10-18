
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
    }




    /**
     * Solves the challenge
     *
     * @return the flag in the format FLAG{********}
     */
    public static int[] getfrequencies(String message){
        int[] frequencies = new int[26] ;
        for(char c : message.toCharArray()){
            frequencies[c-'A'] +=1 ;
        }
        return frequencies ;
    }
//    public static byte[] sortfrequencies(int[] frequencies){
//        String onder = "" ;
//        int maxIdx = 0 ;
//        int max = 0;
//        for(int i = 0 ; i < 26 ; ++i){
//            if(frequencies[i] > max ){
//                max = frequencies[i] ;
//                maxIdx = i ;
//            }
//        }
//        frequencies[maxIdx] = 0 ;
//
//    }
    public static String challenge(){
        String frequencyOrder = "ETAOINSHRDLCUMWFGYPBVKJXQZ";
        byte[] key = Text.toBytes(frequencyOrder) ;
        int[][] image1 = Helper.readImage("C:\\Users\\LEGION\\IdeaProjects\\MP_crypto_stegano\\src\\MP1_EPFL\\src\\main\\java\\ch\\epfl\\cs107\\image1.png")  ;
        int[][] image2 = Helper.readImage("C:\\Users\\LEGION\\IdeaProjects\\MP_crypto_stegano\\src\\MP1_EPFL\\src\\main\\java\\ch\\epfl\\cs107\\image2.png") ;

        String message1 = Text.toString(TextSteganography.revealText(image1)) ;
        String onlyEnglish = message1.replaceAll("[^A-Za-z]", "").toUpperCase(); ;
        System.out.println(onlyEnglish);

//        String onlyEnglish =  message1.substring(0, 250);
//        for(int i = 0; i < 256; ++i) {
//            System.out.println(Text.toString(Decrypt.caesar(Text.toBytes(onlyEnglish), (byte)i)));
//        }
        return message1 ;
    }
}
