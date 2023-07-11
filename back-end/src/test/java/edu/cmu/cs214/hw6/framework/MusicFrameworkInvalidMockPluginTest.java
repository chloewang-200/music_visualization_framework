package edu.cmu.cs214.hw6.framework;


import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw6.mocks.MockInvalidDataPlugin;
import edu.cmu.cs214.hw6.mocks.MockInvalidVisualPlugin;

import static org.junit.Assert.assertTrue;

public class MusicFrameworkInvalidMockPluginTest {
    private MusicFrameworkImpl fm;
    private DataPlugin dp;
    private VisualPlugin vp;


    @Before
    public void setUp() {
        fm = new MusicFrameworkImpl();
        dp = new MockInvalidDataPlugin();
        vp = new MockInvalidVisualPlugin();
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
    public void testFetchNullSongs() {
        fm.getJson();
        assertTrue(fm.getInvalidSongNames().length == 0);
        assertTrue(fm.getSongNumber() == 0);
    }

    @Test
    public void testFetchNullAvgSongFactory() {
        fm.getJson();
        Float tmp = fm.getAverageEmotions().getAvgfor(Attribute.dancibility());
        assertTrue(tmp == 0f);
    }

    @Test
    public void testFetchNullEachSongFactory() {
        fm.getJson();
        Float tmp = fm.getEachSongAttributes().getValueFor(SongName.construct("test"), Attribute.dancibility());
        assertTrue(tmp == 0f);
    }
}
