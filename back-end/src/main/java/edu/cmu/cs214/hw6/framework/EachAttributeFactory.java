package edu.cmu.cs214.hw6.framework;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class EachAttributeFactory {
    private HashMap<String, HashMap<String,Float>> attributeFactory;

    /**
     * construct an EachAttributeFactory
     */
    private EachAttributeFactory(HashMap<String, HashMap<String,Float>> emotionFactory) {
        this.attributeFactory = emotionFactory;
    }

    /**
     * construct an EachAttributeFactory
     */
    protected static EachAttributeFactory construct(HashMap<String, HashMap<String,Float>> emotionFactory) {
        return new EachAttributeFactory(emotionFactory);
    }

    /**
     * get the size of the attribute factory
     * @return a float representation of the average attribute
     */
    public int getSize() {
        return this.attributeFactory.size();
    }

    /**
     * get the attribute for a song
     * @param songName the song name
     * @param emotion the attribute
     * @return a float representation of the attribute
     */
    public float getValueFor(SongName songName, Attribute emotion) {
        String name = songName.toString();
        String e = emotion.toString();
        
        try {
            return this.attributeFactory.get(name).get(e);
        } catch (Exception exp) {
            return 0f;
        }
    }

    /**
     * get the set of all song names provided by the framework
     * @return a set of all song names
     */
    public Set<SongName> getSongNames(){
        Set<SongName> res = new HashSet<SongName>();
        for (String e: this.attributeFactory.keySet()) {
            res.add(SongName.construct(e));
        }
        return res;
    }

}
