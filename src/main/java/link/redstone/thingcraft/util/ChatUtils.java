package link.redstone.thingcraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

/**
 * Created by Erioifpud on 16/9/6.
 */
public class ChatUtils {
    public static void message(Object o) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(o.toString()));
    }

    public static void error(Object o) {
        message(String.format("\u00a7c\u00a7l[ERROR]\u00a7f\u00a7r %s", o.toString()));
    }
}
