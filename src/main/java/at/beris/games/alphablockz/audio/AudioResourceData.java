package at.beris.games.alphablockz.audio;

public enum AudioResourceData {
    GAME_MUSIC("bensound-clearday.mp3"),
    SOUND_LETTER_MOVING("Eye_Poke-Klocko-584660179.mp3"),
    SOUND_LETTER_FALLING("Realistic_Punch-Mark_DiAngelo-1609462330.mp3"),
    SOUND_WORD_MATCHING("Gun_Shot-Marvin-1140816320.mp3"),
    SOUND_LEVEL_COMPLETED("News_Intro-Maximilien_-1801238420.mp3"),
    SOUND_GAME_OVER("Evil_Laugh_2-Sound_Explorer-1081271267.mp3");

    private String filename;

    AudioResourceData(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
