package funkyflamingos.bisonfit.persistence;

import java.util.ArrayList;

import funkyflamingos.bisonfit.dso.ExerciseHeader;

public interface IExerciseLookupPersistence {
    ArrayList<ExerciseHeader> getAllExerciseHeaders();
}
