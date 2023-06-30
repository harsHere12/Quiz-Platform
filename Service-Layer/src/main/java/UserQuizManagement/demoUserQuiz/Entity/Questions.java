package UserQuizManagement.demoUserQuiz.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionId;
    private Long subjectId; // mapping required 1:N (one subject many question)
    private String description;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctAnswer;
    public Questions(Long subjectId, String description, String option1, String option2, String option3, String option4, int correctAnswer) {
        this.subjectId = subjectId;
        this.description = description;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
    }
    public Questions() {
    }
}
