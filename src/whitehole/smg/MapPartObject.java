/*
    Copyright 2012 The Whitehole team

    This file is part of Whitehole.

    Whitehole is free software: you can redistribute it and/or modify it under
    the terms of the GNU General Public License as published by the Free
    Software Foundation, either version 3 of the License, or (at your option)
    any later version.

    Whitehole is distributed in the hope that it will be useful, but WITHOUT ANY 
    WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
    FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along 
    with Whitehole. If not, see http://www.gnu.org/licenses/.
*/

package whitehole.smg;

import whitehole.PropertyGrid;
import whitehole.vectors.Vector3;

public class MapPartObject extends LevelObject
{
    public MapPartObject(ZoneArchive zone, String filepath, Bcsv.Entry entry)
    {
        this.zone = zone;
        String[] stuff = filepath.split("/");
        directory = stuff[0];
        layer = stuff[1].toLowerCase();
        file = stuff[2];
        
        data = entry;
        
        name = (String)data.get("name");
        loadDBInfo();
        renderer = null;
        
        uniqueID = -1;
        
        position = new Vector3((float)data.get("pos_x"), (float)data.get("pos_y"), (float)data.get("pos_z"));
        rotation = new Vector3((float)data.get("dir_x"), (float)data.get("dir_y"), (float)data.get("dir_z"));
        scale = new Vector3((float)data.get("scale_x"), (float)data.get("scale_y"), (float)data.get("scale_z"));
    }
    
    public MapPartObject(ZoneArchive zone, String filepath, int game, String objname, Vector3 pos)
    {
        this.zone = zone;
        String[] stuff = filepath.split("/");
        directory = stuff[0];
        layer = stuff[1].toLowerCase();
        file = stuff[2];
        
        data = new Bcsv.Entry();
        
        name = objname;
        loadDBInfo();
        renderer = null;
        
        uniqueID = -1;
        
        position = pos;
        rotation = new Vector3(0f, 0f, 0f);
        scale = new Vector3(1f, 1f, 1f);
        
        data.put("name", name);
        data.put("pos_x", position.x); data.put("pos_y", position.y); data.put("pos_z", position.z);
        data.put("dir_x", rotation.x); data.put("dir_y", rotation.y); data.put("dir_z", rotation.z);
        data.put("scale_x", scale.x); data.put("scale_y", scale.y); data.put("scale_z", scale.z);
        
        data.put("Obj_arg0", -1);
        data.put("Obj_arg1", -1);
        data.put("Obj_arg2", -1);
        data.put("Obj_arg3", -1);
        
        data.put("l_id", 0);
        data.put("CameraSetId", -1);
        data.put("SW_APPEAR", -1);
        data.put("SW_DEAD", -1);
        data.put("SW_A",  -1);
        data.put("SW_B", -1);
        if (game == 2)
        {
            data.put("SW_AWAKE", -1);
            data.put("SW_PARAM", -1);
            data.put("ParamScale", 1f);
        }
        else
            data.put("SW_SLEEP", -1);
        
        data.put("CastId", -1);
        data.put("ViewGroupId", -1);
        data.put("ShapeModelNo", (short)-1);
        data.put("CommonPath_ID", (short)-1);
        data.put("ClippingGroupId", (short)-1);
        data.put("GroupId", (short)-1);
        data.put("DemoGroupId", (short)-1);
        if (game == 2)
        {
            data.put("MapParts_ID", (short)-1);
            data.put("Obj_ID", (short)-1);
        }

        data.put("MoveConditionType", 0);
        data.put("RotateSpeed", 0);
        data.put("RotateAngle", 0);
        data.put("RotateAxis", 0);
        data.put("RotateAccelType", 0);
        data.put("RotateStopTime", 0);
        data.put("RotateType", 0);
        data.put("ShadowType", 0);
        data.put("SignMotionType", 0);
        data.put("PressType", -1);
        data.put("FarClip", -1);
        if (game == 2)
            data.put("ParentId", (short)-1);
    }
    
