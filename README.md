# Introduction

This is a unofficial fork of GregTech-6,which aims to expend to technology tree, with better diffculty curve.

DON'T report bugs to greg or GregTech-6 forums UNLESS you sure it exists on GregTech-6

Will break saves of GregTech-6. NEVER UPGRADE YOUR GT6 SAVES!

# License

This Mod is licensed under the [GNU Lesser General Public License](LICENSE).
All assets, unless otherwise stated, are dedicated to the public domain
according to the [CC0 1.0 Universal Public Domain Dedication](src/main/resources/LICENSE.assets).
Any assets containing the [GregTech logo](src/main/resources/logos) or any
derivative of it are licensed under the
[Creative Commons Attribution-NonCommercial 4.0 International Public License](src/main/resources/LICENSE.logos).

# Dev Environment Setup

Once you have this project cloned then forge needs to be set up.  There are 2 options:

* If you want to just compile GT6 as quick as possible, then start by running `./gradlew setupCIWorkspace`.  This tasks just builds enough of Minecraft and Forge to be able to build Forge Mods, but not to do any development.
* If you want to compile GT6 and want the full decompiled and deobfuscated source code in the development environment and want to be able to run MC from within the development environment then run `./gradlew setupDevWorkspace setupDecompWorkspace`.

Once you have set up your environment then you can run the `assemble` task to build GT6 like:  `./gradlew assemble`

If you want to run the client then you can run the `runClient` task:  `./gradlew runClient`

Ditto with `runServer` for a server run.

To edit the code in an IDE just open the gradle project in IntelliJ or other decent IDE.  Use the `assemble` gradle task to build or `runClient` or `runServer` tasks to be able to actively debug the running the game.

On the commandline you can combine classes altogether, for example, to just build GT6 you can do this after a fresh clone:
```sh
./gradlew setupCIWorkspace assemble
```
And the file will be in `build/libs` as usual.

Can fully get a full dev environment, build, and run the client all at once from a fresh clone with:
```sh
./gradlew setupDevWorkspace setupDecompWorkspace assemble runClient
```

