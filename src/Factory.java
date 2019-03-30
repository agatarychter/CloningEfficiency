import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Factory {
    private Random random;
    private final String [] sectionNames = {"ESN PO", "ESN UJ", "ESN PWr", "ESN Lisboa", "ESN Prague", "ESN UE" };
    private final String [] countries = {"Poland", "Sweden", "Portugal", "Spain"};
    private final String [] cities = {"Lisboa", "Cracow", "Wroclaw","Lisboa", "Opole"};
    private final String [] fields = {"PR", "ESNcard", "Discover Europe", "FR", "HR"};
    private final String [] firstNames = {"Jao", "Magda", "Milena", "Jesus", "John"};
    private final String [] lastNames = {"Rodriguez", "Caro", "Zielinski", "May"};
    private final String [] functions = {"ESNcard coordinator", "average member", "newbie", "PR coordinator","president", "treasurer"};

    public Factory ()
    {
        random = new Random();
    }

    public List<Section> generateSections(int sectionsSize, int workgroupsSize, int membersSize)
    {
        List<Section> sections = new ArrayList<>(sectionsSize);
        for(int i=0;i<sectionsSize;i++) {
            Section section = new Section(sectionNames[random.nextInt(sectionNames.length)], countries[random.nextInt(countries.length)], cities[random.nextInt(cities.length)]);
            section.setWorkgroups(generateWorkgroups(workgroupsSize,membersSize));
            sections.add(section);
        }
        return sections;
    }

    public List<Workgroup> generateWorkgroups(int workgroupsSize, int membersSize)
    {
        List<Workgroup> workgroups = new ArrayList<>(workgroupsSize);
        for(int i=0;i<workgroupsSize;i++)
        {
            Workgroup workgroup = new Workgroup(fields[random.nextInt(fields.length)]);
            workgroup.setMembers(generateMembers(membersSize));
            workgroups.add(workgroup);


        }
        return workgroups;

    }

    public List<Member> generateMembers(int number)
    {
        List<Member> members = new ArrayList<>(number);
        for(int i=0;i<number;i++)
        {
            members.add(new Member(firstNames[random.nextInt(firstNames.length)], lastNames[random.nextInt(lastNames.length)],functions[random.nextInt(functions.length)]));

        }
        return members;
    }

}
