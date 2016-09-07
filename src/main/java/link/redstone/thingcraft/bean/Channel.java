package link.redstone.thingcraft.bean;

import scala.tools.nsc.typechecker.Tags;

import java.util.List;

/**
 * Created by Erioifpud on 16/9/6.
 */
public class Channel {
    private int id;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private String created_at;
    private String elevation;
    private int last_entry_id;
    private int ranking;
    private String metadata;
    private List<Tag> tags;
    private List<ApiKey> api_keys;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public int getLast_entry_id() {
        return last_entry_id;
    }

    public void setLast_entry_id(int last_entry_id) {
        this.last_entry_id = last_entry_id;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public List<ApiKey> getApi_keys() {
        return api_keys;
    }

    public void setApi_keys(List<ApiKey> api_keys) {
        this.api_keys = api_keys;
    }

    public static class Tag {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ApiKey {
        private String api_key;
        private boolean write_flag;

        public String getApi_key() {
            return api_key;
        }

        public void setApi_key(String api_key) {
            this.api_key = api_key;
        }

        public boolean isWrite_flag() {
            return write_flag;
        }

        public void setWrite_flag(boolean write_flag) {
            this.write_flag = write_flag;
        }
    }

}
