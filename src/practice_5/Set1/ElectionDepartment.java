package practice_5.Set1;

import java.util.*;

/**
 * Created by swanta on 22.06.16.
 */
public class ElectionDepartment {
    static Set<ElectionVoter> voters = new TreeSet<>(new Comparator<ElectionVoter>() {
        @Override
        public int compare(ElectionVoter o1, ElectionVoter o2) {
            //при наличии компаратора compareTo класса не используется
            int result;
            result = o1.getAddress().compareTo(o2.getAddress());
            if (result == 0) {
                result = o1.getName().compareTo(o2.getName());
            }
            return result;
        }
    });

    public static void main(String[] args) {
        String[] names = {
                "Adam Stewart",
                "Cothline Mann",
                "Cathrine Johns",
                "Monna Lisa",
                "Laura Schwartz"};
        String[] addresses = {
                "Central Park",
                "Wall street",
                "Big Ben"};
        for (String name :
                names) {
            for (String address :
                    addresses) {
                voters.add(new ElectionVoter(name, address));
            }
        }

        addVote(new ElectionVoter(names[1]+ "Junior", addresses[1]));
        addVote(new ElectionVoter(names[1], addresses[1]));
    }

    public static void addVote (ElectionVoter voter) {
        if (voters.contains(voter)) {
            System.out.println(voter + " уже есть в списке!");
        } else {
            System.out.println(voter + " добавлен");
            voters.add(voter);
        }
    }
}
