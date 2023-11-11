package zen.relife.util;

import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;

public class FriendsUtil {
    private static final ArrayList<EntityPlayer> friends = new ArrayList<>();

    public static void addFriend(EntityPlayer player) {
        friends.add(player);
    }

    public static void removeFriend(EntityPlayer player) {
        friends.remove(player);
    }

    public static boolean isFriend(EntityPlayer player) {
        for (EntityPlayer p : friends) {
            if (p.getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }
}
