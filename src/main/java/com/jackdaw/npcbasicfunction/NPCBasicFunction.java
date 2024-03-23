package com.jackdaw.npcbasicfunction;

import com.jackdaw.chatwithnpc.openaiapi.function.FunctionManager;
import com.jackdaw.npcbasicfunction.function.BasicActionFunction;
import com.jackdaw.npcbasicfunction.function.BasicFeelingFunction;
import com.jackdaw.npcbasicfunction.function.GiveDiamondFunction;
import net.fabricmc.api.ModInitializer;

public class NPCBasicFunction implements ModInitializer {
    @Override
    public void onInitialize() {
        FunctionManager.registerFunction("give_diamond", new GiveDiamondFunction());
        FunctionManager.registerFunction("basic_feeling", new BasicFeelingFunction());
        FunctionManager.registerFunction("basic_action", new BasicActionFunction());
    }
}
