package com.grouptwo.dyoga.activities;

import static com.grouptwo.dyoga.utils.Constants.CLASS_INSTANCE;
import static com.grouptwo.dyoga.utils.Constants.COURSE;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.adapters.ClassInstanceAdapter;
import com.grouptwo.dyoga.data.entities.Course;
import com.grouptwo.dyoga.data.viewmodels.ClassInstanceViewModel;

public class ViewCourseActivity extends AppCompatActivity {

    private ImageButton addClassInstanceButton;
    private EditText classInstanceSearchEditText;
    private RecyclerView classInstanceRecyclerView;
    private View emptyClassView;

    private ClassInstanceAdapter classInstanceAdapter;
    private ClassInstanceViewModel classInstanceViewModel;

    private final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             course = (Course) bundle.getSerializable(COURSE);
        }

        emptyClassView = findViewById(R.id.emptyClassView);
        addClassInstanceButton = findViewById(R.id.addClassInstanceImageButton);
        classInstanceRecyclerView = findViewById(R.id.classInstanceRecyclerView);
        classInstanceSearchEditText = findViewById(R.id.searchClassInstanceEditText);

        classInstanceAdapter = new ClassInstanceAdapter();
        classInstanceViewModel = new ViewModelProvider(this).get(ClassInstanceViewModel.class);

        initViews();
        initViewClicks();
    }

    private void initViews() {
        classInstanceRecyclerView.setLayoutManager(layoutManager);
        classInstanceRecyclerView.setAdapter(classInstanceAdapter);

        classInstanceViewModel.queryAllByCourseId(course.getId());
        classInstanceViewModel.getClassInstances().observe(this, classInstances -> {
            if(classInstances.isEmpty()) {
                classInstanceSearchEditText.setVisibility(View.GONE);
                emptyClassView.setVisibility(View.VISIBLE);
            } else {
                classInstanceSearchEditText.setVisibility(View.VISIBLE);
                emptyClassView.setVisibility(View.GONE);
            }

            classInstanceAdapter.setClassInstances(classInstances);
        });

        classInstanceSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                classInstanceViewModel.search(s.toString());
            }
        });
    }

    private void initViewClicks() {
        addClassInstanceButton.setOnClickListener(v
                -> {

            Intent intent = new Intent(this, AddClassInstanceActivity.class);
            intent.putExtra(COURSE, course);
            startActivity(intent);

        });

        classInstanceAdapter.setItemClickListener((v, itemPosition, classInstance) -> {
            Intent intent = new Intent(this, EditClassInstanceActivity.class);
            intent.putExtra(CLASS_INSTANCE, classInstance);
            startActivity(intent);
        });
    }
}