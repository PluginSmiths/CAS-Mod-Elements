package ${package}.config.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonSerializer extends ConfigSerializer {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String extension = "json";

    public GsonSerializer(String configFileName) {
        super(configFileName, extension);
    }

    @Override
    public void serialize(Map<String, Object> entries) {
        try (FileWriter writer = new FileWriter(this.getConfigFile())) {
            gson.toJson(entries, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> deserialize() {
        try (FileReader reader = new FileReader(this.getConfigFile())) {
            return gson.fromJson(reader, new TypeToken<Map<String, Object>>(){}.getType());
        } catch (IOException e) {
            this.createConfigFile();
            return null;
        }
    }
}