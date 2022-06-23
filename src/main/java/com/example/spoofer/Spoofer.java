package com.example.spoofer;

import com.example.spoofer.binarization.GlobalThresholding;
import com.example.spoofer.skeletonization.ZhangSuen;

import java.awt.image.BufferedImage;

public class Spoofer {

    public static BufferedImage spoof(BufferedImage img) {

        BufferedImage binarizatedImg = GlobalThresholding.simpleBinarization(img, 120);
        int [][] thinnedImg = ZhangSuen.thinImage(Helpers.convertBinarizatedImgToArray2D(binarizatedImg));
        BufferedImage spoofImg = Helpers.convertArraytoBinarizatedImg(thinnedImg);
        return spoofImg;

    }

}
