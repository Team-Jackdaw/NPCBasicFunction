# NPC Basic Functions

> The mod is still under development, and the current version is not stable. If you encounter any problems, please
> submit an issue.

## 1. Introduction

NPC basic functions are a feature of the OpenAI API that allows ChatGPT to call functions that may affect game balance
as an NPC at the appropriate time. The functions are defined by the server administrator, and the effects that can be
achieved are determined by you. For a detailed introduction to this feature, please refer to
the [OpenAI API documentation](https://beta.openai.com/docs/api-reference/function-calls/create-function-call).

In this mod, we provide some implementation examples of basic functions that can be called by NPCs. These functions are
simple and can be used as a reference for you to create more complex functions.

## 2. Dependencies

- [ChatWithNPC](https://npchat.jackdaw.wdr.im/) >= 0.0.3
- [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) *
- Minecraft Server 1.19.4 or higher
- Fabric Loader 0.12.0 or higher

## 3. Basic Functions Provided

### 3.1. Give Diamond Function

The `give_diamond` function is used to give the player a certain number of diamonds. This function accepts a
parameter `number`, which represents how many diamonds to give to the player. The function is implemented by creating a
diamond item stack and giving it to the player.

### 3.2. Basic Action Function

The `basic_action` function is a generic function that can be used to perform NPC's basic actions in the game. It
accepts a parameter `action`, which represents the action to be performed. The function is implemented by executing the
specified action.

The basic actions that can be performed include:

- `walk to player`: The NPC walks towards the player.
- `escape`: The NPC escapes from the player.

### 3.3. Basic Feeling Function

The `basic_feeling` function is a generic function that can be used to express NPC's basic feelings in the game. It
accepts a parameter `feeling`, which represents the feeling to be expressed. The function is implemented by displaying
the actions below:

- `happy`: The NPC expresses happiness by displaying love particles.
- `shake head`: The NPC shakes its head to express disagreement.

## 4. Usage

To use these basic functions, you can add or remove them from the NPC closest to you using the following commands:

- `/npchat npc addFunction <function>` - Add a function to the NPC closest to you
- `/npchat npc deleteFunction <function>` - Remove a function from the NPC closest to you

and don't forget to save this setting by using the `/npchat saveAll` command.

## 5. Developer Notes

Developers can create their own custom functions by extending the `CustomFunction` class provided by the `ChatWithNPC`
API. For more information on creating custom functions, please refer to
the [Advanced Function Calling](https://npchat.jackdaw.wdr.im/docs/Advanced.html) documentation.

The [javadoc](https://npchat.doc.ussjackdaw.com) of the `chat-with-NPC` mod is available.

The npchat-api dependency configuration is as follows:

```groovy
repositories {
    maven {
        name = "Team-Jackdaw"
        url = uri("https://maven.ussjackdaw.com/repository/maven-releases/")
    }
}

dependencies {
    modImplementation "com.jackdaw:chat-with-NPC:${project.chat_with_npc_version}"
}
```

You can start with fork the source code of this mod and write your own custom functions base on this structure to
enhance the interaction between NPCs and players in your game.

