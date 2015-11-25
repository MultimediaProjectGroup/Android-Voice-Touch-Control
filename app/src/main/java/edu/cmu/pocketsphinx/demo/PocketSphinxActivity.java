

package edu.cmu.pocketsphinx.demo;

import static android.widget.Toast.makeText;
import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;

public class PocketSphinxActivity extends Activity implements
        RecognitionListener, View.OnClickListener {

    private static final String CMD_SEARCH = "cmd";
    private static final String DIGITS_SEARCH = "digits";
    //private static final String PHONE_SEARCH = "phone";
    private SpeechRecognizer recognizer;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        setContentView(R.layout.main);
//        ((TextView) findViewById(R.id.caption_text))
//                .setText("Preparing the recognizer");

        Intent intent = new Intent(getApplicationContext(), TouchController.class);
        startService(intent);
        Button stopSer = (Button) findViewById(R.id.stopVoiceBtn);
        stopSer.setOnClickListener(this);
        try {
            Assets assets = new Assets(PocketSphinxActivity.this);
            File assetDir = assets.syncAssets();
            setupRecognizer(assetDir);
        } catch (IOException e) {
            // oops
        }

        //((TextView) findViewById(R.id.caption_text)).setText("Android Voice Control");

        reset();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recognizer.cancel();
        recognizer.shutdown();
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
    }

    /**
     * This callback is called when we stop the recognizer.
     */
    @Override
    public void onResult(Hypothesis hypothesis) {
        //((TextView) findViewById(R.id.result_text)).setText("");
        Intent intent = new Intent(getApplicationContext(), TouchController.class);

        if (recognizer.getSearchName().equalsIgnoreCase(DIGITS_SEARCH)) {
            if (hypothesis != null) {
                String text = hypothesis.getHypstr();
                String[] temp = text.split(" ");
                ArrayList<String> arrayList = new ArrayList<>();
                for (String aTemp : temp) {
                    switch (aTemp) {
                        case "zero":
                            arrayList.add("input tap 350 1600");
                            break;
                        case "one":
                            arrayList.add("input tap 160 1400");
                            break;
                        case "two":
                            arrayList.add("input tap 350 1400");
                            break;
                        case "three":
                            arrayList.add("input tap 550 1400");
                            break;
                        case "four":
                            arrayList.add("input tap 160 1200");
                            break;
                        case "five":
                            arrayList.add("input tap 350 1200");
                            break;
                        case "six":
                            arrayList.add("input tap 550 1200");
                            break;
                        case "seven":
                            arrayList.add("input tap 160 900");
                            break;
                        case "eight":
                            arrayList.add("input tap 350 900");
                            break;
                        case "nine":
                            arrayList.add("input tap 550 900");
                            break;
                        case "plus":
                            arrayList.add("input tap 900 1600");
                            break;
                        case "minus":
                            arrayList.add("input tap 900 1400");
                            break;
                        case "divide":
                            arrayList.add("input tap 900 1000");
                            break;
                        case "multiple":
                            arrayList.add("input tap 900 1200");
                            break;
                        case "reset":
                            arrayList.add("input tap 900 800");
                            break;
                        case "equal":
                            arrayList.add("input tap 550 1600");
                            break;
                    }
                }
                intent.setAction(Constant.ACTION_CALCULATION);
                intent.putStringArrayListExtra("calArray", arrayList);
                startService(intent);
            }
        }

        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            if (text.equalsIgnoreCase("open browser")) {
                intent.setAction(Constant.ACTION_OPEN_BROWSER);
                startService(intent);
            } else if (text.equalsIgnoreCase("close browser")) {
                intent.setAction(Constant.ACTION_CLOSE_BROWSER);
                startService(intent);
            } else if (text.equalsIgnoreCase("open calculator")) {
                intent.setAction(Constant.ACTION_OPEN_CALCULATOR);
                startService(intent);
                switchSearch(DIGITS_SEARCH);
            } else if (text.equalsIgnoreCase("close calculator")) {
                intent.setAction(Constant.ACTION_CLOSE_CALCULATOR);
                startService(intent);
                reset();
            } else if (text.equalsIgnoreCase("notification")) {
                intent.setAction(Constant.ACTION_NOTIFICATION);
                startService(intent);
            } else if (text.equalsIgnoreCase("go home")) {
                intent.setAction(Constant.ACTION_GO_Home);
                startService(intent);
            } else if (text.equalsIgnoreCase("go back")) {
                intent.setAction(Constant.ACTION_GO_BACK);
                startService(intent);
            } else if (text.equalsIgnoreCase("open camera")) {
                intent.setAction(Constant.ACTION_OPEN_CAMERA);
                startService(intent);
            } else if (text.equalsIgnoreCase("take a picture")) {
                intent.setAction(Constant.ACTION_TAKE_A_PICTURE);
                startService(intent);
            } else if (text.equalsIgnoreCase("open game")) {
                intent.setAction(Constant.ACTION_OPEN_GAME);
                startService(intent);
            } else if (text.equalsIgnoreCase("swipe down")) {
                intent.setAction(Constant.ACTION_SWIPE_DOWN);
                startService(intent);
            } else if (text.equalsIgnoreCase("swipe up")) {
                intent.setAction(Constant.ACTION_SWIPE_UP);
                startService(intent);
            } else if (text.equalsIgnoreCase("swipe left")) {
                intent.setAction(Constant.ACTION_SWIPE_LEFT);
                startService(intent);
            } else if (text.equalsIgnoreCase("swipe right")) {
                intent.setAction(Constant.ACTION_SWIPE_RIGHT);
                startService(intent);
            } else if (text.equalsIgnoreCase("click")) {
                intent.setAction(Constant.ACTION_TAP);
                startService(intent);
            } else if (text.equalsIgnoreCase("open facebook")) {
                intent.setAction(Constant.ACTION_OPEN_FACEBOOK);
                startService(intent);
            } else if (text.equalsIgnoreCase("close facebook")) {
                intent.setAction(Constant.ACTION_CLOSE_FACEBOOK);
                startService(intent);
            }

            makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech() {
        switchSearch(recognizer.getSearchName());
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                        // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                .setRawLogDir(assetsDir)

                        // Threshold to tune for keyphrase to balance between false alarms and misses
                .setKeywordThreshold(1e-1f)

                        // Use context-independent phonetic search, context-dependent is too slow for mobile
                //.setBoolean("-allphone_ci", true)

                        //.setInteger("-pl_window",10)


                .getRecognizer();

        recognizer.addListener(this);

        /** In your application you might not need to add all those searches.
         * They are added here for demonstration. You can leave just one.
         */

        // Create keyword-activation search.
        // recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);

        // Create grammar-based search for selection between demos
        // File cmdGrammar = new File(assetsDir, "cmds.gram");
        // recognizer.addGrammarSearch(CMD_SEARCH, cmdGrammar);


        File keywords = new File(assetsDir, "keywords.gram");
        recognizer.addGrammarSearch(CMD_SEARCH, keywords);

        // Create grammar-based search for digit recognition
        File digitsGrammar = new File(assetsDir, "digits.gram");
        recognizer.addGrammarSearch(DIGITS_SEARCH, digitsGrammar);

        // Create language model search
        /*File languageModel = new File(assetsDir, "en-phone.dmp");
        recognizer.addAllphoneSearch(PHONE_SEARCH, languageModel);*/
        
        // Phonetic search
       /* File languageModel = new File(assetsDir, "en-us.lm.bin");
        recognizer.addNgramSearch(PHONE_SEARCH, languageModel);*/
    }

    @Override
    public void onError(Exception error) {
//        ((TextView) findViewById(R.id.caption_text)).setText(error.getMessage());
    }

    @Override
    public void onTimeout() {
        reset();
    }

    private void reset() {
        recognizer.stop();
        recognizer.startListening(CMD_SEARCH);
    }

    private void switchSearch(String searchName) {
        recognizer.stop();
        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
        recognizer.startListening(searchName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stopVoiceBtn:
                Intent intent = new Intent(getApplicationContext(), TouchController.class);
                stopService(intent);
                break;
            default:
        }
    }
}
