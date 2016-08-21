package practice_5.Set1;

/**
 * Created by swanta on 22.06.16.
 */
public class ElectionVoter implements Comparable{
    private final String name;
    private final String address;

    public ElectionVoter(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElectionVoter that = (ElectionVoter) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return address != null ? address.equals(that.address) : that.address == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ElectionVoter{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        // используется в отсутствие компратора
        int result;
        ElectionVoter anotherVoter = (ElectionVoter) o;
        result = address.compareTo(anotherVoter.getAddress());
        if (result == 0) {
            result = name.compareTo(anotherVoter.getName());
        }
        return result;
    }
}
