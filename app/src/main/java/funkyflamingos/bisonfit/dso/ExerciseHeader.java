package funkyflamingos.bisonfit.dso;

public class ExerciseHeader {

    private String name;
    private int id;
    private int setCount;
    private boolean selected;

    public ExerciseHeader(String name, int id) {
        this.name = name;
        this.id = id;
        this.setCount = 1;
        selected = false;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void incrementSet() //this should also edit the database as well?
    {
        setCount++;
    }

    public void decrementSet()
    {
        setCount = Math.max(1, setCount - 1);
    }

    public String getSetCount()
    {
        return "x" + setCount;
    }
    public String getSetCountText()
    {
        return getSetCount() + " SET" + (setCount > 1 ? "S" : "");
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void toggleSelected()
    {
        selected = !selected;
    }
}

