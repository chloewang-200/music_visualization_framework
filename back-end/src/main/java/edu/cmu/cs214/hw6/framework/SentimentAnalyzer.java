package edu.cmu.cs214.hw6.framework;

import java.util.Properties;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;


/**
* The sentiment analyzer uses the Stanford CoreNLP library to analyze the sentiment of a song
*/
public class SentimentAnalyzer {

    static StanfordCoreNLP pipeline;


    /**
    * initialize the sentiment analyzer
    */
    public static void init() {
        RedwoodConfiguration.current().clear().apply();
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    /**
    * find the sentiment of a song given the lyrics
    * @param lyrics the lyrics of the song
    * @return a string representation of the sentiment from the keywords
    * "very negative, negative, neutral, positive, very positive"
    */
    public static String findSentiment(String lyrics) {
       
        String sentimentType = "NULL";
        if (lyrics != null && lyrics.length() > 0) {
            Annotation annotation = new Annotation(lyrics);
            pipeline.annotate(annotation);
            for (CoreMap sentence : annotation
                    .get(CoreAnnotations.SentencesAnnotation.class)) {
                sentimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            }
        }
        //sentiment ranges from very negative, negative, neutral, positive, very positive
        return sentimentType;
    }

}