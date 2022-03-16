package POJO;

import com.google.gson.Gson;

import java.util.Map;

public class dataPOJO {
    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String password;
    public int getId()
    {
        return  id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getFirst_name(){
        return  first_name;
    }
    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }
    public String getLast_name(){
        return last_name;
    }
    public void setLast_name(String last_name){
        this.last_name=last_name;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getUserPayload(Map<String, String> testData) {
        dataPOJO user = new dataPOJO();
        user.setId(Integer.parseInt(testData.get("id")));
        user.setEmail(testData.get("email"));
        user.setFirst_name(testData.get("first_name"));
        user.setLast_name(testData.get("last_name"));

        Gson gson = new Gson();

        return gson.toJson(user);
    }
    public String updateUserPayload(Map<String, String> testData)
    {
        dataPOJO user = new dataPOJO();
        user.setEmail(testData.get("email"));
        user.setPassword(testData.get("password"));
        Gson gson = new Gson();
        return gson.toJson(user);

    }
}
