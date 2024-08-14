package UserQuizManagement.demoUserQuiz.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor



public class User {
    @Id
    @SequenceGenerator(name = "user_sequence" , sequenceName = "user_sequence", initialValue = 1 , allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "user_sequence")
    private Long userId;
    @Column(name = "user_name")
    private String userName ;
    @Column(name = "user_phone_no")
    private String userPhoneNo;
    @Column(name="user_email")
    private String userEmail;
    @Column(name="user_password")
    private String userPassword;
    @JsonFormat(pattern = "dd-mm-yyyy")
    @Column(name = "Date")
    private Date dob;
    @Column(name = "security_question")
    private String question;
    @Column(name = "security_answer")
    private String answer;

    @Column(name = "roles")
    private  String roles;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "user" , fetch = FetchType.LAZY)
    private List<Score > userScores ;

    public User(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public User(Long userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public User(Long userId, String userName, String userEmail, String roles) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.roles = roles;
    }

    public void setUserScores(List<Score> userScores) {
        this.userScores = userScores;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(userName, user.userName) && Objects.equals(userEmail, user.userEmail)  && Objects.equals(roles, user.roles) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userPhoneNo, userEmail, userPassword, dob, question, answer, roles, userScores);
    }
}



