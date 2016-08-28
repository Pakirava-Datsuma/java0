package StreamApi;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by swanta on 27.08.16.
 */
class StreamApiDemo{

    static List<B> getBList(List<A> aList, boolean useStreamAPI){
        List<B> bList;
        if (useStreamAPI) {
            bList = aList.stream()
                    .map(A::getB)
                    .collect(Collectors.toList());
        }
        else {
            bList = new ArrayList<>();
            for (A a : aList) {
                bList.add(a.getB());
            }
        }
        return bList;
    }

    public static void main(String[] args) throws Exception{
        ObjectOutputStream oos = new ObjectOutputStream(System.out);

        List<A> aList = new ArrayList<>();
        aList.add(new A());

        System.out.println("\n\n with Stream API:\n  ");
        oos.writeObject(getBList(aList, true));
//        System.out.println("-------------");
        System.out.println("\n\n with for...each:\n  ");
        oos.writeObject(getBList(aList, false));
    }

}
