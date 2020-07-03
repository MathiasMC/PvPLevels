package me.MathiasMC.PvPLevels.files;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.gui.GUI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUIFolder {

    private final PvPLevels plugin;

    public GUIFolder(final PvPLevels plugin) {
        this.plugin = plugin;
        File folder = new File(plugin.getDataFolder() + File.separator + "gui");
        if (!folder.exists()) {
            folder.mkdir();
            File boostersFile = new File(folder, "boosters.yml");
            File globalBoostersFile = new File(folder, "globalBoosters.yml");
            File personalBoostersFile = new File(folder, "personalBoosters.yml");
            File profileFile = new File(folder, "profile.yml");
            File profileAllFile = new File(folder, "profileAll.yml");
            try {
                boostersFile.createNewFile();
                globalBoostersFile.createNewFile();
                personalBoostersFile.createNewFile();
                profileFile.createNewFile();
                profileAllFile.createNewFile();
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
            FileConfiguration boosters = YamlConfiguration.loadConfiguration(boostersFile);
            FileConfiguration globalBoosters = YamlConfiguration.loadConfiguration(globalBoostersFile);
            FileConfiguration personalBoosters = YamlConfiguration.loadConfiguration(personalBoostersFile);
            FileConfiguration profile = YamlConfiguration.loadConfiguration(profileFile);
            FileConfiguration profileAll = YamlConfiguration.loadConfiguration(profileAllFile);
            boosters(boosters);
            Boosters(globalBoosters, "Your Global Boosters", "global-boosters");
            Boosters(personalBoosters, "Your Personal Boosters", "personal-boosters");
            profile(profile);
            profileAll(profileAll);
            try {
                boosters.save(boostersFile);
                globalBoosters.save(globalBoostersFile);
                personalBoosters.save(personalBoostersFile);
                profile.save(profileFile);
                profileAll.save(profileAllFile);
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        }
        for (File file : folder.listFiles()) {
            String fileName = file.getName();
            if (fileName.endsWith(".yml")) {
                plugin.guiList.put(fileName, new GUI(plugin, YamlConfiguration.loadConfiguration(file)));
                plugin.textUtils.info("[GUI] " + fileName + " ( Loaded )");
            } else {
                plugin.textUtils.error("[GUI] " + fileName + " ( Error Loading )");
            }
        }
    }

    private void Boosters(FileConfiguration fileConfiguration, String name, String settings) {
        fileConfiguration.set("settings.name", "&f&l" + name);
        fileConfiguration.set("settings.size", 54);
        fileConfiguration.set("settings." + settings + ".INDEX", 10);
        fileConfiguration.set("settings." + settings + ".PAGE", 28);
        fileConfiguration.set("glass1.NAME", " ");
        fileConfiguration.set("glass1.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass1.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass1.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass1.AMOUNT", 1);
        fileConfiguration.set("glass1.POSITION", 9);
        ArrayList<String> list5 = new ArrayList<>();
        list5.add("GLOW");
        fileConfiguration.set("glass1.OPTIONS", list5);

        fileConfiguration.set("glass2.NAME", " ");
        fileConfiguration.set("glass2.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass2.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass2.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass2.AMOUNT", 1);
        fileConfiguration.set("glass2.POSITION", 17);
        ArrayList<String> list6 = new ArrayList<>();
        list6.add("GLOW");
        fileConfiguration.set("glass2.OPTIONS", list6);

        fileConfiguration.set("glass3.NAME", " ");
        fileConfiguration.set("glass3.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass3.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass3.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass3.AMOUNT", 1);
        fileConfiguration.set("glass3.POSITION", 18);
        ArrayList<String> list7 = new ArrayList<>();
        list7.add("GLOW");
        fileConfiguration.set("glass3.OPTIONS", list7);

        fileConfiguration.set("glass4.NAME", " ");
        fileConfiguration.set("glass4.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass4.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass4.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass4.AMOUNT", 1);
        fileConfiguration.set("glass4.POSITION", 26);
        ArrayList<String> list8 = new ArrayList<>();
        list8.add("GLOW");
        fileConfiguration.set("glass4.OPTIONS", list8);
        fileConfiguration.set("glass5.NAME", " ");
        fileConfiguration.set("glass5.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass5.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass5.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass5.AMOUNT", 1);
        fileConfiguration.set("glass5.POSITION", 27);
        ArrayList<String> list9 = new ArrayList<>();
        list9.add("GLOW");
        fileConfiguration.set("glass5.OPTIONS", list9);
        fileConfiguration.set("glass6.NAME", " ");
        fileConfiguration.set("glass6.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass6.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass6.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass6.AMOUNT", 1);
        fileConfiguration.set("glass6.POSITION", 35);
        ArrayList<String> list10 = new ArrayList<>();
        list10.add("GLOW");
        fileConfiguration.set("glass6.OPTIONS", list10);
        fileConfiguration.set("glass7.NAME", " ");
        fileConfiguration.set("glass7.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass7.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass7.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass7.AMOUNT", 1);
        fileConfiguration.set("glass7.POSITION", 36);
        ArrayList<String> list11 = new ArrayList<>();
        list11.add("GLOW");
        fileConfiguration.set("glass7.OPTIONS", list11);
        fileConfiguration.set("glass8.NAME", " ");
        fileConfiguration.set("glass8.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass8.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass8.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass8.AMOUNT", 1);
        fileConfiguration.set("glass8.POSITION", 44);
        ArrayList<String> list12 = new ArrayList<>();
        list12.add("GLOW");
        fileConfiguration.set("glass8.OPTIONS", list12);
        fileConfiguration.set("back.NAME", "&f&lGo back to menu");
        fileConfiguration.set("back.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("back.MATERIAL", "340:0");
        } else {
            fileConfiguration.set("back.MATERIAL", "BOOK");
        }
        fileConfiguration.set("back.AMOUNT", 1);
        fileConfiguration.set("back.POSITION", 45);
        ArrayList<String> list13 = new ArrayList<>();
        list13.add("GLOW");
        list13.add("CLOSE");
        fileConfiguration.set("back.OPTIONS", list13);
        ArrayList<String> list14 = new ArrayList<>();
        list14.add("pvplevels gui open boosters.yml {pvplevels_player}");
        fileConfiguration.set("back.COMMANDS", list14);
    }

    private void boosters(FileConfiguration fileConfiguration) {
        fileConfiguration.set("settings.name", "&f&lBoosters Menu");
        fileConfiguration.set("settings.size", 27);
        fileConfiguration.set("personalBoosters.NAME", "&f&lYour Personal Boosters");
        fileConfiguration.set("personalBoosters.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("personalBoosters.MATERIAL", "41:0");
        } else {
            fileConfiguration.set("personalBoosters.MATERIAL", "GOLD_BLOCK");
        }
        fileConfiguration.set("personalBoosters.AMOUNT", 1);
        fileConfiguration.set("personalBoosters.POSITION", 11);
        ArrayList<String> list = new ArrayList<>();
        list.add("CLOSE");
        fileConfiguration.set("personalBoosters.OPTIONS", list);
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("pvplevels gui open personalBoosters.yml {pvplevels_player}");
        fileConfiguration.set("personalBoosters.COMMANDS", list1);
        fileConfiguration.set("globalBoosters.NAME", "&f&lYour Global Boosters");
        fileConfiguration.set("globalBoosters.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("globalBoosters.MATERIAL", "41:0");
        } else {
            fileConfiguration.set("globalBoosters.MATERIAL", "GOLD_BLOCK");
        }
        fileConfiguration.set("globalBoosters.AMOUNT", 1);
        fileConfiguration.set("globalBoosters.POSITION", 15);
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("GLOW");
        list2.add("CLOSE");
        fileConfiguration.set("globalBoosters.OPTIONS", list2);
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("pvplevels gui open globalBoosters.yml {pvplevels_player}");
        fileConfiguration.set("globalBoosters.COMMANDS", list3);
    }

    private void profile(FileConfiguration fileConfiguration) {
        fileConfiguration.set("settings.name", "&f&lProfile Menu");
        fileConfiguration.set("settings.size", 27);
        fileConfiguration.set("profiles.NAME", "&f&lAll Profiles");
        fileConfiguration.set("profiles.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("profiles.MATERIAL", "41:0");
        } else {
            fileConfiguration.set("profiles.MATERIAL", "GOLD_BLOCK");
        }
        fileConfiguration.set("profiles.AMOUNT", 1);
        fileConfiguration.set("profiles.POSITION", 11);
        ArrayList<String> list = new ArrayList<>();
        list.add("CLOSE");
        fileConfiguration.set("profiles.OPTIONS", list);
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("pvplevels gui open profileAll.yml {pvplevels_player}");
        fileConfiguration.set("profiles.COMMANDS", list1);
        fileConfiguration.set("profile.NAME", "&f&lYour Profile");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("&eKills: &7> &6{pvplevels_kills}");
        list2.add("&eDeaths: &7> &6{pvplevels_deaths}");
        list2.add("&eXP: &7> &6{pvplevels_xp}");
        list2.add("&eLevel: &7> &6{pvplevels_level}");
        list2.add("&eKDR: &7> &6{pvplevels_kdr}");
        list2.add("&eKill Factor: &7> &6{pvplevels_kill_factor}");
        list2.add("&eKillStreak: &7> &6{pvplevels_killstreak}");
        list2.add("&eXP Required: &7> &6{pvplevels_xp_required}");
        list2.add("&eXP Progress: &7> &6{pvplevels_xp_progress}%");
        list2.add("&6{pvplevels_xp_progress_style}");
        list2.add("&eGroup: &7> &6{pvplevels_group} &7/ &6{pvplevels_group_to}");
        fileConfiguration.set("profile.LORES", list2);
        if (plugin.versionID()) {
            fileConfiguration.set("profile.MATERIAL", "397:3");
        } else {
            fileConfiguration.set("profile.MATERIAL", "PLAYER_HEAD");
        }
        fileConfiguration.set("profile.AMOUNT", 1);
        fileConfiguration.set("profile.POSITION", 15);
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("PLAYERSKULL");
        fileConfiguration.set("profile.OPTIONS", list3);
    }

    private void profileAll(FileConfiguration fileConfiguration) {
        fileConfiguration.set("settings.name", "&f&lAll Profiles");
        fileConfiguration.set("settings.size", 54);
        fileConfiguration.set("settings.profile-all.INDEX", 10);
        fileConfiguration.set("settings.profile-all.PAGE", 28);
        fileConfiguration.set("settings.profile-all.default-sort", "level");
        fileConfiguration.set("settings.profile-all.NAME", "&6{pvplevels_player}");
        ArrayList<String> list21 = new ArrayList<>();
        list21.add("&eKills: &7> &6{pvplevels_kills}");
        list21.add("&eDeaths: &7> &6{pvplevels_deaths}");
        list21.add("&eXP: &7> &6{pvplevels_xp}");
        list21.add("&eLevel: &7> &6{pvplevels_level}");
        list21.add("&eKDR: &7> &6{pvplevels_kdr}");
        list21.add("&eKill Factor: &7> &6{pvplevels_kill_factor}");
        list21.add("&eKillStreak: &7> &6{pvplevels_killstreak}");
        list21.add("&eXP Required: &7> &6{pvplevels_xp_required}");
        list21.add("&eXP Progress: &7> &6{pvplevels_xp_progress}%");
        list21.add("&6{pvplevels_xp_progress_style}");
        fileConfiguration.set("settings.profile-all.LORES", list21);
        if (plugin.versionID()) {
            fileConfiguration.set("settings.profile-all.MATERIAL", "397:3");
        } else {
            fileConfiguration.set("settings.profile-all.MATERIAL", "PLAYER_HEAD");
        }
        fileConfiguration.set("settings.profile-all.AMOUNT", 1);
        fileConfiguration.set("settings.profile-all.back.NAME", "&bGo Back");
        ArrayList<String> list = new ArrayList<>();
        fileConfiguration.set("settings.profile-all.back.LORES", list);
        if (plugin.versionID()) {
            fileConfiguration.set("settings.profile-all.back.MATERIAL", "280:0");
        } else {
            fileConfiguration.set("settings.profile-all.back.MATERIAL", "STICK");
        }
        fileConfiguration.set("settings.profile-all.back.AMOUNT", 1);
        fileConfiguration.set("settings.profile-all.back.POSITION", 46);
        fileConfiguration.set("settings.profile-all.next.NAME", "&bNext Page");
        ArrayList<String> list3 = new ArrayList<>();
        fileConfiguration.set("settings.profile-all.next.LORES", list3);
        if (plugin.versionID()) {
            fileConfiguration.set("settings.profile-all.next.MATERIAL", "280:0");
        } else {
            fileConfiguration.set("settings.profile-all.next.MATERIAL", "STICK");
        }
        fileConfiguration.set("settings.profile-all.next.AMOUNT", 1);
        fileConfiguration.set("settings.profile-all.next.POSITION", 53);
        fileConfiguration.set("glass1.NAME", " ");
        fileConfiguration.set("glass1.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass1.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass1.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass1.AMOUNT", 1);
        fileConfiguration.set("glass1.POSITION", 9);
        ArrayList<String> list5 = new ArrayList<>();
        list5.add("GLOW");
        fileConfiguration.set("glass1.OPTIONS", list5);

        fileConfiguration.set("glass2.NAME", " ");
        fileConfiguration.set("glass2.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass2.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass2.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass2.AMOUNT", 1);
        fileConfiguration.set("glass2.POSITION", 17);
        ArrayList<String> list6 = new ArrayList<>();
        list6.add("GLOW");
        fileConfiguration.set("glass2.OPTIONS", list6);

        fileConfiguration.set("glass3.NAME", " ");
        fileConfiguration.set("glass3.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass3.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass3.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass3.AMOUNT", 1);
        fileConfiguration.set("glass3.POSITION", 18);
        ArrayList<String> list7 = new ArrayList<>();
        list7.add("GLOW");
        fileConfiguration.set("glass3.OPTIONS", list7);

        fileConfiguration.set("glass4.NAME", " ");
        fileConfiguration.set("glass4.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass4.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass4.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass4.AMOUNT", 1);
        fileConfiguration.set("glass4.POSITION", 26);
        ArrayList<String> list8 = new ArrayList<>();
        list8.add("GLOW");
        fileConfiguration.set("glass4.OPTIONS", list8);
        fileConfiguration.set("glass5.NAME", " ");
        fileConfiguration.set("glass5.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass5.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass5.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass5.AMOUNT", 1);
        fileConfiguration.set("glass5.POSITION", 27);
        ArrayList<String> list9 = new ArrayList<>();
        list9.add("GLOW");
        fileConfiguration.set("glass5.OPTIONS", list9);

        fileConfiguration.set("glass6.NAME", " ");
        fileConfiguration.set("glass6.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass6.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass6.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass6.AMOUNT", 1);
        fileConfiguration.set("glass6.POSITION", 35);
        ArrayList<String> list10 = new ArrayList<>();
        list10.add("GLOW");
        fileConfiguration.set("glass6.OPTIONS", list10);

        fileConfiguration.set("glass7.NAME", " ");
        fileConfiguration.set("glass7.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass7.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass7.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass7.AMOUNT", 1);
        fileConfiguration.set("glass7.POSITION", 36);
        ArrayList<String> list11 = new ArrayList<>();
        list11.add("GLOW");
        fileConfiguration.set("glass7.OPTIONS", list11);

        fileConfiguration.set("glass8.NAME", " ");
        fileConfiguration.set("glass8.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("glass8.MATERIAL", "160:3");
        } else {
            fileConfiguration.set("glass8.MATERIAL", "BLUE_STAINED_GLASS_PANE");
        }
        fileConfiguration.set("glass8.AMOUNT", 1);
        fileConfiguration.set("glass8.POSITION", 44);
        ArrayList<String> list12 = new ArrayList<>();
        list12.add("GLOW");
        fileConfiguration.set("glass8.OPTIONS", list12);

        fileConfiguration.set("backMenu.NAME", "&f&lGo back to menu");
        fileConfiguration.set("backMenu.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("backMenu.MATERIAL", "340:0");
        } else {
            fileConfiguration.set("backMenu.MATERIAL", "BOOK");
        }
        fileConfiguration.set("backMenu.AMOUNT", 1);
        fileConfiguration.set("backMenu.POSITION", 45);
        ArrayList<String> list13 = new ArrayList<>();
        list13.add("GLOW");
        list13.add("CLOSE");
        fileConfiguration.set("backMenu.OPTIONS", list13);
        ArrayList<String> list14 = new ArrayList<>();
        list14.add("pvplevels gui open profile.yml {pvplevels_player}");
        fileConfiguration.set("backMenu.COMMANDS", list14);
        fileConfiguration.set("sortKills.NAME", "&f&lSort &7&l- &b&lKills");
        fileConfiguration.set("sortKills.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("sortKills.MATERIAL", "267:0");
        } else {
            fileConfiguration.set("sortKills.MATERIAL", "IRON_SWORD");
        }
        fileConfiguration.set("sortKills.AMOUNT", 1);
        fileConfiguration.set("sortKills.POSITION", 0);
        ArrayList<String> list15 = new ArrayList<>();
        list15.add("SORT_KILLS");
        fileConfiguration.set("sortKills.OPTIONS", list15);
        ArrayList<String> list16 = new ArrayList<>();
        list16.add("pvplevels gui open profileAll.yml {pvplevels_player}");
        fileConfiguration.set("sortKills.COMMANDS", list16);
        fileConfiguration.set("sortDeaths.NAME", "&f&lSort &7&l- &b&lDeaths");
        fileConfiguration.set("sortDeaths.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("sortDeaths.MATERIAL", "327:0");
        } else {
            fileConfiguration.set("sortDeaths.MATERIAL", "LAVA_BUCKET");
        }
        fileConfiguration.set("sortDeaths.AMOUNT", 1);
        fileConfiguration.set("sortDeaths.POSITION", 2);
        ArrayList<String> list17 = new ArrayList<>();
        list17.add("SORT_DEATHS");
        fileConfiguration.set("sortDeaths.OPTIONS", list17);
        ArrayList<String> list18 = new ArrayList<>();
        list18.add("pvplevels gui open profileAll.yml {pvplevels_player}");
        fileConfiguration.set("sortDeaths.COMMANDS", list18);
        fileConfiguration.set("sortXP.NAME", "&f&lSort &7&l- &b&lXP");
        fileConfiguration.set("sortXP.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("sortXP.MATERIAL", "384:0");
        } else {
            fileConfiguration.set("sortXP.MATERIAL", "EXPERIENCE_BOTTLE");
        }
        fileConfiguration.set("sortXP.AMOUNT", 1);
        fileConfiguration.set("sortXP.POSITION", 4);
        ArrayList<String> list19 = new ArrayList<>();
        list19.add("SORT_XP");
        fileConfiguration.set("sortXP.OPTIONS", list19);
        ArrayList<String> list20 = new ArrayList<>();
        list20.add("pvplevels gui open profileAll.yml {pvplevels_player}");
        fileConfiguration.set("sortXP.COMMANDS", list20);
        fileConfiguration.set("sortLevel.NAME", "&f&lSort &7&l- &b&lLevel");
        fileConfiguration.set("sortLevel.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("sortLevel.MATERIAL", "122:0");
        } else {
            fileConfiguration.set("sortLevel.MATERIAL", "DRAGON_EGG");
        }
        fileConfiguration.set("sortLevel.AMOUNT", 1);
        fileConfiguration.set("sortLevel.POSITION", 6);
        ArrayList<String> list22 = new ArrayList<>();
        list22.add("SORT_LEVEL");
        fileConfiguration.set("sortLevel.OPTIONS", list22);
        ArrayList<String> list23 = new ArrayList<>();
        list23.add("pvplevels gui open profileAll.yml {pvplevels_player}");
        fileConfiguration.set("sortLevel.COMMANDS", list23);
        fileConfiguration.set("sortKillStreak.NAME", "&f&lSort &7&l- &b&lKillStreak");
        fileConfiguration.set("sortKillStreak.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("sortKillStreak.MATERIAL", "261:0");
        } else {
            fileConfiguration.set("sortKillStreak.MATERIAL", "BOW");
        }
        fileConfiguration.set("sortKillStreak.AMOUNT", 1);
        fileConfiguration.set("sortKillStreak.POSITION", 8);
        ArrayList<String> list24 = new ArrayList<>();
        list24.add("SORT_KILLSTREAK");
        fileConfiguration.set("sortKillStreak.OPTIONS", list24);
        ArrayList<String> list25 = new ArrayList<>();
        list25.add("pvplevels gui open profileAll.yml {pvplevels_player}");
        fileConfiguration.set("sortKillStreak.COMMANDS", list25);
    }
}
