package ibf2022.batch2.ssf.frontcontroller.models;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserCredentials implements Serializable {

  @NotNull(message = "Please enter a username.")
  @Size(min = 2, message = "Username must be at least 2 characters long.")
  private String username;

  @NotNull(message = "Please enter a password")
  @Size(min = 2, message = "Password must be at least 2 characters long.")
  private String password;

  @Valid
  private CaptchaInput captchaInput;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("username", username)
        .add("password", password)
        .build();
  }

  public static UserCredentials fromJson(JsonObject o) {
    UserCredentials c = new UserCredentials();
    c.setUsername(o.getString("username"));
    c.setPassword(o.getString("password"));

    return c;
  }
}
