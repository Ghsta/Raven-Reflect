package keystrokesmod.client.module.modules.world;

import keystrokesmod.client.module.Module;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatLogger extends Module {
    private final File dir;
    private File chatLog;
    public String fileName;
    public String extension;

    public ChatLogger() {
        super("Chat Logger", ModuleCategory.world);

        extension = "txt";
        dir = new File(mc.mcDataDir, "keystrokes" + File.separator + "logs");
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void onEnable() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        fileName = dtf.format(now) + "." + extension;
        this.chatLog = new File(dir, fileName);
        if (!chatLog.exists()) {
            try {
                chatLog.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onEnable();
    }

    @SubscribeEvent
    public void onMessageRecieved(ClientChatReceivedEvent c) {
        try(FileWriter fw = new FileWriter(this.chatLog.getPath(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(c.getMessage().getUnformattedText());
        } catch (IOException e) {
           //shit
        }
    }
}
