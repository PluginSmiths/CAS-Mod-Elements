package ${package}.config.serializer;

import me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.constructor.Constructor;

public class YamlSerializer extends ConfigSerializer {
    private static final Yaml yaml = new Yaml(new Constructor(Map.class));
    private static final String extension = "yaml";

    public YamlSerializer(String configFileName) {
        super(configFileName, extension);
    }

    @Override
    public void serialize(Map<String, Object> entries) {
        try (FileWriter writer = new FileWriter(this.getConfigFile())) {
            yaml.dump(entries, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> deserialize() {
        try (FileReader reader = new FileReader(this.getConfigFile())) {
            return yaml.load(reader);
        } catch (IOException e) {
            this.createConfigFile();
            return null;
        }
    }
}