package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class FormulActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private boolean skipIteration = false;

    private TextView sentenceTextView;
    private TextView count;
    private TextView nameSentenceTextView;
    private EditText userInputEditText;
    private Button checkButton;
    private Button re;
    private Button but1;
    private Button but2;
    private Button but3;
    private Button but4;
    private ArrayDeque<String> sent = new ArrayDeque<String>();
    private ArrayDeque<String[]> word  = new ArrayDeque<String[]>();
    private String sentence ;
    private String missingWord;
    private Context context=this;
    int c=1;
    DatabaseHelper dbHelper = new DatabaseHelper(context);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formul);
        Button button6 =(Button) findViewById(R.id.button6);
        nameSentenceTextView = findViewById(R.id.textView2);
        sentenceTextView = findViewById(R.id.sentenceTextView);
        userInputEditText = findViewById(R.id.userInputEditText);
        checkButton = findViewById(R.id.checkButton);
        count = findViewById(R.id.textView3);
        re = findViewById(R.id.button7);
        but1=findViewById(R.id.button8);
        but2=findViewById(R.id.button9);
        but3=findViewById(R.id.button10);
        but4=findViewById(R.id.button11);
        context = this;
        ArrayList<ArrayList<String>> getMainData = mainInfo(getApplicationContext());
        ArrayList<ArrayList<String[]>> getWordInBracket = wordInBracket(getApplicationContext());
        ArrayDeque<ArrayList<String[]>> getWordInBrackets = new ArrayDeque<>(getWordInBracket);
        dbHelper.addToDataBase(getMainData,getWordInBracket);
        getMainData = null;
        getWordInBracket =null;
        System.gc();
        Cursor data = dbHelper.getData();
        ArrayDeque<String> sent = new ArrayDeque<String>();
        ArrayDeque<String> Nsent = new ArrayDeque<String>();
        ArrayDeque<String[]> word = new ArrayDeque<String[]>();
        count.setText(c+"/28");
        int i =0;
        if (data.moveToFirst()) {
            do {
                i++;
                String column1Value = data.getString(0);
                String column2Value = data.getString(1);
                String column3Value = data.getString(2);
                String[] arrayValues = column2Value.substring(0, column2Value.length()-1).split(", ");
                sent.add(column1Value);
                word.add(arrayValues);
                Nsent.add(column3Value);
                if(i==28) break;
            } while (data.moveToNext());
        }
        data.close();
        generateSentence(sent,word,Nsent,getWordInBrackets);



        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = userInputEditText.getText().toString().toLowerCase().trim();

                if (userInput.equals(missingWord.toLowerCase())) {
                    // Выводим сообщение об успехе
                    sentenceTextView.setText("Поздравляю, вы ввели правильное пропущенное слово!");
                } else {
                    // Выводим сообщение о неудаче
                    sentenceTextView.setText("К сожалению, вы ввели неправильное пропущенное слово, правильный ответ: "+sentence);
                }
            }
        });
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(but1.getText().equals(missingWord)){
                    sentenceTextView.setText("Поздравляю, вы ввели правильное пропущенное слово!");
                }
                else {
                    sentenceTextView.setText("К сожалению, вы ввели неправильное пропущенное слово, правильный ответ: "+sentence);
                }
            }
        });
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(but2.getText().equals(missingWord)){
                    sentenceTextView.setText("Поздравляю, вы ввели правильное пропущенное слово!");
                }
                else {
                    sentenceTextView.setText("К сожалению, вы ввели неправильное пропущенное слово, правильный ответ: "+sentence);
                }
            }
        });
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(but3.getText().equals(missingWord)){
                    sentenceTextView.setText("Поздравляю, вы ввели правильное пропущенное слово!");
                }
                else {
                    sentenceTextView.setText("К сожалению, вы ввели неправильное пропущенное слово, правильный ответ: "+sentence);
                }
            }
        });
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(but4.getText().equals(missingWord)){
                    sentenceTextView.setText("Поздравляю, вы ввели правильное пропущенное слово!");
                }
                else {
                    sentenceTextView.setText("К сожалению, вы ввели неправильное пропущенное слово, правильный ответ: "+sentence);
                }
            }
        });
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создайте Intent для открытия новой активности
                finish();
                startActivity(getIntent());
            }
        });
        Button button5 =(Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создайте Intent для открытия новой активности
                Intent intent = new Intent(FormulActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создайте Intent для открытия новой активности

                if(c!=28){
                c++;
                count.setText(c+"/28");}
                userInputEditText.setText("");
                skipIteration = true;
                Random rand = new Random();
                int randomNumber = rand.nextInt(2);
                if(randomNumber==0){
                    checkButton.setVisibility(View.VISIBLE);
                    userInputEditText.setVisibility(View.VISIBLE);
                    but1.setVisibility(View.GONE);
                    but2.setVisibility(View.GONE);
                    but3.setVisibility(View.GONE);
                    but4.setVisibility(View.GONE);
                }
                else{
                    checkButton.setVisibility(View.GONE);
                    userInputEditText.setVisibility(View.GONE);
                    but1.setVisibility(View.VISIBLE);
                    but2.setVisibility(View.VISIBLE);
                    but3.setVisibility(View.VISIBLE);
                    but4.setVisibility(View.VISIBLE);
                    if(but1.getText()==null){
                        but1.setVisibility(View.GONE);
                    }
                    if(but2.getText()==null){
                        but2.setVisibility(View.GONE);
                    }
                    if(but3.getText()==null){
                        but3.setVisibility(View.GONE);
                    }
                    if(but4.getText()==null){
                        but4.setVisibility(View.GONE);
                    }

                }
                generateSentence(sent, word, Nsent,getWordInBrackets);
            }
        });
    }
    private void generateSentence(ArrayDeque<String> sent1, ArrayDeque<String[]> word1, ArrayDeque<String> nsent, ArrayDeque<ArrayList<String[]>> wordInBrak) {
        if(sent1.isEmpty() || wordInBrak.isEmpty()){
            sentenceTextView.setText("Вы прошли все определения, так держать, нажмите на кнопку заново если хотите начать сначала");
            checkButton.setVisibility(View.GONE);
            userInputEditText.setVisibility(View.GONE);
            but1.setVisibility(View.GONE);
            but2.setVisibility(View.GONE);
            but3.setVisibility(View.GONE);
            but4.setVisibility(View.GONE);
        }
        else {
            Random random = new Random();
            sentence = sent1.poll();
            String[] words = word1.poll();
            nameSentenceTextView.setText(nsent.poll());
            missingWord = words[random.nextInt(words.length)];
            sentenceTextView.setText(sentence.replace(missingWord, "____"));
            ArrayList<String[]> wordsIN = new ArrayList<>(wordInBrak.poll());
            String[] res = {" ", " ", " ", " "};
            for (String[] array : wordsIN) {
                for (String str1 : array) {
                    if (str1.contains(missingWord)) {
                        int len = array.length;
                        res = Arrays.copyOf(array, 4);
                    }
                }
            }
            View v;
            but1.setText(res[0]);
            but2.setText(res[1]);
            but3.setText(res[2]);
            but4.setText(res[3]);
            if (but1.getText() == null) {
                but1.setVisibility(View.GONE);
            }
            if (res[1] == null) {
                but2.setVisibility(View.GONE);
            }
            if (res[2] == null) {
                but3.setVisibility(View.GONE);
            }
            if (res[3] == null) {
                but4.setVisibility(View.GONE);
            }
        }

    }
    private ArrayList< ArrayList<String[]>> wordInBracket(Context context){ //Возвращает массив со словами в скобках
        String filePath = "test.txt";
        ArrayList<ArrayList<String[]>> wordsInBrackets = new ArrayList<>();
        try  {
            InputStream inputStream = context.getAssets().open("test.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            ArrayList<ArrayList<String[]>> result = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] sentences = line.split("\n");

                for (String sentence : sentences) {

                    ArrayList<String[]> inerList = new ArrayList<>();
                    int startIndex = sentence.indexOf('(');
                    int i=0;
                    while (startIndex != -1) {
                        int endIndex = sentence.indexOf(')', startIndex + 1);
                        if (endIndex != -1) {
                            String wordInBrackets = sentence.substring(startIndex + 1, endIndex);
                            String[] words = wordInBrackets.split(",");
                            inerList.add(words);
                        }

                        startIndex = sentence.indexOf('(', endIndex + 1);
                    }
                    wordsInBrackets.add(inerList);
                }
            }
            for (int i = wordsInBrackets.size() - 1; i >= 0; i--) {
                ArrayList<String[]> innerList = wordsInBrackets.get(i);
                for (int j = innerList.size() - 1; j >= 0; j--) {
                    String[] array = innerList.get(j);
                    if (array.length == 0 || array.length == 1) {
                        innerList.remove(j);
                    }
                }
                if (innerList.isEmpty()) {
                    wordsInBrackets.remove(i);
                }
            }
        }
        catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return wordsInBrackets;
    }

    private ArrayList<ArrayList<String>> mainInfo (Context context) {
        String filePath = "test.txt";
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open("test.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line2;
            int k = 0;
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> form = new ArrayList<>();
            while ((line2 = reader.readLine()) != null) {
                String[] sentences = line2.split("\n");
                for (String sentence : sentences) {
                    k++;
                    if (k == 2) form.add(sentence);
                    if (k == 1) name.add(sentence);
                    if (k == 3) k = 0;
                }
            }
            res.add(name);
            res.add(form);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return res;
    }

}





