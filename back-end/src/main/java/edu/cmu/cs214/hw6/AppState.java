package edu.cmu.cs214.hw6;

import edu.cmu.cs214.hw6.framework.MusicFrameworkImpl;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
public final class AppState {
    private Boolean setDataPlugin = false;
    private Boolean setVisualPlugin = false;
    private String chartData = null;
    private Plugin[] dataPlugins;
    private Plugin[] visualPlugins;
    private String instruction;
    private int songNum;
    private String[] invalidSongs;


    private AppState(Boolean setData, Boolean setVis, String chartJSON, Plugin[] dataPlugins,
    Plugin[] visualPlugins, String instruction, int songNum, String[] invalidSongs) {
        this.setDataPlugin = setData;
        this.setVisualPlugin = setVis;
        this.chartData = chartJSON;
        this.dataPlugins = dataPlugins;
        this.visualPlugins = visualPlugins;
        this.instruction = instruction;
        this.songNum = songNum;
        this.invalidSongs = invalidSongs;
    }

    public static AppState forApp(Boolean setData, Boolean setVis, MusicFrameworkImpl musicFramework) {
        // return new AppState(dataP, visualP, chartJSON);
        
        return new AppState(setData, setVis, musicFramework.getJson(), getDataPlugins(musicFramework),
        getVisualPlugins(musicFramework), musicFramework.getInstruction(), musicFramework.getSongNumber(),
        musicFramework.getInvalidSongNames());
    }

    private static Plugin[] getDataPlugins(MusicFrameworkImpl fm) {
        List<String> dataPlugins = fm.getDataPluginsNames();
        Plugin[] plugins = new Plugin[dataPlugins.size()];
        for (int i=0; i < dataPlugins.size(); i++){
            plugins[i] = new Plugin(dataPlugins.get(i));
        }
        return plugins;
    }

    private static Plugin[] getVisualPlugins(MusicFrameworkImpl fm) {
        List<String> visualPlugins = fm.getVisualPluginsNames();
        Plugin[] plugins = new Plugin[visualPlugins.size()];
        for (int i=0; i < visualPlugins.size(); i++){
            plugins[i] = new Plugin(visualPlugins.get(i));
        }
        return plugins;
    }

    @Override
    public String toString() {
        String charData = " ";
        if (this.chartData != null) {
            charData = this.chartData;
        }
        Gson gson = new Gson();
        charData = gson.toJson(charData);
        
        String json = """
                { "setDataPlugin": %b,
                  "setVisualPlugin": %b,
                  "dataPlugins": %s,
                  "visualPlugins": %s,
                  "chartJSON": %s,
                  "instruction": "%s",
                  "songNum" : %d,
                  "invalidSongs": %s
                }
                """.formatted(this.setDataPlugin, this.setVisualPlugin, Arrays.toString(dataPlugins),
                Arrays.toString(visualPlugins), charData, this.instruction, this.songNum,
                gson.toJson(invalidSongs));
        return json;
    }
}
