package ibf2022.batch2.ssf.frontcontroller.models;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CaptchaInput implements Serializable {
  @NotNull(message = "Please answer the captcha.")
  @Size(min = 0, max = 100, message = "Please enter a valid number.")
  private String input;
}
