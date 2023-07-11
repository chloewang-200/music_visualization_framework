package edu.cmu.cs214.hw6.framework;

import org.junit.Before;
import org.junit.Test;


import edu.cmu.cs214.hw6.mocks.MockDataPlugin;
import edu.cmu.cs214.hw6.mocks.MockVisualPlugin;

import static org.junit.Assert.assertTrue;

public class MusicFrameworkMockPluginTest {
    private MusicFrameworkImpl fm;
    private DataPlugin dp;
    private VisualPlugin vp;

    @Before
    public void setUp() {
        fm = new MusicFrameworkImpl();
        dp = new MockDataPlugin();
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
    public void testFetchSongsWithNoInput() {
        fm.fetchSongs();
        assertTrue(fm.getSongNumber() == 0);
    }

    @Test
    public void testFetchSongsWithInput() {
        fm.setUserInput("");
        fm.fetchSongs();
        assertTrue(fm.getSongNumber() == 5);
    }

    @Test 
    public void testSetUsrInput() {
        fm.setUserInput("abc");
        assertTrue(fm.getUserInput().equals("abc"));
    }

    @Test
    public void testVisual() {
        fm.render();
        assertTrue(fm.getJson().equals(""));
    }

}
