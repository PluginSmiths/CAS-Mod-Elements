package ${package}.config;

import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import org.apache.logging.log4j.Level;

@Mod.EventBusSubscriber(modid = "${modid}", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ${JavaModName}ConfigInit {
    @SubscribeEvent
    public static void register(FMLConstructModEvent event) {
        event.enqueueWork(() -> ModLoadingContext.get().registerExtensionPoint(ConfigScreenFactory.class,
                () -> new ConfigScreenFactory(
                        (client, screen) -> ${JavaModName}Config.getConfigBuilder().setParentScreen(screen).build())));
    }

    @SubscribeEvent
    public static void clientSetup(FMLCommonSetupEvent event) {
        Map<String, Object> entries = ${JavaModName}Config.serializer.deserialize();

        ${JavaModName}Config.entries = entries == null ? new HashMap<>() : entries;
        ${JavaModName}Config.getConfigBuilder();
        ${JavaModName}Config.serializer.serialize(${JavaModName}Config.entries);

        ${JavaModName}.LOGGER.atLevel(Level.DEBUG).withMarker(MarkerManager.getMarker("CONFIG")).log(${JavaModName}Config.serializer.getMessage());
    }
}