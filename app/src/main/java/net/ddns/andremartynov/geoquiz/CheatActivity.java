package net.ddns.andremartynov.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

	private static final String TAG = "CheatActivity";
	private static final String EXTRA_ANSWER_IS_TRUE = "answer_is_true";
	private static final String EXTRA_ANSWER_SHOWN = "answer_shown";
	private static final String KEY_ANSWER_SHOWN = "user_cheated";
	private boolean mAnswerIsTrue;
	private TextView mAnswerTextView;
	private Button mShowAnswer;
	private boolean mUserCheated;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		Log.d(TAG, "onCreate() called");


		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		mUserCheated = getIntent().getBooleanExtra(EXTRA_ANSWER_SHOWN, false);

		if (savedInstanceState != null) {
			Log.d(TAG, "State restored");
			mUserCheated = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN, false);
		}

		setUpWidgets();

		if (mUserCheated) {
			updateAnswer();
			setAnswerShownResult(true);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.d(TAG, "onSaveInstanceState() called");
		savedInstanceState.putBoolean(KEY_ANSWER_SHOWN, mUserCheated);
	}

	private void setUpWidgets() {
		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
		mShowAnswer = (Button) findViewById(R.id.showAnswerButton);

		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mUserCheated = true;
				updateAnswer();
				setAnswerShownResult(true);
				addAnimation();
			}
		});

		if (mUserCheated)
			mShowAnswer.setVisibility(View.INVISIBLE);
	}

	private void addAnimation() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			int cx = mShowAnswer.getWidth() / 2;
			int cy = mShowAnswer.getHeight() / 2;
			float radius = mShowAnswer.getWidth();
			Animator anim = ViewAnimationUtils
					.createCircularReveal(mShowAnswer, cx, cy, radius, 0);
			anim.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					mAnswerTextView.setVisibility(View.VISIBLE);
					mShowAnswer.setVisibility(View.INVISIBLE);
				}
			});
			anim.start();
		} else {
			mAnswerTextView.setVisibility(View.VISIBLE);
			mShowAnswer.setVisibility(View.INVISIBLE);
		}
	}

	private void updateAnswer() {
		if (mAnswerIsTrue) {
			mAnswerTextView.setText(R.string.true_button);
		} else {
			mAnswerTextView.setText(R.string.false_button);
		}
	}

	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}

	public static Intent newIntent(Context packageContext, boolean answerIsTrue,
	                               boolean userCheated) {
		Intent i = new Intent(packageContext, CheatActivity.class);
		i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
		i.putExtra(EXTRA_ANSWER_SHOWN, userCheated);
		return i;
	}

	public static boolean wasAnswerShown(Intent result) {
		return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
	}
}
