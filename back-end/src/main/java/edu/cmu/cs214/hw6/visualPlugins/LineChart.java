package edu.cmu.cs214.hw6.visualPlugins;

import java.util.ArrayList;
import java.util.List;
import org.icepear.echarts.Line;
import org.icepear.echarts.render.Engine;

import edu.cmu.cs214.hw6.framework.AvgAttributeFactory;
import edu.cmu.cs214.hw6.framework.EachAttributeFactory;
import edu.cmu.cs214.hw6.framework.Attribute;
import edu.cmu.cs214.hw6.framework.VisualPlugin;

public class LineChart implements VisualPlugin{
    private Engine engine;
    private static final String CHART_NAME = "Line";
    private Line line;

    /*
     * Initialized the line chart.
     */
    public LineChart() {
        this.engine = new Engine();
        this.line = new Line().setTooltip("item");
    }

     /**
     * get the name of the plugin
     * @return the name of the plugin
     */
    @Override
    public String getPluginName() {
        return CHART_NAME;
    }

    /**
     * Get the line chart.
     * @return Line chart
     */
    public Line getChart() {
        return this.line;
    }

    /**
     * Get the emtions categories from emotions and get emotion data from the
     * emotionsMap, and visualize them.
     * @param emotions attributes that are provided by the framework
     * @param emotionsMap the map of the average value of each attribute
     * @param eachEmotionsMap the map of the value of each attribute for each song
     * @return the string representation of the visualized data
     */
    @Override
    public String draw(Attribute[] emotions, AvgAttributeFactory emotionsMap, 
    EachAttributeFactory eachEmotionsMap) {
        this.line = new Line().setTooltip("item");
        List<String> category = new ArrayList<String>();
        ArrayList<Number> data = new ArrayList<Number>();

            for (Attribute e: new Attribute[]{Attribute.dancibility(),Attribute.liveness(),
            Attribute.speechiness(), Attribute.valence()}) {
               category.add(e.toString());
               data.add(emotionsMap.getAvgfor(e));
            }

        this.line.addXAxis(category.toArray(new String[category.size()]))
                 .addYAxis()
                 .addSeries(data.toArray());

        return render();
    }

    /**
     * return the JSON string to the frontend
     * @return the JSON string
     */
    private String render() {
        return engine.renderJsonOption(line);
    }

}