package funkyflamingos.bisonfit.persistence;

import java.util.List;

import funkyflamingos.bisonfit.dso.Exercise;

public interface IExerciseLookupPersistence {

    List<Exercise> getExerciseList();
}
