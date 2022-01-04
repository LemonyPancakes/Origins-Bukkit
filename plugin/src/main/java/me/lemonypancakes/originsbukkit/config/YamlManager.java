/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2021 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.originsbukkit.config;

import com.google.common.io.ByteStreams;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class YamlManager {

    private final OriginsBukkit plugin;
    private final String internalName;
    private final String internalLocation;
    private final String externalName;
    private final String externalLocation;
    private FileConfiguration file;
    private File loc;

    public YamlManager(OriginsBukkit plugin, String name) {
        this.plugin = plugin;
        this.internalName = name;
        this.externalName = name;
        this.internalLocation = null;
        this.externalLocation = null;
        load();
    }

    public YamlManager(OriginsBukkit plugin, String name, String location) {
        this.plugin = plugin;
        this.internalName = name;
        this.externalName = name;
        this.internalLocation = location;
        this.externalLocation = location;
        load();
    }

    public YamlManager(OriginsBukkit plugin, String name, String location, String newName) {
        this.plugin = plugin;
        this.internalName = name;
        this.externalName = newName;
        this.internalLocation = location;
        this.externalLocation = location;
        load();
    }

    public YamlManager(OriginsBukkit plugin, String name, String location, String newName, String newLocation) {
        this.plugin = plugin;
        this.internalName = name;
        this.externalName = newName;
        this.internalLocation = location;
        this.externalLocation = newLocation;
        load();
    }

    public static boolean isReadable(File file) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.load(file);
            return true;
        } catch (IOException event) {
            return false;
        } catch (InvalidConfigurationException event) {
            return false;
        }
    }

    public void addComments(Map<Integer, String> comments) {
        save(true);
        //load all data from file
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(loc);
        } catch (FileNotFoundException event) {
            return;
        }
        Reader reader = new InputStreamReader(stream);
        BufferedReader input = (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
        List<String> toSave = new ArrayList<String>();
        try {
            String temp;
            try {
                while ((temp = input.readLine()) != null) {
                    toSave.add(temp);
                }
            } catch (IOException event) {
                return;
            }
        } finally {
            try {
                input.close();
            } catch (IOException event) {
                return;
            }
        }
        //add comments to the data
        int maxIndex = Integer.MIN_VALUE;
        for (int i : comments.keySet())
            if (i > maxIndex)
                maxIndex = i;
        while (maxIndex > toSave.size())
            toSave.add("");
        for (int i = 0; i <= maxIndex; i++) {
            if (comments.containsKey(i)) {
                String comment = comments.get(i);
                if (!comment.startsWith("#"))
                    comment = "#" + comment;
                toSave.add(i, comment);
            }
        }
        //make string
        StringBuilder builder = new StringBuilder();
        for (String s : toSave) {
            builder.append(s);
            builder.append("\n");
        }
        //save it
        try {
            Files.write(loc.toPath(), builder.toString().getBytes(), StandardOpenOption.WRITE);
        } catch (IOException event) {
            return;
        }
    }

    public void addComment(String comment, int line) {
        save(true);
        if (!comment.startsWith("#"))
            comment = "#" + comment;
        //load all data from file
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(loc);
        } catch (FileNotFoundException event) {
            return;
        }
        Reader reader = new InputStreamReader(stream);
        BufferedReader input = (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
        List<String> toSave = new ArrayList<String>();
        try {
            String temp;
            try {
                while ((temp = input.readLine()) != null) {
                    toSave.add(temp);
                }
            } catch (IOException event) {
                return;
            }
        } finally {
            try {
                input.close();
            } catch (IOException event) {
                return;
            }
        }
        //add comments to the data
        while (line > toSave.size())
            toSave.add("");
        toSave.add(line, comment);
        //make string
        StringBuilder builder = new StringBuilder();
        for (String s : toSave) {
            builder.append(s);
            builder.append("\n");
        }
        //save it
        try {
            Files.write(loc.toPath(), builder.toString().getBytes(), StandardOpenOption.WRITE);
        } catch (IOException event) {
            return;
        }
    }

    public void addComment(String comment) {
        save(true);
        if (!comment.startsWith("#"))
            comment = "#" + comment;
        //load all data from file
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(loc);
        } catch (FileNotFoundException event) {
            return;
        }
        Reader reader = new InputStreamReader(stream);
        BufferedReader input = (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
        List<String> toSave = new ArrayList<String>();
        try {
            String temp;
            try {
                while ((temp = input.readLine()) != null) {
                    toSave.add(temp);
                }
            } catch (IOException event) {
                return;
            }
        } finally {
            try {
                input.close();
            } catch (IOException event) {
                return;
            }
        }
        //add comments to the data
        toSave.add(comment);
        //make string
        StringBuilder builder = new StringBuilder();
        for (String s : toSave) {
            builder.append(s);
            builder.append("\n");
        }
        //save it
        try {
            Files.write(loc.toPath(), builder.toString().getBytes(), StandardOpenOption.WRITE);
        } catch (IOException event) {
            return;
        }
    }

    public FileConfiguration getFile() {
        return this.file;
    }

    public void save(boolean preserveComments) {
        if (preserveComments) {
            saveCommented(this.loc);
        } else {
            try {
                file.save(loc);
            } catch (IOException event) {
            }
        }
    }

    public void save(File location, boolean preserveComments) {
        if (preserveComments) {
            saveCommented(location);
        } else {
            try {
                file.save(loc);
            } catch (IOException event) {
            }
        }
    }

    public void reload() {
        load();
    }

    public void loadFromPlugin() {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        //make directories upto external location
        if (externalLocation != null) {
            File temp = new File(plugin.getDataFolder() + File.separator + externalLocation);
            if (!temp.exists())
                temp.mkdirs();
            loc = new File(plugin.getDataFolder() + File.separator + externalLocation, externalName + ".yml");
        } else
            loc = new File(plugin.getDataFolder(), externalName + ".yml");
        //create a new file
        try {
            loc.createNewFile();
            //check if there is a file within plugin
            if (internalLocation == null && plugin.getResource(internalName + ".yml") != null) { //exists
                InputStream is = plugin.getResource(internalName + ".yml");
                OutputStream os = new FileOutputStream(loc);
                ByteStreams.copy(is, os);
            } else if (plugin.getClass().getResourceAsStream(File.separator + internalLocation + File.separator + internalName + ".yml") != null) { //exists
                InputStream is = plugin.getClass().getResourceAsStream(File.separator + internalLocation + File.separator + internalName + ".yml");
                OutputStream os = new FileOutputStream(loc);
                ByteStreams.copy(is, os);
            }
        } catch (IOException event) {
        }
        //load the yml file
        file = YamlConfiguration.loadConfiguration(loc);
    }

    public File getLoc() {
        return loc;
    }

    private void load() {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        //make directories upto external location
        if (externalLocation != null) {
            File temp = new File(plugin.getDataFolder() + File.separator + externalLocation);
            if (!temp.exists())
                temp.mkdirs();
            loc = new File(plugin.getDataFolder() + File.separator + externalLocation, externalName + ".yml");
        } else
            loc = new File(plugin.getDataFolder(), externalName + ".yml");
        //check if the yml file already exists
        if (!loc.exists()) {
            //create a new file
            try {
                loc.createNewFile();
                //check if there is a file within plugin
                if (internalLocation == null && plugin.getResource(internalName + ".yml") != null) { //exists
                    InputStream is = plugin.getResource(internalName + ".yml");
                    OutputStream os = new FileOutputStream(loc);
                    ByteStreams.copy(is, os);
                } else if (plugin.getClass().getResourceAsStream(File.separator + internalLocation + File.separator + internalName + ".yml") != null) { //exists
                    InputStream is = plugin.getClass().getResourceAsStream(File.separator + internalLocation + File.separator + internalName + ".yml");
                    OutputStream os = new FileOutputStream(loc);
                    ByteStreams.copy(is, os);
                }
            } catch (IOException event) {
            }
        }
        //load the yml file
        file = YamlConfiguration.loadConfiguration(loc);
    }

    private void saveCommented(File location) {
        //load all comments
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(location);
        } catch (FileNotFoundException event) {
            return;
        }
        Reader reader = new InputStreamReader(stream);
        BufferedReader input = (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
        Map<Integer, String> comments = new HashMap<Integer, String>();
        try {
            String line;
            int index = 0;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.contains("#"))
                        comments.put(index, line.substring(line.indexOf("#")));
                    index++;
                }
            } catch (IOException event) {
                return;
            }
        } finally {
            try {
                input.close();
            } catch (IOException event) {
                return;
            }
        }
        //load all data
        List<String> toSave = new ArrayList<String>();
        String dataStream = file.saveToString();
        for (String s : dataStream.split("\n")) {
            toSave.add(s);
        }
        //add comments to the data
        int maxIndex = Integer.MIN_VALUE;
        for (int i : comments.keySet())
            if (i > maxIndex)
                maxIndex = i;
        while (maxIndex > toSave.size())
            toSave.add("");
        for (int i = 0; i <= maxIndex; i++) {
            if (comments.containsKey(i))
                toSave.add(i, comments.get(i));
        }
        //make string
        StringBuilder builder = new StringBuilder();
        for (String s : toSave) {
            builder.append(s);
            builder.append("\n");
        }
        //save it
        try {
            Files.write(location.toPath(), builder.toString().getBytes(), StandardOpenOption.WRITE);
        } catch (IOException event) {
            return;
        }
    }
}
