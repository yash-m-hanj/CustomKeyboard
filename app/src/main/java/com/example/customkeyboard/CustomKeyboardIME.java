package com.example.customkeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

@SuppressWarnings("ALL")
public class CustomKeyboardIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView inputView;
    private Keyboard keyboard,currentKeyboard,symbolKeyboard,symbolKeyboard2;
    private boolean caps = false;

    public void setCurrentKeyboard(Keyboard currentKeyboard) {
        this.currentKeyboard = currentKeyboard;
        inputView.setKeyboard(currentKeyboard);
        inputView.setOnKeyboardActionListener(this);
    }

    @Override
    public View onCreateInputView() {
        super.onCreateInputView();

        inputView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view_layout,null);

        keyboard = new Keyboard(this,R.xml.en_qwerty);
        symbolKeyboard = new Keyboard(this,R.xml.en_symbol);
        symbolKeyboard2 = new Keyboard(this,R.xml.en_symbol_2);

        setCurrentKeyboard(keyboard);

        inputView.setOnKeyboardActionListener(this);
        inputView.setKeyboard(currentKeyboard);

        return inputView;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        InputConnection ic = getCurrentInputConnection();
        switch(primaryCode){

            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                break;

            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                currentKeyboard.setShifted(caps);
                inputView.invalidateAllKeys();

                break;

            case Keyboard.KEYCODE_DONE:

                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

                break;

            case Keyboard.KEYCODE_MODE_CHANGE:

                if(currentKeyboard == keyboard)
                {
                    setCurrentKeyboard(symbolKeyboard);
                }
                else
                {
                    setCurrentKeyboard(keyboard);
                }

                break;

            case Keyboard.KEYCODE_ALT:

                if(currentKeyboard == symbolKeyboard)
                {
                    setCurrentKeyboard(symbolKeyboard2);
                }
                else
                {
                    setCurrentKeyboard(symbolKeyboard);
                }

                break;

            default:

                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps )
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);

                break;
        }

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
