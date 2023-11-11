package zen.relife.loader;

import zen.relife.Relife;

// Reserved class for hot injection
public class InjectionEndpoint {
    public static void tryLoad(String account, String token) {
        // 发布的时候要检验账户w名和tkn (???)
        (Relife.INSTANCE.instance = new Relife()).init();
    }
}
