package weather.com.ztt.zttweatheralarm.weather.impl;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by vtcmer on 29/12/2017.
 */

public class ZttTextToSpeach implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {

    private Context context;

    private TextToSpeech tts;

    public ZttTextToSpeach(final Context context){
        this.context = context;

        tts = new TextToSpeech(this.context,this);
        Locale loc = new Locale("es","","");
        if (tts.isLanguageAvailable(loc) >= TextToSpeech.LANG_AVAILABLE)
            tts.setLanguage(loc);
        tts.setPitch(0.8f);
        tts.setSpeechRate(1.1f);
    }

    public void speak(final String txt){

        tts.speak(txt, TextToSpeech.QUEUE_ADD, null);

    }

    public void stop(){
        if (tts != null) {
            tts.stop();
            tts.shutdown();

        }
    }


    @Override
    public void onInit(int i) {

    }

    @Override
    public void onUtteranceCompleted(String s) {
        System.out.println();
    }
}
