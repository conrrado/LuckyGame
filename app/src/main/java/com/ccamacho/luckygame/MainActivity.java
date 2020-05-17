package com.ccamacho.luckygame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int BUTTON_1 = 1;
    private static final int BUTTON_2 = 2;
    private static final int BUTTON_3 = 3;
    private static final int BUTTON_4 = 4;
    private static final int BUTTON_5 = 5;

    TextView textViewPlayerCount;
    TextView textViewMachineCount;
    TextView textViewNumberChoice;
    TextView textViewResult;
    Button buttonNumber1;
    Button buttonNumber2;
    Button buttonNumber3;
    Button buttonNumber4;
    Button buttonNumber5;
    Button buttonEven;
    Button buttonOdd;

    private int choiceUser;
    private boolean isEvenUser;

    private int playerCount;
    private int machineCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerCount = findViewById(R.id.textView_player_count);
        textViewMachineCount = findViewById(R.id.textView_machine_count);
        textViewNumberChoice = findViewById(R.id.textView_number_choice);
        textViewResult = findViewById(R.id.textView_result);
        buttonNumber1 = findViewById(R.id.button_number1);
        buttonNumber2 = findViewById(R.id.button_number2);
        buttonNumber3 = findViewById(R.id.button_number3);
        buttonNumber4 = findViewById(R.id.button_number4);
        buttonNumber5 = findViewById(R.id.button_number5);
        buttonEven = findViewById(R.id.button_even);
        buttonOdd = findViewById(R.id.button_odd);

        buttonNumber1.setOnClickListener(buttonClickWithNumber(BUTTON_1));
        buttonNumber2.setOnClickListener(buttonClickWithNumber(BUTTON_2));
        buttonNumber3.setOnClickListener(buttonClickWithNumber(BUTTON_3));
        buttonNumber4.setOnClickListener(buttonClickWithNumber(BUTTON_4));
        buttonNumber5.setOnClickListener(buttonClickWithNumber(BUTTON_5));
        buttonEven.setOnClickListener(buttonClickWithBoolean(true));
        buttonOdd.setOnClickListener(buttonClickWithBoolean(false));

        startGame();
    }

    private View.OnClickListener buttonClickWithNumber(final int number) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetColorButtons();
                v.setBackgroundColor(Color.GREEN);
                choiceUser = number;
            }
        };
    }

    private View.OnClickListener buttonClickWithBoolean(final boolean isEven) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFieldValid()) {
                    isEvenUser = isEven;
                    runGame();
                }
            }
        };
    }

    private boolean isFieldValid() {
        if (choiceUser == 0) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.error_field_valid), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void runGame() {
        int numberChoiceMachine = ThreadLocalRandom.current().nextInt(1, 5);
        int sum = numberChoiceMachine + choiceUser;

        Log.d(TAG, "numberChoiceMachine: " + numberChoiceMachine);

        textViewNumberChoice.setText(String.valueOf(numberChoiceMachine));

        if (isEvenNumber(sum) == isEvenUser) {
            // usuario venceu
            showResultWithMessageRes(R.string.message_success_user);
            playerCount++;
            showPlayerCount();
        } else {
            // maquina venceu
            showResultWithMessageRes(R.string.message_success_machine);
            machineCount++;
            showMachineCount();
        }

    }

    private boolean isEvenNumber(int number) {
        return number % 2 == 0;
    }

    private void showResultWithMessageRes(int messageRes) {
        textViewResult.setText(getResources().getString(messageRes));
    }

    private void showPlayerCount() {
        textViewPlayerCount.setText(String.valueOf(playerCount));
    }

    private void showMachineCount() {
        textViewMachineCount.setText(String.valueOf(machineCount));
    }

    private void startGame() {
        choiceUser = 0;
        playerCount = 0;
        machineCount = 0;
        showPlayerCount();
        showMachineCount();
        resetColorButtons();

        textViewNumberChoice.setText("");
        showResultWithMessageRes(R.string.message_start_game);
    }

    private void resetColorButtons() {
        buttonNumber1.setBackgroundColor(Color.LTGRAY);
        buttonNumber2.setBackgroundColor(Color.LTGRAY);
        buttonNumber3.setBackgroundColor(Color.LTGRAY);
        buttonNumber4.setBackgroundColor(Color.LTGRAY);
        buttonNumber5.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_restart) {
            startGame();
        }
        return super.onOptionsItemSelected(item);
    }
}
