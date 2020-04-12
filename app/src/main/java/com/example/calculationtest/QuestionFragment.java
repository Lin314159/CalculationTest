package com.example.calculationtest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.calculationtest.databinding.FragmentQuestionBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final MyViewModel model = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        final FragmentQuestionBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        model.generator();
        model.getCurrentScore().setValue(0);
        binding.setData(model);
        binding.setLifecycleOwner(requireActivity());
        final StringBuilder builder = new StringBuilder();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button0:
                        builder.append(0);
                        break;
                    case R.id.button1:
                        builder.append(1);
                        break;
                    case R.id.button2:
                        builder.append(2);
                        break;
                    case R.id.button3:
                        builder.append(3);
                        break;
                    case R.id.button4:
                        builder.append(4);
                        break;
                    case R.id.button5:
                        builder.append(5);
                        break;
                    case R.id.button6:
                        builder.append(6);
                        break;
                    case R.id.button7:
                        builder.append(7);
                        break;
                    case R.id.button8:
                        builder.append(8);
                        break;
                    case R.id.button9:
                        builder.append(9);
                        break;
                    case R.id.button_clear:
                        builder.setLength(0);
                        break;
                }
                if (builder.length() == 0) {
                    binding.textView9.setText(getString(R.string.input_indicator));

                } else {
                    binding.textView9.setText(builder.toString());
                }
            }
        };
        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.buttonClear.setOnClickListener(listener);

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View v) {
                if (builder.toString().trim().isEmpty()) {
                    Toast.makeText(requireActivity(), getString(R.string.plase_putin_answer), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.valueOf(builder.toString()).intValue() == model.getAnswer().getValue()) {
                    builder.setLength(0);
                    //builder.append(getString(R.string.answer_right_message));
                    binding.textView9.setText(getString(R.string.answer_right_message));
                    model.answerRight();

                } else {
                    NavController controller = Navigation.findNavController(v);
                    if (model.win_flag) {
                        controller.navigate(R.id.action_questionFragment_to_winFragment);
                        model.win_flag = false;
                        model.save();
                    } else {
                        controller.navigate(R.id.action_questionFragment_to_loseFragment);
                    }
                }
            }
        });
        return binding.getRoot();

    }

}
