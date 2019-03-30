import com.google.gson.Gson;

import java.io.*;

public class Member implements Cloneable, Serializable {
    private String firstName;
    private String lastName;
    private String function;

    public Member( String firstName, String lastName, String function) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.function = function;
    }

    public Member(Member member)
    {
        this.firstName = new String(member.firstName);
        this.lastName = new String(member.lastName);
        this.function = new String(member.function);
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }


    public Object clone() {
        Member member = null;
        try {
            member = (Member)super.clone();
            member.firstName = new String(firstName);
            member.lastName = new String(lastName);
            member.function = new String(function);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return member;
    }

    public Member cloneWithLib()
    {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), Member.class);
    }

    public Member copy() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.obj"));
             ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.obj"))) {
            out.writeObject(this);
            return (Member) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toString()
    {
        return "Member: " + firstName + " " + lastName + "\t" + function;
    }

    public boolean equals(Object object)
    {
        if(object instanceof Member)
        {
            return ((Member) object).firstName.equals(this.firstName) && ((Member) object).lastName.equals(this.lastName) && ((Member) object).function.equals(this.function);
        }
        return false;
    }

}
