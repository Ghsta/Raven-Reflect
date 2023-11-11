package zen.relife.hook.impl;

import net.minecraft.client.settings.KeyBinding;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import zen.relife.hook.AbstractHook;
import zen.relife.hook.HookInfo;
import zen.relife.util.asm.ASMUtil;

@HookInfo(KeyBinding.class)
public final class KeyBindingHook extends AbstractHook {
    public static void onKeyCall(int key) {

    }

    @Override
    public void hook(ClassNode cn) {
        MethodNode onTickMn = ASMUtil.getMethod(cn, "onTick", "(I)V");

        InsnList list = new InsnList();
        list.add(new VarInsnNode(Opcodes.ILOAD, 0));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, this.getClass().getName().replace(".", "/"), "onKeyCall", "(I)V"));
        onTickMn.instructions.insertBefore(onTickMn.instructions.get(1), list);
    }
}
