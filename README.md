# Domnian IRC Bot
---

This is a small project that I started working on for the fun of it, but now it's being used as a support bot on my IRC network.

This bot makes use of the Schwering IRC Library, which can be found [here](https://github.com/java-irclib/irclib).  
I also have a fork of the library located [here](https://github.com/willies952002/irclib) which I will be working on updating to Java 8.
  
___Java 8 is required to run this bot as it makes use of features only available in Java 8.___


### Commands
The bot comes with various built-in commands:
* `!help` - Display the help message (May conflict with other bots)
* `!about` - Display information about the bot
* `!dhelp` - Alias command for !help
* `!restart` - Restart the running bot instance (Restricted) - WIP
* `!quit` - Shut down the running bot instance (Restricted)
* `!die` - Alias command for !quit (Restricted)

It is possible to write custom commands that can be installed into the `commands/` directory.
Here is the template for commands:

    import com.domnian.api.API;
    import com.domnian.command.BotCommand;
    import com.domnian.command.PermissionLevel;
    import org.schwering.irc.lib.IRCUser;

    public class Example extends BotCommand {
    
        String desc = "Some Usefull Description";
    
        public void execute(String chan, IRCUser user, String[] args, PermissionLevel level) {
            // Do Something
        }
    
    }

To compile the commands, which requires a JDK, first remove the old class files then run:

    javac -cp path/to/DomnianIRCBot.jar [class].java

### Command and Module API
There is an API provided at `com.domnian.api.API` which can be used to interact with the bot without importing lots of classes

##### Methods:
* `API.privateMessage(String target, String message)` - Send a message to either a nick or channel.
* `API.notice(String target, String message)` - Send a notice to either a nick or channel.
* `API.join(String channel)` - Join a Channel.
* `API.quit()` - Disconnect with the message "Bot Disconnecting".
* `API.quit(String message)` - Disconnect with a message.
* `API.getExecutor()` - Get the command executor (self) for the bot instance.
* `API.getVersion()` - Get the version of this bot.
* `API.loadFile(String path)` - Load a file and return it's contents as a `List<String>`
* `API.action(String target, String msg)` - Send a message to a nick or channel as if the bot did `/me [msg]`
