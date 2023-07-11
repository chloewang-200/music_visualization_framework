package edu.cmu.cs214.hw6.dataPlugins;

// import classes available in jsoup  
import org.jsoup.Jsoup;   
import org.jsoup.nodes.Document;   
import org.jsoup.nodes.Element;   
import org.jsoup.select.Elements;   
// import exception and collection classes    
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs214.hw6.framework.DataPlugin;
import edu.cmu.cs214.hw6.framework.MusicFramework;

/**
 * Code for class Billboard Hot 100 Plugin implements DataPlugin interface.
 */
public class BillboardHot100Plugin implements DataPlugin {
    private static final String DATA_PLUGIN_NAME = "Billboard Hot 100";
    private static final String URL = "https://www.billboard.com/charts/hot-100/";
    private MusicFramework framework;
    private List<String> songs = new ArrayList<>();

    /**
     * Returns the data plugin name Billboard Hot 100.
     * This will be displayed in the front-end when the user selects which plugin to use.
     */
    @Override
    public String getPluginName() {
        return DATA_PLUGIN_NAME;
    }

    /**
     * Returns the instruction for Billboard Hot 100 plugin.
     * The instruction will be displayed on the front-end to help the user know how to use Billboard Hot 100 plugin.
     */
    @Override
    public String getInstruction() {
        return "please type in input date for biillboard hot 100, example: 2019-11-30 "+
        "if empty, gets the latest one. The date should be no later than today.";
    }

    /**
     * Get hot 100 songs according to the input date.
     * If the date is later than today or the date does not match the form yyyy-mm-dd,
     * Billboard Hot 100 plugin will return 0 songs.
     * 
     * @param date example format "2019-11-30" or empty (empty means the latest one)
     */
    @Override
    public List<String> getSongNames(String date) {
        if (date == null) {
            date = "";
        }
        this.songs = new ArrayList<>();
        if (date != "" && !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return this.songs;
        }
        String url = URL + date;
        try {
            Document doc = Jsoup.connect(url)
                                .header("Accept-Encoding", "gzip, deflate")
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                                .maxBodySize(0)
                                .timeout(600000)
                                .get();
            Elements songsOnPage = doc.select("h3.a-no-trucate");
            for (Element ele : songsOnPage) {
                songs.add(ele.text());
            }
            System.out.println(songs);
            System.out.println("total songs: " + songs.size());
        } catch (IOException e) {
            System.out.println("For '" + url + "':" + e.getMessage());
        }
        return songs;
    }

    /**
     * Called (only once) when the data plugin is first registered with the
     * framework, giving the data plugin a chance to perform any initial set-up
     * before the framework has begun (if necessary).
     * 
     * @param f The {@link MusicFrameworkImpl} instance with 
     *                  which the data plugin was registered.
     */
    @Override
    public void setApplication(MusicFramework f) {
        this.framework = f;
    }

}