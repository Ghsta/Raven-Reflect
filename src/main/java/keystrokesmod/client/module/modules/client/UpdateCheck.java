package keystrokesmod.client.module.modules.client;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import keystrokesmod.client.utils.version.Version;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UpdateCheck extends Module {
    public static DescriptionSetting howToUse;
    public static TickSetting copyToClipboard;
    public static TickSetting openLink;

    private Future<?> f;
    private final ExecutorService executor;
    private final Runnable task;

    public UpdateCheck() {
        super("Update", ModuleCategory.client);

        this.registerSetting(howToUse = new DescriptionSetting(Utils.Java.capitalizeWord("command") + ": update"));
        this.registerSetting(copyToClipboard = new TickSetting("Copy to clipboard", true));
        this.registerSetting(openLink = new TickSetting("Open dl in browser", true));

        executor = Executors.newFixedThreadPool(1);
        task = () -> {
            Version latest = Raven.versionManager.getLatestVersion();
            Version current = Raven.versionManager.getClientVersion();
            this.disable();
        };
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (f == null) {
            f = executor.submit(task);
            Utils.Player.sendMessageToSelf("Update check started !");
        } else if (f.isDone()) {
            f = executor.submit(task);
            Utils.Player.sendMessageToSelf("Update check started !");
        }
    }
}
