package com.jackdaw.npcbasicfunction.function;

import com.jackdaw.chatwithnpc.conversation.ConversationHandler;
import com.jackdaw.chatwithnpc.openaiapi.function.CustomFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class GiveDiamondFunction extends CustomFunction {

    public GiveDiamondFunction() {
        description = "This function is used to give player a diamond. You can give player diamonds if you want.";
        properties = Map.of(
                "number", Map.of(
                        "type", "integer",
                        "description", "the number of diamonds to give to the player."
                )
        );
    }

    @Override
    public Map<String, String> execute(@NotNull ConversationHandler conversation, @NotNull Map<String, Object> args) {
        int number;
        try {
            number = (int) args.get("number");
        } catch (ClassCastException ignore) {
            try{
                double doubleNumber = Double.parseDouble(args.get("number").toString());
                number = (int) doubleNumber;
            } catch (NumberFormatException ignore2) {
                number = 1;
            }
        }
        ItemStack diamond = new ItemStack(Items.DIAMOND, number);
        conversation.getNpc().findNearbyPlayers(10).forEach(player -> player.giveItemStack(diamond));
        return Map.of("status", "success");
    }
}
