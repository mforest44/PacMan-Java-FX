package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.BitSet;

public class Input {

    private BitSet keyboardBitSet = new BitSet();

    private KeyCode upKey = KeyCode.UP;
    private KeyCode downKey = KeyCode.DOWN;
    private KeyCode leftKey = KeyCode.LEFT;
    private KeyCode rightKey = KeyCode.RIGHT;
    private KeyCode primaryWeaponKey = KeyCode.SPACE;
    private KeyCode secondaryWeaponKey = KeyCode.CONTROL;

    Scene scene;

    public Input( Scene scene) {
        this.scene = scene;
    }

    public void addListeners() {

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

    }

    public void removeListeners() {

       scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
       scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

    }

    private EventHandler<KeyEvent> keyPressedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            keyboardBitSet.set(event.getCode().ordinal(), true);

        }
    };

    private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            keyboardBitSet.set(event.getCode().ordinal(), false);

        }
    };

    public boolean isMoveUp() {
        return keyboardBitSet.get( upKey.ordinal()) && !keyboardBitSet.get( downKey.ordinal());
    }

    public boolean isMoveDown() {
        return keyboardBitSet.get( downKey.ordinal()) && !keyboardBitSet.get( upKey.ordinal());
    }

    public boolean isMoveLeft() {
        return keyboardBitSet.get( leftKey.ordinal()) && !keyboardBitSet.get( rightKey.ordinal());  
    }

    public boolean isMoveRight() {
        return keyboardBitSet.get( rightKey.ordinal()) && !keyboardBitSet.get( leftKey.ordinal());
    }

    public boolean isFirePrimaryWeapon() {
        return keyboardBitSet.get( primaryWeaponKey.ordinal());
    }

    public boolean isFireSecondaryWeapon() {
        return keyboardBitSet.get( secondaryWeaponKey.ordinal());
    }

}