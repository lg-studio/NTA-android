package com.usinformatics.nytrip.databases.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by D1m11n on 22.06.2015.
 */
@DatabaseTable(tableName = "task")
public class TaskDbModel {

    public interface Columns{

        public String SCENE_ID="scene_id";

        public String TASK_ID="task_id";

    }

    @DatabaseField(canBeNull = false, columnName = Columns.TASK_ID, dataType = DataType.STRING, unique = true, id = true)
    public String taskId;

    @DatabaseField(canBeNull = false, columnName = Columns.SCENE_ID ,dataType = DataType.STRING  )
    public String sceneId;

    @DatabaseField(canBeNull = true, columnName = "desc" ,dataType = DataType.STRING  )
    public String desc;

    @DatabaseField(canBeNull = true, columnName = "name" ,dataType = DataType.STRING  )
    public String name;

    @DatabaseField(canBeNull = true, columnName = "imgurl" ,dataType = DataType.STRING  )
    public String imageURL;

    @DatabaseField(canBeNull = true, columnName = "goal" ,dataType = DataType.STRING  )
    public String goal;

    @DatabaseField(canBeNull = true, columnName = "status" ,dataType = DataType.STRING  )
    public String status;

    @DatabaseField(canBeNull = true, columnName = "rating" ,dataType = DataType.FLOAT )
    public float rating;

    @DatabaseField(canBeNull = true, columnName = "time_started" ,dataType = DataType.LONG  )
    public long timesStarted;

    @DatabaseField(canBeNull = true, columnName = "time_completed" ,dataType = DataType.LONG  )
    public long timesCompleted;

//    public /*int []*/ String excercisesIds;
//
//    public /*int []*/ String bestThreeFriendsScores;


    @DatabaseField(canBeNull = true, columnName = "character_obj" ,dataType = DataType.STRING  )
    public String characterObjJson;

    @DatabaseField(canBeNull = true, columnName = "location_obj" ,dataType = DataType.STRING  )
    public String locationObjJson;

    @DatabaseField(canBeNull = false, columnName = "chat_obj" ,dataType = DataType.STRING  )
    public String chatObjJson;

    @DatabaseField(canBeNull = true, columnName = "due_date" ,dataType = DataType.LONG  )
    public long dueDate;


}
