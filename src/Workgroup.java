import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Workgroup implements Cloneable, Serializable {
    private String field;
    private  List<Member> members;

    public Workgroup( String field) {
        this.field = field;
        this.members = new ArrayList<>();
    }
    public Workgroup(Workgroup workgroup)
    {
        this.field = new String(workgroup.field);
        members = new ArrayList<>(workgroup.members.size());
        for(Member member: workgroup.members)
        {
            this.members.add(new Member(member));
        }

    }

    public Object clone() {
        Workgroup workgroup = null;
        try {
            workgroup = (Workgroup) super.clone();
            workgroup.field = new String(this.field);
            List<Member> clonedMembers = new ArrayList<>(this.members.size());
            for(Member member: members)
            {
                clonedMembers.add((Member) member.clone());
            }
            workgroup.members = clonedMembers;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    return workgroup;
    }

    public Workgroup cloneWithLib()
    {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), Workgroup.class);
    }

    public Workgroup copy() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.obj"));
             ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.obj"))) {
            out.writeObject(this);
            return (Workgroup) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toString()
    {
        return "Workgroup: " + field + "\t";
    }


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public boolean equals(Object object)
    {
        if(object instanceof Workgroup)
        {
            if(!this.field.equals(((Workgroup) object).field))
                return false;
            for(int i=0;i<this.members.size();i++)
            {
                if(!this.members.get(i).equals(((Workgroup) object).members.get(i)))
                    return false;
            }
            return true;

        }
        return false;
    }


}
