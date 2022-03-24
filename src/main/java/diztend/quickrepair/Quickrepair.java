package diztend.quickrepair;

import diztend.quickrepair.config.ModConfigs;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Quickrepair implements ModInitializer {

    public static String MOD_ID = "quickrepair";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModConfigs.registerConfigs();
    }
}
