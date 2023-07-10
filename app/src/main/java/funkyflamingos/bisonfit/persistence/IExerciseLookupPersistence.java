package funkyflamingos.bisonfit.persistence;

import java.util.ArrayList;

import funkyflamingos.bisonfit.dso.ExerciseHeader;

public interface IExerciseLookupPersistence {
    public ArrayList<ExerciseHeader> getAllExerciseHeaders();
}
