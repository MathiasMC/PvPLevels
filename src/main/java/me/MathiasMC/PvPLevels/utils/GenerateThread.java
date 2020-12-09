package me.MathiasMC.PvPLevels.utils;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;

import javax.script.ScriptException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateThread extends Thread {

    private final PvPLevels plugin;

    private final String group;
    private final long amount;
    private final long startLevel;

    final FileConfiguration config;
    final FileConfiguration levels;
    final FileConfiguration execute;

    public GenerateThread(final PvPLevels plugin, final String group, final long amount, final long startLevel) {
        this.plugin = plugin;
        this.config = plugin.getFileUtils().config;
        this.levels = plugin.getFileUtils().levels;
        this.execute = plugin.getFileUtils().execute;
        setName("PvPLevels-Thread");
        this.group = group;
        this.amount = amount;
        this.startLevel = startLevel;
    }

    public void run() {
        plugin.isGenerate = true;
        plugin.getTextUtils().warning("[Generate] Be sure to check the level system, when finished to check that everything, was added correct.");
        plugin.getTextUtils().info("[Generate] Started task...");
        long lastXP = 0;
        final String math = config.getString("generate.math");
        long size = amount + startLevel;
        if (size == amount) {
            size++;
        }
        final int percent10 = (int) (size * (10.0f / 100.0f));
        final int percent25 = (int) (size * (25.0f / 100.0f));
        final int percent50 = (int) (size * (50.0f / 100.0f));
        final int percent75 = (int) (size * (75.0f / 100.0f));
        final long random = plugin.getCalculateManager().randomNumber(config.getLong("generate.random.min"), config.getLong("generate.random.max"));
        boolean startUP = false;
        levels.set(group + ".execute", group);
        execute.set(group + ".xp.get", config.getStringList("generate.get"));
        execute.set(group + ".xp.item", config.getStringList("generate.item"));
        execute.set(group + ".xp.boost", config.getStringList("generate.boost"));
        execute.set(group + ".xp.both", config.getStringList("generate.both"));
        execute.set(group + ".xp.lose", config.getStringList("generate.lose"));
        execute.set(group + ".level.up", config.getStringList("generate.up"));
        execute.set(group + ".level.down", config.getStringList("generate.down"));
        final LinkedHashMap<Integer, Integer> mapList = new LinkedHashMap<>();
        for (String get : config.getConfigurationSection("generate.percent").getKeys(false)) {
            mapList.put((int) (size * (Float.parseFloat(get) / 100.0f)), Integer.parseInt(get));
        }
        for (long i = startLevel; i < size; i++) {
            try {
                if (startUP) {
                    lastXP = config.getLong("generate.start");
                    startUP = false;
                }
                long randomNumber = 0;
                final String Srandom = getRandom(math);
                if (Srandom != null) {
                    final String[] split = Srandom.split("_");
                    randomNumber = plugin.getCalculateManager().randomNumber(Long.parseLong(split[0]), Long.parseLong(split[1]));
                }
                final Object object = plugin.getScriptEngine().eval(math.replace("{lastXP}", String.valueOf(lastXP)).replace("{level}", String.valueOf(i)).replace("{random}", String.valueOf(random)).replace("[" + Srandom + "]", String.valueOf(randomNumber)));
                for (Map.Entry<Integer, Integer> s : mapList.entrySet()) {
                    if (i <= s.getKey()) {
                        setLevelPath(new String[]{"prefix", "suffix", "group", "execute"}, "generate.percent." + s.getValue(), i);
                        break;
                    }
                }
                if (config.contains("generate.levels." + i)) {
                    final String executeS = config.getString("generate.levels." + i + ".execute").replace("{group}", group).replace("{level}", String.valueOf(i));
                    levels.set(group + "." + i + ".execute", executeS);
                    execute.set(executeS + ".xp.lose", config.getStringList("generate.lose"));
                    execute.set(executeS + ".level.up", config.getStringList("generate.levels." + i + ".up"));
                    execute.set(executeS + ".level.down", config.getStringList("generate.levels." + i + ".down"));
                    plugin.getTextUtils().info("[Generate] ( " + i + " ) has config commands ( Added )");
                }
                if (lastXP != 0) {
                    levels.set(group + "." + i + ".xp", lastXP);
                } else {
                    startUP = true;
                    levels.set(group + "." + i + ".xp", 0);
                }
                lastXP = Math.round(Double.parseDouble(object.toString()));
                if (i == percent10) {
                    plugin.getTextUtils().info("[Generate] Progress: 10%");
                }
                if (i == percent25) {
                    plugin.getTextUtils().info("[Generate] Progress: 25%");
                }
                if (i == percent50) {
                    plugin.getTextUtils().info("[Generate] Progress: 50%");
                }
                if (i == percent75) {
                    plugin.getTextUtils().info("[Generate] Progress: 75%");
                }
                if (i == (size - 1)) {
                    plugin.getTextUtils().info("[Generate] Progress: 100%");
                }
            } catch (ScriptException ignored) {}
        }
        plugin.getFileUtils().saveLevels();
        plugin.getFileUtils().loadLevels();
        plugin.getFileUtils().saveExecute();
        plugin.getFileUtils().loadExecute();
        plugin.getTextUtils().info("[Generate] Finished ( " + amount + " ) levels for group ( " + group + " ) level system start at ( " + startLevel + " )");
        plugin.generateGroup = null;
        plugin.generateAmount = 0;
        plugin.isGenerate = false;
    }

    private void setLevelPath(final String[] list, final String path, final long i) {
        for (String test : list) {
            levels.set(group + "." + i + "." + test, config.getString(path + "." + test).replace("{group}", group).replace("{level}", String.valueOf(i)));
        }
    }

    private String getRandom(final String math) {
        final Matcher matcher = Pattern.compile("\\[([^]]+)]").matcher(math);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}