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

Brise allows you to choose your Unit System. By default, Brise will display information using the Metric System. 



<h2>Commands List </h2>

<h5> Informations </h5>
As one would know, a city can have a multiple words name like San Francisco or Saint Petersburg. To avoid an error, please, replace the space by a "+". <br>
Example : New York -> New+York

<h5> Hello </h5>

```hello <city name> <country code> <state code>``` <br> <br>
Hello is a quick and simple command that will fetch simple weather information as the weather condition, the temperature,
the direction and speed of the wind and the humidity level.<br> <br>
/!\ Please note that you cannot save a city or fetch a save city using this command. /!\

<h5> Weather Information </h5>

<h6> Now </h6>

````weather now <city name> <country code> <state code>````

This command is quite similar to hello command but will get you more information in a more formal way. <br>

* Example <br>
```
 Brise > weather now Washington US DC

> Current Weather Condition : broken clouds
> Air Condition :
    Temperature : 20.6°C    | Humidity : 46
    Atmospheric Pressure : 1027 hPa
> Wind Condition :
    Speed : 5.54 kph  | Degree : 130.0 SW
> Precipitations :
    No precipitation in the last hour.

Current condition for Washington, DC
```

<h6> Today </h6>

````weather today <city name> <country code> <state code>````

This will fetch you the forecast for the same day.
* Example <br>
```
 Brise > weather today Caen

> Air Condition :
    Temperature :   10.6°C - 13.4°C
    Humidity :  74 - 87
    Atmospheric Pressure : 1012 hPa - 1013 hPa
> Wind Condition :
    Speed : 23.11 kph - 27.14 kph

Today's Forecast for Caen
```

<h6> Tomorrow </h6>

````weather tomorrow <city name> <country code> <state code>````

This will fetch you the forecast for the next day.
* Example <br>
```
weather tomorrow Montréal

> Air Condition :
    Temperature :   11.5°C - 14.3°C
    Humidity :  36 - 85
    Atmospheric Pressure : 1021 hPa - 1024 hPa
> Wind Condition :
    Speed : 3.71 kph - 14.22 kph

Tomorrow's Forecast for Montréal
```

<h5> Current Air Pollution Information </h5>

<h6> Now </h6>

````air <city name> <country code> <state code>````

This will get you the current air pollution information of a city.
* Example <br>
```
 Brise > air Paris

 > Molecules :
 CO : 193.6 μg/m3 | NO : 0.5 μg/m3
 NO2 : 2.51 μg/m3  | O3 : 75.1 μg/m3
 SO2 : 1.1 μg/m3 | NH3 : 0.59 μg/m3
 > Particulates :
 PM2.5 : 2.33 μg/m3 | PM10 : 2.66 μg/m3

> Air Quality : Good

Air Pollution Information for Paris
```

<h6>sub-commands</h6>

``air -names  -> Will fetch you all pollutants names.``<br>
``air -limits -> Will fetch you the colours code and limits according to the WHO.``

<h5> Key </h5>

```key <appId key>``` <br> <br>
This command is to be used when you want to change the AppId key from OpenWeatherMap. <br>
Once you've used this command, you'll be asked to restart the TUI to load the correct key. <br>

<h5> Help </h5>

```help```

The help command will list every command and sub-commands.

<h5> Settings </h5>

```settings```

Typing this command will make you enter the settings shell. It will display your API key, your city saved and the System Unit Brise used. <br>
Example with Caen saved :

````
    > City Saved :
City : Caen  | Country : N/A  | State : N/A
Latitute : 49.0833 | Longitude : -0.4167

    > Key Saved :
OpenWeatherMap : [Your Key will appear here]

    > Preference :
Unit : metric
````
<h6> sub-commands </h6>

```
key <appId key> -> To change your key
unit -> To change the System Unit used by Brise. Will show you the available list before you can choose.
```

<h2> License Information </h2>

This work is licensed under the Creative Commons Attribution-NonCommercial 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.


<br> <br>Made with ❤ and eun ch'tio peu d'crème fraiche!
