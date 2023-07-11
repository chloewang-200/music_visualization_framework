package edu.cmu.cs214.hw6.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The framework core implementation.
 */
public class MusicFrameworkImpl implements MusicFramework {
    private List<String> songNames;
    private List<Song> songs;
    private DataPlugin currentDataPlugin;
    private VisualPlugin currentVisualPlugin;
    private List<DataPlugin> registeredDataPlugins;
    private List<VisualPlugin> registeredVisualPlugins;
    private List<String> invalidSongNames;
    private String json;
    private String userInput;
    private boolean fetched;


    /**
     * creates a framework for the music sentiment analysis
     */
    public MusicFrameworkImpl() {
        this.songNames = new ArrayList<String> ();
        this.invalidSongNames =  new ArrayList<String>();
        this.registeredDataPlugins = new ArrayList<DataPlugin>();
        this.registeredVisualPlugins = new ArrayList<VisualPlugin>();
        this.songs = new ArrayList<Song>();
        this.json = "";
        this.userInput = null;
        this.invalidSongNames =  new ArrayList<String>();
        SpotifyApiGenerator.initSpotifyApi();
        SentimentAnalyzer.init();
    }


     /**
     * Starts a new game for the provided {@link GamePlugin}
     * @param dataPlugin to use for the framework
     * @param visualPlugin to use for the framework
     */
    public void startNewFramework(DataPlugin dataPlugin, VisualPlugin visualPlugin) {
        this.json = "";
        this.fetched = false;
        this.userInput = null;
        this.currentDataPlugin = dataPlugin;
        this.currentVisualPlugin = visualPlugin;
        this.invalidSongNames =  new ArrayList<String>();
        this.songs = new ArrayList<Song>();
        this.invalidSongNames =  new ArrayList<String>();
        
    }

    /**
     * This data plugin processees the song names given by the data plugin, and
     * passes into the Spotify API to fetch song Spotfy ID. Songs that could not
     * be found are also stored here
     */
    public void fetchSongs() {
        this.songNames = this.currentDataPlugin.getSongNames(this.userInput);
        for (String name: this.songNames) {
            Song tmp = new Song(name);
            if (tmp.getSpotifyId()) {
                this.songs.add(tmp);
            }
            else {
                this.invalidSongNames.add(name); 
            }
        }
    }


    /**
     * fetch attributes for each song in the framework
     * it uses SpotifyAPI to fetch music attributes
     * and it uses MusixMatch API to fetch lyrics and then use Stanford CoreNLP to
     * fetch lyric sentiment data
     */
    public void fetchAttributes() {
        for (Song song: this.songs) {
            song.fetchAttributes();
            song.getLyricSentiment();
        }
    }

     /**
     * set the user input according to what the front-end provides.
     * User input are passed into data plugin to be further processed
     * @param input
     */
    public void setUserInput(String input) {
        this.userInput = input;
    }

    
     /**
     * Registers a new {@link DataPlugin} with the game framework
     * @param plugin {@link DataPlugin} to be registered with
     */
    public void registerPlugin(DataPlugin plugin) {
        plugin.setApplication(this);
        registeredDataPlugins.add(plugin);
    }

      /**
     * Registers a new {@link VisualPlugin} with the game framework
     * @param plugin {@link VisualPlugin} to be registered
     */
    public void registerPlugin(VisualPlugin plugin) {
        registeredVisualPlugins.add(plugin);
    }


    public void render() {
        AvgAttributeFactory avg = this.getAverageEmotions();
        EachAttributeFactory each =this.getEachSongAttributes();
        ArrayList<Attribute> emos = new ArrayList<Attribute>();
        for (Attribute e: avg.getEmotions()) {
            emos.add(e);
        }
        this.json = this.currentVisualPlugin.draw(emos.toArray(new Attribute[0]),
        avg, each);
    }

