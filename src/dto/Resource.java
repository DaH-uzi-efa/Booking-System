package dto;

public record Resource(int id, String name, String location) {

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
