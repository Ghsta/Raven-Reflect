package keystrokesmod.client.module.modules.other;

import com.mojang.realmsclient.gui.ChatFormatting;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.utils.Util;
import keystrokesmod.client.utils.Utils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FakeChat extends Module {
   public static DescriptionSetting a;
   public static String msg = "&eThis is a fake chat message.";
   public static final String command = "fakechat";
   public static final String c4 = "&cInvalid message.";

   public FakeChat() {
      super("Fake Chat", ModuleCategory.other);
      this.registerSetting(a = new DescriptionSetting(Utils.Java.capitalizeWord("command") + ": " + command + " [msg]"));
   }

   public void onEnable() {
      if (msg.contains("\\n")) {
         String[] split = msg.split("\\\\n");

         for (String s : split) {
            FakeChat.ChatMessage.sendSilentMessage(s);
         }
      } else {
         FakeChat.ChatMessage.sendSilentMessage(msg);
      }

      this.disable();
   }


   public static class ChatMessage
           extends TextComponentBase {
      private final String text;

      public ChatMessage(String text) {
         Pattern pattern = Pattern.compile("&[0123456789abcdefrlosmk]");
         Matcher matcher = pattern.matcher(text);
         StringBuffer stringBuffer = new StringBuffer();
         while (matcher.find()) {
            String replacement = matcher.group().substring(1);
            matcher.appendReplacement(stringBuffer, replacement);
         }
         matcher.appendTail(stringBuffer);
         this.text = stringBuffer.toString();
      }

      private void sendMessage(String message) {
         sendSilentMessage("[Keystrokes]" + ChatFormatting.GRAY + message);
      }

      private static void sendSilentMessage(String message) {
         if (fullNullCheck()) {
            return;
         }
         Util.mc.player.sendMessage(new ChatMessage(message));
      }

      public static boolean fullNullCheck() {
         return Util.mc.player == null || Util.mc.world == null;
      }


      public String getUnformattedComponentText() {
         return this.text;
      }

      public ITextComponent createCopy() {
         return null;
      }

      public ITextComponent shallowCopy() {
         return new ChatMessage(this.text);
      }
   }
}