package zen.relife.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;

public class Game {
    public static EntityPlayerSP Player() {
        return Game.Minecraft().player;
    }

    public static WorldClient World() {
        return Game.Minecraft().world;
    }

    public static Minecraft Minecraft() {
        return Minecraft.getMinecraft();
    }
}

