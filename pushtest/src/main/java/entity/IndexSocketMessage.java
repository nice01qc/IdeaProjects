package entity;

public class IndexSocketMessage {

    String direction;
    String text;
    String img;
    String imageState;
    String allImageState;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImageState() {
        return imageState;
    }

    public void setImageState(String imageState) {
        this.imageState = imageState;
    }

    public String getAllImageState() {
        return allImageState;
    }

    public void setAllImageState(String allImageState) {
        this.allImageState = allImageState;
    }


    @Override
    public String toString() {
        return "IndexSocketMessage{" +
                "direction='" + direction + '\'' +
                ", text='" + text + '\'' +
                ", img='" + img + '\'' +
                ", imageState='" + imageState + '\'' +
                ", allImageState='" + allImageState + '\'' +
                '}';
    }
}
