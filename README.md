# AgrouAgrou

A Werewolf game manager to play if you do not have the cards. 🐺

You will have the Game Master screen, where you can have your list and apply the actions done by the player.
And the player will have a screen where he can see his role in the game.

Note: The game is not fully implemented, so be aware of it.

We use: Clipboard API, Accelerometer API and network access (we start a gRPC server on your phone and other mobile devices connect to it).

## Project structure

- `app/` -> android app
- `common/` -> shared code between modules
- `dedicated_server/` -> [gRPC](https://grpc.io/) server library
- `protos/` -> [protobuf](https://protobuf.dev/) definitions for the gRPC services

## How to dev

To develop the mobile application, you need to install [Android Studio](https://developer.android.com/studio).
And you can start it normally like any other mobile application.

And if you want an emulator instance to act like a server (like the Game Master),
you want to interconnect multiple emulator instances, you need to do:

```shell
# connect to the emulator console
telnet localhost:5554

# authenticate (by reading the output) with:
auth <TOKEN>

# set up network redirection from emulator 50051 to host 50051 (port)
redir add tcp:50051:50051 
```

> [!TIP]
> You can now interact with your gRPC server in the emulator,
> with your Postman/Insomnia/etc by doing gRPC request on it!

## Credits

We use the illustration from the opensource project [github.com/Aqu1tain/LoupsGarousCerlin](https://github.com/Aqu1tain/LoupsGarousCerlin) of the Werewolf game.

Our license is [MIT LICENSED](./LICENSE).
