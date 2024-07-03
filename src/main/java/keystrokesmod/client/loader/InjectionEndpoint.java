package keystrokesmod.client.loader;


import keystrokesmod.Mahiro;
import keystrokesmod.client.main.Raven;
public class InjectionEndpoint {
    public static void tryLoad(String account, String token) {

        (Mahiro.INSTANCE.instance = new Mahiro()).init();

    }
}
