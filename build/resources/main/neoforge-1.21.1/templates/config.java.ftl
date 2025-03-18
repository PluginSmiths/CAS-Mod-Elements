package ${package}.config;

import me.shedaniel.clothconfig2.api.ConfigCategory;

public class ${JavaModName}Config {
    <#if data.fileExtension == "Json">
        public static final GsonSerializer serializer = new GsonSerializer("${data.configFileName}");
    <#elseif data.fileExtension == "Toml">
        public static final TomlSerializer serializer = new TomlSerializer("${data.configFileName}");
    <#else>
        public static final YamlSerializer serializer = new YamlSerializer("${data.configFileName}");
    </#if>
    public static Map<String, Object> entries = new HashMap<>();

    public static ConfigBuilder getConfigBuilder() {
        ConfigBuilder builder = ConfigBuilder.create().setTitle(Component.literal("${JavaModName} Config"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        <#compress>
            ${code}
        </#compress>

        builder.setSavingRunnable(() -> serializer.serialize(entries));

        return builder;
    }

    public static void setValue(String key, Object newValue) {
        entries.put(key, newValue);
        getConfigBuilder();
        serializer.serialize(entries);
    }
}