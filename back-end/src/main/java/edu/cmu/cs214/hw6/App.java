package edu.cmu.cs214.hw6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import fi.iki.elonen.NanoHTTPD;
import edu.cmu.cs214.hw6.framework.DataPlugin;
import edu.cmu.cs214.hw6.framework.VisualPlugin;
import edu.cmu.cs214.hw6.framework.MusicFrameworkImpl;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private MusicFrameworkImpl musicFramework;
    private List<DataPlugin> dataPlugins;
    private List<VisualPlugin> visualPlugins;
    private DataPlugin currDataPlugin;
    private VisualPlugin currVisualPlugin;
    private Boolean setDataPlugin;
    private Boolean setVisualPlugin;

    /**
     * Start the server at :8080 port.
     * @throws IOException
     */
    public App() throws IOException {
        super(8080);

        this.musicFramework = new MusicFrameworkImpl();
        dataPlugins = loadDataPlugins();
        visualPlugins = loadVisualPlugins();
        currDataPlugin = null;
        currVisualPlugin = null;
        setDataPlugin = false;
        setVisualPlugin = false;
        
        for (DataPlugin p: dataPlugins){
            musicFramework.registerPlugin(p);
        }

        for (VisualPlugin p: visualPlugins){
            musicFramework.registerPlugin(p);
        }

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String,String> params = session.getParms();
        if (uri.equals("/dataPlugin")) {
            currDataPlugin = this.dataPlugins.get(Integer.parseInt(params.get("i")));
            setDataPlugin = true;
            System.out.println("setData" + Integer.parseInt(params.get("i")));
        } else
        if (uri.equals("/visualPlugin")) {
            currVisualPlugin = this.visualPlugins.get(Integer.parseInt(params.get("i")));
            setVisualPlugin = true;
            this.musicFramework.startNewFramework(currDataPlugin, currVisualPlugin);
        } 
         else if (uri.equals("/input")) {
            String input = params.get("s");
            musicFramework.setUserInput(input);
        } else if (uri.equals("/start")) {
            this.setDataPlugin = false;
            this.setVisualPlugin = false;
        }
        AppState appState = AppState.forApp(setDataPlugin, setVisualPlugin, musicFramework);
        return newFixedLengthResponse(appState.toString());
    }


    /**
     * Load data plugins listed in META-INF/services/...
     *
     * @return List of instantiated plugins
     */
    private static List<DataPlugin> loadDataPlugins() {
        ServiceLoader<DataPlugin> plugins = ServiceLoader.load(DataPlugin.class);
        List<DataPlugin> result = new ArrayList<>();
        for (DataPlugin plugin : plugins) {
            System.out.println("Loaded plugin " + plugin.getPluginName());
            result.add(plugin);
        }
        return result;
    }

     /**
     * Load visual plugins listed in META-INF/services/...
     *
     * @return List of instantiated plugins
     */
    private static List<VisualPlugin> loadVisualPlugins() {
        ServiceLoader<VisualPlugin> plugins = ServiceLoader.load(VisualPlugin.class);
        List<VisualPlugin> result = new ArrayList<>();
        for (VisualPlugin plugin : plugins) {
            System.out.println("Loaded plugin " + plugin.getPluginName());
            result.add(plugin);
        }
        return result;
    }

    public static class Test {
        public String getText() {
            return "Hello World!";
        }
    }
}
