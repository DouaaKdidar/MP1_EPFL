package ch.epfl.cs107.stegano;

import ch.epfl.cs107.Helper;
import ch.epfl.cs107.crypto.Encrypt;
import ch.epfl.cs107.utils.Bit;
import ch.epfl.cs107.utils.Text;

import static ch.epfl.cs107.utils.Text.*;
import static ch.epfl.cs107.utils.Image.*;
import static ch.epfl.cs107.utils.Bit.*;
import static ch.epfl.cs107.stegano.ImageSteganography.*;
import static ch.epfl.cs107.stegano.TextSteganography.*;
import static ch.epfl.cs107.crypto.Encrypt.*;
import static ch.epfl.cs107.crypto.Decrypt.*;
import static ch.epfl.cs107.Main.*;

/**
 * <b>Task 3.2: </b>Utility class to perform Text Steganography
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public class TextSteganography {


    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private TextSteganography(){}

    // ============================================================================================
    // =================================== EMBEDDING BIT ARRAY ====================================
    // ============================================================================================

    /**
     * Embed a bitmap message in an ARGB image
     * @param cover Cover image
     * @param message Embedded message
     * @return ARGB image with the message embedded
     */
    public static int[][] embedBitArray(int[][] cover, boolean[] message) {
        assert cover != null ;
        assert message != null ;
        int length = cover[0].length ;
        for(int[] elem : cover){
            assert elem != null ;
            assert elem.length == length ;
        }
        int n = 0 ;
        int m = 0 ;
        int max = cover[0].length ;
        int MAX = cover.length ;
        int[][] encripted = new int[MAX][max];
        for(int i = 0; i < MAX ; ++i){
            for(int j = 0; j < max ; ++j){
                encripted[i][j] = cover[i][j] ;
            }
        }

        for(int i = 0 ; i < message.length ; ++i ){
            encripted[n][m] = Bit.embedInLSB(cover[n][m] , message[i]) ;
            if(m == max-1){
                if(n == MAX-1) return encripted ;
                m = 0 ;
                ++n ;
            }else{
                ++m ;
            }
        }
        return encripted ;
    }

    /**
     * Extract a bitmap from an image
     * @param image Image to extract from
     * @return extracted message
     */
    public static boolean[] revealBitArray(int[][] image) {
        assert image != null ;
        assert image[0] != null ;
        int n = image.length ;
        int m = image[0].length ;
        for(int[] elem : image){
            assert elem.length == m  ;
        }
        if(n == 0 && m==0) {
            boolean[] bitArray = new boolean[0];
            return bitArray ;
        }
        boolean bitArray[] = new boolean[n*m] ;
        for(int i = 0; i  < n ; ++i){
            for(int j = 0 ; j  < m ; ++j){
                bitArray[m*i+j] =  Bit.getLSB(image[i][j]) ;
            }
        }
        return bitArray;
    }



    // ============================================================================================
    // ===================================== EMBEDDING STRING =====================================
    // ============================================================================================

    /**
     * Embed a String message in an ARGB image
     * @param cover Cover image
     * @param message Embedded message
     * @return ARGB image with the message embedded
     */
    public static int[][] embedText(int[][] cover, byte[] message) {
        assert cover != null ;
        assert message != null ;
        assert cover[0] != null ;
        int length = cover[0].length ;
        for(int[] elem : cover){
            assert elem != null ;
            assert elem.length == length ;
        }
        boolean[] bitArray = Text.toBitArray(Text.toString(message)) ;
        cover = embedBitArray(cover , bitArray) ;
        return cover ;
    }

    /**
     * Extract a String from an image
     * @param image Image to extract from
     * @return extracted message
     */
    public static byte[] revealText(int[][] image) {
        assert image != null ;
        assert image[0]!= null ;
        assert image[0].length > 0 ;
        int length = image[0].length ;
        for(int[] elem : image){
            assert elem != null ;
            assert elem.length == length ;
        }
        boolean[] bitArray = revealBitArray(image) ;
        String text = Text.toString(bitArray) ;
        return Text.toBytes(text) ;
    }








    // section 5.4
    public static int[][] embedText(int[][] cover , byte[] message , String encryptionMethodName , byte[] key){
        byte[] hidden = new byte[message.length] ;
         switch(encryptionMethodName){
             case "cbc" :
                 hidden = Encrypt.cbc(message , key) ;
                 break ;


         }
         cover = embedText(cover , hidden) ;
         return cover ;
    }

    public static void revealText(int[][] image , String encryptionMethodName , byte[] key){

    }

}
