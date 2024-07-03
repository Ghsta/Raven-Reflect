package keystrokesmod.client.utils;

import net.minecraft.util.text.ITextComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ChatMessage {
    private String text;

    public ChatMessage(String message) {
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

    public void sendIText(String text) {
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
    public ITextComponent shallowCopy() {
        return (ITextComponent) new ChatMessage(this.text);
    }
}
