package zen.relife.module.impl.render;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.lwjgl.input.Keyboard;
import zen.relife.module.Category;
import zen.relife.module.Module;

public class FakePlayer extends Module {
    EntityOtherPlayerMP fakePlayer;

    public FakePlayer() {
        super("FakePlayer", Keyboard.KEY_NONE, Category.RENDER, false);
    }

    @Override
    public void onEnable() {
        fakePlayer = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
        fakePlayer.setEntityId(-1882);
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(fakePlayer.getEntityId(), fakePlayer);
    }

    @Override
    public void disable() {
        mc.world.removeEntityFromWorld(fakePlayer.getEntityId());
    }
}

