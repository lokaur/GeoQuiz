package net.ddns.andremartynov.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class QuizActivity extends AppCompatActivity {

	public static final String TAG = "QuizActivity";

	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private TextView mQuestionTextView;
	private int mCurrentIndex = 0;

	private Question[] mQuestionBank = new Question[] {
		new Question(R.string.question_oceans, true),
		new Question(R.string.question_mideast, false),
		new Question(R.string.question_africa, false),
		new Question(R.string.question_americas, true),
		new Question(R.string.question_asia, true)
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		setUpButtons();

		mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
		mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "TextView clicked");
				goToNextQuestion();
			}
		});
		updateQuestion();
	}

	private void setUpButtons() {
		mTrueButton = (Button)findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "True button clicked");
				checkAnswer(true);
			}
		});

		mFalseButton = (Button)findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "False button clicked");
				checkAnswer(false);
			}
		});

		mNextButton = (Button)findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Next button clicked");
				goToNextQuestion();
			}
		});
	}

	private void checkAnswer(boolean trueButtonPressed) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

		int messageResId = 0;

		if (trueButtonPressed == answerIsTrue)
			messageResId = R.string.correct_toast;
		else
			messageResId = R.string.incorrect_toast;

		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}

	private void goToNextQuestion() {
		mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
		updateQuestion();
	}

	private void updateQuestion() {
		mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_quiz, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
