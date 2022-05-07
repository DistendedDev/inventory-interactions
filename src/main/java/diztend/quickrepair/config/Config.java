package diztend.quickrepair.config;

import diztend.quickrepair.Quickrepair;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Config {

    private final HashMap<String, String> configs = new LinkedHashMap<>();

    public Config(String fileName, HashMap<String, String> defaultFields) {
        File file = FabricLoader.getInstance().getConfigDir().resolve(fileName).toFile();
        try {
            if (file.createNewFile()) {
                Quickrepair.log("config file created");
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] config = line.split("#")[0].split("=");
                if (config.length == 2 && !line.startsWith("#")) {
                    configs.put(config[0].trim(), config[1].trim());
                }
            }
            scanner.close();
            boolean flag = false;
            FileWriter writer = new FileWriter(file, true);
            for (String field : defaultFields.keySet()) {
                if (!configs.containsKey(field)) {
                    configs.put(field, defaultFields.get(field));
                    if (!flag) {
                        writer.write("\n");
                        flag = true;
                    }
                    writer.write(field + "=" + defaultFields.get(field));
                    writer.write("\n");
                    Quickrepair.log("config " + field + " created");
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getConfig(String key) {
        return configs.get(key);
    }

    public static class Builder {

        private final String name;
        private final HashMap<String, String> configFields = new LinkedHashMap<>();

        public Builder(String name) {
            this.name = name;
        }

        public Builder addField(String name, String defaultVal) {
            configFields.put(name, defaultVal);
            return this;
        }

        public Config build() {
            return new Config(name + ".txt", configFields);
        }

    }

}
