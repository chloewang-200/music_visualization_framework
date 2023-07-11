package edu.cmu.cs214.hw6.visualPlugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.icepear.echarts.Option;
import org.icepear.echarts.Pie;
import org.icepear.echarts.charts.pie.PieItemStyle;
import org.icepear.echarts.charts.pie.PieLabel;
import org.icepear.echarts.charts.pie.PieLabelLine;
import org.icepear.echarts.charts.pie.PieSeries;
import org.icepear.echarts.components.dataset.DataTransform;
import org.icepear.echarts.components.dataset.DataTransformConfig;
import org.icepear.echarts.components.dataset.Dataset;
import org.icepear.echarts.components.legend.Legend;
import org.icepear.echarts.components.title.Title;
import org.icepear.echarts.origin.component.dataset.DatasetOption;
import org.icepear.echarts.origin.component.title.TitleOption;
import org.icepear.echarts.origin.util.SeriesOption;
import org.icepear.echarts.serializer.EChartsSerializer;

import edu.cmu.cs214.hw6.framework.AvgAttributeFactory;
import edu.cmu.cs214.hw6.framework.EachAttributeFactory;
import edu.cmu.cs214.hw6.framework.Attribute;
import edu.cmu.cs214.hw6.framework.SongName;
import edu.cmu.cs214.hw6.framework.VisualPlugin;

public class MultiplePieChart implements VisualPlugin{

    private static final String CHART_NAME = "Multiple Pie";
    private Pie pie;
    private int notPopular;
    private int popular;
    List<HashMap<String, Integer>> listPositivityData;
    
    /**
     * Initialized the Multiple Pie chart.
     */
    public MultiplePieChart() {
        this.pie = new Pie().setTooltip("item");
        this.notPopular = 0;
        this.popular = 0;
    }

    /**
     * get the pie chart.
     * @return Pie chart
     */
    public Pie getChart() {
        return this.pie;
    }

    /**
     * get the plugin name
     * @return the name of the plugin
     */
    @Override
    public String getPluginName() {
        return CHART_NAME;
    }

    /**
     * segment the songs into popular and not popular by comparing the value of the
     * attribute to the average value of the attribute.
     * 
     * @param avg the average value of the attribute
     * @param num the value of the attribute
     * @return the segment the song belongs to
     */
    private int getTwoSeg(float avg, float num) {
        if (num < avg) {
            this.notPopular += 1;
            return 0;
        } else {
            this.popular += 1;
            return 1;
        }
    }

    /**
     * get the data source of the pie charts.
     * 
     * @param attribute the attribute provided by the framework
     * @param avgMap    the map of the average value of each attribute
     * @param eachMap   the map of the value of each attribute for each song
     * @return the data source of the pie charts
     */
    private ArrayList<Object[]> generateDataSource(AvgAttributeFactory avgEmotionsMap, 
    EachAttributeFactory eachEmotionsMap){
        this.notPopular = 0;
        this.popular = 0;
        // EachEmotionFactory eachEmotionsMap = this.analysisApp.getEachSongEmotions();
        // AvgEmotionFactory avgEmotionsMap = this.analysisApp.getAverageEmotions();
        float avgPopularity = avgEmotionsMap.getAvgfor(Attribute.popularity());
        ArrayList<Object[]> source = new ArrayList<Object[]>();
        source.add(new Object[]{"Emotion", "Number", "Popularity"});
        for (SongName songName: eachEmotionsMap.getSongNames()) {
            int popularity = getTwoSeg(avgPopularity, eachEmotionsMap.getValueFor(songName, Attribute.popularity()));
            source.add(new Object[]{"Positive", eachEmotionsMap.getValueFor(songName, Attribute.positive()),
            popularity});
        } 
        for (SongName songName: eachEmotionsMap.getSongNames()) {
            int popularity = getTwoSeg(avgPopularity, eachEmotionsMap.getValueFor(songName, Attribute.popularity()));
            source.add(new Object[]{"Neutral", eachEmotionsMap.getValueFor(songName, Attribute.neutral()),
            popularity});
        } 
        for (SongName songName: eachEmotionsMap.getSongNames()) {
            int popularity = getTwoSeg(avgPopularity, eachEmotionsMap.getValueFor(songName, Attribute.popularity()));
            source.add(new Object[]{"Negative", eachEmotionsMap.getValueFor(songName, Attribute.negative()),
            popularity});

        } 
        return source;
    }    
    
    /**
     * create a dataset for each pie chart. 
     * 
     * @param num the index of the pie chart
     * @return the dataset for the pie chart
     */
    private Dataset setNewDataset(int num) {
        Dataset dataset = new Dataset()
                .setTransform(new DataTransform()
                        .setType("filter")
                        .setConfig(new DataTransformConfig()
                                .setDimension("Popularity")
                                .setValue(num)));
        return dataset;
    }

    /**
     * create a pie series.
     * 
     * @param num the index of the pie chart
     * @param prop1 the position X of the pie chart
     * @param prop2 the position Y of the pie chart
     * @return the pie sieries
     */
    private PieSeries setPieSeries(int num, String prop1, String prop2){
        PieSeries series = new PieSeries()
        .setType("pie")
        .setRadius(new String[]{"30%","50%"})
        .setCenter(new String[] { prop1, prop2 })
        .setDatasetIndex(num)
        .setItemStyle(new PieItemStyle().setBorderColor("#fff")
                        .setBorderRadius(0)
                        .setBorderWidth(0))
        .setLabel(new PieLabel()
        .setShow(false)
        .setPosition("center"))
        .setLabelLine(new PieLabelLine().setShow(false));
        return series;
    }

    /**
     * create a title for the pie chart.
     * 
     * @param txt the title of the pie chart
     * @param subtxt the subtitle of the pie chart
     * @param prop1 the position X of the title
     * @param prop2 the position Y of the title
     * @return the title
     */ 
    private Title setTitleSeries(String txt, String subtxt, String prop1, String prop2){
        Title title = new Title()
        .setText(txt)
        .setSubtext(subtxt)
        .setLeft(prop1)
        .setTextAlign("center")
        .setTop(prop2);
        return title;
    }

    /**
     * draw the pie chart.
     * 
     * @param emotions the attribute provided by the framework
     * @param avgEmotionsMap the map of the average value of each attribute
     * @param eachEmotionsMap the map of the value of each attribute for each song
     * @return the json string representation of the pie chart
     */
    @Override
    public String draw(Attribute[] emotions, AvgAttributeFactory avgEmotionsMap, 
    EachAttributeFactory eachEmotionsMap) {
        Dataset dataset = new Dataset().setSource(this.generateDataSource(avgEmotionsMap,
        eachEmotionsMap));
        ArrayList<DatasetOption> datasets = new ArrayList<>();
        datasets.add(dataset);
        Option option = new Option()
        .setLegend(new Legend().setTop("5%").setLeft("center"))
        .setDataset(new DatasetOption[] { dataset,
            this.setNewDataset(0),
            this.setNewDataset(1)})
        .setSeries(new SeriesOption[] { setPieSeries(1, "25%", "55%"),
            setPieSeries(2, "75%", "55%")})
         .setTitle(new TitleOption[] { 
            this.setTitleSeries("not popular", 
                String.format("%d songs", this.notPopular / 3), "25%", "15%"),
            this.setTitleSeries("popular",
                String.format("%d songs", this.popular / 3), "75%", "15%")});
        return new EChartsSerializer().toJson(option);
    }

}