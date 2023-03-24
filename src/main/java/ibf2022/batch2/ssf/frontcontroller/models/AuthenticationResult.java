package ibf2022.batch2.ssf.frontcontroller.models;

import java.io.Serializable;

import jakarta.json.JsonObject;

public class AuthenticationResult implements Serializable {
  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public static AuthenticationResult fromJson(JsonObject o) {
    AuthenticationResult r = new AuthenticationResult();
    r.setMessage(o.getString("message"));

    return r;
  }
}
