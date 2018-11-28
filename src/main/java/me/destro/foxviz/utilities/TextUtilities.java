package me.destro.foxviz.utilities;

import processing.core.PApplet;

public class TextUtilities {

    public static String createLineBreaks(String str, float maxWidth, PApplet scene) {
        // Remove unnecessary spaces
        // and add (unix) linebreak characters if line length exceeds maxWidth
        StringBuilder strBuffer = new StringBuilder(str.length());
        boolean firstSpace = false;
        int lastSpace = -1, iB = 0;
        float currentWidth = 0, wordWidth = 0;

        for(int i = 0, n = str.length(); i < n; i++) {
            char c = str.charAt(i);
            if(c == ' ') { // If this character is a space
                if(firstSpace) { // If this space is the first space in a row
                    if(currentWidth > maxWidth && lastSpace > -1) {
                        strBuffer.setCharAt(lastSpace, (char)10);
                        currentWidth -= wordWidth;
                    }
                    currentWidth += scene.textWidth(c);
                    wordWidth = currentWidth;
                    lastSpace = iB;
                    strBuffer.append(c);
                    firstSpace = false;
                    iB++;
                }
            } else { // If character is no space
                currentWidth += scene.textWidth(c);
                strBuffer.append(c);
                firstSpace = true;
                iB++;
            }
        }
        if(currentWidth > maxWidth && lastSpace > -1) // If last line still exceeds maxWidth
            strBuffer.setCharAt(lastSpace, (char)10);

        // Return string
        return strBuffer.toString();
    }

    public static float textHeight(String str, PApplet scene) {
        // Count (unix) linebreaks
        int linebreaks = 0;
        for(int i = 0, n = str.length(); i < n; i++)
            if(str.charAt(i) == (char)10)
                linebreaks++;

        // Calculate & return height
        if(linebreaks == 0)
            return scene.textAscent() + scene.textDescent();
        else
            //return linebreaks * scene.textLeading() + scene.textAscent() + scene.textDescent();
            return linebreaks * scene.g.textLeading + scene.textAscent() + scene.textDescent();

    }

    public static float textHeight(String str, PApplet scene, float textLeading) {
        // Count (unix) linebreaks
        int linebreaks = 0;
        for(int i = 0, n = str.length(); i < n; i++)
            if(str.charAt(i) == (char)10)
                linebreaks++;

        // Calculate & return height
        if(linebreaks == 0)
            return scene.textAscent() + scene.textDescent();
        else
            //return linebreaks * scene.textLeading() + scene.textAscent() + scene.textDescent();
            return linebreaks * scene.g.textLeading + scene.textAscent() + scene.textDescent();

    }
}
