package ibf2022.batch2.ssf.frontcontroller.models;

import java.io.Serializable;
import java.util.Random;

public class Captcha implements Serializable {

  private Integer num1;
  private Integer num2;
  private String operator;

  private static String result;

  // generate captcha:
  public Captcha() {
    Random random = new Random();
    num1 = random.nextInt(50) + 1;
    num2 = random.nextInt(50) + 1;
    Integer opNum = random.nextInt(4);
    operator = this.getOperator(opNum);
    result = this.getResult(num1, num2, opNum);
  }

  private String getOperator(Integer n) {
    String operator;

    switch (n) {
      case 0:
        operator = "+";
        break;
      case 1:
        operator = "-";
        break;
      case 2:
        operator = "x";
        break;
      case 3:
        operator = "÷";
        break;
      default:
        operator = "+";
        break;
    }

    return operator;
  }

  private String getResult(Integer num1, Integer num2, Integer n) {
    Integer result = 0;

    switch (n) {
      case 0:
        result = num1 + num2;
        break;
      case 1:
        result = num1 - num2;
        break;
      case 2:
        result = num1 * num2;
        break;
      case 3:
        result = num1 / num2;
        break;
      default:
        result = num1 + num2;
        break;
    }

    return result.toString();
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder(num1.toString());
    output.append(" ");
    output.append(operator);
    output.append(" ");
    output.append(num2);
    return output.toString();
  }

  private static boolean validCaptcha(String input) {
    return (input.trim().equalsIgnoreCase(result));
  }
  // get first number
  // get second number
  // generate operation
  // calculate result
  // get input from user
  // compare input from user to result
  // return success: true or false
}
