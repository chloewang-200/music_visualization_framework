# Domain

### General Description
The framework functions as a music sentiment visualizer, it will accept the name of one or multiple songs and provide visualization of the emotions expressed through sentiment analysis. 

<img width="305" alt="Screenshot 2023-04-06 at 19 41 00" src="https://user-images.githubusercontent.com/84855841/230514539-5ba5ad16-4912-4f22-8baf-b6c0d893a319.png">

### Data Plugin
Data plugins may include options for users to manually input song names on the front end or provide a TXT file. Another option is for the clients to input a url to their playlist, and use APIs to fetch the names of songs in the specific playlist and analyze the sentiment of the entire list. Alternatively, the plugin could use APIs to fetch data from a music ranking website, such as the Billboard Top 100 most popular songs of 2023. 

### Visualization Plugin
Visualization plugins may include generating a bar chart of the distribution of different emotions of all the input songs, a series of pie charts showing the amount of each emotion in each song, or a timeline showing how the dominant emotion of songs changed throughout the years.

# Generality vs specificity 

Introduction

One of the critical design decisions in the development of our music visualization framework is determining the appropriate balance between generality and specificity. This document aims to outline how our framework achieves this balance to create a flexible yet focused solution.

Objective

The aim of our framework is to visualize the information of music pieces, providing meaningful visualizations that aid in understanding the characteristics and features of the music. To achieve this objective, we have made deliberate design decisions regarding the generality and specificity of the framework.

### Generality (allow multiple plugin)
The framework is designed to be adaptable and flexible, allowing for different types of music pieces and analysis. The framework will accept input as names of music, providing flexibility in how music information is provided to the system. For example, one could manually write a txt file containing all the music names, or one could get the music names by fetching from online resources, such as a specific Spotify playlist, a Billboard ranking, youtube music playlist and so on. This generality in input formats allows for ease of use and reuse, as users can input music data in various ways, making the framework adaptable to different scenarios.

### Specificity (has reuse value)
While the framework is designed to be general in terms of input formats, it also incorporates specific functionalities tailored to its objective of music visualization. The framework utilizes the Spotify API, making it compatible only with music visualization that uses song names as input and focuses primarily on sentiment analysis of those songs. As a result, it offers significant value for reusing sentiment analysis of music data, enabling users to process sentiment data by year, artist, playlist, album, and other parameters. These specific functionalities are carefully designed to provide meaningful and valuable results for visualizations, ensuring that the framework fulfills its intended purpose effectively.

Conclusion

In conclusion, our music visualization framework strikes a balance between generality and specificity. It is designed to be adaptable and flexible, accommodating to different sources of music & songs with the input in the format of name of songs. However, it also includes specific functionalities that are tailored to its objective of music visualization, providing meaningful and valuable results. This balance allows our framework to be versatile, reusable, and effective in achieving its intended purpose.

# Project structure

![objectModel](https://user-images.githubusercontent.com/84855841/230526555-8f371c7e-d21b-4668-867f-b14efbfc4461.png)

Our music visualization framework supports two kinds of plugins, data plugins and visualization plugins. Data plugins are responsible for importing song names to the framework as a list. We choose three kinds of data sources at present, extracting from playlists, reading from .txt files, and extracting from the most popular music chart in external websites (e.g. Billboard Hot 100). Visualization plugins are responsible for creating a specific type of chart for a selected dataset. We choose three kinds of charts for data visualization at this stage, stacked line charts, bar charts, and pie charts. The music visualization framework takes charge of sending a list of song names to Spotify API and then obtaining JSON data containing all of the information about this song list. Since the outputting JSON data for each song is like a dictionary (e.g. {‘artist’: ‘Taylor Swift’, ‘danceability’: 0.78}), we create a record class Song to store all the values. Every time before HTTP connecting request, plugins are registered inside the framework by calling the function setApplication(app: SongAnalysisApp). 

<img width="725" alt="Screenshot 2023-04-06 at 22 08 14" src="https://user-images.githubusercontent.com/84855841/230526328-9d9e5985-8221-46e2-80f5-9f48558571a0.png">

# Plugin interfaces

### Data Plugin Interface

<img width="292" alt="Screenshot 2023-04-06 at 22 09 26" src="https://user-images.githubusercontent.com/84855841/230526434-7f0c4d20-8d45-4e4c-9d92-3915f5814d04.png">

Data plugins are responsible for extracting a list of song names from inputting sources, such as a personal playlist in music applications, the most popular music charts in external websites, user manual input and so on. The extracted song names are stored in a list. There are three useful methods in the interface. The getPluginName() method returns the name of the specific data plugin, which will be further utilized in the front-end. The getSongsName() method returns the most important output of data plugins, a list of song names, which will be processed in the framework. The setApplication() method is called inside the framework to register data plugins with the framework.

### Visualization Plugin Interface

<img width="393" alt="Screenshot 2023-04-06 at 22 09 07" src="https://user-images.githubusercontent.com/84855841/230526401-476e9f4c-634c-47e5-9237-9a722b0a5c25.png">

Visualization plugins are responsible for creating a specific type of chart for a selected dataset. Various kinds of visualization methods can be used to implement the interface, such as stacked line charts, pie charts, bubble charts, heat maps, and even 3D diagrams. We can select different datasets by different elements, such as artists, genres, emotions, year of release, and so on. The selected dataset is stored in a HashTable in the form of labels and counting values. There are several useful methods in the interface. The getPluginName() method returns the name of the specific visualization plugin, which will be further utilized in the front-end. The getSelectedFeature() method returns a dataset selected by the element. The addXAxis() method and the addYAxis() method set the labels and the values of the chart, respectively. The setTitle() method sets the title of the chart. The render() method generates the most significant output of visualization plugins, JSON data containing all of the information required to draw a chart in the front-end. The setApplication() method is called inside the framework to register visualization plugins with the framework. 
