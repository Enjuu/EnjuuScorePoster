# EnjuuScorePoster - FR1

## Installing
To use it download the Latest Release, you don't need to compile it, because it has a Config

## Starting
To start you need to have Java on your Server/PC! Without Java it wouldn't work. The Basic Command for Start Java Files is
> java -jar enjuurankbot-r-snapshot.jar

## Custom Start
To use a custom definition to start the Java File look at that Documentation [Start Java Files with Custom Arguments](https://stackoverflow.com/questions/20149304/how-to-set-the-xmx-when-start-running-a-jar-file)

## Configuration
If you start it you get an ```config.json``` and a ```sqlconfig.json``` edit this File and it should look like that. Now edit the Config File. If you get an Error anyways please open a Issue
```json
{"web":"enjuu.click","name":"Enjuu","api":"enjuu.click","channelid":"466597888089391116","token":"NaN","status":"Listening to scores"}
```
```json
{"hostname":"localhost","database":"ripple","password":"","port":"3306","username":"root"}
```

## Libaries
* [RippleAPI-Java-LATEST](https://github.com/MarcPlaying/RippleAPI-Java) - Easy implementation of the RippleAPI
  * [RippleAPI](https://github.com/osuripple/api) - For get the Informations from Ripple
  * [JSON-Simple](https://github.com/fangyidong/json-simple) - Config and read informations from the RippleAPI-RestAPI
* [SoosBot](https://github.com/MarcPlaying/SoosBot-Discord-Bot) - Commandbase
  * [JDA](https://github.com/DV8FromTheWorld/JDA) - Discord Bots
