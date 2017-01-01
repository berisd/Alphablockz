package at.beris.games.alphablockz.gui;

import at.beris.games.alphablockz.word.Letter;
import at.beris.games.alphablockz.word.LetterColor;
import at.beris.games.alphablockz.word.Word;

import java.awt.*;

public class Logo implements Drawable {
    private Word word;
    private int x;
    private int y;


    public Logo(int x, int y, String wordString) {
        this.word = new Word(wordString);
        this.x = x;
        this.y = y;
        createWord();
    }

    @Override
    public void draw(Graphics2D context) {
        word.draw(context, x, y, 15, 15);
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    private void createWord() {
        LetterColor[] foregroundColors = {LetterColor.RED, LetterColor.GREEN, LetterColor.BLUE, LetterColor.CYAN};
        for (int x = 0; x < word.length(); x++) {
            Letter letter = word.get(x);
            letter.setColor(foregroundColors[x % foregroundColors.length]);
            letter.setBackgroundColor(LetterColor.YELLOW);
        }
    }

    public void cycleLetterColors(boolean reverse) {
        for (Letter letter : word) {
            letter.setColor(reverse ? letter.getColor().getPreviousForeColor() : letter.getColor().getNextForeColor());
        }
    }
}
