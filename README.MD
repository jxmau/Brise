[![Generic badge](https://img.shields.io/badge/License-CC%204.0%20BY%20--%20NC-green)](https://creativecommons.org/licenses/by-nc/4.0/)
[![Generic badge](https://img.shields.io/badge/Release-V1.1.0-blueviolet)](https://github.com/jxmau/Brise/releases/tag/V1.0.0)
[![CodeFactor](https://www.codefactor.io/repository/github/jxmau/brise/badge)](https://www.codefactor.io/repository/github/jxmau/brise)

<h2>Brise </h2>

<b>Brise-tui</b> is a simple Terminal User Interface using OpenWeatherMap's Current Weather and Air Pollution APIs.

<h3>Ressources</h3>
<h5>Dependencies and Libraries Used</h5>

- Apache Http Component <br>
- Json Simple

<h5> APIs </h5>

- Current Weather <br>
- Air Pollution <br>
- 5 Days/ 3 Hours Forecast

<h3>Disclaimer</h3>
- This TUI use OpenWeatherMap's APIs, but it is not related in any way to it. <br>
- This TUI is for personal use only. <br>
- Information fetched by the TUI are for indications purpose only. <br>

<h2>Prerequisites</h2>

<h5> OpenWeatherMap appId key </h5>

Brise-TUI uses apis from OPM accessible in the free plan. <br>
While free, a key is required for each request from the TUI, that's why that the first time you'll launch Brise, you'll be asked to enter a key. <br>
Don't worry if you've made a mistake, you can modify your key with the "key" command below.

<h2>Customization</h2>

<h6> Saving a city </h6>

Brise allows you the possibility to save a city in your settings file. To save your city, you'll just need to add "-s" at the end of your command line. <br>

<h6> System Unit </h6>

Brise supports three types of Unit System, but you can also used a personalized one by changing unit directly in the unit shell.

| Unit System | Speed | Temperature | Precipitation | Air Pollution |
| ----------- | ----- | ----------- | ------------- | ------------- |
| Metric | Kilometers per hour (kph) | Celsius (°C) | Millimeters (mm) | Micrograms per cube meter(μgm3) |
| Metric | Miles per hour (mph) | Fahrenheit (°F) | Inches (in) | Micrograms per cube meter (μgm3) |
| Metric | Meters per second (ms) | Kelvin (°K) | Millimeters (mm) | Parts per million (ppm) |


<h2>Commands List </h2>

<h5> Information </h5>

<h6> City Name </h6>
As one would know, a city can have a multiple words name like San Francisco or Saint Petersburg. To avoid an error, please, replace the space by a "+". <br>
Example : New York -> New+York

<h6> State ISO code </h6>
Can be used only for US' cities. <br>
Example : Washington US DC.

<h4> Main shell </h4>

| Action               | Command Line                                  | Option | 
|----------------------|-----------------------------------------------|--------|
| Quick Weather Info   | hello [city] [country ISO Code] [State]       |        |
| Current Weather Info | weather now [city] [country ISO Code] [State] | -s     | 
| Today's Weather Forecast | weather today [city] [country ISO Code] [State] | -s     | 
| Tomorrow's Weather Forecast| weather tomorrow [city] [country ISO Code] [State] | -s     |
| Current Air Pollution Info | air [city] [country ISO Code] [State] | -s     | 
| Current Precipitation Info | rain now [city] [country ISO Code] [State] | -s     | 
| Today's Precipitation Forecast | rain today [city] [country ISO Code] [State] | -s     | 
| Tomorrow's Precipitation Forecast| rain tomorrow [city] [country ISO Code] [State] | -s     |
| Change the OWM AppId key | key [your key] | |
| View the commands' help | help | |
| Close Brise | exit | |
| Enter the settings shell | settings | |

<h4> Help sub-commands </h4>

| Action | Command Line | Details |
| ------ | ------------ | ------- |
| To view the name of pollutants | air -pollutants | |
| To view the code colour and limits per pollutants | air -limits | <a href="https://www.github.com/jxmau/Brise/tree/main/src/tech/weather/tools/LIMITS.MD"> A MD doc exists in the tools folder. </a>|


<h4> Settings shell </h4>

| Action | Command Line |
| ------ | ------------ |
| To exit the shell| exit |
| To change the key | key |
| To change unit - To enter unit shell| unit |

<h5> Unit shell </h5>
This shell does not possess a help command, but the list and explication of the key will be shown at all time.
<br>

| Action | Command Line | Possible choice |
| ------ | ------------ | --------------- |
| To change the Unit System| [Unit System] | metric - imperial - scientific
| To change the speed's unit | speed [unit] | kph - mph - ms
| To change the temperature's unit | temperature [unit] | celcius - fahrenheit - kelvin
| To change the Air Pollution's unit | speed [unit] | μgm3 - ppm
| To change the Precipitation's unit | speed [unit] | metric - imperial - scientific
| To exit | exit |




<h2> License Information </h2>

This work is licensed under the Creative Commons Attribution-NonCommercial 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.


<br> <br>Made with ❤ and eun ch'tio peu d'crème fraiche!
