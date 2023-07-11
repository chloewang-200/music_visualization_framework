package edu.cmu.cs214.hw6.framework;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw6.mocks.MockDataPluginOneSong;
import edu.cmu.cs214.hw6.mocks.MockVisualPlugin;

import static org.junit.Assert.assertTrue;

public class MusicFrameworkMockPluginOneSongTest {
    private MusicFrameworkImpl fm;
    private DataPlugin dp;
    private VisualPlugin vp;

    @Before
    public void setUp() {
        fm = new MusicFrameworkImpl();
        dp = new MockDataPluginOneSong();
        vp = new MockVisualPlugin();
        fm.registerPlugin(dp);
        fm.registerPlugin(vp);
        fm.startNewFramework(dp, vp);
    }

    @Test
    public void testRegisterPlugin() {
        assertTrue(fm.getDataPluginsNames().contains(dp.getPluginName()));
        assertTrue(fm.getVisualPluginsNames().contains(vp.getPluginName()));
    }

    @Test
    public void getAvgEmo() {
        fm.fetchSongs();
        fm.fetchAttributes();
        fm.getJson();
        Float tmp = fm.getAverageEmotions().getAvgfor(Attribute.popularity());
        assertTrue(tmp == 75f);
    }

    @Test
    public void getEachEmo() {
        fm.fetchSongs();
        fm.fetchAttributes();
        fm.getJson();
        Float tmp = fm.getEachSongAttributes().getValueFor(SongName.construct("hello"), Attribute.popularity());
        assertTrue(tmp == 75f);
    }

    @Test
    public void getBothTrue() {
        fm.fetchSongs();
        fm.fetchAttributes();
        fm.getJson();
        Float tmp = fm.getAverageEmotions().getAvgfor(Attribute.neutral());
        Float tmp2 = fm.getEachSongAttributes()
            .getValueFor(SongName.construct("hello"), Attribute.neutral());
        assertTrue(tmp.equals(tmp2));
    }


    @Test
    public void testFetchSongsWithInput() {
        fm.setUserInput("");
        fm.fetchSongs();
        assertTrue(fm.getSongNumber() == 1);
    }


}
