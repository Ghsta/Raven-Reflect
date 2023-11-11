package zen.relife.module.impl.render;


import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.IntegerSetting;

public class AttackEffect extends Module {
    private static final EnableSetting Redstone = new EnableSetting("Redstone", false);
    private static final EnableSetting Magic = new EnableSetting("Magic", false);
    private static final EnableSetting Critical = new EnableSetting("Critical", false);
    private static final EnableSetting Cloud = new EnableSetting("Cloud", false);
    private static final EnableSetting Flame = new EnableSetting("Flame", false);
    private static final EnableSetting Spell = new EnableSetting("Spell", false);
    private static final EnableSetting SpellWitch = new EnableSetting("SpellWitch", false);
    private static final EnableSetting Totem = new EnableSetting("Totem", false);
    private static final EnableSetting Happy = new EnableSetting("Happy", false);
    private static final EnableSetting Angry = new EnableSetting("Angry", false);
    private static final EnableSetting Water = new EnableSetting("Water", false);
    private static final EnableSetting Barrier = new EnableSetting("Barrier", false);
    private static final EnableSetting Snowball = new EnableSetting("Snowball", false);
    private static final EnableSetting Heart = new EnableSetting("Heart", false);
    private static final EnableSetting Portal = new EnableSetting("Portal", false);
    private static final EnableSetting X2 = new EnableSetting("X2", false);
    private static final EnableSetting Mode = new EnableSetting("Mode", false);
    public static IntegerSetting Amount = new IntegerSetting("Amount", 10, 1, 15, 1);

    public AttackEffect() {
        super("AttackEffect", Keyboard.KEY_NONE, Category.OTHER, false);
        this.getSetting().add(Mode);
        this.getSetting().add(X2);
        this.getSetting().add(Amount);
        this.getSetting().add(Redstone);
        this.getSetting().add(Critical);
        this.getSetting().add(Magic);
        this.getSetting().add(Cloud);
        this.getSetting().add(Flame);
        this.getSetting().add(Spell);
        this.getSetting().add(SpellWitch);
        this.getSetting().add(Totem);
        this.getSetting().add(Happy);
        this.getSetting().add(Angry);
        this.getSetting().add(Water);
        this.getSetting().add(Barrier);
        this.getSetting().add(Snowball);
        this.getSetting().add(Heart);
        this.getSetting().add(Portal);
    }

    @SubscribeEvent
    public void onUpdate(AttackEntityEvent e) {
        String mode = Mode.name;
        int amount = (int) Amount.getCurrent();
        boolean x2 = X2.getEnable();

        if (Redstone.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, e.getTarget().posX, e.getTarget().posY + e.getTarget().height - 0.75, e.getTarget().posZ, 0, 0, 0, Block.getStateId(Blocks.REDSTONE_BLOCK.getDefaultState()));
                if (x2) {
                    mc.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, e.getTarget().posX, e.getTarget().posY + e.getTarget().height - 0.75, e.getTarget().posZ, 0, 0, 0, Block.getStateId(Blocks.REDSTONE_BLOCK.getDefaultState()));
                }
            }
        }

        if (Critical.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.player.onCriticalHit(e.getTarget());
                if (x2) {
                    mc.player.onCriticalHit(e.getTarget());
                }
            }
        }

        if (Magic.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.CRIT_MAGIC);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.CRIT_MAGIC);
                }
            }
        }

        if (Cloud.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.CLOUD);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.CLOUD);
                }
            }
        }

        if (Flame.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.FLAME);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.FLAME);
                }
            }
        }

        if (Spell.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.SPELL);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.SPELL);
                }
            }
        }

        if (SpellWitch.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.SPELL_WITCH);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.SPELL_WITCH);
                }
            }
        }

        if (Totem.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.TOTEM);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.TOTEM);
                }
            }
        }

        if (Happy.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.VILLAGER_HAPPY);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.VILLAGER_HAPPY);
                }
            }
        }

        if (Angry.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.VILLAGER_ANGRY);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.VILLAGER_ANGRY);
                }
            }
        }

        if (Water.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.WATER_SPLASH);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.WATER_SPLASH);
                }
            }
        }

        if (Barrier.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, e.getTarget().posX, e.getTarget().posY + e.getTarget().height - 0.75, e.getTarget().posZ, 0, 0, 0, Block.getStateId(Blocks.BARRIER.getDefaultState()));
                if (x2) {
                    mc.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, e.getTarget().posX, e.getTarget().posY + e.getTarget().height - 0.75, e.getTarget().posZ, 0, 0, 0, Block.getStateId(Blocks.BARRIER.getDefaultState()));
                }
            }
        }

        if (Snowball.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.SNOWBALL);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.SNOWBALL);
                }
            }
        }

        if (Heart.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.HEART);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.HEART);
                }
            }
        }

        if (Portal.getEnable()) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.PORTAL);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.PORTAL);
                }
            }
        }
    }
}
