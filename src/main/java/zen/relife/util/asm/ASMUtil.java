package zen.relife.util.asm;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public final class ASMUtil {
    public static MethodNode getMethod(ClassNode cn, String name, String desc) {
        return cn.methods
                .stream()
                .filter(mn -> mn.name.equals(name) && mn.desc.equals(desc))
                .findFirst()
                .orElse(null);
    }
}
