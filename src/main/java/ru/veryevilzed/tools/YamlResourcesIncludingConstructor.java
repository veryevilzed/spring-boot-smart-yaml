package ru.veryevilzed.tools;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class YamlResourcesIncludingConstructor extends Constructor {

    public YamlResourcesIncludingConstructor(String basePath) {
        yamlConstructors.put(new Tag("!include"), new FilesImportConstruct(basePath));
        yamlConstructors.put(new Tag("!import"), new FilesImportConstruct(basePath));
    }

    private static class FilesImportConstruct extends AbstractConstruct {
        private String basePath;

        FilesImportConstruct(String basePath) {
            this.basePath = basePath;
        }

        public Object construct(Node node) {
            if (!(node instanceof ScalarNode)) {
                throw new IllegalArgumentException("Non-scalar !import: " + node.toString());
            }

            final ScalarNode scalarNode = (ScalarNode) node;
            final String value = scalarNode.getValue();

            String document;
            try {
                if (value.startsWith("/"))
                    document = FileUtils.readFileToString(Paths.get(value).toFile());
                else
                    document = FileUtils.readFileToString(Paths.get(basePath, value).toFile());
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (document != null) {
                final Yaml yaml = new Yaml(new YamlResourcesIncludingConstructor(basePath));
                return yaml.load(new ByteArrayInputStream(document.getBytes()));
            }
            return null;
        }
    }

}
