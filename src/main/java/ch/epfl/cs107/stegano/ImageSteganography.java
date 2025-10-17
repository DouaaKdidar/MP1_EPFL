package ch.epfl.cs107.stegano;

import ch.epfl.cs107.Helper;
import ch.epfl.cs107.utils.Bit;
import ch.epfl.cs107.utils.Image;

import static ch.epfl.cs107.utils.Text.*;
import static ch.epfl.cs107.utils.Image.*;
import static ch.epfl.cs107.utils.Bit.*;
import static ch.epfl.cs107.stegano.ImageSteganography.*;
import static ch.epfl.cs107.stegano.TextSteganography.*;
import static ch.epfl.cs107.crypto.Encrypt.*;
import static ch.epfl.cs107.crypto.Decrypt.*;
import static ch.epfl.cs107.Main.*;

/**
 * <b>Task 3.1: </b>Utility class to perform Image Steganography
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ImageSteganography {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private ImageSteganography(){}

    // ============================================================================================
    // ================================== EMBEDDING METHODS =======================================
    // ============================================================================================

    /**
     * Embed an ARGB image on another ARGB image (the cover)
     * @param cover Cover image
     * @param argbImage Embedded image
     * @param threshold threshold to use for binary conversion
     * @return ARGB image with the image embedded on the cover
     */
    public static int[][] embedARGB(int[][] cover, int[][] argbImage, int threshold){
        boolean[][] bandw = Image.toBinary(argbImage, threshold);
        return embedBW(cover, bandw);
    }

    /**
     * Embed a Gray scaled image on another ARGB image (the cover)
     * @param cover Cover image
     * @param grayImage Embedded image
     * @param threshold threshold to use for binary conversion
     * @return ARGB image with the image embedded on the cover
     */
    public static int[][] embedGray(int[][] cover, int[][] grayImage, int threshold){
        boolean[][] bandw = Image.toBinary(grayImage, threshold);
        return embedBW(cover, bandw);
    }

    /**
     * Embed a binary image on another ARGB image (the cover)
     * @param cover Cover image
     * @param load Embedded image
     * @return ARGB image with the image embedded on the cover
     */
    public static int[][] embedBW(int[][] cover, boolean[][] load){
        assert cover == null;
        assert cover.length > 0;
        assert cover[0].length > 0;
        assert load == null;
        assert load.length > 0;
        assert load[0].length > 0;
        assert load.length < cover.length;
        assert load[0].length < cover[0].length;
        int w = cover.length;
        int h = cover[0].length;
        int bw = load.length;
        int bh = load[0].length;
        int[][] hidden = new int[w][h];
        int trues = 0;
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                if (i < bw && j < bh) {
                    if (load[i][j]) {
                        ++trues;
                    }
                    hidden[i][j] = Bit.embedInLSB(cover[i][j], load[i][j]);
                } else {
                    hidden[i][j] = cover[i][j];
                }
            }
        }
        System.out.println("Trues: " + trues);
        return hidden;
    }

    // ============================================================================================
    // =================================== REVEALING METHODS ======================================
    // ============================================================================================

    /**
     * Reveal a binary image from a given image
     * @param image Image to reveal from
     * @return binary representation of the hidden image
     */
    public static boolean[][] revealBW(int[][] image) {
        assert image == null;
        assert image.length > 0;
        assert image[0].length > 0;
        int w = image.length;
        int h = image[0].length;
        boolean[][] bandw = new boolean[w][h];
        
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                bandw[i][j] = Bit.getLSB(image[i][j]);
            }
        }
        return bandw;
    }

}
