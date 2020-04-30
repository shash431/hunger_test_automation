package automation.restassured.domain.pets;

import java.util.Objects;

public class PetNotFound {
  private int code;
  private String type;
  private String message;

  @Override
  public String toString() {
    return "PetNotFound{" +
        "code=" + code +
        ", type='" + type + '\'' +
        ", message='" + message + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PetNotFound)) return false;
    PetNotFound that = (PetNotFound) o;
    return getCode() == that.getCode() &&
        Objects.equals(getType(), that.getType()) &&
        Objects.equals(getMessage(), that.getMessage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCode(), getType(), getMessage());
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
