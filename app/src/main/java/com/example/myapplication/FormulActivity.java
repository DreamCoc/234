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

import java.util.ArrayDeque;
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

        dbHelper.insertData("Раздел физики, в котором изучают закономерности механического движения тел и причины, изменяющие это движение", new String[]{"физики", "движения", "причины"},"Механика");
        dbHelper.insertData("Раздел механики, в котором движение тел рассматривается без выяснения причин, этого движения", new String[]{"механики", "движение", "без","причин"},"Кинематика");
        dbHelper.insertData("Изменение положения тела в пространстве, относительно других тел с течением времени", new String[]{"Изменение", "пространстве", "времени",},"Механическое движение");
        dbHelper.insertData("Тело, относительно которого рассматривается движение других тел", new String[]{"относительно которого", "движение"},"Тело отсчета");
        dbHelper.insertData("Совокупность тела отсчета, связанной с ним системы координат, приборов для измерения расстояния и времени", new String[]{"Совокупность", "системы координат", "измерения",},"Система отсчета");
        dbHelper.insertData("Вектор, проведенный из начала системы координат к рассматриваемой материальной точке", new String[]{"начала", "рассматриваемой материальной точке"},"Радиус-вектор");
        dbHelper.insertData("Движение, при котором прямая, соединяющая две любые точки тела, движется параллельно самой себе", new String[]{"прямая", "любые", "параллельно",},"Поступательное движение");
        dbHelper.insertData("Движение твердого тела, при котором все его точки описывают окружности, центры которых лежат на одной прямой, называемой осью вращения", new String[]{"все", "окружности", "прямой",},"Вращательное движение");
        dbHelper.insertData("Модель тела, размерами, которого можно пренебречь в условиях данной задачи", new String[]{"размерами", "можно"},"Материальная точка");
        dbHelper.insertData("Линия, вдоль которой движется тело", new String[]{"Линия", "вдоль"},"Траектория");
        dbHelper.insertData("Вектор, проведенный из начальной в конечную точку траектории", new String[]{"Вектор", "начальной", "конечную",},"Перемещение");
        dbHelper.insertData("Длина траектории", new String[]{"Длина", "траектории"},"Путь");
        dbHelper.insertData("Движение, при котором точка за любые равные промежутки времени совершает одинаковые перемещения", new String[]{"равные", "одинаковые", "перемещения",},"Равномерное движение");
        dbHelper.insertData("Движение, при котором точка за любые равные промежутки времени совершает различные перемещения", new String[]{"любые", "равные", "различные",},"Неравномерное движение");
        dbHelper.insertData("Векторная физическая величина, равная отношению вектора перемещения тела ко времени, за которое оно совершено", new String[]{"Векторная", "отношению"},"Скорость равномерного прямолинейного движения");
        dbHelper.insertData("Векторная физическая величина, равная отношению вектора перемещения тела ко времени, за которое оно совершено", new String[]{"Векторная", "отношению", "времени",},"Средняя скорость");
        dbHelper.insertData("Скалярная физическая величина, равная отношению всего пройденного пути ко времени", new String[]{"Скалярная", "отношению", "пути",},"Средняя путевая скорость");
        dbHelper.insertData("Движение, при котором мгновенная скорость неравномерного движения изменяется одинаково за любые одинаковые промежутки времени", new String[]{"мгновенная", "неравномерного", "одинаково","промежутки"},"Равнопеременное движение");
        dbHelper.insertData("Векторная физическая величина; предел, к которому стремится значение средней скорости тела при уменьшении рассматриваемого промежутка времени до нуля", new String[]{"Векторная", "уменьшении", "нуля",},"Мгновенная скорость");
        dbHelper.insertData("Векторная физическая величина, равная отношению изменения скорости тела ко времени, за которое оно произошло", new String[]{"Векторная", "отношению", "скорости",},"Ускорение");
        dbHelper.insertData("Составляющая полного вектора ускорения, направленная по касательной к траектории и показывающая, насколько быстро меняется скорость точки по модулю", new String[]{"ускорения", "по касательной", "модулю",},"Тангенциальное (касательное) ускорение");
        dbHelper.insertData("Составляющая полного вектора ускорения, направленная к центру окружности и показывающая, насколько быстро меняется скорость точки по направлению", new String[]{"ускорения", "центру", "направлению",},"Центростремительное (нормальное) ускорение");
        dbHelper.insertData("Движение тела под действием только силы тяжести", new String[]{"только", "силы тяжести"},"Свободное падение");
        dbHelper.insertData("Тело, расстояние между любыми двумя точками которого сохраняется с течением времени", new String[]{"любыми", "сохраняется", "времени",},"Абсолютно твердое тело");
        dbHelper.insertData("Отношение угла поворота радиус-вектора  вращающегося тела ко времени этого поворота", new String[]{"угла", "радиус-вектора любой точки", "времени",},"Угловая скорость");
        dbHelper.insertData("Мгновенная скорость любой точки вращающегося тела", new String[]{"Мгновенная", "точки", "вращающегося",},"Линейная скорость");
        dbHelper.insertData("Физическая величина, равная времени, в течении которого точка, двигаясь по окружности, совершает один оборот", new String[]{"окружности", "один оборот"},"Период обращения");
        dbHelper.insertData("Физическая величина, равная числу оборотов, совершаемых точкой при равномерном движении по окружности за одну секунду", new String[]{"Физическая", "точкой", "равномерном","одну секунду"},"Частота вращения");
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
                String[] arrayValues = column2Value.substring(1, column2Value.length() - 1).split(", ");
                sent.add(column1Value);
                word.add(arrayValues);
                Nsent.add(column3Value);
                if(i==28) break;
            } while (data.moveToNext());
        }
        data.close();
        generateSentence(sent,word,Nsent);



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
                generateSentence(sent, word, Nsent);
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
                }
              //  button6.setVisibility(View.GONE);
            }
        });
    }
    private void generateSentence(ArrayDeque<String> sent1, ArrayDeque<String[]> word1, ArrayDeque<String> nsent) {
        if(sent1.isEmpty()){
            sentenceTextView.setText("Вы прошли все определения, так держать, нажмите на кнопку заново если хотите начать сначала");
        }
        else{
        Random random = new Random();
        sentence = sent1.poll();
        String[] words = word1.poll();
        nameSentenceTextView.setText(nsent.poll());
        missingWord = words[random.nextInt(words.length)];
        sentenceTextView.setText(sentence.replace(missingWord, "____"));}
    }
}
