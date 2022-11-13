package ecell.app.ecellteam;

import com.google.firebase.database.PropertyName;
import java.io.Serializable;

public class Poster implements Serializable {
    public String imageUrl;
    public String message;
    public String title;

    public Poster() {
    }

    public Poster(String imageUrl2, String message2, String title2) {
        this.imageUrl = imageUrl2;
        this.message = message2;
        this.title = title2;
    }

    @PropertyName("imageUrl")
    public String getImageUrl() {
        return this.imageUrl;
    }

    @PropertyName("title")
    public String getTitle() {
        return this.title;
    }

    @PropertyName("message")
    public String getMessage() {
        return this.message;
    }

    @PropertyName("imageUrl")
    public void setImageUrl(String imageUrl2) {
        this.imageUrl = imageUrl2;
    }

    @PropertyName("title")
    public void setTitle(String title2) {
        this.title = title2;
    }

    @PropertyName("message")
    public void setMessage(String message2) {
        this.message = message2;
    }
}
