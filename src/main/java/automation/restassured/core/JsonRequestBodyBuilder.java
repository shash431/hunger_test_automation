package automation.restassured.core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonRequestBodyBuilder {

  public JSONObject postBodyAddOrUpdatePet(String id,
                               String name,
                               String status,
                               int petCategoryId,
                               String petCategoryName,
                               String photoUrlValues,
                               String tagIdValues,
                               String tagNameValues) {
    JSONObject root = new JSONObject();

    root.put("id", id);
    root.put("name", name);
    root.put("status",status);

    JSONObject petCategory = new JSONObject();
    petCategory.put("id", petCategoryId);
    petCategory.put("name", petCategoryName);

    root.put("category", petCategory);

    //photoUrls
    JSONArray photoUrls = new JSONArray();
    for (String str : photoUrlValues.split(";"))
      photoUrls.add(str);
    root.put("photoUrls", photoUrls);

    //tags
    JSONObject tag = new JSONObject();
    for (String str : tagIdValues.split(";"))
      tag.put("id", str);
    for (String str : tagNameValues.split(";"))
      tag.put("name", str);
    JSONArray tags = new JSONArray();
    tags.add(tag);

    root.put("tags", tags);
    return root;
  }
}
