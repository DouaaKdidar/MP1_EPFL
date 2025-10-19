package ch.epfl.cs107.utils;

import ch.epfl.cs107.Helper;

import static ch.epfl.cs107.utils.Text.*;
import static ch.epfl.cs107.utils.Image.*;
import static ch.epfl.cs107.utils.Bit.*;
import static ch.epfl.cs107.stegano.ImageSteganography.*;
import static ch.epfl.cs107.stegano.TextSteganography.*;
import static ch.epfl.cs107.crypto.Encrypt.*;
import static ch.epfl.cs107.crypto.Decrypt.*;
import static ch.epfl.cs107.Main.*;

/**
 * <b>Task 1.3: </b>Utility class to manipulate ARGB images
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Image {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Image(){}

    // ============================================================================================
    // ==================================== PIXEL MANIPULATION ====================================
    // ============================================================================================

    /**
     * Build a given pixel value from its respective components
     *
     * @param alpha alpha component of the pixel
     * @param red red component of the pixel
     * @param green green component of the pixel
     * @param blue blue component of the pixel
     * @return packed value of the pixel
     */
    public static int argb(byte alpha, byte red, byte green, byte blue){
        return alpha << 24 | (red & 0xFF)<<16 | (green & 0xFF) << 8 | (blue & 0xFF); 
    }

    /**
     * Extract the alpha component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the alpha component of the pixel
     */
    public static byte alpha(int pixel){
        return (byte)(pixel >> 24);
        // return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Extract the red component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the red component of the pixel
     */
    public static byte red(int pixel){
        return (byte)(pixel >> 16);
    }

    /**
     * Extract the green component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the green component of the pixel
     */
    public static byte green(int pixel){
        return (byte)(pixel >> 8);
    }

    /**
     * Extract the blue component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the blue component of the pixel
     */
    public static byte blue(int pixel){
        return (byte)pixel;
    }

    /**
     * Compute the gray scale of the given pixel
     *
     * @param pixel packed value of the pixel
     * @return gray scaling of the given pixel
     */
    public static int gray(int pixel){
        return ((red(pixel) & 255) + (blue(pixel) & 255) + (green(pixel) & 255)) / 3;
    }

    /**
     * Compute the binary representation of a given pixel.
     *
     * @param gray gray scale value of the given pixel
     * @param threshold when to consider a pixel white
     * @return binary representation of a pixel
     */
    public static boolean binary(int gray, int threshold){
        assert gray >=0 && gray <=255 ;
        return gray >= threshold;
    }

    // ============================================================================================
    // =================================== IMAGE MANIPULATION =====================================
    // ============================================================================================

    /**
     * Build the gray scale version of an ARGB image
     *
     * @param image image in ARGB format
     * @return the gray scale version of the image
     */
    public static int[][] toGray(int[][] image){
        assert image != null;
        if (image.length == 0) {
            return image;
        }
        assert image[0] != null;
        assert image[0].length > 0;
        int w = image.length;
        int h = image[0].length;
        int[][] grayIm = new int[w][h];
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                grayIm[i][j] = gray(image[i][j]);
            }
        }
        return grayIm;
    }

    /**
     * Build the binary representation of an image from the gray scale version
     *
     * @param image Image in gray scale representation
     * @param threshold Threshold to consider
     * @return binary representation of the image
     */
    public static boolean[][] toBinary(int[][] image, int threshold){
        assert image != null;
        if (image.length == 0) {
            boolean[][] ret = new boolean[0][0];
            return ret;
        }
        assert image[0] != null;
        assert image[0].length > 0;
        int w = image.length;
        int h = image[0].length;
        boolean[][] boolIm = new boolean[w][h];
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                boolIm[i][j] = binary(image[i][j], threshold);
            }
        }
        return boolIm;
    }

    /**
     * Build an ARGB image from the gray-scaled image
     * @implNote The result of this method will a gray image, not the original image
     * @param image grayscale image representation
     * @return <b>gray ARGB</b> representation
     */
    public static int[][] fromGray(int[][] image){
        assert image != null;
        if(image.length == 0 ) return image ;
        for(int[] elem : image){
            assert elem!=null ;
        }
        assert image[0].length > 0;
        int w = image.length;
        int h = image[0].length;
        int[][] colorIm = new int[w][h];
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                byte grayValue = blue(image[i][j]);
                // System.out.println(grayValue);
                colorIm[i][j] = argb((byte)255, grayValue, grayValue, grayValue);
            }
        }
        return colorIm;

    }

    /**
     * Build an ARGB image from the binary image
     * @implNote The result of this method will a black and white image, not the original image
     * @param image binary image representation
     * @return <b>black and white ARGB</b> representation
     */
    public static int[][] fromBinary(boolean[][] image){
        assert image != null;
        assert image.length >= 0;
        if (image.length == 0) {
            int [][] res = new int[0][0];
            return res;
        }
        assert image[0] != null;
        
        int w = image.length;
        int h = image[0].length;
        int[][] colorIm = new int[w][h];
        // int trues = 0;
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                if (image[i][j]) {
                    // ++trues;
                    colorIm[i][j] = 0xFF_FF_FF_FF;
                } else {
                    colorIm[i][j] = 0xFF_00_00_00;
                }
            }
        }
        // System.out.println("Trues: " + trues);
        return colorIm;
    }

}
