package me.MathiasMC.PvPLevels.utils;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.LinkedHashMap;
import java.util.Map;

public class GenerateThread extends Thread {

    private final PvPLevels plugin;

    private final String group;
    private final long amount;
    private final long startLevel;

    final FileConfiguration config;
    final FileConfiguration levels;
    final FileConfiguration execute;

    public GenerateThread(PvPLevels plugin, String group, long amount, long startLevel) {
        this.plugin = plugin;
        this.config = plugin.getFileUtils().config;
        this.levels = plugin.getFileUtils().levels;
        this.execute = plugin.getFileUtils().execute;
        setName("Generate");
        this.group = group;
        this.amount = amount;
        this.startLevel = startLevel;
    }

    public void run() {
        Utils.info("Started task...");
        plugin.isGenerate = true;
        long xp = 0;
        String gKey  = "generate";
        long level = amount + startLevel;
        int percent10 = (int) (level * (10.0f / 100.0f));
        int percent25 = (int) (level * (25.0f / 100.0f));
        int percent50 = (int) (level * (50.0f / 100.0f));
        int percent75 = (int) (level * (75.0f / 100.0f));
        String[] list = new String[]{"prefix", "suffix", "group", "execute"};
        levels.set(group + ".execute", group);
        execute.set(group + ".xp.get", config.getStringList(gKey + ".get"));
        execute.set(group + ".xp.item", config.getStringList(gKey + ".item"));
        execute.set(group + ".xp.boost", config.getStringList(gKey + ".boost"));
        execute.set(group + ".xp.both", config.getStringList(gKey + ".both"));
        execute.set(group + ".xp.lose", config.getStringList(gKey + ".lose"));
        execute.set(group + ".level.up", config.getStringList(gKey + ".up"));
        execute.set(group + ".level.down", config.getStringList(gKey + ".down"));
        LinkedHashMap<Integer, Integer> mapList = new LinkedHashMap<>();
        for (String get : config.getConfigurationSection(gKey + ".percent").getKeys(false)) {
            mapList.put((int) (level * (Float.parseFloat(get) / 100.0f)), Integer.parseInt(get));
        }
        if (startLevel > 0) {
            level = level - 1;
        }
        for (long i = startLevel; i <= level; i++) {
            for (Map.Entry<Integer, Integer> s : mapList.entrySet()) {
                if (i <= s.getKey()) {
                    for (String key : list) {
                        levels.set(
                                group + "." + i + "." + key,
                                config.getString(gKey + ".percent." + s.getValue() + "." + key)
                                        .replace("{group}", group)
                                        .replace("{level}", String.valueOf(i)));
                    }
                    break;
                }
            }
            if (config.contains(gKey + ".levels." + i)) {
                String executeS = config.getString(gKey + ".levels." + i + ".execute")
                        .replace("{group}", group)
                        .replace("{level}", String.valueOf(i));
                levels.set(group + "." + i + ".execute", executeS);
                execute.set(executeS + ".xp.lose", config.getStringList(gKey + ".lose"));
                execute.set(executeS + ".level.up", config.getStringList(gKey + ".levels." + i + ".up"));
                execute.set(executeS + ".level.down", config.getStringList(gKey + ".levels." + i + ".down"));
                Utils.info("( " + i + " ) has config commands ( Added )");
            }
            levels.set(group + "." + i + ".xp", xp);
            String min = gKey + ".min";
            String max = gKey + ".max";
            if (config.contains(gKey + "." + i)) {
                min = gKey + "." + i + ".min";
                max = gKey + "." + i + ".max";
            }
            if (i == startLevel) {
                xp = config.getLong(gKey + ".start");
            } else {
                xp = xp + Utils.randomNumber(config.getLong(min), config.getLong(max));
            }
            if (i == percent10) {
                Utils.info("Progress: 10%");
            }
            if (i == percent25) {
                Utils.info("Progress: 25%");
            }
            if (i == percent50) {
                Utils.info("Progress: 50%");
            }
            if (i == percent75) {
                Utils.info("Progress: 75%");
            }
            if (i == (level - 1)) {
                Utils.info("Progress: 100%");
            }
        }
        plugin.getFileUtils().saveLevels();
        plugin.getFileUtils().loadLevels();
        plugin.getFileUtils().saveExecute();
        plugin.getFileUtils().loadExecute();
        plugin.generateGroup = null;
        plugin.generateAmount = 0;
        plugin.isGenerate = false;
        Utils.info("Finished ( " + amount + " ) levels for group ( " + group + " ) level system start at ( " + startLevel + " )");
    }
}