package usermanagement.utility;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import usermanagement.model.User;

import java.util.List;

import com.google.gson.Gson;

public class JsonConverter {
	
    private final Gson gson;

    public JsonConverter() {

        gson = new GsonBuilder().create();
    }

    public String convertToJson(User user) {
        return gson.toJson(user);
    }
    
    public String convertToJson(List<User> users) {
        var jarray = gson.toJsonTree(users).getAsJsonArray();
        return jarray.toString();
    }
    
    public User convertToUser(String user) {
        return gson.fromJson(user, User.class);
    }
	
}