package ${package}.config.serializer;

import me.shedaniel.cloth.clothconfig.shadowed.com.moandjiezana.toml.TomlWriter;

public class TomlSerializer extends ConfigSerializer {
    private static final TomlWriter toml = new TomlWriter();
    private static final String extension = "toml";

    public TomlSerializer(String configFileName) {
        super(configFileName, extension);
    }

    @Override
    public void serialize(Map<String, Object> entries) {
        try (FileWriter writer = new FileWriter(this.getConfigFile())) {
            toml.write(entries, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> deserialize() {
        try (FileReader reader = new FileReader(this.getConfigFile())) {
            Toml parser = new Toml().read(reader);
            return parser.toMap();
        } catch (IOException e) {
            this.createConfigFile();
            return null;
        }
    }
}