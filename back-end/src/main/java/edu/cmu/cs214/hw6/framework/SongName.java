package edu.cmu.cs214.hw6.framework;

 /**
 * The song name class stores the name of a song
 */
public final class SongName {
    private String name;

    /**
     * initialize a song name
     */
    private SongName(String s) {
        this.name = s;
    }

    /**
     * construct a song name
     */
    protected static SongName construct(String s) {
        return new SongName(s);
    }

    /**
     * @return string representation of the song name for front-end rendering purpose
     */
    public String toString() {
        return this.name;
    }
    
}
