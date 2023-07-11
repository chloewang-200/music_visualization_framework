package edu.cmu.cs214.hw6.dataPlugins;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw6.framework.DataPlugin;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;    
import java.util.List;

public class UserManualInputPluginTest {

    private DataPlugin p;
    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        p = new UserManualInputPlugin();
    }

    @Test
    public void testGetPluginName() {
        assertEquals("User Manual Input", p.getPluginName());
    }

    @Test
    public void testGetSongNames() {
        String input = "Flowers\nKill Bill\nBoy's a liar Pt. 2\nBESO\nPlayers";
        List<String> names = p.getSongNames(input);
        List<String> ref = new ArrayList<>();
        ref.add("Flowers");
        ref.add("Kill Bill");
        ref.add("Boy's a liar Pt. 2");
        ref.add("BESO");
        ref.add("Players");
        assertEquals(ref, names);
    }

}
