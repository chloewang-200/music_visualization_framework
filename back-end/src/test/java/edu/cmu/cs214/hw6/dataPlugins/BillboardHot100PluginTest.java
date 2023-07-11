package edu.cmu.cs214.hw6.dataPlugins;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw6.framework.DataPlugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

public class BillboardHot100PluginTest {
    private DataPlugin p;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        p = new BillboardHot100Plugin();
    }

    @Test
    public void testGetPluginName() {
        assertEquals("Billboard Hot 100", p.getPluginName());
    }

    @Test
    public void testGetSongNamesWithInvalidDate() {
        String date = "2024-08-29";
        List<String> names = p.getSongNames(date);
        assertTrue(names.isEmpty());
    }



    @Test
    public void testGetSongNamesWithValidDate() {
        String date = "2022-08-29";
        List<String> names = p.getSongNames(date);
        assertEquals(100, names.size());
    }
}
