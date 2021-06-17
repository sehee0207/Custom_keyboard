package com.example.customkeyboard;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodSubtype;

import java.security.Key;

@SuppressWarnings("EqualsBetweenInconvertibleTypes")
public class EDMTKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private KeyboardView kv;
    private Keyboard keyboard;

    private boolean isCaps = false;

//    public void onCurrentInputMethodSubtypeChanged(InputMethodSubtype subtype){
//        if(subtype.getExtraValue().equals("Korean"))mCurKeyboard = mHangulKeyboard;
//        else mCurKeyboard = mQwertyKeyboard;
//        setLatinKeyboard(mCurKeyboard);
//        mInputView.closing();
//        mInputView.setSubtypeOnSpaceKey(subtype);
//    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateInputView() {
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this,R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {

        InputConnection ic = getCurrentInputConnection();

//        if(ic.equals(3))
//            ic.setComposingText("what!", 1);

        playClick(i);
        switch (i){
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
            break;

            case Keyboard.KEYCODE_SHIFT:
                isCaps = !isCaps;
                keyboard.setShifted(isCaps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent (KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                break;
                default:
                char code = (char)i;
                if(Character.isLetter(code) && isCaps)
                    code = Character.toUpperCase(code);
                ic.commitText(String.valueOf(code), 1);
                //if(EditorInfo.== "tlqkf")
                    //ic.commitText("땡", 1);
        }

    }



    private void playClick(int i) {
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch (i)
        {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

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