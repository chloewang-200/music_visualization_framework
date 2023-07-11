# Domain

### General Description
The framework functions as a music sentiment visualizer, it will accept the name of one or multiple songs and provide visualization of the emotions expressed through sentiment analysis. 

<img width="305" alt="Screenshot 2023-04-06 at 19 41 00" src="https://user-images.githubusercontent.com/84855841/230514539-5ba5ad16-4912-4f22-8baf-b6c0d893a319.png">

# how to compile and start the framework

1. back-end

    follow the following commands:

    cd back-end

    mvn compile

    mvn exec:exec

2. front-end

    follow the following commands:

    cd front-end

    npm install

    npm run start

Note: if you see the exception "The driver is not executable", please enter chmod +x chromedrivermac for mac and chmod +x chromedriverlinux for linux systems.

Note: We suggest try entering a few song manually using the manual input plugin first. The Stanford NLP sentiment analysis & MusicMatch API & SpotifyAPI results in a relatively long processing time when given large number of songs.

# before starting the framework

### Overview

The goal of the framework is to help conduct sentiment analysis on music. The sentiment analysis consists of 2 parts - acoustic sentiment analysis and textual sentiment analysis for the lyrics if applied.

The framework uses 2 API - Spotify API & MusixMatch API. SpotifyAPI will fetch song attributes such as danceability, livenses, and valence. MusixMatch API will fetch the lyrics of the songs because SpotifyAPI does not provide lyrics due to legal issues. The framework will fetch music sentiment analysis results provided by Spotify, and the lyrics will then go through Stanford CoreNLP for textual sentiment analysis. 

Note that we used free accounts for this project, so MusixMatch API will only fetch the first 30% of the lyrics. We left the API tokens in the project for the ease of grading (all API accounts used for this project are free accounts, hence some have limited number of calls per day). If you want to generate your own API keys, please follow the following steps:

### generating API tokens

#### SpotifyAPI
To use SpotifyAPI, generate client id and client secret following the following steps: 

1. create a SpotifyAPI account and then log in at https://developer.spotify.com/ 
2. go to your dashboard by clicking your user name at the top right corner of the webpage
3. click on the create an app button
4. now you can click on settings to view your client id & client secret!
5. go to the framework code, replace the variables "CLIENT_ID" & "CLIENT_SECRET" in file at path: 

    [back-end/src/main/java/edu/cmu/cs214/hw6/framework/SpotifyApiGenerator.java] with your generated client id & secret. 

For more details on how to generate the API key, visit https://developer.spotify.com/documentation/web-api


#### MusixMatchAPI
To use MusixMatch API, generate token by following the following instructions:

1. create an account at https://developer.musixmatch.com/signup
2. create an Application in your dashboard, then you can see your API right next to your app name!
3. go to the framework code, replace the variable "apiToken" in file at path: 

    [back-end/src/main/java/edu/cmu/cs214/hw6/framework/MusixApi.java] with the generated token.

For more details on how to generate the API key, visit https://developer.musixmatch.com/documentation


# How to extend the framework

## 1. Data Plugin

### data plugin interface

```java
String getInstructions()
```

Returns the specific instruction for each data plugin. The instruction will be displayed on the front-end to help the user know how to use data plugins.

```java
String getPluginName()
```

Returns the name of the specific data plugin. This will be displayed in the front-end when users select which plugin to use.

```java
List<String> getSongNames(String info)
```

Returns a list of song names extracted by the data plugin. String info is the user input to use the data plugin. Different data plugins expect different formats of user input.

```java
Void setApplication(MusicFramework framework)
```

Called (only once) when the data plugin is first registered with the framework, giving the data plugin a chance to perform any initial set-up before the framework has begun (if necessary). 

### Sample data plugins and their uses: 

- Billboard Hot 100 plugin: This data plugin extracts the hottest 100 songs from the ranking website Billboard on a specific date. The user needs to input a date to extract the hottest 100 songs on the date. If the user does not input anything, the latest ranking will be extracted. The date should not be later than today. If the date is not in the form of yyyy-mm-dd or the date is in the future, the data plugin will return 0 songs. 

- User manual input plugin: This data plugin extracts songs inside the user input. The user needs to input all the songs manually, and each song is separated by semicolon. If the user does not input anything, the data plugin will return 0 songs. 

## 2. Visual Plugin

Before writing the visual plugin, the programmer must understand the following data structures

```java
Attribute
```

contains information about all possible song attributes the framework provides (such as instrumentalness, liveness, speechness…). Calling toString() on an attribute will return the string format of the attribute for front-end rendering purposes. 

```java
SongName
```

represents the name of the song. Calling toString() will return the string version of the song name. We use this class instead of string so that later if we want to change the name of the song or the format on our end, it won’t affect the client’s implementation.

```java
AvgEmotionFactory
```

stores the average value of all attributes of all songs. Calling getAvgfor(Attribute attribute) will get the average value of the given attribute for all songs. Calling getEmotions will get all the types of attributes present

```java
EachEmotionFactory
```

stores all the attributes for each song. The function getSongNames() will return all song names in the framework. Calling getValueFor(SongName songName, Attribute emotion) will get the specific attribute value for the given song name.


### visual plugin interface
   
```java
String getPluginName()
```

Return the plugin name. This name will be shown in the front-end for users when selecting a plugin to use. The plugin name is defined with final type and cannot be changed by users.

```java
String draw(Emotion[] emotions, AvgEmotionFactory emotionsMap, EachEmotionFactory eachEmotionsMap)
```

This function will be called by the framework to supply information about the attributes of the songs in the framework, and it returns the Echart json string representation of the visualization for the frontend.

### Sample visual plugins and their uses: 

- Line Chart plugin: this plugin chooses the attributes of danceability, liveliness, speechiness, and valence, and utilizes the AvgAttributeFactory to compute their average values. These attributes are then plotted on the y-axis, while their respective names are displayed on the x-axis. The visualization is passed to the framework in EChart Json format.

- Multiple Pie Chart plugin: this plugin uses the eachEmotionsMap to extract the attributes of Positive, Neutral, and Negative for each song. The songs are then categorized as popular and not popular, and the number of songs in each category is tallied. Finally, a pie chart is generated for each category, representing the proportion of the attributes for the respective songs. The visualization is passed to the framework in EChart Json format.

## 3. Registering Plugins

To add a plugin, it is recommended to put data plugins under path back-end/src/main/java/edu/cmu/cs214/hw6/dataPlugins

And to put visual plugins under path 
back-end/src/main/java/edu/cmu/cs214/hw6/visualPlugins
Then add the plugin class names under their respective register file in back-end/src/main/resources/META-INF/services

# Data Processing

The framework uses machine learning by Stanford CoreNLP to conduct textual sentiment analysis. Song lyrics are provided by MusixMatch API, and the lyrics are passed to SentimentAnalyzer.java for sentiment analysis. For better time efficiency and performance, lyrics are passed in line by line in the function getLyricSentiment in Song.java. The result of the sentiment analysis ranges from very negative, negative, neutral, positive, very positive. The multiplePieChart visual plugin specifically renders results from this machine learning algorithm.





