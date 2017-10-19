package ru.veryevilzed.tools;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class YamlObjectMapper {

    public static Object load(File file) throws IOException {
        final Yaml yaml = new Yaml(new YamlResourcesIncludingConstructor(file.getParent()));
        return yaml.load(FileUtils.openInputStream(file));
    }

    public static <T> T loadAs(File file, Class<T> type) throws IOException {
        final Yaml yaml = new Yaml(new YamlResourcesIncludingConstructor(file.getParent()));
        return yaml.loadAs(FileUtils.openInputStream(file), type);
    }

    public static LinkedHashMap loadMap(File file) throws IOException {
        final Yaml yaml = new Yaml(new YamlResourcesIncludingConstructor(file.getParent()));
        return yaml.loadAs(FileUtils.openInputStream(file), LinkedHashMap.class);
    }

}
