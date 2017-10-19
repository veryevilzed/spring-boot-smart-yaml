package ru.veryevilzed.tools.tests;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import ru.veryevilzed.tools.YamlResourcesIncludingConstructor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlIncludeTest {

    @Test
    public void testInclude() throws IOException {
        File file = Paths.get("examples","a.yml").toFile();
        final Yaml yaml = new Yaml(new YamlResourcesIncludingConstructor(file.getParent()));
        Map<String, Object> m = yaml.loadAs(FileUtils.openInputStream(file), Map.class);
        Assert.assertEquals(m.get("a"), 5);
        Assert.assertEquals(m.get("b").getClass(), LinkedHashMap.class);
        Assert.assertEquals( ((Map<String, Object>)m.get("b")).get("b1"), 7);
        Assert.assertEquals( ((Map<String, Object>)m.get("b")).get("b3").getClass(), LinkedHashMap.class);
        Assert.assertEquals( ((Map<String, Object>)((Map<String, Object>)m.get("b")).get("b3")).get("z"), 12);
        Assert.assertEquals( ((Map<String, Object>)m.get("c")).get("z"), 12);

    }
}