    @Override
    public void save()
    {
        data.put("name", name);
        data.put("pos_x", position.x); data.put("pos_y", position.y); data.put("pos_z", position.z);
        data.put("dir_x", rotation.x); data.put("dir_y", rotation.y); data.put("dir_z", rotation.z);
        data.put("scale_x", scale.x); data.put("scale_y", scale.y); data.put("scale_z", scale.z);
    }

    
    @Override
    public void getProperties(PropertyGrid panel)
    {
        panel.addCategory("obj_position", "Position");
        panel.addField("pos_x", "X position", "float", null, position.x);
        panel.addField("pos_y", "Y position", "float", null, position.y);
        panel.addField("pos_z", "Z position", "float", null, position.z);
        panel.addField("dir_x", "X rotation", "float", null, rotation.x);
        panel.addField("dir_y", "Y rotation", "float", null, rotation.y);
        panel.addField("dir_z", "Z rotation", "float", null, rotation.z);
        panel.addField("scale_x", "X scale", "float", null, scale.x);
        panel.addField("scale_y", "Y scale", "float", null, scale.y);
        panel.addField("scale_z", "Z scale", "float", null, scale.z);
        if (zone.gameMask == 2)
            panel.addField("ParamScale", "ParamScale", "float", null, data.get("ParamScale"));

        // TODO nice object args (ObjectDB integration)

        panel.addCategory("obj_args", "Object arguments");
        panel.addField("Obj_arg0", "Obj_arg0", "int", null, data.get("Obj_arg0"));
        panel.addField("Obj_arg1", "Obj_arg1", "int", null, data.get("Obj_arg1"));
        panel.addField("Obj_arg2", "Obj_arg2", "int", null, data.get("Obj_arg2"));
        panel.addField("Obj_arg3", "Obj_arg3", "int", null, data.get("Obj_arg3"));
        
        panel.addCategory("obj_eventinfo", "Event IDs");
        panel.addField("SW_APPEAR", "SW_APPEAR", "int", null, data.get("SW_APPEAR"));
        panel.addField("SW_DEAD", "SW_DEAD", "int", null, data.get("SW_DEAD"));
        panel.addField("SW_A", "SW_A", "int", null, data.get("SW_A"));
        panel.addField("SW_B", "SW_B", "int", null, data.get("SW_B"));
        if (zone.gameMask == 2)
        {
            panel.addField("SW_AWAKE", "SW_AWAKE", "int", null, data.get("SW_AWAKE"));
            panel.addField("SW_PARAM", "SW_PARAM", "int", null, data.get("SW_PARAM"));
        }
        else
            panel.addField("SW_SLEEP", "SW_SLEEP", "int", null, data.get("SW_SLEEP"));

        panel.addCategory("obj_objinfo", "Object settings");
        panel.addField("l_id", "Object ID", "int", null, data.get("l_id"));
        
        panel.addField("ViewGroupId", "View group ID", "int", null, data.get("ViewGroupId"));
        panel.addField("CommonPath_ID", "Path ID", "int", null, data.get("CommonPath_ID"));
        panel.addField("ClippingGroupId", "Clipping group ID", "int", null, data.get("ClippingGroupId"));
        panel.addField("GroupId", "Group ID", "int", null, data.get("GroupId"));
        panel.addField("DemoGroupId", "Demo group ID", "int", null, data.get("DemoGroupId"));
        
        panel.addCategory("obj_mappartsinfo", "Map part settings");
        panel.addField("MoveConditionType", "Move condition", "int", null, data.get("MoveConditionType"));
        panel.addField("RotateSpeed", "Rotate speed", "int", null, data.get("RotateSpeed"));
        panel.addField("RotateAngle", "Rotate angle", "int", null, data.get("RotateAngle"));
        panel.addField("RotateAxis", "Rotate axis", "int", null, data.get("RotateAxis"));
        panel.addField("RotateAccelType", "Rotate accel type", "int", null, data.get("RotateAccelType"));
        panel.addField("RotateStopTime", "Rotate stop time", "int", null, data.get("RotateStopTime"));
        panel.addField("RotateType", "Rotate type", "int", null, data.get("RotateType"));
        panel.addField("ShadowType", "Shadow type", "int", null, data.get("ShadowType"));
        panel.addField("SignMotionType", "Rotate axis", "int", null, data.get("SignMotionType"));
        panel.addField("PressType", "Press type", "int", null, data.get("PressType"));
        panel.addField("FarClip", "Clip distance", "int", null, data.get("FarClip"));
        if (zone.gameMask == 2)
            panel.addField("ParentId", "Parent ID", "int", null, data.get("ParentId"));

        panel.addCategory("obj_misc", "Misc. settings");
        
        panel.addField("CameraSetId", "CameraSetId", "int", null, data.get("CameraSetId"));
        panel.addField("CastId", "CastId", "int", null, data.get("CastId"));
        panel.addField("ShapeModelNo", "ShapeModelNo", "int", null, data.get("ShapeModelNo"));
        if (zone.gameMask == 2)
        {
            panel.addField("MapParts_ID", "MapParts_ID", "int", null, data.get("MapParts_ID"));
            panel.addField("Obj_ID", "Obj_ID", "int", null, data.get("Obj_ID"));
        }
    }
    
    @Override
    public String toString()
    {
        String l = layer.equals("common") ? "Common" : "Layer"+layer.substring(5).toUpperCase();
        return dbInfo.name + " [" + l + "]";
    }
    
    /*
    Seems some have limited values. Need to get a Drop-Down list for these values...
[1A691A84]=Activation (0=normal, 1=stepped on)
[D1DFFB8C]=Speed (higher=faster)
[D0E17418]=Degrees of rotation per stop (higher=more rotation)
[7217EFBC]=Axis of rotation (0=X, 1=Y, 2=Z)
[86AC8907]=Smoothness (0=sharp start/end, 1=smooth start/end)
[4558808A]=Wait time (higher=more wait between rotations)
[72209755]=Rotation controller (0=stops after rotating, 1=swings back and forth, 2=rotates in one direction)
[39FCC89A]=??? (2 for FlipTurnMoveStep)
[6D3E35CD]=Shaking (0=none, 1=shakes before each rotation)
[4137EDFD]=??? (Seems to be an integer)
*/
}
