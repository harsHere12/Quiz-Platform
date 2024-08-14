package UserQuizManagement.demoUserQuiz.Utils;


import UserQuizManagement.demoUserQuiz.Entity.Topic;
import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Embeddable
public class ScoreId implements Serializable  {
    private Long userId ;
    private Long topicId ;

    //equals() && hashcode()

    public boolean equals(Object o ){
        if(this == o) return true ;
        if(o == null ) return false ;
        if(getClass() != o.getClass()) return false ;
        ScoreId thatId = (ScoreId) o ;

        return Objects.equals(this.userId,thatId.userId) && Objects.equals(topicId,thatId.topicId) ;

    }

    public int hashCode(){
        return Objects.hash(userId,topicId) ;
    }


}
