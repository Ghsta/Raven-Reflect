package zen.relife.util;

import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class KeyboardUtils {

    // Copied from LWJGL keyboard class
    public static final Map<String, Integer> keyMap = new HashMap<String, Integer>(253);

    static {
        // Use reflection to find out key names
        Field[] fields = Keyboard.class.getFields();
        try {
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())
                        && Modifier.isPublic(field.getModifiers())
                        && Modifier.isFinal(field.getModifiers())
                        && field.getType().equals(int.class)
                        && field.getName().startsWith("KEY_")
                        && !field.getName().endsWith("WIN")) { /* Don't use deprecated names */

                    int key = field.getInt(null);
                    String name = field.getName().substring(4);
                    keyMap.put(name, key);
                }

            }
        } catch (Exception ignored) {
        }
    }
}
