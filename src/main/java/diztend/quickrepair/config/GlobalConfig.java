package diztend.quickrepair.config;

import diztend.quickrepair.Quickrepair;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class GlobalConfig {

    private final LinkedHashMap<String, String> configs;

    public GlobalConfig(String fileName, LinkedHashMap<String, String> defaultFields) {
        this.configs = defaultFields;
        File file = FabricLoader.getInstance().getConfigDir().resolve(fileName).toFile();
        try {
            if (file.createNewFile()) {
                Quickrepair.log("config file created");
                FileWriter writer = new FileWriter(file, true);
                for (String field : defaultFields.keySet()) {
                    writer.write(field + "=" + defaultFields.get(field));
                    writer.write("\n");
                    Quickrepair.log("config " + field + " created");
                }
                writer.close();
            } else {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().split("=");
                    if (line.length == 2 && configs.containsKey(line[0])) {
                        configs.put(line[0], line[1]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getConfig(String key) {
        return configs.get(key);
    }

    public static class Builder {

        private final String name;
        private final LinkedHashMap<String, String> configFields = new LinkedHashMap<>();
        public Builder(String name) {
            this.name = name;
        }

        public Builder addField(String name, String defaultVal) {
            configFields.put(name, defaultVal);
            return this;
        }

        public GlobalConfig build() {
            return new GlobalConfig(name + ".txt", configFields);
        }

    }

}
