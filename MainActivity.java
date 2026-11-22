package com.example.cbsestudytracker;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.content.SharedPreferences;
import java.util.*;

public class MainActivity extends Activity {
    private final String[] subjects = {"Mathematics", "Science", "Social Science", "English", "Hindi"};
    private final String[] chapters = {
        "Real Numbers", "Polynomials", "Pair of Linear Equations", "Quadratic Equations",
        "Arithmetic Progressions", "Triangles", "Coordinate Geometry", "Introduction to Trigonometry",
        "Light – Reflection and Refraction", "Human Eye and the Colourful World", "Electricity",
        "Magnetic Effects of Electric Current", "Chemical Reactions and Equations", "Acids, Bases and Salts",
        "Life Processes", "Control and Coordination", "Resources and Development", "Nationalism in India",
        "The Rise of Nationalism in Europe", "Power Sharing", "Federalism", "Development",
        "The Making of a Global World", "Print Culture and the Modern World"
    };
    private SharedPreferences prefs;
    private LinearLayout page;
    private int done = 0, total = 0;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        prefs = getSharedPreferences("tracker", MODE_PRIVATE);
        build();
    }

    private int dp(int v) { return (int)(v * getResources().getDisplayMetrics().density); }
    private TextView text(String s, int size, int color, boolean bold) {
        TextView t = new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color);
        if (bold) t.setTypeface(null, Typeface.BOLD);
        t.setPadding(dp(2), dp(7), dp(2), dp(7)); return t;
    }
    private void section(String title) { page.addView(text(title, 19, Color.WHITE, true)); }
    private void line() {
        View v = new View(this); v.setBackgroundColor(Color.rgb(54,56,72));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, dp(1));
        p.topMargin = dp(8); p.bottomMargin = dp(8); page.addView(v,p);
    }
    private void build() {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.rgb(16,17,26));
        page = new LinearLayout(this); page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(18), dp(18), dp(18), dp(28));
        scroll.addView(page); setContentView(scroll);

        page.addView(text("CBSE STUDY TRACKER", 25, Color.WHITE, true));
        page.addView(text("Class 10  •  Your progress, one day at a time", 13, Color.LTGRAY, false));
        line();

        section("Today's study plan");
        String[][] slots = {
            {"7:30–9:30 AM", "Mathematics"},
            {"9:45–11:45 AM", "Science"},
            {"2:30–3:00 PM", "Quick revision"},
            {"4:00–5:00 PM", "SST / PYQ practice"},
            {"6:30–7:00 PM", "English / Hindi"},
            {"8:30–11:00 PM", "Online classes"}
        };
        for (String[] slot : slots) page.addView(text(slot[0] + "     " + slot[1], 15, Color.rgb(220,221,235), false));
        page.addView(text("Tick each session when you finish it:", 13, Color.LTGRAY, false));
        for (String[] slot : slots) addCheck("slot_" + slot[0], slot[0] + " completed");

        line(); section("Weekly subject planner");
        String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        String[][] plan = {
            {"Maths","Science","Revision","Maths PYQs","English"},
            {"Science","SST","Revision","Science PYQs","Hindi"},
            {"Maths","Science","Revision","SST PYQs","English"},
            {"SST","Maths","Revision","Maths PYQs","Hindi"},
            {"Science","SST","Revision","Science PYQs","English"},
            {"Maths","Science","Revision","SST PYQs","Hindi"},
            {"Timed sample paper","Check mistakes","Weak topics","Rest"}
        };
        for (int i=0;i<days.length;i++) page.addView(text(days[i] + ":  " + String.join(" • ", plan[i]), 14, Color.rgb(220,221,235), false));

        line(); section("NCERT chapter checklist");
        page.addView(text("Starter checklist — add or adapt chapters to your current CBSE syllabus.", 12, Color.LTGRAY, false));
        for (String c : chapters) addCheck("chapter_" + c, c);

        line(); section("PYQ practice tracker");
        for (String s : new String[]{"Maths","Science","Social Science"}) {
            EditText e = numberField(s + " PYQs solved");
            page.addView(e);
        }

        line(); section("Sample paper record");
        EditText paper = new EditText(this); paper.setSingleLine(true);
        paper.setHint("Subject and paper number (e.g. Maths – Paper 1)");
        styleEdit(paper); page.addView(paper); loadText(paper,"paper_name");
        EditText marks = new EditText(this); marks.setSingleLine(true); marks.setHint("Marks obtained (e.g. 72/80)");
        styleEdit(marks); page.addView(marks); loadText(marks,"paper_marks");
        Button save = new Button(this); save.setText("Save sample paper result");
        save.setOnClickListener(v -> {
            prefs.edit().putString("paper_name",paper.getText().toString())
                .putString("paper_marks",marks.getText().toString()).apply();
            Toast.makeText(this,"Sample paper record saved",Toast.LENGTH_SHORT).show();
        });
        page.addView(save);

        line(); section("Your progress");
        TextView progress = text("",16,Color.rgb(170,164,255),true); page.addView(progress);
        Button refresh = new Button(this); refresh.setText("Refresh progress");
        refresh.setOnClickListener(v -> updateProgress(progress)); page.addView(refresh);
        updateProgress(progress);
        page.addView(text("Your checkmarks and entries are saved on this phone.",12,Color.LTGRAY,false));
    }

    private void styleEdit(EditText e) {
        e.setTextColor(Color.WHITE); e.setHintTextColor(Color.GRAY);
        e.setPadding(dp(10),dp(8),dp(10),dp(8));
        e.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(139,128,249)));
    }
    private void loadText(EditText e,String key) {
        e.setText(prefs.getString(key,""));
        e.addTextChangedListener(new android.text.TextWatcher() {
            public void beforeTextChanged(CharSequence s,int st,int c,int a){}
            public void onTextChanged(CharSequence s,int st,int before,int count){prefs.edit().putString(key,s.toString()).apply();}
            public void afterTextChanged(android.text.Editable x){}
        });
    }
    private EditText numberField(String hint) {
        EditText e = new EditText(this); e.setSingleLine(true); e.setInputType(2); e.setHint(hint); styleEdit(e);
        String key = "count_" + hint; e.setText(prefs.getString(key,"0"));
        e.addTextChangedListener(new android.text.TextWatcher() {
            public void beforeTextChanged(CharSequence s,int st,int c,int a){}
            public void onTextChanged(CharSequence s,int st,int before,int count){prefs.edit().putString(key,s.toString()).apply();}
            public void afterTextChanged(android.text.Editable x){}
        });
        return e;
    }
    private void addCheck(String key,String label) {
        CheckBox cb = new CheckBox(this); cb.setText(label); cb.setTextColor(Color.rgb(225,225,238));
        cb.setButtonTintList(android.content.res.ColorStateList.valueOf(Color.rgb(139,128,249)));
        cb.setChecked(prefs.getBoolean(key,false));
        cb.setOnCheckedChangeListener((b,checked)->prefs.edit().putBoolean(key,checked).apply());
        page.addView(cb);
    }
    private void updateProgress(TextView view) {
        int d=0,t=0;
        Map<String,?> all=prefs.getAll();
        for (Map.Entry<String,?> e:all.entrySet()) {
            if (e.getKey().startsWith("chapter_") || e.getKey().startsWith("slot_")) {
                t++; if (Boolean.TRUE.equals(e.getValue())) d++;
            }
        }
        view.setText("Completed: " + d + " of " + t + " tracked tasks");
    }
}