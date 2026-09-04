package anby;

import java.io.InputStream;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

/**
 * A dialog box containing one chat message.
 */
public class DialogBox extends HBox {
    private static final String USER_AVATAR_PATH = "/images/user.jpg";
    private static final String ANBY_AVATAR_PATH = "/images/anby.jpg";
    private static final double AVATAR_SIZE = 88.0;

    private final Label dialog;
    private final StackPane avatar;

    /**
     * Creates a dialog box.
     *
     * @param text message text
     * @param avatarPath image path for the avatar
     * @param fallbackText text to show if the image does not exist
     */
    public DialogBox(String text, String avatarPath, String fallbackText) {
        dialog = new Label(text);
        avatar = createAvatar(avatarPath, fallbackText);

        dialog.setWrapText(true);
        dialog.setMinHeight(Region.USE_PREF_SIZE);
        avatar.setAlignment(Pos.CENTER);

        getStyleClass().add("dialog-box");
        dialog.getStyleClass().add("dialog-label");
        avatar.getStyleClass().add("avatar-label");
        getChildren().addAll(dialog, avatar);
    }

    /**
     * Returns a user dialog box.
     *
     * @param text user input
     * @return user dialog box
     */
    public static DialogBox getUserDialog(String text) {
        DialogBox dialogBox = new DialogBox(text, USER_AVATAR_PATH, "You");
        dialogBox.setAlignment(Pos.TOP_RIGHT);
        dialogBox.dialog.getStyleClass().add("user-label");
        dialogBox.avatar.getStyleClass().add("user-avatar");
        return dialogBox;
    }

    /**
     * Returns an Anby dialog box.
     *
     * @param text Anby's response
     * @return Anby dialog box
     */
    public static DialogBox getAnbyDialog(String text) {
        DialogBox dialogBox = new DialogBox(text, ANBY_AVATAR_PATH, "A");
        dialogBox.moveAvatarToLeft();
        dialogBox.dialog.getStyleClass().add("anby-label");
        dialogBox.avatar.getStyleClass().add("anby-avatar");
        return dialogBox;
    }

    private static StackPane createAvatar(String imagePath, String fallbackText) {
        StackPane avatarContainer = new StackPane();
        avatarContainer.setMinSize(AVATAR_SIZE, AVATAR_SIZE);
        avatarContainer.setPrefSize(AVATAR_SIZE, AVATAR_SIZE);
        avatarContainer.setMaxSize(AVATAR_SIZE, AVATAR_SIZE);
        avatarContainer.getStyleClass().add("avatar-label");
        avatarContainer.getChildren().add(loadAvatarContent(imagePath, fallbackText));
        return avatarContainer;
    }

    private static Node loadAvatarContent(String imagePath, String fallbackText) {
        InputStream imageStream = DialogBox.class.getResourceAsStream(imagePath);

        if (imageStream == null) {
            Label fallbackAvatar = new Label(fallbackText);
            fallbackAvatar.getStyleClass().add("fallback-avatar-text");
            return fallbackAvatar;
        }

        Image image = new Image(imageStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(AVATAR_SIZE);
        imageView.setFitWidth(AVATAR_SIZE);
        imageView.setPreserveRatio(false);
        imageView.setViewport(getCenteredSquareViewport(image));
        imageView.setClip(new Circle(AVATAR_SIZE / 2, AVATAR_SIZE / 2, AVATAR_SIZE / 2));
        imageView.setSmooth(true);
        return imageView;
    }

    private static Rectangle2D getCenteredSquareViewport(Image image) {
        double size = Math.min(image.getWidth(), image.getHeight());
        double x = (image.getWidth() - size) / 2;
        double y = (image.getHeight() - size) / 2;
        return new Rectangle2D(x, y, size, size);
    }

    private void moveAvatarToLeft() {
        getChildren().remove(avatar);
        getChildren().add(0, avatar);
        setAlignment(Pos.TOP_LEFT);
    }
}
