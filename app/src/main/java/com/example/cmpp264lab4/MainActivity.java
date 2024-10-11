package com.example.cmpp264lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
/**
 * Application to manage Agents in a SQLite Database
 * Author: Mackenzie Whitney
 * Date: Oct. 2024
 */
public class MainActivity extends AppCompatActivity {
    ListView lvAgents;
    Button btnAdd;

    ArrayAdapter<Agent> adapter;
    AgentDB dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvAgents = findViewById(R.id.lvAgents);
        btnAdd = findViewById(R.id.btnAdd);

        // set on click event on items in the list view to open agent details
        lvAgents.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra("agent", adapter.getItem(position));
            intent.putExtra("mode", "Edit");
            startActivity(intent);
        });

        // set on click event to add agent
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra("mode", "Add");
            startActivity(intent);
        });

        // initialize data source
        dataSource = new AgentDB(this);
        loadData();
    } // onCreate

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    // method to be called on onStart() and onResume() to set agents in the listView.
    private void loadData() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                dataSource.getAllAgents());
        lvAgents.setAdapter(adapter);
    }
}