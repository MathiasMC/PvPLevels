package me.MathiasMC.PvPLevels.placeholders;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends PlaceholderExpansion {

    private PvPLevels plugin;

    public PlaceholderAPI(PvPLevels plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier(){
        return "pvplevels";
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier){
        if(player == null){
            return "";
        }
        if(identifier.equals("kills")) {
            return String.valueOf(plugin.get(player.getUniqueId().toString()).kills());
        }
        if(identifier.equals("deaths")){
            return String.valueOf(plugin.get(player.getUniqueId().toString()).deaths());
        }
        if(identifier.equals("xp")){
            return String.valueOf(plugin.get(player.getUniqueId().toString()).xp());
        }
        if(identifier.equals("level")){
            return String.valueOf(plugin.get(player.getUniqueId().toString()).level());
        }
        if(identifier.equals("killstreak")){
            return String.valueOf(plugin.get(player.getUniqueId().toString()).killstreak());
        }
        if(identifier.equals("kdr")){
            return plugin.statsManager.kdr(player.getUniqueId().toString());
        }
        if(identifier.equals("kill_factor")){
            return plugin.statsManager.kill_factor(player.getUniqueId().toString());
        }
        if(identifier.equals("xp_required")){
            return String.valueOf(plugin.statsManager.xp_required(player.getUniqueId().toString()));
        }
        if(identifier.equals("xp_progress")){
            return String.valueOf(plugin.statsManager.xp_progress(player.getUniqueId().toString()));
        }
        if(identifier.equals("xp_progress_style")){
            return String.valueOf(plugin.statsManager.xp_progress_style(player.getUniqueId().toString()));
        }
        if(identifier.equals("group")){
            return String.valueOf(plugin.statsManager.group(player));
        }
        if(identifier.equals("group_to")){
            return String.valueOf(plugin.statsManager.group_to(player));
        }
        if(identifier.equals("prefix")){
            return String.valueOf(plugin.statsManager.prefix(player));
        }
        if(identifier.equals("time")){
            return String.valueOf(plugin.statsManager.time(player.getUniqueId().toString()));
        }
        if(identifier.equals("date")){
            return String.valueOf(plugin.statsManager.date(player.getUniqueId().toString()));
        }
        if(identifier.equals("global_booster")){
            return plugin.boostersManager.getGlobalPlaceholder();
        }
        if(identifier.equals("global_booster_name")){
            return plugin.boostersManager.getGlobalNamePlaceholder();
        }
        if(identifier.equals("global_booster_time")){
            return plugin.boostersManager.getGlobalTimePlaceholder();
        }
        if(identifier.equals("global_booster_time_left")){
            return plugin.boostersManager.getGlobalTimeLeftPlaceholder();
        }
        if(identifier.equals("global_booster_time_prefix")){
            return plugin.boostersManager.getGlobalTimePrefixPlaceholder();
        }
        if(identifier.equals("global_booster_time_left_prefix")){
            return plugin.boostersManager.getGlobalTimeLeftPrefixPlaceholder();
        }
        if(identifier.equals("personal_booster")){
            return plugin.boostersManager.getPersonalPlaceholder(player.getUniqueId().toString());
        }
        if(identifier.equals("personal_booster_time")){
            return plugin.boostersManager.getPersonalTimePlaceholder(player.getUniqueId().toString());
        }
        if(identifier.equals("personal_booster_time_left")){
            return plugin.boostersManager.getPersonalTimeLeftPlaceholder(player.getUniqueId().toString());
        }
        if(identifier.equals("personal_booster_time_prefix")){
            return plugin.boostersManager.getPersonalTimePrefixPlaceholder(player.getUniqueId().toString());
        }
        if(identifier.equals("personal_booster_time_left_prefix")){
            return plugin.boostersManager.getPersonalTimeLeftPrefixPlaceholder(player.getUniqueId().toString());
        }
        if(identifier.equals("top_1_kills_name")){
            return plugin.statsManager.getTopValue("kills", 0, true);
        }
        if(identifier.equals("top_1_kills")){
            return plugin.statsManager.getTopValue("kills", 0, false);
        }
        if(identifier.equals("top_2_kills_name")){
            return plugin.statsManager.getTopValue("kills", 1, true);
        }
        if(identifier.equals("top_2_kills")){
            return plugin.statsManager.getTopValue("kills", 1, false);
        }
        if(identifier.equals("top_3_kills_name")){
            return plugin.statsManager.getTopValue("kills", 2, true);
        }
        if(identifier.equals("top_3_kills")){
            return plugin.statsManager.getTopValue("kills", 2, false);
        }
        if(identifier.equals("top_4_kills_name")){
            return plugin.statsManager.getTopValue("kills", 3, true);
        }
        if(identifier.equals("top_4_kills")){
            return plugin.statsManager.getTopValue("kills", 3, false);
        }
        if(identifier.equals("top_5_kills_name")){
            return plugin.statsManager.getTopValue("kills", 4, true);
        }
        if(identifier.equals("top_5_kills")){
            return plugin.statsManager.getTopValue("kills", 4, false);
        }
        if(identifier.equals("top_6_kills_name")){
            return plugin.statsManager.getTopValue("kills", 5, true);
        }
        if(identifier.equals("top_6_kills")){
            return plugin.statsManager.getTopValue("kills", 5, false);
        }
        if(identifier.equals("top_7_kills_name")){
            return plugin.statsManager.getTopValue("kills", 6, true);
        }
        if(identifier.equals("top_7_kills")){
            return plugin.statsManager.getTopValue("kills", 6, false);
        }
        if(identifier.equals("top_8_kills_name")){
            return plugin.statsManager.getTopValue("kills", 7, true);
        }
        if(identifier.equals("top_8_kills")){
            return plugin.statsManager.getTopValue("kills", 7, false);
        }
        if(identifier.equals("top_9_kills_name")){
            return plugin.statsManager.getTopValue("kills", 8, true);
        }
        if(identifier.equals("top_9_kills")){
            return plugin.statsManager.getTopValue("kills", 8, false);
        }
        if(identifier.equals("top_10_kills_name")){
            return plugin.statsManager.getTopValue("kills", 9, true);
        }
        if(identifier.equals("top_10_kills")){
            return plugin.statsManager.getTopValue("kills", 9, false);
        }
        if(identifier.equals("top_1_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 0, true);
        }
        if(identifier.equals("top_1_deaths")){
            return plugin.statsManager.getTopValue("deaths", 0, false);
        }
        if(identifier.equals("top_2_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 1, true);
        }
        if(identifier.equals("top_2_deaths")){
            return plugin.statsManager.getTopValue("deaths", 1, false);
        }
        if(identifier.equals("top_3_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 2, true);
        }
        if(identifier.equals("top_3_deaths")){
            return plugin.statsManager.getTopValue("deaths", 2, false);
        }
        if(identifier.equals("top_4_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 3, true);
        }
        if(identifier.equals("top_4_deaths")){
            return plugin.statsManager.getTopValue("deaths", 3, false);
        }
        if(identifier.equals("top_5_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 4, true);
        }
        if(identifier.equals("top_5_deaths")){
            return plugin.statsManager.getTopValue("deaths", 4, false);
        }
        if(identifier.equals("top_6_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 5, true);
        }
        if(identifier.equals("top_6_deaths")){
            return plugin.statsManager.getTopValue("deaths", 5, false);
        }
        if(identifier.equals("top_7_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 6, true);
        }
        if(identifier.equals("top_7_deaths")){
            return plugin.statsManager.getTopValue("deaths", 6, false);
        }
        if(identifier.equals("top_8_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 7, true);
        }
        if(identifier.equals("top_8_deaths")){
            return plugin.statsManager.getTopValue("deaths", 7, false);
        }
        if(identifier.equals("top_9_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 8, true);
        }
        if(identifier.equals("top_9_deaths")){
            return plugin.statsManager.getTopValue("deaths", 8, false);
        }
        if(identifier.equals("top_10_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 9, true);
        }
        if(identifier.equals("top_10_deaths")){
            return plugin.statsManager.getTopValue("deaths", 9, false);
        }
        if(identifier.equals("top_1_xp_name")){
            return plugin.statsManager.getTopValue("xp", 0, true);
        }
        if(identifier.equals("top_1_xp")){
            return plugin.statsManager.getTopValue("xp", 0, false);
        }
        if(identifier.equals("top_2_xp_name")){
            return plugin.statsManager.getTopValue("xp", 1, true);
        }
        if(identifier.equals("top_2_xp")){
            return plugin.statsManager.getTopValue("xp", 1, false);
        }
        if(identifier.equals("top_3_xp_name")){
            return plugin.statsManager.getTopValue("xp", 2, true);
        }
        if(identifier.equals("top_3_xp")){
            return plugin.statsManager.getTopValue("xp", 2, false);
        }
        if(identifier.equals("top_4_xp_name")){
            return plugin.statsManager.getTopValue("xp", 3, true);
        }
        if(identifier.equals("top_4_xp")){
            return plugin.statsManager.getTopValue("xp", 3, false);
        }
        if(identifier.equals("top_5_xp_name")){
            return plugin.statsManager.getTopValue("xp", 4, true);
        }
        if(identifier.equals("top_5_xp")){
            return plugin.statsManager.getTopValue("xp", 4, false);
        }
        if(identifier.equals("top_6_xp_name")){
            return plugin.statsManager.getTopValue("xp", 5, true);
        }
        if(identifier.equals("top_6_xp")){
            return plugin.statsManager.getTopValue("xp", 5, false);
        }
        if(identifier.equals("top_7_xp_name")){
            return plugin.statsManager.getTopValue("xp", 6, true);
        }
        if(identifier.equals("top_7_xp")){
            return plugin.statsManager.getTopValue("xp", 6, false);
        }
        if(identifier.equals("top_8_xp_name")){
            return plugin.statsManager.getTopValue("xp", 7, true);
        }
        if(identifier.equals("top_8_xp")){
            return plugin.statsManager.getTopValue("xp", 7, false);
        }
        if(identifier.equals("top_9_xp_name")){
            return plugin.statsManager.getTopValue("xp", 8, true);
        }
        if(identifier.equals("top_9_xp")){
            return plugin.statsManager.getTopValue("xp", 8, false);
        }
        if(identifier.equals("top_10_xp_name")){
            return plugin.statsManager.getTopValue("xp", 9, true);
        }
        if(identifier.equals("top_10_xp")){
            return plugin.statsManager.getTopValue("xp", 9, false);
        }
        if(identifier.equals("top_1_level_name")){
            return plugin.statsManager.getTopValue("level", 0, true);
        }
        if(identifier.equals("top_1_level")){
            return plugin.statsManager.getTopValue("level", 0, false);
        }
        if(identifier.equals("top_2_level_name")){
            return plugin.statsManager.getTopValue("level", 1, true);
        }
        if(identifier.equals("top_2_level")){
            return plugin.statsManager.getTopValue("level", 1, false);
        }
        if(identifier.equals("top_3_level_name")){
            return plugin.statsManager.getTopValue("level", 2, true);
        }
        if(identifier.equals("top_3_level")){
            return plugin.statsManager.getTopValue("level", 2, false);
        }
        if(identifier.equals("top_4_level_name")){
            return plugin.statsManager.getTopValue("level", 3, true);
        }
        if(identifier.equals("top_4_level")){
            return plugin.statsManager.getTopValue("level", 3, false);
        }
        if(identifier.equals("top_5_level_name")){
            return plugin.statsManager.getTopValue("level", 4, true);
        }
        if(identifier.equals("top_5_level")){
            return plugin.statsManager.getTopValue("level", 4, false);
        }
        if(identifier.equals("top_6_level_name")){
            return plugin.statsManager.getTopValue("level", 5, true);
        }
        if(identifier.equals("top_6_level")){
            return plugin.statsManager.getTopValue("level", 5, false);
        }
        if(identifier.equals("top_7_level_name")){
            return plugin.statsManager.getTopValue("level", 6, true);
        }
        if(identifier.equals("top_7_level")){
            return plugin.statsManager.getTopValue("level", 6, false);
        }
        if(identifier.equals("top_8_level_name")){
            return plugin.statsManager.getTopValue("level", 7, true);
        }
        if(identifier.equals("top_8_level")){
            return plugin.statsManager.getTopValue("level", 7, false);
        }
        if(identifier.equals("top_9_level_name")){
            return plugin.statsManager.getTopValue("level", 8, true);
        }
        if(identifier.equals("top_9_level")){
            return plugin.statsManager.getTopValue("level", 8, false);
        }
        if(identifier.equals("top_10_level_name")){
            return plugin.statsManager.getTopValue("level", 9, true);
        }
        if(identifier.equals("top_10_level")){
            return plugin.statsManager.getTopValue("level", 9, false);
        }
        if(identifier.equals("top_1_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 0, true);
        }
        if(identifier.equals("top_1_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 0, false);
        }
        if(identifier.equals("top_2_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 1, true);
        }
        if(identifier.equals("top_2_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 1, false);
        }
        if(identifier.equals("top_3_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 2, true);
        }
        if(identifier.equals("top_3_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 2, false);
        }
        if(identifier.equals("top_4_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 3, true);
        }
        if(identifier.equals("top_4_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 3, false);
        }
        if(identifier.equals("top_5_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 4, true);
        }
        if(identifier.equals("top_5_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 4, false);
        }
        if(identifier.equals("top_6_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 5, true);
        }
        if(identifier.equals("top_6_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 5, false);
        }
        if(identifier.equals("top_7_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 6, true);
        }
        if(identifier.equals("top_7_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 6, false);
        }
        if(identifier.equals("top_8_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 7, true);
        }
        if(identifier.equals("top_8_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 7, false);
        }
        if(identifier.equals("top_9_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 8, true);
        }
        if(identifier.equals("top_9_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 8, false);
        }
        if(identifier.equals("top_10_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 9, true);
        }
        if(identifier.equals("top_10_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 9, false);
        }
        return null;
    }
}