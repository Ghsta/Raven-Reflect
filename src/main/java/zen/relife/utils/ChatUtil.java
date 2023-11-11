package zen.relife.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import zen.relife.Relife;

public class ChatUtil {
    private final TextComponentString message;

    private ChatUtil(TextComponentString message) {
        this.message = message;
    }

    /* synthetic */ ChatUtil(TextComponentString chatComponentText, ChatUtil chatUtils) {
        this(chatComponentText);
    }

    public static String addFormat(String message, String regex) {
        return message.replaceAll("(?i)" + regex + "([0-9a-fklmnor])", "\u00a7$1");
    }

    public void displayClientSided() {
        Minecraft.getMinecraft().player.sendChatMessage(String.valueOf(this.message));
    }

    private TextComponentString getChatComponent() {
        return this.message;
    }

    public static class ChatMessageBuilder {
        private static final TextFormatting defaultMessageColor = TextFormatting.WHITE;
        private final TextComponentString theMessage = new TextComponentString("");
        private boolean useDefaultMessageColor = false;
        private Style workingStyle = new Style();
        private TextComponentString workerMessage = new TextComponentString("");

        public ChatMessageBuilder(boolean prependDefaultPrefix, boolean useDefaultMessageColor) {
            if (prependDefaultPrefix) {
                Relife.INSTANCE.getClass();
                this.theMessage.appendSibling(new ChatMessageBuilder(false, false)
                        .setColor(TextFormatting.RED).build().getChatComponent());
            }
            this.useDefaultMessageColor = useDefaultMessageColor;
        }

        public ChatMessageBuilder() {
        }

        public ChatMessageBuilder appendText(String text) {
            this.appendSibling();
            this.workerMessage = new TextComponentString(text);
            this.workingStyle = new Style();
            if (this.useDefaultMessageColor) {
                this.setColor(defaultMessageColor);
            }
            return this;
        }

        public ChatMessageBuilder setColor(TextFormatting color) {
            this.workingStyle.setColor(color);
            return this;
        }

        public ChatMessageBuilder bold() {
            this.workingStyle.setBold(true);
            return this;
        }

        public ChatMessageBuilder italic() {
            this.workingStyle.setItalic(true);
            return this;
        }

        public ChatMessageBuilder strikethrough() {
            this.workingStyle.setStrikethrough(true);
            return this;
        }

        public ChatMessageBuilder underline() {
            this.workingStyle.setUnderlined(true);
            return this;
        }

        public ChatUtil build() {
            this.appendSibling();
            return new ChatUtil(this.theMessage, null);
        }

        private void appendSibling() {
            this.theMessage.appendSibling(this.workerMessage.setStyle(this.workingStyle));
        }
    }

}
