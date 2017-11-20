# RPChannels

**What's this plugin ?**

RPChannels is a plugin that allow you to set custom channels in your server chat. You just have to edit the JSON config file !

**How can I custom my channels ?**

You have to edit the chat.json file in the plugin folder.
Let's explain this with three simple examples which are implemented by default.

```json
{
	"channels":[
		{
			"prefix":"default",
			"range":20,
			"format":"%a &f<&7says&f> &a%m",
			"sources":[
				"*"
			],
			"destinations":[
				"*"
			],
			"everyworld":false,
			"target":false
		},
		{
			"prefix":"?",
			"range":-1,
			"format":"%a &f<&7asks&f> &5%m",
			"sources":[
				"*"
			],
			"destinations":[
				"admin",
				"modo"
			],
			"everyworld":true,
			"target":false
		},
		{
			"prefix":"?>",
			"range":-1,
			"format":"%a &7-> &f%t &f<&7answers&f> &5%m",
			"sources":[
				"admin",
				"modo"
			],
			"destinations":[
				"*"
			],
			"everyworld":true,
			"target":true
		}
	]
}
```

This is an example of chat.json file.  
  
The first channel is the default one, as shown by the prefix.  
When a player send a message without any prefix, it's in this channel.  
All players in a radius of 20 blocks can hear him.  
The format will be explained later.  
Every group can write a message in this channel and every group can read.  
A player in another world can't see the channel.  
The message isn't for a specific player.  
  
The second channel is a support.  
When a player send a message which start with ?, it's sended here.  
All players can hear him without any radius.  
Every group can send a message in this channel but only the players who are in admin group or modo group can read.  
We can see the messages from another world.  
The message isn't for a specific player.  
  
The third channel is the support response.  
When a player send a message which start with ?>, it's sended here.  
All players can hear him without any radius.  
Only the players who are in admin group or modo group can send a message but every group can read.  
We can see the messages from another world.  
The message is for a specific player, so the raw message should be something like : ?> pseudo The message you want to send.  
  
Now, we're going to explain the format row.  
The plugin will take the format line and replace the following elements :  
%a -> sender name  
%t -> target name (if there is one)  
%m -> message  
& + number -> minecraft color associated.  
So with the third example we have :  

![support reponse example](https://image.prntscr.com/image/5vB0WGNYTja3uVjmV-tscg.png)

And if you set the radius to -1, it will not set any radius.  
  
Try to add your own channels now :)

**Plugin commands**

There's only one command :

/group <Player> <Group>

"/group Steve admin" will add the player Steve in the group admin.