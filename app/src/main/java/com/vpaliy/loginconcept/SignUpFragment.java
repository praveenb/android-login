package com.vpaliy.loginconcept;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import android.text.Editable;

import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import android.util.TypedValue;
import android.view.View;

import java.util.List;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;


public class SignUpFragment extends AuthFragment {

  @BindViews(value = {R.id.email_input_edit,
          R.id.password_input_edit,
          R.id.confirm_password_edit})
  List<TextInputEditText> views;


  @BindView(R.id.password_input)
  TextInputLayout inputLayout;
 @BindView(R.id.confirm_password)
  TextInputLayout confirmLayout;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
      ButterKnife.bind(this,view);
    view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_sign_up));
    caption.setText(getString(R.string.sign_up_label));
    for (TextInputEditText editText : views) {
      if (editText.getId() == R.id.password_input_edit) {
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        inputLayout.setTypeface(boldTypeface);
        confirmLayout.setTypeface(boldTypeface);
        editText.addTextChangedListener(new TextWatcherAdapter() {
          @Override
          public void afterTextChanged(Editable editable) {
            inputLayout.setPasswordVisibilityToggleEnabled(editable.length() > 0);
          }
        });
      }
      editText.setOnFocusChangeListener((temp, hasFocus) -> {
        if (!hasFocus) {
          boolean isEnabled = editText.getText().length() > 0;
          editText.setSelected(isEnabled);
        }
      });
    }
    caption.setVerticalText(true);
    foldStuff();
    caption.setTranslationX(getTextPadding());
  }

  @Override
  public int authLayout() {
    return R.layout.sign_up_fragment;
  }

  @Override
  public void clearFocus() {
    for (View view : views) view.clearFocus();
  }

  @Override
  public void fold() {
    lock = false;
    Rotate transition = new Rotate();
    transition.setEndAngle(-90f);
    transition.addTarget(caption);
    TransitionSet set = new TransitionSet();
    set.setDuration(getResources().getInteger(R.integer.duration));
    ChangeBounds changeBounds = new ChangeBounds();
    set.addTransition(changeBounds);
    set.addTransition(transition);
    TextSizeTransition sizeTransition = new TextSizeTransition();
    sizeTransition.addTarget(caption);
    set.addTransition(sizeTransition);
    set.setOrdering(TransitionSet.ORDERING_TOGETHER);
    set.addListener(new Transition.TransitionListenerAdapter() {
      @Override
      public void onTransitionEnd(Transition transition) {
        super.onTransitionEnd(transition);
        caption.setTranslationX(getTextPadding());
        caption.setRotation(0);
        caption.setVerticalText(true);
        caption.requestLayout();

      }
    });
    TransitionManager.beginDelayedTransition(parent, set);
    foldStuff();
    caption.setTranslationX(-caption.getWidth() / 8 + getTextPadding());
  }

  private void foldStuff() {
    caption.setTextSize(TypedValue.COMPLEX_UNIT_PX, caption.getTextSize() / 2f);
    caption.setTextColor(Color.WHITE);
    ConstraintLayout.LayoutParams params = getParams();
    params.rightToRight = ConstraintLayout.LayoutParams.UNSET;
    params.verticalBias = 0.5f;
    caption.setLayoutParams(params);
  }

  private float getTextPadding() {
    return getResources().getDimension(R.dimen.folded_label_padding) / 2.1f;
  }
}
