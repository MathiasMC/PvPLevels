package me.MathiasMC.PvPLevels.support;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends PlaceholderExpansion {

    private final PvPLevels plugin;

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
        final String uuid = player.getUniqueId().toString();
        final PlayerConnect playerConnect = plugin.getPlayerConnect(uuid);
        if(identifier.equals("kills")) {
            return String.valueOf(playerConnect.getKills());
        }
        if(identifier.equals("deaths")){
            return String.valueOf(playerConnect.getDeaths());
        }
        if(identifier.equals("xp")){
            return String.valueOf(playerConnect.getXp());
        }
        if(identifier.equals("level")){
            return String.valueOf(playerConnect.getLevel());
        }
        if(identifier.equals("level_next")){
            return String.valueOf(playerConnect.getLevel() + 1);
        }
        if(identifier.equals("killstreak")){
            return String.valueOf(playerConnect.getKillstreak());
        }
        if(identifier.equals("killstreak_top")){
            return String.valueOf(playerConnect.getKillstreakTop());
        }
        if(identifier.equals("kdr")){
            return plugin.getStatsManager().getKDR(playerConnect);
        }
        if(identifier.equals("kill_factor")){
            return plugin.getStatsManager().getKillFactor(playerConnect);
        }
        if (identifier.equals("xp_need")) {
            return String.valueOf(plugin.getStatsManager().getXPNeeded(playerConnect));
        }
        if(identifier.equals("xp_required")){
            return String.valueOf(plugin.getStatsManager().getXPRequired(playerConnect, false));
        }
        if(identifier.equals("xp_required_next")){
            return String.valueOf(plugin.getStatsManager().getXPRequired(playerConnect, true));
        }
        if(identifier.equals("xp_progress")){
            return String.valueOf(plugin.getStatsManager().getXPProgress(playerConnect));
        }
        if(identifier.equals("xp_progress_style")){
            return String.valueOf(plugin.getStatsManager().getXPProgressStyle(playerConnect, "xp-progress-style"));
        }
        if(identifier.equals("xp_progress_style_2")){
            return String.valueOf(plugin.getStatsManager().getXPProgressStyle(playerConnect, "xp-progress-style-2"));
        }
        if(identifier.equals("time")){
            return String.valueOf(plugin.getStatsManager().getTime(System.currentTimeMillis() - playerConnect.getTime().getTime()));
        }
        if(identifier.equals("group")) {
            return playerConnect.getGroup();
        }
        if(identifier.equals("level_group")) {
            return plugin.getStatsManager().getGroup(playerConnect);
        }
        if(identifier.equals("level_prefix")) {
            return plugin.getStatsManager().getPrefix(player, playerConnect);
        }
        if(identifier.equals("level_suffix")) {
            return plugin.getStatsManager().getSuffix(player, playerConnect);
        }
        if (identifier.equals("xp_type")) {
            return plugin.getStatsManager().getType(playerConnect);
        }
        if (identifier.equals("xp_get")) {
            return plugin.getStatsManager().getGet(playerConnect);
        }
        if (identifier.equals("xp_lost")) {
            return plugin.getStatsManager().getLost(playerConnect);
        }
        if (identifier.equals("xp_item")) {
            return plugin.getStatsManager().getItem(playerConnect);
        }
        if(identifier.equals("multiplier")) {
            return plugin.getStatsManager().getMultiplier(playerConnect);
        }
        if(identifier.equals("multiplier_time")) {
            return plugin.getStatsManager().getMultiplierTime(playerConnect);
        }
        if(identifier.equals("multiplier_time_left")) {
            return plugin.getStatsManager().getMultiplierTimeLeft(playerConnect);
        }
        if(identifier.equals("top_1_kills_name")){
            return plugin.getStatsManager().getTopKills( 1, true);
        }
        if(identifier.equals("top_1_kills")){
            return plugin.getStatsManager().getTopKills( 1, false);
        }
        if(identifier.equals("top_2_kills_name")){
            return plugin.getStatsManager().getTopKills( 2, true);
        }
        if(identifier.equals("top_2_kills")){
            return plugin.getStatsManager().getTopKills( 2, false);
        }
        if(identifier.equals("top_3_kills_name")){
            return plugin.getStatsManager().getTopKills( 3, true);
        }
        if(identifier.equals("top_3_kills")){
            return plugin.getStatsManager().getTopKills( 3, false);
        }
        if(identifier.equals("top_4_kills_name")){
            return plugin.getStatsManager().getTopKills( 4, true);
        }
        if(identifier.equals("top_4_kills")){
            return plugin.getStatsManager().getTopKills( 4, false);
        }
        if(identifier.equals("top_5_kills_name")){
            return plugin.getStatsManager().getTopKills( 5, true);
        }
        if(identifier.equals("top_5_kills")){
            return plugin.getStatsManager().getTopKills( 5, false);
        }
        if(identifier.equals("top_6_kills_name")){
            return plugin.getStatsManager().getTopKills( 6, true);
        }
        if(identifier.equals("top_6_kills")){
            return plugin.getStatsManager().getTopKills( 6, false);
        }
        if(identifier.equals("top_7_kills_name")){
            return plugin.getStatsManager().getTopKills( 7, true);
        }
        if(identifier.equals("top_7_kills")){
            return plugin.getStatsManager().getTopKills( 7, false);
        }
        if(identifier.equals("top_8_kills_name")){
            return plugin.getStatsManager().getTopKills( 8, true);
        }
        if(identifier.equals("top_8_kills")){
            return plugin.getStatsManager().getTopKills( 8, false);
        }
        if(identifier.equals("top_9_kills_name")){
            return plugin.getStatsManager().getTopKills( 9, true);
        }
        if(identifier.equals("top_9_kills")){
            return plugin.getStatsManager().getTopKills( 9, false);
        }
        if(identifier.equals("top_10_kills_name")){
            return plugin.getStatsManager().getTopKills( 10, true);
        }
        if(identifier.equals("top_10_kills")){
            return plugin.getStatsManager().getTopKills( 10, false);
        }
        if(identifier.equals("top_11_kills_name")){
            return plugin.getStatsManager().getTopKills( 11, true);
        }
        if(identifier.equals("top_11_kills")){
            return plugin.getStatsManager().getTopKills( 11, false);
        }
        if(identifier.equals("top_12_kills_name")){
            return plugin.getStatsManager().getTopKills( 12, true);
        }
        if(identifier.equals("top_12_kills")){
            return plugin.getStatsManager().getTopKills( 12, false);
        }
        if(identifier.equals("top_13_kills_name")){
            return plugin.getStatsManager().getTopKills( 13, true);
        }
        if(identifier.equals("top_13_kills")){
            return plugin.getStatsManager().getTopKills( 13, false);
        }
        if(identifier.equals("top_14_kills_name")){
            return plugin.getStatsManager().getTopKills( 14, true);
        }
        if(identifier.equals("top_14_kills")){
            return plugin.getStatsManager().getTopKills( 14, false);
        }
        if(identifier.equals("top_15_kills_name")){
            return plugin.getStatsManager().getTopKills( 15, true);
        }
        if(identifier.equals("top_15_kills")){
            return plugin.getStatsManager().getTopKills( 15, false);
        }
        if(identifier.equals("top_1_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 1, true);
        }
        if(identifier.equals("top_1_deaths")){
            return plugin.getStatsManager().getTopDeaths( 1, false);
        }
        if(identifier.equals("top_2_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 2, true);
        }
        if(identifier.equals("top_2_deaths")){
            return plugin.getStatsManager().getTopDeaths( 2, false);
        }
        if(identifier.equals("top_3_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 3, true);
        }
        if(identifier.equals("top_3_deaths")){
            return plugin.getStatsManager().getTopDeaths( 3, false);
        }
        if(identifier.equals("top_4_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 4, true);
        }
        if(identifier.equals("top_4_deaths")){
            return plugin.getStatsManager().getTopDeaths( 4, false);
        }
        if(identifier.equals("top_5_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 5, true);
        }
        if(identifier.equals("top_5_deaths")){
            return plugin.getStatsManager().getTopDeaths( 5, false);
        }
        if(identifier.equals("top_6_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 6, true);
        }
        if(identifier.equals("top_6_deaths")){
            return plugin.getStatsManager().getTopDeaths( 6, false);
        }
        if(identifier.equals("top_7_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 7, true);
        }
        if(identifier.equals("top_7_deaths")){
            return plugin.getStatsManager().getTopDeaths( 7, false);
        }
        if(identifier.equals("top_8_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 8, true);
        }
        if(identifier.equals("top_8_deaths")){
            return plugin.getStatsManager().getTopDeaths( 8, false);
        }
        if(identifier.equals("top_9_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 9, true);
        }
        if(identifier.equals("top_9_deaths")){
            return plugin.getStatsManager().getTopDeaths( 9, false);
        }
        if(identifier.equals("top_10_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 10, true);
        }
        if(identifier.equals("top_10_deaths")){
            return plugin.getStatsManager().getTopDeaths( 10, false);
        }
        if(identifier.equals("top_11_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 11, true);
        }
        if(identifier.equals("top_11_deaths")){
            return plugin.getStatsManager().getTopDeaths( 11, false);
        }
        if(identifier.equals("top_12_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 12, true);
        }
        if(identifier.equals("top_12_deaths")){
            return plugin.getStatsManager().getTopDeaths( 12, false);
        }
        if(identifier.equals("top_13_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 13, true);
        }
        if(identifier.equals("top_13_deaths")){
            return plugin.getStatsManager().getTopDeaths( 13, false);
        }
        if(identifier.equals("top_14_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 14, true);
        }
        if(identifier.equals("top_14_deaths")){
            return plugin.getStatsManager().getTopDeaths( 14, false);
        }
        if(identifier.equals("top_15_deaths_name")){
            return plugin.getStatsManager().getTopDeaths( 15, true);
        }
        if(identifier.equals("top_15_deaths")){
            return plugin.getStatsManager().getTopDeaths( 15, false);
        }
        if(identifier.equals("top_1_xp_name")){
            return plugin.getStatsManager().getTopXp( 1, true);
        }
        if(identifier.equals("top_1_xp")){
            return plugin.getStatsManager().getTopXp( 1, false);
        }
        if(identifier.equals("top_2_xp_name")){
            return plugin.getStatsManager().getTopXp( 2, true);
        }
        if(identifier.equals("top_2_xp")){
            return plugin.getStatsManager().getTopXp( 2, false);
        }
        if(identifier.equals("top_3_xp_name")){
            return plugin.getStatsManager().getTopXp( 3, true);
        }
        if(identifier.equals("top_3_xp")){
            return plugin.getStatsManager().getTopXp( 3, false);
        }
        if(identifier.equals("top_4_xp_name")){
            return plugin.getStatsManager().getTopXp( 4, true);
        }
        if(identifier.equals("top_4_xp")){
            return plugin.getStatsManager().getTopXp( 4, false);
        }
        if(identifier.equals("top_5_xp_name")){
            return plugin.getStatsManager().getTopXp( 5, true);
        }
        if(identifier.equals("top_5_xp")){
            return plugin.getStatsManager().getTopXp( 5, false);
        }
        if(identifier.equals("top_6_xp_name")){
            return plugin.getStatsManager().getTopXp( 6, true);
        }
        if(identifier.equals("top_6_xp")){
            return plugin.getStatsManager().getTopXp( 6, false);
        }
        if(identifier.equals("top_7_xp_name")){
            return plugin.getStatsManager().getTopXp( 7, true);
        }
        if(identifier.equals("top_7_xp")){
            return plugin.getStatsManager().getTopXp( 7, false);
        }
        if(identifier.equals("top_8_xp_name")){
            return plugin.getStatsManager().getTopXp( 8, true);
        }
        if(identifier.equals("top_8_xp")){
            return plugin.getStatsManager().getTopXp( 8, false);
        }
        if(identifier.equals("top_9_xp_name")){
            return plugin.getStatsManager().getTopXp( 9, true);
        }
        if(identifier.equals("top_9_xp")){
            return plugin.getStatsManager().getTopXp( 9, false);
        }
        if(identifier.equals("top_10_xp_name")){
            return plugin.getStatsManager().getTopXp( 10, true);
        }
        if(identifier.equals("top_10_xp")){
            return plugin.getStatsManager().getTopXp( 10, false);
        }
        if(identifier.equals("top_11_xp_name")){
            return plugin.getStatsManager().getTopXp( 11, true);
        }
        if(identifier.equals("top_11_xp")){
            return plugin.getStatsManager().getTopXp( 11, false);
        }
        if(identifier.equals("top_12_xp_name")){
            return plugin.getStatsManager().getTopXp( 12, true);
        }
        if(identifier.equals("top_12_xp")){
            return plugin.getStatsManager().getTopXp( 12, false);
        }
        if(identifier.equals("top_13_xp_name")){
            return plugin.getStatsManager().getTopXp( 13, true);
        }
        if(identifier.equals("top_13_xp")){
            return plugin.getStatsManager().getTopXp( 13, false);
        }
        if(identifier.equals("top_14_xp_name")){
            return plugin.getStatsManager().getTopXp( 14, true);
        }
        if(identifier.equals("top_14_xp")){
            return plugin.getStatsManager().getTopXp( 14, false);
        }
        if(identifier.equals("top_15_xp_name")){
            return plugin.getStatsManager().getTopXp( 15, true);
        }
        if(identifier.equals("top_15_xp")){
            return plugin.getStatsManager().getTopXp( 15, false);
        }
        if(identifier.equals("top_1_level_name")){
            return plugin.getStatsManager().getTopLevel( 1, true);
        }
        if(identifier.equals("top_1_level")){
            return plugin.getStatsManager().getTopLevel( 1, false);
        }
        if(identifier.equals("top_2_level_name")){
            return plugin.getStatsManager().getTopLevel( 2, true);
        }
        if(identifier.equals("top_2_level")){
            return plugin.getStatsManager().getTopLevel( 2, false);
        }
        if(identifier.equals("top_3_level_name")){
            return plugin.getStatsManager().getTopLevel( 3, true);
        }
        if(identifier.equals("top_3_level")){
            return plugin.getStatsManager().getTopLevel( 3, false);
        }
        if(identifier.equals("top_4_level_name")){
            return plugin.getStatsManager().getTopLevel( 4, true);
        }
        if(identifier.equals("top_4_level")){
            return plugin.getStatsManager().getTopLevel( 4, false);
        }
        if(identifier.equals("top_5_level_name")){
            return plugin.getStatsManager().getTopLevel( 5, true);
        }
        if(identifier.equals("top_5_level")){
            return plugin.getStatsManager().getTopLevel( 5, false);
        }
        if(identifier.equals("top_6_level_name")){
            return plugin.getStatsManager().getTopLevel( 6, true);
        }
        if(identifier.equals("top_6_level")){
            return plugin.getStatsManager().getTopLevel( 6, false);
        }
        if(identifier.equals("top_7_level_name")){
            return plugin.getStatsManager().getTopLevel( 7, true);
        }
        if(identifier.equals("top_7_level")){
            return plugin.getStatsManager().getTopLevel( 7, false);
        }
        if(identifier.equals("top_8_level_name")){
            return plugin.getStatsManager().getTopLevel( 8, true);
        }
        if(identifier.equals("top_8_level")){
            return plugin.getStatsManager().getTopLevel( 8, false);
        }
        if(identifier.equals("top_9_level_name")){
            return plugin.getStatsManager().getTopLevel( 9, true);
        }
        if(identifier.equals("top_9_level")){
            return plugin.getStatsManager().getTopLevel( 9, false);
        }
        if(identifier.equals("top_10_level_name")){
            return plugin.getStatsManager().getTopLevel( 10, true);
        }
        if(identifier.equals("top_10_level")){
            return plugin.getStatsManager().getTopLevel( 10, false);
        }
        if(identifier.equals("top_11_level_name")){
            return plugin.getStatsManager().getTopLevel( 11, true);
        }
        if(identifier.equals("top_11_level")){
            return plugin.getStatsManager().getTopLevel( 11, false);
        }
        if(identifier.equals("top_12_level_name")){
            return plugin.getStatsManager().getTopLevel( 12, true);
        }
        if(identifier.equals("top_12_level")){
            return plugin.getStatsManager().getTopLevel( 12, false);
        }
        if(identifier.equals("top_13_level_name")){
            return plugin.getStatsManager().getTopLevel( 13, true);
        }
        if(identifier.equals("top_13_level")){
            return plugin.getStatsManager().getTopLevel( 13, false);
        }
        if(identifier.equals("top_14_level_name")){
            return plugin.getStatsManager().getTopLevel( 14, true);
        }
        if(identifier.equals("top_14_level")){
            return plugin.getStatsManager().getTopLevel( 14, false);
        }
        if(identifier.equals("top_15_level_name")){
            return plugin.getStatsManager().getTopLevel( 15, true);
        }
        if(identifier.equals("top_15_level")){
            return plugin.getStatsManager().getTopLevel( 15, false);
        }
        if(identifier.equals("top_1_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 1, true);
        }
        if(identifier.equals("top_1_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 1, false);
        }
        if(identifier.equals("top_2_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 2, true);
        }
        if(identifier.equals("top_2_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 2, false);
        }
        if(identifier.equals("top_3_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 3, true);
        }
        if(identifier.equals("top_3_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 3, false);
        }
        if(identifier.equals("top_4_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 4, true);
        }
        if(identifier.equals("top_4_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 4, false);
        }
        if(identifier.equals("top_5_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 5, true);
        }
        if(identifier.equals("top_5_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 5, false);
        }
        if(identifier.equals("top_6_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 6, true);
        }
        if(identifier.equals("top_6_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 6, false);
        }
        if(identifier.equals("top_7_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 7, true);
        }
        if(identifier.equals("top_7_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 7, false);
        }
        if(identifier.equals("top_8_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 8, true);
        }
        if(identifier.equals("top_8_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 8, false);
        }
        if(identifier.equals("top_9_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 9, true);
        }
        if(identifier.equals("top_9_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 9, false);
        }
        if(identifier.equals("top_10_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 10, true);
        }
        if(identifier.equals("top_10_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 10, false);
        }
        if(identifier.equals("top_11_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 11, true);
        }
        if(identifier.equals("top_11_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 11, false);
        }
        if(identifier.equals("top_12_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 12, true);
        }
        if(identifier.equals("top_12_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 12, false);
        }
        if(identifier.equals("top_13_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 13, true);
        }
        if(identifier.equals("top_13_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 13, false);
        }
        if(identifier.equals("top_14_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 14, true);
        }
        if(identifier.equals("top_14_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 14, false);
        }
        if(identifier.equals("top_15_killstreak_name")){
            return plugin.getStatsManager().getTopKillStreak( 15, true);
        }
        if(identifier.equals("top_15_killstreak")){
            return plugin.getStatsManager().getTopKillStreak( 15, false);
        }
        if(identifier.equals("top_1_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 1, true);
        }
        if(identifier.equals("top_1_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 1, false);
        }
        if(identifier.equals("top_2_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 2, true);
        }
        if(identifier.equals("top_2_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 2, false);
        }
        if(identifier.equals("top_3_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 3, true);
        }
        if(identifier.equals("top_3_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 3, false);
        }
        if(identifier.equals("top_4_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 4, true);
        }
        if(identifier.equals("top_4_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 4, false);
        }
        if(identifier.equals("top_5_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 5, true);
        }
        if(identifier.equals("top_5_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 5, false);
        }
        if(identifier.equals("top_6_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 6, true);
        }
        if(identifier.equals("top_6_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 6, false);
        }
        if(identifier.equals("top_7_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 7, true);
        }
        if(identifier.equals("top_7_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 7, false);
        }
        if(identifier.equals("top_8_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 8, true);
        }
        if(identifier.equals("top_8_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 8, false);
        }
        if(identifier.equals("top_9_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 9, true);
        }
        if(identifier.equals("top_9_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 9, false);
        }
        if(identifier.equals("top_10_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 10, true);
        }
        if(identifier.equals("top_10_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 10, false);
        }
        if(identifier.equals("top_11_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 11, true);
        }
        if(identifier.equals("top_11_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 11, false);
        }
        if(identifier.equals("top_12_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 12, true);
        }
        if(identifier.equals("top_12_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 12, false);
        }
        if(identifier.equals("top_13_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 13, true);
        }
        if(identifier.equals("top_13_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 13, false);
        }
        if(identifier.equals("top_14_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 14, true);
        }
        if(identifier.equals("top_14_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 14, false);
        }
        if(identifier.equals("top_15_killstreak_top_name")){
            return plugin.getStatsManager().getTopKillStreakTop( 15, true);
        }
        if(identifier.equals("top_15_killstreak_top")){
            return plugin.getStatsManager().getTopKillStreakTop( 15, false);
        }
        if (identifier.equals("helmet_remaining_durability")) {
            return String.valueOf(Utils.getDurability(player.getInventory().getHelmet())[0]);
        }
        if (identifier.equals("helmet_max_durability")) {
            return String.valueOf(Utils.getDurability(player.getInventory().getHelmet())[1]);
        }
        if (identifier.equals("chestplate_remaining_durability")) {
            return String.valueOf(Utils.getDurability(player.getInventory().getChestplate())[0]);
        }
        if (identifier.equals("chestplate_max_durability")) {
            return String.valueOf(Utils.getDurability(player.getInventory().getChestplate())[1]);
        }
        if (identifier.equals("leggings_remaining_durability")) {
            return String.valueOf(Utils.getDurability(player.getInventory().getLeggings())[0]);
        }
        if (identifier.equals("leggings_max_durability")) {
            return String.valueOf(Utils.getDurability(player.getInventory().getLeggings())[1]);
        }
        if (identifier.equals("boots_remaining_durability")) {
            return String.valueOf(Utils.getDurability(player.getInventory().getBoots())[0]);
        }
        if (identifier.equals("boots_max_durability")) {
            return String.valueOf(Utils.getDurability(player.getInventory().getBoots())[1]);
        }
        if (identifier.equals("item_in_mainhand_remaining_durability")) {
            return String.valueOf(Utils.getDurability(Utils.getHandItemStack(player, true))[0]);
        }
        if (identifier.equals("item_in_mainhand_max_durability")) {
            return String.valueOf(Utils.getDurability(Utils.getHandItemStack(player, true))[1]);
        }
        if (identifier.equals("item_in_offhand_remaining_durability")) {
            return String.valueOf(Utils.getDurability(Utils.getHandItemStack(player, false))[0]);
        }
        if (identifier.equals("item_in_offhand_max_durability")) {
            return String.valueOf(Utils.getDurability(Utils.getHandItemStack(player, false))[1]);
        }
        return null;
    }
}