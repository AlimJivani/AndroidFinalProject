package com.example.alimjivanifinalproject;

public class productData {

    private String name;
    private String description;
    private String price;
    private String image;

    private String id;


    public productData() {
        // Required empty constructor for Firebase deserialization
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLimitedDescription(int maxWords) {
        String[] words = description.split("\\s+");
        if (words.length > maxWords) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < maxWords; i++) {
                sb.append(words[i]);
                sb.append(" ");
            }
            sb.append("read more...");
            return sb.toString().trim();
        } else {
            return description;
        }
    }

}
