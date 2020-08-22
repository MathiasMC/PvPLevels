package me.MathiasMC.PvPLevels.placeholders;

import me.MathiasMC.PvPLevels.PvPLevels;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

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
        final String uuid = player.getUniqueId().toString();
        final PlayerConnect playerConnect = plugin.get(uuid);
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
            return String.valueOf(playerConnect.getKillstreak_top());
        }
        if(identifier.equals("kdr")){
            return plugin.statsManager.kdr(playerConnect);
        }
        if(identifier.equals("kill_factor")){
            return plugin.statsManager.kill_factor(playerConnect);
        }
        if (identifier.equals("xp_need")) {
            return String.valueOf(plugin.statsManager.xp_need(playerConnect));
        }
        if(identifier.equals("xp_required")){
            return String.valueOf(plugin.statsManager.xp_required(playerConnect, false));
        }
        if(identifier.equals("xp_required_next")){
            return String.valueOf(plugin.statsManager.xp_required(playerConnect, true));
        }
        if(identifier.equals("xp_progress")){
            return String.valueOf(plugin.statsManager.xp_progress(playerConnect));
        }
        if(identifier.equals("xp_progress_style")){
            return String.valueOf(plugin.statsManager.xp_progress_style(playerConnect, "xp-progress-style"));
        }
        if(identifier.equals("xp_progress_style_2")){
            return String.valueOf(plugin.statsManager.xp_progress_style(playerConnect, "xp-progress-style-2"));
        }
        if(identifier.equals("time")){
            return String.valueOf(plugin.statsManager.time("time", playerConnect.getTime().getTime()));
        }
        if(identifier.equals("date")){
            return String.valueOf(plugin.statsManager.time("date", playerConnect.getTime().getTime()));
        }
        if(identifier.equals("group")) {
            return playerConnect.getGroup();
        }
        if(identifier.equals("level_group")) {
            return plugin.statsManager.group(playerConnect);
        }
        if(identifier.equals("level_prefix")) {
            return plugin.statsManager.prefix(playerConnect);
        }
        if(identifier.equals("multiplier")) {
            if (playerConnect.getMultiplier() != 0D) {
                return String.valueOf(playerConnect.getMultiplier());
            } else {
                return String.valueOf(1);
            }
        }
        if(identifier.equals("multiplier_time")) {
            if (playerConnect.getMultiplier_time() != 0D) {
                return plugin.statsManager.time("multiplier", (new GregorianCalendar(0, 0,0,0,0, playerConnect.getMultiplier_time()).getTime().getTime()));
            } else {
                return String.valueOf(0);
            }
        }
        if(identifier.equals("multiplier_time_left")) {
            if (playerConnect.getMultiplier_time() != 0D) {
                return plugin.statsManager.time("multiplier", (new GregorianCalendar(0, 0,0,0,0, playerConnect.getMultiplier_time_left()).getTime().getTime()));
            } else {
                return String.valueOf(0);
            }
        }
        if (identifier.equals("helmet_remaining_durability")) {
            return String.valueOf(plugin.placeholderManager.getDurability(player.getInventory().getHelmet())[0]);
        }
        if (identifier.equals("helmet_max_durability")) {
            return String.valueOf(plugin.placeholderManager.getDurability(player.getInventory().getHelmet())[1]);
        }
        if (identifier.equals("chestplate_remaining_durability")) {
            return String.valueOf(plugin.placeholderManager.getDurability(player.getInventory().getChestplate())[0]);
        }
        if (identifier.equals("chestplate_max_durability")) {
            return String.valueOf(plugin.placeholderManager.getDurability(player.getInventory().getChestplate())[1]);
        }
        if (identifier.equals("leggings_remaining_durability")) {
            return String.valueOf(plugin.placeholderManager.getDurability(player.getInventory().getLeggings())[0]);
        }
        if (identifier.equals("leggings_max_durability")) {
            return String.valueOf(plugin.placeholderManager.getDurability(player.getInventory().getLeggings())[1]);
        }
        if (identifier.equals("boots_remaining_durability")) {
            return String.valueOf(plugin.placeholderManager.getDurability(player.getInventory().getBoots())[0]);
        }
        if (identifier.equals("boots_max_durability")) {
            return String.valueOf(plugin.placeholderManager.getDurability(player.getInventory().getBoots())[1]);
        }
        if (identifier.equals("item_in_mainhand_remaining_durability")) {
            return String.valueOf(plugin.placeholderManager.getDurability(plugin.placeholderManager.getHandItemStack(player, true))[0]);
        }
        if (identifier.equals("item_in_mainhand_max_durability")) {
            return String.valueOf(plugin.placeholderManager.getDurability(plugin.placeholderManager.getHandItemStack(player, true))[1]);
        }
        if (identifier.equals("item_in_offhand_remaining_durability")) {
            return String.valueOf(plugin.placeholderManager.getDurability(plugin.placeholderManager.getHandItemStack(player, false))[0]);
        }
        if (identifier.equals("item_in_offhand_max_durability")) {
            return String.valueOf(plugin.placeholderManager.getDurability(plugin.placeholderManager.getHandItemStack(player, false))[1]);
        }
        if(identifier.equals("top_1_kills_name")){
            return plugin.statsManager.getTopValue("kills", 0, true, true);
        }
        if(identifier.equals("top_1_kills")){
            return plugin.statsManager.getTopValue("kills", 0, false, true);
        }
        if(identifier.equals("top_2_kills_name")){
            return plugin.statsManager.getTopValue("kills", 1, true, true);
        }
        if(identifier.equals("top_2_kills")){
            return plugin.statsManager.getTopValue("kills", 1, false, true);
        }
        if(identifier.equals("top_3_kills_name")){
            return plugin.statsManager.getTopValue("kills", 2, true, true);
        }
        if(identifier.equals("top_3_kills")){
            return plugin.statsManager.getTopValue("kills", 2, false, true);
        }
        if(identifier.equals("top_4_kills_name")){
            return plugin.statsManager.getTopValue("kills", 3, true, true);
        }
        if(identifier.equals("top_4_kills")){
            return plugin.statsManager.getTopValue("kills", 3, false, true);
        }
        if(identifier.equals("top_5_kills_name")){
            return plugin.statsManager.getTopValue("kills", 4, true, true);
        }
        if(identifier.equals("top_5_kills")){
            return plugin.statsManager.getTopValue("kills", 4, false, true);
        }
        if(identifier.equals("top_6_kills_name")){
            return plugin.statsManager.getTopValue("kills", 5, true, true);
        }
        if(identifier.equals("top_6_kills")){
            return plugin.statsManager.getTopValue("kills", 5, false, true);
        }
        if(identifier.equals("top_7_kills_name")){
            return plugin.statsManager.getTopValue("kills", 6, true, true);
        }
        if(identifier.equals("top_7_kills")){
            return plugin.statsManager.getTopValue("kills", 6, false, true);
        }
        if(identifier.equals("top_8_kills_name")){
            return plugin.statsManager.getTopValue("kills", 7, true, true);
        }
        if(identifier.equals("top_8_kills")){
            return plugin.statsManager.getTopValue("kills", 7, false, true);
        }
        if(identifier.equals("top_9_kills_name")){
            return plugin.statsManager.getTopValue("kills", 8, true, true);
        }
        if(identifier.equals("top_9_kills")){
            return plugin.statsManager.getTopValue("kills", 8, false, true);
        }
        if(identifier.equals("top_10_kills_name")){
            return plugin.statsManager.getTopValue("kills", 9, true, true);
        }
        if(identifier.equals("top_10_kills")){
            return plugin.statsManager.getTopValue("kills", 9, false, true);
        }
        if(identifier.equals("top_1_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 0, true, true);
        }
        if(identifier.equals("top_1_deaths")){
            return plugin.statsManager.getTopValue("deaths", 0, false, true);
        }
        if(identifier.equals("top_2_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 1, true, true);
        }
        if(identifier.equals("top_2_deaths")){
            return plugin.statsManager.getTopValue("deaths", 1, false, true);
        }
        if(identifier.equals("top_3_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 2, true, true);
        }
        if(identifier.equals("top_3_deaths")){
            return plugin.statsManager.getTopValue("deaths", 2, false, true);
        }
        if(identifier.equals("top_4_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 3, true, true);
        }
        if(identifier.equals("top_4_deaths")){
            return plugin.statsManager.getTopValue("deaths", 3, false, true);
        }
        if(identifier.equals("top_5_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 4, true, true);
        }
        if(identifier.equals("top_5_deaths")){
            return plugin.statsManager.getTopValue("deaths", 4, false, true);
        }
        if(identifier.equals("top_6_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 5, true, true);
        }
        if(identifier.equals("top_6_deaths")){
            return plugin.statsManager.getTopValue("deaths", 5, false, true);
        }
        if(identifier.equals("top_7_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 6, true, true);
        }
        if(identifier.equals("top_7_deaths")){
            return plugin.statsManager.getTopValue("deaths", 6, false, true);
        }
        if(identifier.equals("top_8_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 7, true, true);
        }
        if(identifier.equals("top_8_deaths")){
            return plugin.statsManager.getTopValue("deaths", 7, false, true);
        }
        if(identifier.equals("top_9_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 8, true, true);
        }
        if(identifier.equals("top_9_deaths")){
            return plugin.statsManager.getTopValue("deaths", 8, false, true);
        }
        if(identifier.equals("top_10_deaths_name")){
            return plugin.statsManager.getTopValue("deaths", 9, true, true);
        }
        if(identifier.equals("top_10_deaths")){
            return plugin.statsManager.getTopValue("deaths", 9, false, true);
        }
        if(identifier.equals("top_1_xp_name")){
            return plugin.statsManager.getTopValue("xp", 0, true, true);
        }
        if(identifier.equals("top_1_xp")){
            return plugin.statsManager.getTopValue("xp", 0, false, true);
        }
        if(identifier.equals("top_2_xp_name")){
            return plugin.statsManager.getTopValue("xp", 1, true, true);
        }
        if(identifier.equals("top_2_xp")){
            return plugin.statsManager.getTopValue("xp", 1, false, true);
        }
        if(identifier.equals("top_3_xp_name")){
            return plugin.statsManager.getTopValue("xp", 2, true, true);
        }
        if(identifier.equals("top_3_xp")){
            return plugin.statsManager.getTopValue("xp", 2, false, true);
        }
        if(identifier.equals("top_4_xp_name")){
            return plugin.statsManager.getTopValue("xp", 3, true, true);
        }
        if(identifier.equals("top_4_xp")){
            return plugin.statsManager.getTopValue("xp", 3, false, true);
        }
        if(identifier.equals("top_5_xp_name")){
            return plugin.statsManager.getTopValue("xp", 4, true, true);
        }
        if(identifier.equals("top_5_xp")){
            return plugin.statsManager.getTopValue("xp", 4, false, true);
        }
        if(identifier.equals("top_6_xp_name")){
            return plugin.statsManager.getTopValue("xp", 5, true, true);
        }
        if(identifier.equals("top_6_xp")){
            return plugin.statsManager.getTopValue("xp", 5, false, true);
        }
        if(identifier.equals("top_7_xp_name")){
            return plugin.statsManager.getTopValue("xp", 6, true, true);
        }
        if(identifier.equals("top_7_xp")){
            return plugin.statsManager.getTopValue("xp", 6, false, true);
        }
        if(identifier.equals("top_8_xp_name")){
            return plugin.statsManager.getTopValue("xp", 7, true, true);
        }
        if(identifier.equals("top_8_xp")){
            return plugin.statsManager.getTopValue("xp", 7, false, true);
        }
        if(identifier.equals("top_9_xp_name")){
            return plugin.statsManager.getTopValue("xp", 8, true, true);
        }
        if(identifier.equals("top_9_xp")){
            return plugin.statsManager.getTopValue("xp", 8, false, true);
        }
        if(identifier.equals("top_10_xp_name")){
            return plugin.statsManager.getTopValue("xp", 9, true, true);
        }
        if(identifier.equals("top_10_xp")){
            return plugin.statsManager.getTopValue("xp", 9, false, true);
        }
        if(identifier.equals("top_1_level_name")){
            return plugin.statsManager.getTopValue("level", 0, true, true);
        }
        if(identifier.equals("top_1_level")){
            return plugin.statsManager.getTopValue("level", 0, false, true);
        }
        if(identifier.equals("top_2_level_name")){
            return plugin.statsManager.getTopValue("level", 1, true, true);
        }
        if(identifier.equals("top_2_level")){
            return plugin.statsManager.getTopValue("level", 1, false, true);
        }
        if(identifier.equals("top_3_level_name")){
            return plugin.statsManager.getTopValue("level", 2, true, true);
        }
        if(identifier.equals("top_3_level")){
            return plugin.statsManager.getTopValue("level", 2, false, true);
        }
        if(identifier.equals("top_4_level_name")){
            return plugin.statsManager.getTopValue("level", 3, true, true);
        }
        if(identifier.equals("top_4_level")){
            return plugin.statsManager.getTopValue("level", 3, false, true);
        }
        if(identifier.equals("top_5_level_name")){
            return plugin.statsManager.getTopValue("level", 4, true, true);
        }
        if(identifier.equals("top_5_level")){
            return plugin.statsManager.getTopValue("level", 4, false, true);
        }
        if(identifier.equals("top_6_level_name")){
            return plugin.statsManager.getTopValue("level", 5, true, true);
        }
        if(identifier.equals("top_6_level")){
            return plugin.statsManager.getTopValue("level", 5, false, true);
        }
        if(identifier.equals("top_7_level_name")){
            return plugin.statsManager.getTopValue("level", 6, true, true);
        }
        if(identifier.equals("top_7_level")){
            return plugin.statsManager.getTopValue("level", 6, false, true);
        }
        if(identifier.equals("top_8_level_name")){
            return plugin.statsManager.getTopValue("level", 7, true, true);
        }
        if(identifier.equals("top_8_level")){
            return plugin.statsManager.getTopValue("level", 7, false, true);
        }
        if(identifier.equals("top_9_level_name")){
            return plugin.statsManager.getTopValue("level", 8, true, true);
        }
        if(identifier.equals("top_9_level")){
            return plugin.statsManager.getTopValue("level", 8, false, true);
        }
        if(identifier.equals("top_10_level_name")){
            return plugin.statsManager.getTopValue("level", 9, true, true);
        }
        if(identifier.equals("top_10_level")){
            return plugin.statsManager.getTopValue("level", 9, false, true);
        }
        if(identifier.equals("top_1_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 0, true, true);
        }
        if(identifier.equals("top_1_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 0, false, true);
        }
        if(identifier.equals("top_2_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 1, true, true);
        }
        if(identifier.equals("top_2_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 1, false, true);
        }
        if(identifier.equals("top_3_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 2, true, true);
        }
        if(identifier.equals("top_3_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 2, false, true);
        }
        if(identifier.equals("top_4_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 3, true, true);
        }
        if(identifier.equals("top_4_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 3, false, true);
        }
        if(identifier.equals("top_5_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 4, true, true);
        }
        if(identifier.equals("top_5_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 4, false, true);
        }
        if(identifier.equals("top_6_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 5, true, true);
        }
        if(identifier.equals("top_6_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 5, false, true);
        }
        if(identifier.equals("top_7_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 6, true, true);
        }
        if(identifier.equals("top_7_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 6, false, true);
        }
        if(identifier.equals("top_8_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 7, true, true);
        }
        if(identifier.equals("top_8_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 7, false, true);
        }
        if(identifier.equals("top_9_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 8, true, true);
        }
        if(identifier.equals("top_9_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 8, false, true);
        }
        if(identifier.equals("top_10_killstreak_name")){
            return plugin.statsManager.getTopValue("killstreak", 9, true, true);
        }
        if(identifier.equals("top_10_killstreak")){
            return plugin.statsManager.getTopValue("killstreak", 9, false, true);
        }
        if(identifier.equals("top_1_killstreak_top_name")){
            return plugin.statsManager.getTopValue("killstreak_top", 0, true, true);
        }
        if(identifier.equals("top_1_killstreak_top")){
            return plugin.statsManager.getTopValue("killstreak_top", 0, false, true);
        }
        if(identifier.equals("top_2_killstreak_top_name")){
            return plugin.statsManager.getTopValue("killstreak_top", 1, true, true);
        }
        if(identifier.equals("top_2_killstreak_top")){
            return plugin.statsManager.getTopValue("killstreak_top", 1, false, true);
        }
        if(identifier.equals("top_3_killstreak_top_name")){
            return plugin.statsManager.getTopValue("killstreak_top", 2, true, true);
        }
        if(identifier.equals("top_3_killstreak_top")){
            return plugin.statsManager.getTopValue("killstreak_top", 2, false, true);
        }
        if(identifier.equals("top_4_killstreak_top_name")){
            return plugin.statsManager.getTopValue("killstreak_top", 3, true, true);
        }
        if(identifier.equals("top_4_killstreak_top")){
            return plugin.statsManager.getTopValue("killstreak_top", 3, false, true);
        }
        if(identifier.equals("top_5_killstreak_top_name")){
            return plugin.statsManager.getTopValue("killstreak_top", 4, true, true);
        }
        if(identifier.equals("top_5_killstreak_top")){
            return plugin.statsManager.getTopValue("killstreak_top", 4, false, true);
        }
        if(identifier.equals("top_6_killstreak_top_name")){
            return plugin.statsManager.getTopValue("killstreak_top", 5, true, true);
        }
        if(identifier.equals("top_6_killstreak_top")){
            return plugin.statsManager.getTopValue("killstreak_top", 5, false, true);
        }
        if(identifier.equals("top_7_killstreak_top_name")){
            return plugin.statsManager.getTopValue("killstreak_top", 6, true, true);
        }
        if(identifier.equals("top_7_killstreak_top")){
            return plugin.statsManager.getTopValue("killstreak_top", 6, false, true);
        }
        if(identifier.equals("top_8_killstreak_top_name")){
            return plugin.statsManager.getTopValue("killstreak_top", 7, true, true);
        }
        if(identifier.equals("top_8_killstreak_top")){
            return plugin.statsManager.getTopValue("killstreak_top", 7, false, true);
        }
        if(identifier.equals("top_9_killstreak_top_name")){
            return plugin.statsManager.getTopValue("killstreak_top", 8, true, true);
        }
        if(identifier.equals("top_9_killstreak_top")){
            return plugin.statsManager.getTopValue("killstreak_top", 8, false, true);
        }
        if(identifier.equals("top_10_killstreak_top_name")){
            return plugin.statsManager.getTopValue("killstreak_top", 9, true, true);
        }
        if(identifier.equals("top_10_killstreak_top")){
            return plugin.statsManager.getTopValue("killstreak_top", 9, false, true);
        }
        return null;
    }
}