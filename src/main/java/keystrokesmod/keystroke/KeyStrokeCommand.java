//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.keystroke;

import keystrokesmod.Mahiro;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class KeyStrokeCommand extends CommandBase {
   public String getCommandName() {
      return "keystrokesmod";
   }

   public void processCommand(ICommandSender sender, String[] args) {
      Mahiro.toggleKeyStrokeConfigGui();
   }

   public String getCommandUsage(ICommandSender sender) {
      return "/keystrokesmod";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public boolean canCommandSenderUseCommand(ICommandSender sender) {
      return true;
   }

   @Override
   public String getName() {
      return "keystrokesmod";
   }

   @Override
   public String getUsage(ICommandSender sender) {
      return null;
   }

   @Override
   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

   }
}
