package ch.epfl.cs107.crypto;

import ch.epfl.cs107.Helper;

import static ch.epfl.cs107.Helper.generateRandomBytes;

/**
 * <b>Task 2: </b>Utility class to encrypt a given plain text.
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Encrypt {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Encrypt(){}

    // ============================================================================================
    // ================================== CAESAR'S ENCRYPTION =====================================
    // ============================================================================================

    /**
     * Method to encode a byte array message using a single character key
     * the key is simply added to each byte of the original message
     *
     * @param plainText The byte array representing the string to encode
     * @param key the byte corresponding to the char we use to shift
     * @return an encoded byte array
     */
    public static byte[] caesar(byte[] plainText, byte key) {
        assert plainText != null ;
        byte[] cipher = new byte[plainText.length] ;

        for(int i = 0 ; i < plainText.length ; ++i){
            cipher[i] = (byte)(plainText[i] + key) ;
        }
        return cipher ;
    }

    // ============================================================================================
    // =============================== VIGENERE'S ENCRYPTION ======================================
    // ============================================================================================

    /**
     * Method to encode a byte array using a byte array keyword
     * The keyword is repeated along the message to encode
     * The bytes of the keyword are added to those of the message to encode
     * @param plainText the byte array representing the message to encode
     * @param keyword the byte array representing the key used to perform the shift
     * @return an encoded byte array
     */
    public static byte[] vigenere(byte[] plainText, byte[] keyword) {
        assert plainText != null ;
        assert keyword != null ;

        int n = plainText.length ;
        int k = keyword.length ;
        byte[] cipher = new byte[n] ;
        for(int i = 0; i < n; ++i){
            cipher[i] = (byte) (plainText[i] + keyword[i%k]);
        }
        return cipher ;

    }

    // ============================================================================================
    // =================================== CBC'S ENCRYPTION =======================================
    // ============================================================================================

    protected static byte[] getNextPad(byte[] cipher , int start , int end){
        byte[] pad = new byte[end-start] ;
        for(int i = start ; i < end ; ++i){
            pad[i-start] = cipher[i] ;
        }
        return pad ;
    }


    /**
     * Method applying a basic chain block counter of XOR without encryption method.
     * @param plainText the byte array representing the string to encode
     * @param iv the pad of size BLOCKSIZE we use to start the chain encoding
     * @return an encoded byte array
     */
    public static byte[] cbc(byte[] plainText, byte[] iv) {
        byte[] cipher = new byte[plainText.length] ;
        byte[] currentPad = iv  ;
        int k = iv.length ;
        for(int i  = 0 ; i < plainText.length ; ++i){
            if(i>0 && i%k==0){
                currentPad = getNextPad(cipher , i-k , i);
            }
            cipher[i] = (byte)(plainText[i] ^ currentPad[i%k]);
        }
        return cipher ;
    }

    // ============================================================================================
    // =================================== XOR'S ENCRYPTION =======================================
    // ============================================================================================

    /**
     * Method to encode a byte array using a XOR with a single byte long key
     * @param plainText the byte array representing the string to encode
     * @param key the byte we will use to XOR
     * @return an encoded byte array
     */
    public static byte[] xor(byte[] plainText, byte key) {
        byte[] cipher = new byte[plainText.length] ;
        for(int i = 0 ; i < plainText.length ; ++i){
            cipher[i] = (byte)(plainText[i]^key) ;
        }
        return cipher ;
    }

    // ============================================================================================
    // =================================== ONETIME'S PAD ENCRYPTION ===============================
    // ============================================================================================

    /**
     * Method to encode a byte array using a one-time pad of the same length.
     *  The method XOR them together.
     * @param plainText the byte array representing the string to encode
     * @param pad the one-time pad
     * @return an encoded byte array
     */
    public static byte[] oneTimePad(byte[] plainText, byte[] pad) {
        assert plainText != null ;
        assert pad != null ;
        assert pad.length >= plainText.length ;
        byte[] cipher = new byte[plainText.length] ;
        for(int i = 0 ; i < plainText.length ; ++i){
            cipher[i] = (byte) (plainText[i] ^ pad[i]) ;
        }
        return cipher;
    }

    /**
     * Method to encode a byte array using a one-time pad
     * @param plainText Plain text to encode
     * @param pad Array containing the used pad after the execution
     * @param result Array containing the result after the execution
     */


    public static void oneTimePad(byte[] plainText, byte[] pad, byte[] result){
        pad =  generateRandomBytes(plainText.length) ;
        result = oneTimePad(plainText , pad) ;
    }
}