    public AvgAttributeFactory getAverageEmotions() {
        int size = this.songs.size();
        HashMap<String, Float> map = new HashMap<>();
        for (Song s: this.songs) {
            map.put("energy", map.getOrDefault("energy", 0f) + s.getEnergy());
            map.put("liveness", map.getOrDefault("liveness", 0f) + s.getLiveness());
            map.put("instrumentalness", map.getOrDefault("instrumentalness", 0f) + s.getInstrumentalness());
            map.put("speechiness", map.getOrDefault("speechiness", 0f) + s.getSpeechiness());
            map.put("dancibility", map.getOrDefault("dancibility", 0f) + s.getDancibility());
            map.put("valence", map.getOrDefault("valence", 0f) + s.getValence());
            map.put("Neutral", map.getOrDefault("Neutal", 0f) + s.getNeutral());
            map.put("Very Negative", map.getOrDefault("Very Negative", 0f) + s.getVeryNegative());
            map.put("Very Positive", map.getOrDefault("Very Positive", 0f) + s.getVeryPositive());
            map.put("Negative", map.getOrDefault("Negative", 0f) + s.getNegative());
            map.put("Positive", map.getOrDefault("Positive", 0f) + s.getPositive());
            map.put("Popularity", map.getOrDefault("Popularity", 0f) + s.getPopularity());
            map.put("Year", 0f+s.getYear());
        }
        for (String e: map.keySet()) {
            map.put(e, map.get(e)/size);
        }
        return AvgAttributeFactory.construct(map);
    }

    public EachAttributeFactory getEachSongAttributes() {
        HashMap<String, HashMap<String,Float>> res = new HashMap<>();
        for (Song s: this.songs) {
            HashMap<String, Float> map = new HashMap<>();
            map.put("energy", s.getEnergy());
            map.put("liveness", s.getLiveness());
            map.put("instrumentalness",s.getInstrumentalness());
            map.put("speechiness", s.getSpeechiness());
            map.put("dancibility", s.getDancibility());
            map.put("valence", s.getValence());
            map.put("Neutral", 0f+s.getNeutral());
            map.put("Very Negative", 0f+s.getVeryNegative());
            map.put("Very Positive", 0f+s.getVeryPositive());
            map.put("Negative", 0f+s.getNegative());
            map.put("Positive", 0f+s.getPositive());
            map.put("Popularity", 0f+s.getPopularity());
            map.put("Year", 0f+s.getYear());
            res.put(s.getSongName(),map);
        }
        return EachAttributeFactory.construct(res);
    }


     /* GameState methods: getter for Gui purposes*/

    /**
     * @return the input the user provided
     */
    public String getUserInput() {
        return this.userInput;
    }

    
    /**
     * songs that cannot be found on Spotify are store as invalid songs
     * @return names of invalid songs
     */
    public String[] getInvalidSongNames() {
        return new ArrayList<>(this.invalidSongNames).toArray(new String[0]);
    }

     
     /**
     * @return the names of the data plugins
     */
    public List<String> getDataPluginsNames(){
        return registeredDataPlugins.stream().map(DataPlugin::getPluginName).collect(Collectors.toList());
    }

     /**
     * @return the names of the visual plugins
     */
    public List<String> getVisualPluginsNames(){
        return registeredVisualPlugins.stream().map(VisualPlugin::getPluginName).collect(Collectors.toList());
    }

    /**
     * @return the number of valid songs in the framework
     */
    @Override
    public int getSongNumber() {
        return this.songs.size();
    }

    /**
     * @return the Json string representation of the visualization
     */
    public String getJson() {
        if (this.currentDataPlugin != null && this.currentVisualPlugin != null
        && this.userInput != null && !this.fetched){
            this.fetchSongs();
            this.fetchAttributes();
            this.render();
            this.fetched = true;
        }
        return this.json;
    }

    /** each data plugin will have a corresponding instruction displayed on the front-end
     * @return the instrution to use the data plugin (provide input)
     */
    public String getInstruction() {
        if (this.currentDataPlugin == null) {
            return "";
        }
        return this.currentDataPlugin.getInstruction();
    }

    
}
