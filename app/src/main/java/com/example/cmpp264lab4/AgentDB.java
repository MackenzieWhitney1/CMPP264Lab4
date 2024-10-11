package com.example.cmpp264lab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

// Database class for crud operations for Agents
public class AgentDB {
    private Context context;
    private SQLiteDatabase db;
    private DBHelper helper;

    public AgentDB(Context context) {
        this.context = context;
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public ArrayList<Agent> getAllAgents(){
        ArrayList<Agent> list = new ArrayList<>();
        String [] columns = {
                "AgentId",
                "AgtFirstName",
                "AgtMiddleInitial",
                "AgtLastName",
                "AgtBusPhone",
                "AgtEmail",
                "AgtPosition",
                "AgencyId"
        };
        Cursor cursor = db.query("Agents", columns, null, null,
                null, null, null);
        while(cursor.moveToNext()){
            list.add(new Agent(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7)));
        }
        return list;
    }

    public void insertAgent(Agent agent){
        ContentValues cv = new ContentValues();
        cv.put("AgtFirstName", agent.getAgtFirstName());
        cv.put("AgtMiddleInitial", agent.getAgtMiddleInitial());
        cv.put("AgtLastName", agent.getAgtLastName());
        cv.put("AgtBusPhone", agent.getAgtBusPhone());
        cv.put("AgtEmail", agent.getAgtEmail());
        cv.put("AgtPosition", agent.getAgtPosition());
        cv.put("AgencyId", agent.getAgencyId());
        long numRows = db.insert("Agents", null, cv);
        if(numRows == -1){ // no rows inserted
            Toast.makeText(context, "Insert failed", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "Insert successful", Toast.LENGTH_LONG).show();
        }
    }

    public void updateAgent(Agent agent){
        ContentValues cv = new ContentValues();
        cv.put("AgtFirstName", agent.getAgtFirstName());
        cv.put("AgtMiddleInitial", agent.getAgtMiddleInitial());
        cv.put("AgtLastName", agent.getAgtLastName());
        cv.put("AgtBusPhone", agent.getAgtBusPhone());
        cv.put("AgtEmail", agent.getAgtEmail());
        cv.put("AgtPosition", agent.getAgtPosition());
        cv.put("AgencyId", agent.getAgentId());
        String where = "AgentId = ?";
        String[] args = {agent.getAgentId() +""};

        long numRows = db.update("Agents", cv, where, args);
        if(numRows == -1){ // no rows updated
            Toast.makeText(context, "Update failed", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "Update successful", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteAgent(int agentId){
        String where = "AgentId = ?";
        String[] args = {agentId + ""};
        long numRows = db.delete("Agents", where, args);
        if(numRows == -1){ // no rows deleted
            Toast.makeText(context, "Delete failed", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "Delete successful", Toast.LENGTH_LONG).show();
        }
    }
}
