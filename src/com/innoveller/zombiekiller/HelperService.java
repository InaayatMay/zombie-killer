package com.innoveller.zombiekiller;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HelperService {

    public ArrayList<Image> readSprites(String filePath, int numSpritesPerCycle) throws IOException {
        ArrayList<Image> imageList = new ArrayList<>();
        File pathToFile = new File(filePath);
        BufferedImage fullImage = ImageIO.read(pathToFile);
        int spriteWidth = fullImage.getWidth() / numSpritesPerCycle;
        int spriteHeight = fullImage.getHeight();
        for(int i = 0; i< numSpritesPerCycle; i++) {
            int x = i * spriteWidth;
            Image subImage = fullImage.getSubimage(x, 0, spriteWidth, spriteHeight);
            imageList.add(subImage);
        }

        return imageList;
    }

    public Clip buildAudioClip(String filePath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(filePath));
        clip.open(inputStream);
        return clip;
    }

    public Clip playBackgroundMusic(String filePath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(filePath));
        clip.open(inputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        return clip;
    }

    public void showFinalResult(Graphics g, Clip backgroundSound, Clip finalTrack, Color color, String message){
        Font font = new Font("Times New Roman", Font.BOLD, 100);
        g.setFont(font);
        g.setColor(color);
        g.drawString(message, 850, 500);
        backgroundSound.stop();
        try {
            finalTrack.open();
            finalTrack.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
