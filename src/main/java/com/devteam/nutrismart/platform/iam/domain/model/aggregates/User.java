package com.devteam.nutrismart.platform.iam.domain.model.aggregates;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Agregado raíz del contexto IAM que representa a un usuario registrado en la plataforma NutriSmart.
 * <p>
 * Encapsula toda la información del perfil nutricional, metas de salud, datos biométricos
 * y credenciales de autenticación. Los objetivos metabólicos (calorías, proteínas, etc.)
 * se calculan automáticamente a partir de los datos biométricos y la actividad física.
 * </p>
 */
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private EmailAddress email;
    private String passwordHash;
    private UserGoal goal;
    private Double weight;
    private Double height;
    private ActivityLevel activityLevel;
    private UserPlan plan;
    private List<DietaryRestriction> restrictions;
    private List<String> medicalConditions;
    private Double dailyCalorieTarget;
    private Double proteinTarget;
    private Double carbsTarget;
    private Double fatTarget;
    private Double fiberTarget;
    private Integer streak;
    private Integer consecutiveMisses;
    private LocalDate birthday;
    private String biologicalSex;
    private LocalDate createdAt;
    private String homeCity;
    private LocalDate goalStartedAt;
    private LocalDate planExpiresAt;

    private User() {}

    /**
     * Crea un nuevo usuario con sus datos biométricos y de perfil, calculando automáticamente
     * los objetivos metabólicos diarios (calorías, proteínas, carbohidratos, grasas y fibra).
     *
     * @param firstName       nombre del usuario
     * @param lastName        apellido del usuario
     * @param email           dirección de correo electrónico validada
     * @param passwordHash    contraseña ya hasheada antes de pasarla al agregado
     * @param goal            objetivo nutricional principal (pérdida de peso o ganancia muscular)
     * @param weight          peso en kilogramos
     * @param height          altura en centímetros
     * @param activityLevel   nivel de actividad física para el cálculo del TDEE
     * @param plan            plan de suscripción inicial
     * @param restrictions    lista de restricciones dietéticas del usuario
     * @param medicalConditions condiciones médicas relevantes declaradas
     * @param birthday        fecha de nacimiento para el cálculo de la edad
     * @param biologicalSex   sexo biológico ("male" o "female") para el cálculo del BMR
     * @param homeCity        ciudad de residencia
     * @return instancia nueva de {@link User} con objetivos metabólicos calculados
     */
    public static User create(
            String firstName,
            String lastName,
            EmailAddress email,
            String passwordHash,
            UserGoal goal,
            Double weight,
            Double height,
            ActivityLevel activityLevel,
            UserPlan plan,
            List<DietaryRestriction> restrictions,
            List<String> medicalConditions,
            LocalDate birthday,
            String biologicalSex,
            String homeCity
    ) {
        if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("First name cannot be blank");
        if (lastName == null || lastName.isBlank()) throw new IllegalArgumentException("Last name cannot be blank");

        User user = new User();
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.passwordHash = passwordHash;
        user.goal = goal;
        user.weight = weight;
        user.height = height;
        user.activityLevel = activityLevel;
        user.plan = null;
        user.restrictions = restrictions != null ? restrictions : new ArrayList<>();
        user.medicalConditions = medicalConditions != null ? medicalConditions : new ArrayList<>();
        user.birthday = birthday;
        user.biologicalSex = biologicalSex;
        user.homeCity = homeCity;
        user.streak = 0;
        user.consecutiveMisses = 0;
        user.createdAt = LocalDate.now();
        user.goalStartedAt = LocalDate.now();
        user.calculateMetabolicTargets();
        return user;
    }

    /**
     * Reconstituye un usuario existente desde el almacenamiento persistente sin recalcular
     * los objetivos metabólicos, usando los valores ya guardados en base de datos.
     *
     * @param id                   identificador único del usuario
     * @param firstName            nombre del usuario
     * @param lastName             apellido del usuario
     * @param email                dirección de correo electrónico
     * @param passwordHash         hash de la contraseña almacenada
     * @param goal                 objetivo nutricional
     * @param weight               peso en kilogramos
     * @param height               altura en centímetros
     * @param activityLevel        nivel de actividad física
     * @param plan                 plan de suscripción actual
     * @param restrictions         restricciones dietéticas
     * @param medicalConditions    condiciones médicas declaradas
     * @param dailyCalorieTarget   objetivo de calorías diarias
     * @param proteinTarget        objetivo de proteínas en gramos
     * @param carbsTarget          objetivo de carbohidratos en gramos
     * @param fatTarget            objetivo de grasas en gramos
     * @param fiberTarget          objetivo de fibra en gramos
     * @param streak               racha actual de días de cumplimiento
     * @param consecutiveMisses    fallos consecutivos en el seguimiento
     * @param birthday             fecha de nacimiento
     * @param biologicalSex        sexo biológico
     * @param createdAt            fecha de creación de la cuenta
     * @param homeCity             ciudad de residencia
     * @param goalStartedAt        fecha en que inició el objetivo activo
     * @param planExpiresAt        fecha de expiración del plan de suscripción
     * @return instancia reconstituida de {@link User}
     */
    public static User rehydrate(
            Long id,
            String firstName,
            String lastName,
            EmailAddress email,
            String passwordHash,
            UserGoal goal,
            Double weight,
            Double height,
            ActivityLevel activityLevel,
            UserPlan plan,
            List<DietaryRestriction> restrictions,
            List<String> medicalConditions,
            Double dailyCalorieTarget,
            Double proteinTarget,
            Double carbsTarget,
            Double fatTarget,
            Double fiberTarget,
            Integer streak,
            Integer consecutiveMisses,
            LocalDate birthday,
            String biologicalSex,
            LocalDate createdAt,
            String homeCity,
            LocalDate goalStartedAt,
            LocalDate planExpiresAt
    ) {
        User user = new User();
        user.id = id;
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.passwordHash = passwordHash;
        user.goal = goal;
        user.weight = weight;
        user.height = height;
        user.activityLevel = activityLevel;
        user.plan = plan;
        user.restrictions = restrictions != null ? restrictions : new ArrayList<>();
        user.medicalConditions = medicalConditions != null ? medicalConditions : new ArrayList<>();
        user.dailyCalorieTarget = dailyCalorieTarget;
        user.proteinTarget = proteinTarget;
        user.carbsTarget = carbsTarget;
        user.fatTarget = fatTarget;
        user.fiberTarget = fiberTarget;
        user.streak = streak;
        user.consecutiveMisses = consecutiveMisses;
        user.birthday = birthday;
        user.biologicalSex = biologicalSex;
        user.createdAt = createdAt;
        user.homeCity = homeCity;
        user.goalStartedAt = goalStartedAt;
        user.planExpiresAt = planExpiresAt;
        return user;
    }

    private void calculateMetabolicTargets() {
        long age = (birthday != null)
                ? ChronoUnit.YEARS.between(birthday, LocalDate.now())
                : 25L;
        boolean isFemale = biologicalSex != null && biologicalSex.equalsIgnoreCase("female");

        double bmr;
        if (isFemale) {
            bmr = (10 * weight) + (6.25 * height) - (5 * age) - 161;
        } else {
            bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5;
        }

        double tdee = bmr * activityLevel.getMultiplier();

        double dailyCalories;
        double proteinPct;
        double carbsPct;
        double fatPct;

        if (goal == UserGoal.WEIGHT_LOSS) {
            dailyCalories = tdee - 500;
            proteinPct = 0.30;
            carbsPct = 0.40;
            fatPct = 0.30;
        } else {
            dailyCalories = tdee + 300;
            proteinPct = 0.35;
            carbsPct = 0.45;
            fatPct = 0.20;
        }

        this.dailyCalorieTarget = dailyCalories;
        this.proteinTarget = (dailyCalories * proteinPct) / 4;
        this.carbsTarget = (dailyCalories * carbsPct) / 4;
        this.fatTarget = (dailyCalories * fatPct) / 9;
        this.fiberTarget = isFemale ? 25.0 : 38.0;
    }

    /**
     * Actualiza el perfil del usuario con nuevos datos biométricos y preferencias,
     * y recalcula los objetivos metabólicos en función de los valores actualizados.
     *
     * @param firstName        nuevo nombre del usuario
     * @param lastName         nuevo apellido del usuario
     * @param email            nueva dirección de correo electrónico
     * @param goal             nuevo objetivo nutricional
     * @param weight           nuevo peso en kilogramos
     * @param height           nueva altura en centímetros
     * @param activityLevel    nuevo nivel de actividad física
     * @param restrictions     nueva lista de restricciones dietéticas
     * @param medicalConditions nueva lista de condiciones médicas
     * @param homeCity         nueva ciudad de residencia
     * @param birthday         nueva fecha de nacimiento (se aplica solo si no es nula)
     * @param biologicalSex    nuevo sexo biológico (se aplica solo si no es nulo ni vacío)
     * @param planExpiresAt    nueva fecha de expiración del plan de suscripción
     */
    public void updateProfile(
            String firstName,
            String lastName,
            EmailAddress email,
            UserGoal goal,
            Double weight,
            Double height,
            ActivityLevel activityLevel,
            List<DietaryRestriction> restrictions,
            List<String> medicalConditions,
            String homeCity,
            LocalDate birthday,
            String biologicalSex,
            LocalDate planExpiresAt
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.goal = goal;
        this.weight = weight;
        this.height = height;
        this.activityLevel = activityLevel;
        this.restrictions = restrictions != null ? restrictions : new ArrayList<>();
        this.medicalConditions = medicalConditions != null ? medicalConditions : new ArrayList<>();
        this.homeCity = homeCity;
        if (birthday != null) this.birthday = birthday;
        if (biologicalSex != null && !biologicalSex.isBlank()) this.biologicalSex = biologicalSex;
        this.planExpiresAt = planExpiresAt;
        calculateMetabolicTargets();
    }

    /**
     * Cambia la contraseña del usuario reemplazando el hash almacenado.
     * El hash debe estar codificado antes de invocar este método.
     *
     * @param newPasswordHash nuevo hash de contraseña codificado
     * @throws IllegalArgumentException si el hash proporcionado es nulo o vacío
     */
    public void changePassword(String newPasswordHash) {
        if (newPasswordHash == null || newPasswordHash.isBlank()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }
        this.passwordHash = newPasswordHash;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public EmailAddress getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public UserGoal getGoal() { return goal; }
    public Double getWeight() { return weight; }
    public Double getHeight() { return height; }
    public ActivityLevel getActivityLevel() { return activityLevel; }
    public UserPlan getPlan() { return plan; }
    public List<DietaryRestriction> getRestrictions() { return restrictions; }
    public List<String> getMedicalConditions() { return medicalConditions; }
    public Double getDailyCalorieTarget() { return dailyCalorieTarget; }
    public Double getProteinTarget() { return proteinTarget; }
    public Double getCarbsTarget() { return carbsTarget; }
    public Double getFatTarget() { return fatTarget; }
    public Double getFiberTarget() { return fiberTarget; }
    public Integer getStreak() { return streak; }
    public Integer getConsecutiveMisses() { return consecutiveMisses; }
    public LocalDate getBirthday() { return birthday; }
    public String getBiologicalSex() { return biologicalSex; }
    public LocalDate getCreatedAt() { return createdAt; }
    public String getHomeCity() { return homeCity; }
    public LocalDate getGoalStartedAt() { return goalStartedAt; }
    public LocalDate getPlanExpiresAt() { return planExpiresAt; }
}
