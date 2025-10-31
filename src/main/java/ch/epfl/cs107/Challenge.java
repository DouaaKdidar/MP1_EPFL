
package ch.epfl.cs107;

import ch.epfl.cs107.crypto.Decrypt;
import ch.epfl.cs107.stegano.ImageSteganography;
import ch.epfl.cs107.stegano.TextSteganography;
import ch.epfl.cs107.utils.Bit;
import ch.epfl.cs107.utils.Image;
import ch.epfl.cs107.utils.Text;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.rmi.StubNotFoundException;
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
        // cbc();
        challenge() ;
        // String s = "BKDMEPHEZL GV V V T E Z W X B A F D L N T O L V Q L R W D O N S G I J P W J C W S O T V X F U I D Y F S H B K Q D P F B H U C C M Q T D G O K R Y F E S N B T B I C P D B R W N Q R E S E Z I U" ;
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

//
        return "FLAG{C5-IO7;F0r743w1Nn}" ;
    }

    public static void vignere() { // IV:uV9L2k!dA4rT0N
        var image = Helper.readImage("challenge" + File.separator + "image1.png");
        boolean[][] cipher = ImageSteganography.revealBW(image);
        var data = Decrypt.vigenere(TextSteganography.revealText(Image.fromBinary(cipher)), Text.toBytes("c4Ptur37hEfL46"));
        var iv = Arrays.copyOfRange(data, 120, 136);
        System.out.println(Text.toString(iv));
        // Helper.show(image2, "Test");
    }

    public static void cbc() {
        var image1 = Helper.readImage("challenge" + File.separator + "image1.png");
        boolean[][] cipher1 = ImageSteganography.revealBW(image1);
        var data1 = Decrypt.vigenere(TextSteganography.revealText(Image.fromBinary(cipher1)), Text.toBytes("c4Ptur37hEfL46"));
        var iv1 = Arrays.copyOfRange(data1, 123, 136);

        var image = Helper.readImage("challenge" + File.separator + "image2.png");
        boolean[][] cipher = ImageSteganography.revealBW(image);
        var data = Decrypt.cbc(TextSteganography.revealText(Image.fromBinary(cipher)), iv1);
        var iv = Arrays.copyOfRange(data,0, 23);
        // var iv = Text.toString(data).matches("^[0-9]{8}$");
        System.out.println(Text.toString(iv));
    }
    public static void caesar() { //
        byte[] hint = Helper.read("C:\\Users\\ayfmp\\OneDrive\\Documentos\\EPFL\\ba1\\prog\\projets\\MP1_EPFL\\target\\classes\\challenge\\hint2.txt");
        for (int i = 0; i < 256; ++i) {
            System.out.println(Text.toString(Decrypt.caesar(hint, (byte)i)) + " " + (byte)i);
        } 
    }


    public static void xor() {
        byte[] hint = Helper.read("C:\\Users\\ayfmp\\OneDrive\\Documentos\\EPFL\\ba1\\prog\\projets\\MP1_EPFL\\target\\classes\\challenge\\hint3.txt");
                for (int i = 0; i < 256; ++i) {
            System.out.println();
            System.out.println(Text.toString(Decrypt.xor(hint, (byte)-i)) + " " + (byte)-i);
        } 

    }



// KEYWORD=c4Ptur37hEfL46; IV_POS=120..136
}
