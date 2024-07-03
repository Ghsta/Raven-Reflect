package keystrokesmod.client.module.modules.movement;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;

import java.util.Iterator;

public class SlyPort extends Module {
    public static DescriptionSetting f;
    public static SliderSetting r;
    public static TickSetting b;
    public static TickSetting d;
    public static TickSetting e;
    private final boolean s = false;

    public SlyPort() {
        super("SlyPort", ModuleCategory.movement);
        this.registerSetting(f = new DescriptionSetting("Teleport behind enemies."));
        this.registerSetting(r = new SliderSetting("Range", 6.0D, 2.0D, 15.0D, 1.0D));
        this.registerSetting(e = new TickSetting("Aim", true));
        this.registerSetting(b = new TickSetting("Play sound", true));
        this.registerSetting(d = new TickSetting("Players only", true));
    }

    public void onEnable() {
        Entity en = this.ge();
        if (en != null) {
            this.tp(en);
        }

        this.disable();
    }

    private void tp(Entity en) {
        if (b.isToggled()) {
            // 播放 BLOCK_SHULKER_BOX_OPEN 声音
            SoundEvent soundEvent = SoundEvents.BLOCK_END_PORTAL_FRAME_FILL;
            PositionedSoundRecord soundRecord = PositionedSoundRecord.getMasterRecord(soundEvent, 1.0f);
            mc.getSoundHandler().playSound(soundRecord);
        }

        Vec3d vec = en.getLookVec();
        double x = en.posX - vec.x * 2.5D;
        double z = en.posZ - vec.z * 2.5D;
        mc.player.setPosition(x, mc.player.posY, z);
        if (e.isToggled()) {
            Utils.Player.aim(en, 0.0F, false);
        }

    }

    private Entity ge() {
        Entity en = null;
        double r = Math.pow(SlyPort.r.getInput(), 2.0D);
        double dist = r + 1.0D;
        Iterator var6 = mc.world.loadedEntityList.iterator();

        while(true) {
            Entity ent;
            do {
                do {
                    do {
                        do {
                            if (!var6.hasNext()) {
                                return en;
                            }

                            ent = (Entity)var6.next();
                        } while(ent == mc.player);
                    } while(!(ent instanceof EntityLivingBase));
                } while(((EntityLivingBase)ent).deathTime != 0);
            } while(SlyPort.d.isToggled() && !(ent instanceof EntityPlayer));

            if (!AntiBot.bot(ent)) {
                double d = mc.player.getDistance(ent);
                if (!(d > r) && !(dist < d)) {
                    dist = d;
                    en = ent;
                }
            }
        }
    }
}
