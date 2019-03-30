import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Section implements Cloneable, Serializable {
    private String name;
    private String country;
    private String city;
    private List<Workgroup> workgroups;

    public Section(String name, String country, String city) {
        this.name = name;
        this.country = country;
        this.city = city;
        workgroups = new ArrayList<>();
    }

    public Section(Section section)
    {
        this.name = new String(section.name);
        this.country = new String(section.country);
        this.city = new String(section.city);
        workgroups = new ArrayList<>(section.workgroups.size());
        for(Workgroup workgroup: section.workgroups)
        {
           workgroups.add(new Workgroup(workgroup));
        }
    }

    public Object clone() {
        Section section = null;
        try {
            section = (Section) super.clone();
            section.name = new String(this.name);
            section.city  = new String(this.city);
            section.country = new String(this.country);
            List<Workgroup> clonedWorkgroups = new ArrayList<>(this.workgroups.size());
            for(Workgroup workgroup: workgroups)
            {
                clonedWorkgroups.add((Workgroup) workgroup.clone());
            }
            section.workgroups = clonedWorkgroups;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return section;
    }
    public Section cloneWithLib()
    {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), Section.class);
    }

    public Section copy() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.obj"));
             ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.obj"))) {
            out.writeObject(this);
            return (Section) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toString()
    {
        return "Section: " + name + "\t"  + country + "\t" + city;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Workgroup> getWorkgroups() {
        return workgroups;
    }

    public void setWorkgroups(List<Workgroup> workgroups) {
        this.workgroups = workgroups;
    }

    public boolean equals(Object object)
    {
        if(object instanceof Section)
        {
            if(!this.name.equals(((Section) object).name) || !this.country.equals(((Section) object).country)
                || !this.city.equals(((Section) object).city))
                return false;
            for(int i=0;i<this.workgroups.size();i++)
            {
                if(!this.workgroups.get(i).equals(((Section) object).workgroups.get(i)))
                    return false;
            }
            return true;
        }
        return false;
    }
}
