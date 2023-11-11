package zen.relife.module.impl.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;

public class ChatRemind extends Module {

    public ChatRemind() {
        super("ChatRemind", 0, Category.MISC, false);
    }

    @SubscribeEvent
    public void onUpdate(ServerChatEvent event) {
        if (event.getMessage().contains(Minecraft.getMinecraft().player.getName())) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                    new TextComponentString(ChatFormatting.RED + "[Relife]: " + event.getPlayer().getName() + "1"));
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f));
        }
    }
}
