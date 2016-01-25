package net.ddns.andremartynov.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

	private static final String EXTRA_ANSWER_IS_TRUE = "answer_is_true";
	private static final String EXTRA_ANSWER_SHOWN = "answer_shown";
	private boolean mAnswerIsTrue;
	private TextView mAnswerTextView;
	private Button mShowAnswer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

		setUpWidgets();
	}

	private void setUpWidgets() {
		mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

		mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAnswerIsTrue)
					mAnswerTextView.setText(R.string.true_button);
				else
					mAnswerTextView.setText(R.string.false_button);
				setAnswerShownResult(true);
			}
		});
	}

	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}

	public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
		Intent i = new Intent(packageContext, CheatActivity.class);
		i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
		return i;
	}

	public static boolean wasAnswerShown(Intent result) {
		return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
	}
}