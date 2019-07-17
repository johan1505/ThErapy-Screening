package com.example.therapyscreening;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class QuestionnarieActivity extends AppCompatActivity implements View.OnClickListener {
    String[] parentQuestions = {"Is he/she distractible or has trouble sticking to an activity?", "Does he/she fail to finish tasks once he/she start them?",
            "Can he/she follow directions or instructions correctly?", "Is he/she irritable?", "Does he/she talk back to adults?",
            "Does he/she blame others for his/her own mistakes", "Does he/she steal things?", "Does he/she destroy things that belong to others?",
            "Does he/she engage in vandalism?", "Does he/she worry about being separated from his/her loved ones?",
            "Does he/she worry about bad things that could happen to his/her loved ones?", "Is he/she scared to sleep without parents nearby?",
            "Does he/she worry about past bad behaviours?", "Does he/she worry about doing wrong things?", "Does he/she get pleasure from hurting others?"};

    String[] childQuestions = {"Do you like school?", "Can you pay attention in class?",
            "Can you always finish your homework once you start it?", "Do you understand the directions or instructions on your school books/assignments?",
            "Do you always feel angry?", "Do you always talk balk to adults?", "Do you blame others for your mistakes?", "Do you steal things from others?",
            "Do you break things that belong to others?", "Do you damage school property?", "Do you worry about something bad happening to people close to you?",
            "Do you worry about being separated from people close to you?", "Do you feel scared to go to sleep without your parents nearby?",
            "Do you feel stress at school?", "Do you worry about your past behaviours?", "Do you worry about doing bad things?", "Do you enjoy hurting others?"};

    List<String>parentAndChildAnswers = new ArrayList<>(); // List of strings to store the answers of the parent
    String nameOfParent = "", ageOfParent = "", sexOfParent = "", relationToChild = "", mainConcern = "";
    String nameOfChild = "", ageOfChild = "", sexOfChild = "";
    int questionsListIndex;  // Index used to iterate both the list of parent and child questions
    boolean parentQuestionsOver; // Checks whether or not all the questions from the list of parent questions have been asked
    boolean childQuestionsOver;  // Checks whether or not all the questions from the list of child questions have been asked
    boolean parentInfoCollected;
    String[]sexes = {"Masculine", "Feminine", "Other"};
    String[]relations = {"Father", "Mother", "Guardian", "Other"};
    Button nextButton, neverButton, sometimesButton, alwaysButton;
    TextView firstNameParentTextView, lastNameParentTextView, ageParentTextView, sexParentTextView, firstNameChildTextView, lastNameChildTextView, ageChildTextView, sexChildTextView,relationToChildTextView, questionsTextView, mainConcernQuestionTextView;
    EditText firstNameParentEditTextView, lastNameParentEditTextView, firstNameChildEditTextView, lastNameChildEditTextView,mainConcernAnswerEditTextView;
    NumberPicker ageParentNumberPicker,sexParentNumberPicker, ageChildNumberPicker,sexChildNumberPicker, relationToChildNumberPicker;
    // Multiple choice questions start at position 8 of the list of parent questions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnarie);
        questionsListIndex = -1;
        parentQuestionsOver = false;
        childQuestionsOver = false;
        parentInfoCollected = false;
        parentAndChildAnswers.clear();

        //Questions Text View
        questionsTextView = (TextView) findViewById(R.id.questions_textView);
        questionsTextView.setText("Parent/Guardian please fill out the following information");

        //First Names Text View
        firstNameParentTextView = (TextView) findViewById(R.id.firstName_parent_text_view);
        firstNameChildTextView  = (TextView) findViewById(R.id.firstName_child_text_view);

        //Last Names Text View
        lastNameParentTextView = (TextView) findViewById(R.id.lastName_parent_text_view);
        lastNameChildTextView  = (TextView) findViewById(R.id.lastName_child_text_view);

        //Ages Text View
        ageParentTextView = (TextView) findViewById(R.id.age_parent_text_view);
        ageChildTextView = (TextView) findViewById(R.id.age_child_text_view);

        //Sexs Text View
        sexParentTextView = (TextView) findViewById(R.id.sex_parent_text_view);
        sexChildTextView = (TextView) findViewById(R.id.sex_child_text_view);

        //Relation To Child Question Text View
        relationToChildTextView = (TextView) findViewById(R.id.relationToChild_text_view);

        //Main Concern Question Text View
        mainConcernQuestionTextView = (TextView) findViewById(R.id.MainConcernQuestion_text_view);

        //First Names EditText View
        firstNameParentEditTextView = (EditText) findViewById(R.id.firstName_parent_editText_view);
        firstNameChildEditTextView = (EditText) findViewById(R.id.firstName_child_editText_view);

        //Last Names Text View
        lastNameParentEditTextView = (EditText) findViewById(R.id.lastName_parent_editText_view);
        lastNameChildEditTextView = (EditText) findViewById(R.id.lastName_child_editText_view);

        // Main Concern Answer Edit Text View
        mainConcernAnswerEditTextView = (EditText) findViewById(R.id.MainConcernAnswer_editText_view);

        // Ages NumberPickers
        ageParentNumberPicker = (NumberPicker) findViewById(R.id.age_parent_numberPicker);
        ageParentNumberPicker.setMinValue(0);
        ageParentNumberPicker.setMaxValue(120);

        ageChildNumberPicker = (NumberPicker) findViewById(R.id.age_child_numberPicker);
        ageChildNumberPicker.setMinValue(0);
        ageChildNumberPicker.setMaxValue(120);

        //Sexes NumberPickers
        sexParentNumberPicker = (NumberPicker) findViewById(R.id.sex_parent_numberPicker);
        sexParentNumberPicker.setMinValue(0);
        sexParentNumberPicker.setMaxValue(sexes.length - 1);
        sexParentNumberPicker.setDisplayedValues(sexes);

        sexChildNumberPicker = (NumberPicker) findViewById(R.id.sex_child_numberPicker);
        sexChildNumberPicker.setMinValue(0);
        sexChildNumberPicker.setMaxValue(sexes.length - 1);
        sexChildNumberPicker.setDisplayedValues(sexes);

        //RelationToChild NumberPicker
        relationToChildNumberPicker = (NumberPicker) findViewById(R.id.relationToChild_numberPicker);
        relationToChildNumberPicker.setMinValue(0);
        relationToChildNumberPicker.setMaxValue(relations.length - 1);
        relationToChildNumberPicker.setDisplayedValues(relations);

        //Next Button
        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);

        //Never Button
        neverButton = (Button) findViewById(R.id.never_button);
        neverButton.setVisibility(View.INVISIBLE);
        neverButton.setOnClickListener(this);

        //Sometimes Button
        sometimesButton = (Button) findViewById(R.id.sometimes_button);
        sometimesButton.setVisibility(View.INVISIBLE);
        sometimesButton.setOnClickListener(this);

        //Always Button
        alwaysButton = (Button) findViewById(R.id.always_button);
        alwaysButton.setVisibility(View.INVISIBLE);
        alwaysButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.always_button:
                inCreaseIndexOfList();
                parentAndChildAnswers.add("Always");
                break;

            case R.id.sometimes_button:
                inCreaseIndexOfList();
                parentAndChildAnswers.add("Sometimes");
                break;

            case R.id.never_button:
                inCreaseIndexOfList();
                parentAndChildAnswers.add("Never");
                break;

            case R.id.next_button:
                if (!parentQuestionsOver && !childQuestionsOver) {
                    if (ParentInfoNotInputted()) { // If parent info is not collected then do not change the text of the Questions Text View
                        Context context = getApplicationContext();
                        CharSequence text = "PLEASE FILL OUT ALL THE BOXES";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        inCreaseIndexOfList();
                        setParentQuestionsInvisible();
                        setMultipleChoiceAnswersVisible();
                    }
                }
                else if (parentQuestionsOver && !childQuestionsOver) {
                    nextButton.setVisibility(View.GONE);
                    setMultipleChoiceAnswersVisible();
                    inCreaseIndexOfList();
                }
                else {
                    nextButton.setVisibility(View.GONE);
                    sendEmail();
                }
                break;
        }
    }

    private void saveParentInfoInputted(){
        this.nameOfParent = firstNameParentEditTextView.getText().toString() + " " + lastNameParentEditTextView.getText().toString();
        this.ageOfParent = Integer.toString(ageParentNumberPicker.getValue());
        this.sexOfParent = sexes[sexParentNumberPicker.getValue()];
        this.nameOfChild = firstNameChildEditTextView.getText().toString() + " " + lastNameChildEditTextView.getText().toString();
        this.ageOfChild = Integer.toString(ageChildNumberPicker.getValue());
        this.sexOfChild = sexes[sexChildNumberPicker.getValue()];
        this.relationToChild = relations[relationToChildNumberPicker.getValue()];
        this.mainConcern = mainConcernAnswerEditTextView.getText().toString();
    }

    private boolean ParentInfoNotInputted(){
        boolean noInfoPutted = firstNameParentEditTextView.getText().toString().matches("") || lastNameParentEditTextView.getText().toString().matches("") || firstNameChildEditTextView.getText().toString().matches("") || lastNameChildEditTextView.getText().toString().matches("") || mainConcernAnswerEditTextView.getText().toString().matches("");
        if (!noInfoPutted){
            saveParentInfoInputted();
        }
        return noInfoPutted;
    }

    private void setParentQuestionsInvisible(){
        firstNameParentTextView.setVisibility(View.GONE);
        lastNameParentTextView.setVisibility(View.GONE);
        firstNameParentEditTextView.setVisibility(View.GONE);
        lastNameParentEditTextView.setVisibility(View.GONE);
        ageParentTextView.setVisibility(View.GONE);
        ageParentNumberPicker.setVisibility(View.GONE);
        sexParentTextView.setVisibility(View.GONE);
        sexParentNumberPicker.setVisibility(View.GONE);
        firstNameChildTextView.setVisibility(View.GONE);
        lastNameChildTextView.setVisibility(View.GONE);
        firstNameChildEditTextView.setVisibility(View.GONE);
        lastNameChildEditTextView.setVisibility(View.GONE);
        ageChildTextView.setVisibility(View.GONE);
        ageChildNumberPicker.setVisibility(View.GONE);
        sexChildTextView.setVisibility(View.GONE);
        sexChildNumberPicker.setVisibility(View.GONE);
        relationToChildTextView.setVisibility(View.GONE);
        relationToChildNumberPicker.setVisibility(View.GONE);
        mainConcernQuestionTextView.setVisibility(View.GONE);
        mainConcernAnswerEditTextView.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
    }

    private void setMultipleChoiceAnswersVisible(){
        neverButton.setVisibility(View.VISIBLE);
        sometimesButton.setVisibility(View.VISIBLE);
        alwaysButton.setVisibility(View.VISIBLE);
    }

    private void setMultipleChoiceAnswersInvisible(){
        neverButton.setVisibility(View.GONE);
        sometimesButton.setVisibility(View.GONE);
        alwaysButton.setVisibility(View.GONE);
    }

    private void inCreaseIndexOfList(){
        ++questionsListIndex;
        if (!parentQuestionsOver && !childQuestionsOver && questionsListIndex < parentQuestions.length) {
            questionsTextView.setText(parentQuestions[questionsListIndex]);
        } else if (!parentQuestionsOver && !childQuestionsOver && questionsListIndex == parentQuestions.length) {
            parentQuestionsOver = true;
            questionsTextView.setText("Parent's portion is over. Please press the next button and let your child answer the following questions");
            questionsListIndex = -1; //Reset the index to -1 in order to traverse the childQuestions array
            setMultipleChoiceAnswersInvisible();
            nextButton.setVisibility(View.VISIBLE);
        } else if (parentQuestionsOver && !childQuestionsOver && questionsListIndex < childQuestions.length) {
            questionsTextView.setText(childQuestions[questionsListIndex]);
        } else if (parentQuestionsOver && !childQuestionsOver && questionsListIndex == childQuestions.length) {
            childQuestionsOver = true;
            questionsTextView.setText("Your part is over. Please hand the device back to the front desk!");
            setMultipleChoiceAnswersInvisible();
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    private void sendEmail(){
        String[] TO = {MainActivity.getGoogleAccount().getEmail()};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Report of " + nameOfChild + " (Parent: " + nameOfParent + ")");
        emailIntent.putExtra(Intent.EXTRA_TEXT, makeReport());
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(QuestionnarieActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private String makeReport(){
        int indexOfParentAndChildAnswers = 0;
        StringBuilder report = new StringBuilder("Name of Parent: " + nameOfParent + "\n");
        report.append("Age: " + ageOfParent + "\nSex: " + sexOfParent + "\nRelation to child: " + relationToChild + "\n");
        report.append("Main concern about the child: \n" + mainConcern + "\n\nParent Answers: \n\n");

        for (int i = 0 ; i < parentQuestions.length; i++){
            report.append(Integer.toString(i + 1) + ") " + parentQuestions[i] + "\n");
            report.append(parentAndChildAnswers.get(indexOfParentAndChildAnswers) + "\n\n");
            indexOfParentAndChildAnswers++;
        }
        report.append("------------------------------------------------------------------------------\n\n");
        report.append("Name of Child: " + nameOfChild + "\nAge: " + ageOfChild + "\nSex: " + sexOfChild + "\n\nChild Answers: \n\n");
        for (int i = 0; i < childQuestions.length; i++){
            report.append(Integer.toString(i + 1) + ") " + childQuestions[i] + "\n");
            report.append(parentAndChildAnswers.get(indexOfParentAndChildAnswers) + "\n\n");
            indexOfParentAndChildAnswers++;
        }
        return report.toString();
    }
}