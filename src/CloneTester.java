
import java.util.ArrayList;
import java.util.List;

public class CloneTester {
    private List<Section> sections;
    private Factory factory;

    public CloneTester()
    {
        factory = new Factory();
        sections = new ArrayList<>();
    }

    private void fillData(int sectionsSize, int workgroupsSize, int membersSize)
    {
        sections.addAll(factory.generateSections(sectionsSize,workgroupsSize,membersSize ));
    }

    public boolean checkCopySection(Section original, Section copy)
    {
        if(original==copy)
            return false;
        if(!original.equals(copy))
            return false;
        String city = original.getCity();
        original.setCity("New City");
        if(original.equals(copy)){
            original.setCity(city);
            return false;

        }
        if(original.getCity()==copy.getCity() || original.getCountry()==copy.getCountry() || original.getName()==copy.getName())
        {
            return false;
        }
        original.setCity(city);
        List<Workgroup> orignalWorkgroups = original.getWorkgroups();
        List<Workgroup> copiedWorkgroups = copy.getWorkgroups();

        if(orignalWorkgroups==copiedWorkgroups) {
            return false;
        }
        for(int i=0;i<orignalWorkgroups.size();i++)
        {
            if(orignalWorkgroups.get(i)==copiedWorkgroups.get(i))
                return false;
        }
        original.setWorkgroups(new ArrayList<>());
        if(original.getWorkgroups()==copy.getWorkgroups())
        {
            original.setWorkgroups(orignalWorkgroups);
            return false;
        }
        original.setWorkgroups(orignalWorkgroups);
        return true;
    }

    public boolean checkCopyWorkgroup(Workgroup original, Workgroup copy)
    {
        if(original==copy)
            return false;
        if(!original.equals(copy))
            return false;
        String field = original.getField();
        original.setField("new field");
        if(original.getField().equals(copy.getField())) {
            original.setField(field);
            return false;
        }
        original.setField(field);
        List<Member> originalMembers = original.getMembers();
        List<Member> copiedMembers = copy.getMembers();
        if(originalMembers==copiedMembers)
            return false;
        for(int i=0;i<originalMembers.size();i++)
        {
            if(originalMembers.get(i)==copiedMembers.get(i))
                return false;
        }
        original.setMembers(new ArrayList<>());
        if(original.getMembers()==copy.getMembers()) {
            original.setMembers(originalMembers);
            return false;
        }
        original.setMembers(originalMembers);
        Member member = original.getMembers().get(0);
        original.getMembers().set(0,new Member("Mike","Johnson","newbie"));
        if(original.equals(copy)) {
            original.getMembers().set(0,member);
            return false;
        }
        original.getMembers().set(0,member);

        return true;
    }

    public boolean checkCopyMember(Member original, Member copy)
    {
        if(original==copy)
            return false;
        if(original.getFunction()==copy.getFunction() || original.getFirstName()==copy.getFunction() || original.getLastName()==copy.getLastName())
            return false;
        if(!original.equals(copy))
            return false;
        return true;

    }

    public List<Section> makeCopiesWithConstructor()
    {
        List<Section> copiedSections = new ArrayList<>(sections.size());
        for(Section section: sections)
        {
            copiedSections.add(new Section(section));
        }
        return copiedSections;
    }

    public List<Section> makeCopiesWithClone()
    {
        List<Section> copiedSections = new ArrayList<>(sections.size());
        for(Section section: sections)
        {
            copiedSections.add((Section) section.clone());
        }
        return copiedSections;

    }

    public List<Section> makeCopiesWithGson()
    {
        List<Section> copiedSections = new ArrayList<>(sections.size());
        for(Section section: sections)
        {
            copiedSections.add(section.cloneWithLib());
        }
        return copiedSections;
    }

    public List<Section> makeCopiesWithSerialization()
    {
        List<Section> copiedSections = new ArrayList<>(sections.size());
        for(Section section: sections)
        {
            copiedSections.add(section.copy());
        }
        return copiedSections;
    }

    public static void main(String [] args) {
        CloneTester cloneTester = new CloneTester();
        cloneTester.fillData(1,100,10);
        long time0 = System.nanoTime();
        cloneTester.makeCopiesWithConstructor();
        long timeEnd = System.nanoTime();
        System.out.println("Copying 1000 with copy constructor:\t"+(timeEnd-time0));

        time0 = System.nanoTime();
        cloneTester.makeCopiesWithClone();
        timeEnd = System.nanoTime();
        System.out.println("Copying 1000 with clone method:\t" + (timeEnd-time0));

        time0 = System.nanoTime();
        cloneTester.makeCopiesWithGson();
        timeEnd = System.nanoTime();
        System.out.println("Copying 1000 with deep clone library method:\t" + (timeEnd-time0));

        time0 = System.nanoTime();
        cloneTester.makeCopiesWithSerialization();
        timeEnd = System.nanoTime();
        System.out.println("Copying 1000 with serialization:\t" + (timeEnd-time0));
//
        Section originalSection = cloneTester.sections.get(0);
        Workgroup originalWorkgroup = originalSection.getWorkgroups().get(0);
        Member originalMember = originalWorkgroup.getMembers().get(0);

//
        //copy constructor
        Section copiedSection = new Section(originalSection);
        System.out.println(cloneTester.checkCopySection(originalSection,copiedSection));

        Workgroup copiedWorkgroup = new Workgroup(originalWorkgroup);
        System.out.println(cloneTester.checkCopyWorkgroup(originalWorkgroup,copiedWorkgroup));

        Member copiedMember = new Member(originalMember);
        System.out.println(cloneTester.checkCopyMember(originalMember,copiedMember));

        //clone method
        Section clonedSection = (Section) originalSection.clone();
        System.out.println(cloneTester.checkCopySection(originalSection,clonedSection));

        Workgroup clonedWorkgroup = (Workgroup) originalWorkgroup.clone();
        System.out.println(cloneTester.checkCopyWorkgroup(originalWorkgroup,clonedWorkgroup));

        Member clonedMember = (Member) originalMember.clone();
        System.out.println(cloneTester.checkCopyMember(originalMember,clonedMember));

        //gson clone
        Section gsonCopySection = originalSection.cloneWithLib();
        System.out.println(cloneTester.checkCopySection(originalSection,gsonCopySection));

        Workgroup gsonCopyWorkgroup = originalWorkgroup.cloneWithLib();
        System.out.println(cloneTester.checkCopyWorkgroup(originalWorkgroup,gsonCopyWorkgroup));

        Member gsonCopyMember = originalMember.cloneWithLib();
        System.out.println(cloneTester.checkCopyMember(originalMember,gsonCopyMember));

        //serialization
        Section serializationCopySection = originalSection.copy();
        System.out.println(cloneTester.checkCopySection(originalSection,serializationCopySection));

        Workgroup serializationWorkgroup = originalWorkgroup.copy();
        System.out.println(cloneTester.checkCopyWorkgroup(originalWorkgroup,serializationWorkgroup));

        Member serializationMember = originalMember.cloneWithLib();
        System.out.println(cloneTester.checkCopyMember(originalMember,serializationMember));



    }
}
