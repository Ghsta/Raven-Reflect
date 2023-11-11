package zen.relife.hook.impl;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import zen.relife.Relife;
import zen.relife.eventbus.handler.impl.Render2DEvent;
import zen.relife.hook.AbstractHook;
import zen.relife.hook.HookInfo;
import zen.relife.util.asm.ASMUtil;

@HookInfo(GuiIngame.class)
public class GuiIngameHook extends AbstractHook {
    public static void renderGameOverlayCall(ScaledResolution scaledResolution, float partialTicks) {
        Relife.INSTANCE.getEventBus().call(new Render2DEvent(scaledResolution, partialTicks));
    }

    @Override
    public void hook(ClassNode cn) {
        MethodNode renderGameOverlayMn = ASMUtil.getMethod(cn, "renderGameOverlay", "(F)V");

        InsnList list = new InsnList();
        list.add(new VarInsnNode(Opcodes.ALOAD, 2)); // load scaledresolution into stack
        list.add(new VarInsnNode(Opcodes.FLOAD, 1)); // load partialticks into stack
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, GuiIngameHook.class.getName().replace(".", "/"), "renderGameOverlayCall", "(L" + ScaledResolution.class.getName().replace(".", "/") + ";F)V", false));
        renderGameOverlayMn.instructions.insertBefore(renderGameOverlayMn.instructions.getLast().getPrevious(), list);
    }
}
